package UI.teacher;

import Bean.Info;
import Service.InfoService;
import UI.CommonTable;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class InfoEditPanel extends JPanel {

    private CommonTable table;
    private DefaultTableModel model;
    private JLabel label;
    private JDialog dialog;
    private JTextField studentIdField;
    private JTextField studentNameField;
    private JRadioButton radioButtonMale;
    private JRadioButton radioButtonFemale;
    private JTextField majorField;
    private JComboBox<Integer> gradeComboBox;
    private DatePicker datePicker;
    private JButton okButton;
    private Info info;
    private InfoService infoService;

    public InfoEditPanel() {

        infoService = new InfoService();

        Object[] col = {"学号", "姓名", "性别", "专业", "年级", "生日"};
        model = new DefaultTableModel(new Object[infoService.getInfoRows()][6], col);
        table = new CommonTable(model);
        infoService.fillInfoTable(table);

        JScrollPane upPane = new JScrollPane(table);
        upPane.setPreferredSize(new Dimension(900, 600));

        label = new JLabel(getLabelString());

        JTextField queryField = new JTextField(20);

        JButton newButton = new JButton("添加");
        JButton editButton = new JButton("修改");
        JButton queryButton = new JButton("查询");
        JButton deleteButton = new JButton("删除");

        Box box = Box.createHorizontalBox();

        box.add(label);
        box.add(Box.createHorizontalStrut(30));
        box.add(queryField);
        box.add(Box.createHorizontalStrut(30));
        box.add(queryButton);
        box.add(Box.createHorizontalStrut(30));
        box.add(newButton);
        box.add(Box.createHorizontalStrut(30));
        box.add(editButton);
        box.add(Box.createHorizontalStrut(30));
        box.add(deleteButton);

        JPanel downPane = new JPanel();
        downPane.add(box);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, upPane, downPane);

        splitPane.setDividerSize(10);
        this.add(splitPane);

        //添加数据
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                showCustomDialog("添加");

                okButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        //判断填写内容是否为空、数据类型是否正确
                        if (!isNull() && correctDataType()) {
                            //判断学号是否有重复
                            if (table.isSame(table, studentIdField.getText().trim())) {
                                JOptionPane.showMessageDialog(
                                        dialog, "学号与已有学号重复！", "提示", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                info = new Info();
                                info.setStudentId(Integer.parseInt(studentIdField.getText().trim()));
                                info.setStudentName(studentNameField.getText().trim());
                                info.setGender(getGender());
                                info.setMajor(majorField.getText().trim());
                                info.setGrade(Integer.parseInt(gradeComboBox.getSelectedItem().toString()));
                                info.setBirth(Date.valueOf(convertDate(datePicker.getText())));

                                infoService.insertService(info);

                                Object[] rowData = {
                                        info.getStudentId(),
                                        info.getStudentName(),
                                        info.getGender(),
                                        info.getMajor(),
                                        info.getGrade(),
                                        info.getBirth()
                                };

                                model.addRow(rowData);
                                label.setText(getLabelString());
                                dialog.dispose();
                            }
                        }
                    }
                });
            }
        });

        //删除选中的数据
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //判断是否选择了数据
                if (table.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(
                            new JPanel(), "请选择数据！", "提示", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    //YES是0 NO是1
                    int result = JOptionPane.showConfirmDialog(
                            new JPanel(), "确认删除这一条记录？", "提示", JOptionPane.YES_NO_OPTION);

                    if (result == 0) {
                        infoService.deleteService((Integer) table.getValueAt(table.getSelectedRow(), 0));

                        model.removeRow(table.getSelectedRow());

                        label.setText(getLabelString());
                    }
                }
            }
        });

        //修改数据
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //判断是否选择了数据
                if (table.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(
                            new JPanel(), "请选择数据！", "提示", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    showCustomDialog("修改");

                    dialog.setVisible(false);

                    //将数据显示到文本框中
                    studentIdField.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
                    studentNameField.setText(table.getValueAt(table.getSelectedRow(), 1).toString());

                    if (table.getValueAt(table.getSelectedRow(), 2).equals("男")) {
                        radioButtonMale.setSelected(true);
                    } else {
                        radioButtonFemale.setSelected(true);
                    }

                    majorField.setText(table.getValueAt(table.getSelectedRow(), 3).toString());
                    gradeComboBox.setSelectedItem(table.getValueAt(table.getSelectedRow(), 4));

                    //string转化成localdate
                    datePicker.setDate(LocalDate.parse(table.getValueAt(table.getSelectedRow(), 5).toString(),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd")));

                    //学号不可更改
                    studentIdField.setEditable(false);

                    dialog.setVisible(true);

                    okButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            if (!isNull() && correctDataType()) {
                                info = new Info();
                                info.setStudentId(Integer.parseInt(studentIdField.getText().trim()));
                                info.setStudentName(studentNameField.getText().trim());
                                info.setGender(getGender());
                                info.setMajor(majorField.getText().trim());
                                info.setGrade(Integer.parseInt(gradeComboBox.getSelectedItem().toString()));
                                info.setBirth(Date.valueOf(convertDate(datePicker.getText())));

                                infoService.updateService(info);

                                model.setValueAt(info.getStudentName(), table.getSelectedRow(), 1);
                                model.setValueAt(info.getGender(), table.getSelectedRow(), 2);
                                model.setValueAt(info.getMajor(), table.getSelectedRow(), 3);
                                model.setValueAt(info.getGrade(), table.getSelectedRow(), 4);
                                model.setValueAt(info.getBirth(), table.getSelectedRow(), 5);

                                dialog.dispose();
                            }
                        }
                    });
                }
            }
        });

        //查询数据
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取查询内容
                String query = queryField.getText().trim();

                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
                table.setRowSorter(sorter);
                sorter.setRowFilter(RowFilter.regexFilter(query));

                if (table.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(
                            new JPanel(), "未查询到相关数据！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    sorter.setRowFilter(RowFilter.regexFilter(""));
                }
            }
        });
    }

    //自定义的小对话框
    private void showCustomDialog(String title) {
        dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setSize(250,400);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);

        JLabel studentIdLabel = new JLabel("学号");
        JLabel studentNameLabel = new JLabel("姓名");
        JLabel genderLabel = new JLabel("性别");
        JLabel majorLabel = new JLabel("专业");
        JLabel gradeLabel = new JLabel("年级");
        JLabel birthLabel = new JLabel("生日");

        studentIdField = new JTextField();
        studentNameField = new JTextField();
        majorField = new JTextField();

        radioButtonMale = new JRadioButton("男");
        radioButtonFemale = new JRadioButton("女");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButtonMale);
        buttonGroup.add(radioButtonFemale);

        Integer[] grade = new Integer[]{null, 2019, 2020};
        gradeComboBox = new JComboBox<Integer>(grade);

        datePicker = new DatePicker();

        okButton = new JButton("确定");
        JButton cancelButton = new JButton("取消");

        Box hBox1 = Box.createHorizontalBox();
        Box hBox2 = Box.createHorizontalBox();
        Box hBox3 = Box.createHorizontalBox();
        Box hBox4 = Box.createHorizontalBox();
        Box hBox5 = Box.createHorizontalBox();
        Box hBox6 = Box.createHorizontalBox();
        Box hBox7 = Box.createHorizontalBox();
        Box vBox = Box.createVerticalBox();

        hBox1.add(studentIdLabel);
        hBox1.add(Box.createHorizontalStrut(10));
        hBox1.add(studentIdField);

        hBox2.add(studentNameLabel);
        hBox2.add(Box.createHorizontalStrut(10));
        hBox2.add(studentNameField);

        hBox3.add(genderLabel);
        hBox3.add(Box.createHorizontalStrut(30));
        hBox3.add(radioButtonMale);
        hBox3.add(Box.createHorizontalStrut(20));
        hBox3.add(radioButtonFemale);
        hBox3.add(Box.createHorizontalStrut(10));

        hBox4.add(majorLabel);
        hBox4.add(Box.createHorizontalStrut(10));
        hBox4.add(majorField);

        hBox5.add(gradeLabel);
        hBox5.add(Box.createHorizontalStrut(10));
        hBox5.add(gradeComboBox);

        hBox6.add(birthLabel);
        hBox6.add(Box.createHorizontalStrut(10));
        hBox6.add(datePicker);

        hBox7.add(okButton);
        hBox7.add(Box.createHorizontalStrut(10));
        hBox7.add(cancelButton);

        vBox.add(Box.createVerticalStrut(30));
        vBox.add(hBox1);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(hBox2);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(hBox3);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(hBox4);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(hBox5);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(hBox6);
        vBox.add(Box.createVerticalStrut(50));
        vBox.add(hBox7);

        JPanel panel = new JPanel();
        panel.add(vBox);
        dialog.add(panel);
        dialog.setVisible(true);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
    }

    private String getLabelString() {
        return "当前记录总共" + infoService.getInfoRows() + "条";
    }

    //判断填写内容是否为空
    public boolean isNull() {
        boolean b = false;
        if (studentIdField.getText().trim().equals("")) {
            b = true;
            JOptionPane.showMessageDialog(dialog, "请输入学号！", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
        if (studentNameField.getText().trim().equals("")) {
            b = true;
            JOptionPane.showMessageDialog(dialog, "请输入姓名！", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
        if (getGender().equals("")) {
            b = true;
            JOptionPane.showMessageDialog(dialog, "请选择性别！", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
        if (majorField.getText().trim().equals("")) {
            b = true;
            JOptionPane.showMessageDialog(dialog, "请输入专业！", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
        if (gradeComboBox.getSelectedItem() == null) {
            b = true;
            JOptionPane.showMessageDialog(dialog, "请选择年级！", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
        if (datePicker.getText().equals("")) {
            b = true;
            JOptionPane.showMessageDialog(dialog, "请选择生日！", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
        return b;
    }

    //判断字符串是否为数字
    public boolean isNumber(String s) {
        for (int i=0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    //判断填写的数据类型是否正确
    public boolean correctDataType() {
        boolean b = true;
        if (!isNumber(studentIdField.getText().trim())) {
            b = false;
            JOptionPane.showMessageDialog(dialog, "请输入正确的学号！", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
        if (isNumber(studentNameField.getText().trim())) {
            b = false;
            JOptionPane.showMessageDialog(dialog, "请输入正确的姓名！", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
        if (isNumber(majorField.getText().trim())) {
            b = false;
            JOptionPane.showMessageDialog(dialog, "请输入正确的专业！", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
        return b;
    }

    //获取性别单选框的值
    public String getGender() {
        if (!radioButtonMale.isSelected() && !radioButtonFemale.isSelected()) {
            return "";
        } else if (radioButtonMale.isSelected()) {
            return "男";
        } else {
            return "女";
        }
    }

    //将xxxx年xx月xx日的日期格式转换为yyyy-MM-dd
    public String convertDate(String date) {
        String year;
        String month;
        String day;
        char[] chars = date.toCharArray();

        year = "" + chars[0] + chars[1] + chars[2] + chars[3];
        if (Character.toString(chars[6]).equals("月")) {
            month = "0" + chars[5];
            if (Character.toString(chars[8]).equals("日")) {
                day = "0" + chars[7];
            } else {
                day = "" + chars[7] + chars[8];
            }
        } else {
            month = "" + chars[5] + chars[6];
            if (Character.toString(chars[9]).equals("日")) {
                day = "0" + chars[8];
            } else {
                day = "" + chars[8] + chars[9];
            }
        }
        return year + "-" + month + "-" + day;
    }

}
