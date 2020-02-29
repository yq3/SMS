package UI.teacher;

import Bean.Score;
import Service.ScoreService;
import UI.CommonTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScoreEditPanel extends JPanel {

    private CommonTable table;
    private DefaultTableModel model;
    private JLabel label;
    private JDialog dialog;
    private JTextField studentIdField;
    private JTextField studentNameField;
    private JTextField chineseField;
    private JTextField mathField;
    private JTextField englishField;
    private JButton okButton;
    private Score score;
    private ScoreService scoreService;

    public ScoreEditPanel() {

        scoreService = new ScoreService();

        Object[] col = {"学号", "姓名", "语文", "数学", "英语", "总分"};
        model = new DefaultTableModel(new Object[scoreService.getScoreRows()][6], col);
        table = new CommonTable(model);
        scoreService.fillScoreTable(table);

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
                                score = new Score();
                                score.setStudentId(Integer.parseInt(studentIdField.getText().trim()));
                                score.setStudentName(studentNameField.getText().trim());
                                score.setChinese(Integer.parseInt(chineseField.getText().trim()));
                                score.setMath(Integer.parseInt(mathField.getText().trim()));
                                score.setEnglish(Integer.parseInt(englishField.getText().trim()));
                                score.setSum(score.getChinese()+score.getMath()+score.getEnglish());

                                scoreService.insertService(score);

                                Object[] rowData = {
                                        score.getStudentId(),
                                        score.getStudentName(),
                                        score.getChinese(),
                                        score.getMath(),
                                        score.getEnglish(),
                                        score.getSum()
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
                        scoreService.deleteService((Integer) table.getValueAt(table.getSelectedRow(), 0));

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
                    chineseField.setText(table.getValueAt(table.getSelectedRow(), 2).toString());
                    mathField.setText(table.getValueAt(table.getSelectedRow(), 3).toString());
                    englishField.setText(table.getValueAt(table.getSelectedRow(), 4).toString());
                    //学号不可更改
                    studentIdField.setEditable(false);
                    dialog.setVisible(true);

                    okButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            if (!isNull() && correctDataType()) {
                                score = new Score();
                                score.setStudentId(Integer.parseInt(studentIdField.getText().trim()));
                                score.setStudentName(studentNameField.getText().trim());
                                score.setChinese(Integer.parseInt(chineseField.getText().trim()));
                                score.setMath(Integer.parseInt(mathField.getText().trim()));
                                score.setEnglish(Integer.parseInt(englishField.getText().trim()));
                                score.setSum(score.getChinese()+score.getMath()+score.getEnglish());

                                scoreService.updateService(score);

                                model.setValueAt(score.getStudentName(), table.getSelectedRow(), 1);
                                model.setValueAt(score.getChinese(), table.getSelectedRow(), 2);
                                model.setValueAt(score.getMath(), table.getSelectedRow(), 3);
                                model.setValueAt(score.getEnglish(), table.getSelectedRow(), 4);
                                model.setValueAt(score.getSum(), table.getSelectedRow(), 5);

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
        JLabel chineseLabel = new JLabel("语文");
        JLabel mathLabel = new JLabel("数学");
        JLabel englishLabel = new JLabel("英语");

        studentIdField = new JTextField();
        studentNameField = new JTextField();
        chineseField = new JTextField();
        mathField = new JTextField();
        englishField = new JTextField();

        okButton = new JButton("确定");
        JButton cancelButton = new JButton("取消");

        Box hBox1 = Box.createHorizontalBox();
        Box hBox2 = Box.createHorizontalBox();
        Box hBox3 = Box.createHorizontalBox();
        Box hBox4 = Box.createHorizontalBox();
        Box hBox5 = Box.createHorizontalBox();
        Box hBox6 = Box.createHorizontalBox();
        Box vBox = Box.createVerticalBox();

        hBox1.add(studentIdLabel);
        hBox1.add(Box.createHorizontalStrut(10));
        hBox1.add(studentIdField);

        hBox2.add(studentNameLabel);
        hBox2.add(Box.createHorizontalStrut(10));
        hBox2.add(studentNameField);

        hBox3.add(chineseLabel);
        hBox3.add(Box.createHorizontalStrut(10));
        hBox3.add(chineseField);

        hBox4.add(mathLabel);
        hBox4.add(Box.createHorizontalStrut(10));
        hBox4.add(mathField);

        hBox5.add(englishLabel);
        hBox5.add(Box.createHorizontalStrut(10));
        hBox5.add(englishField);

        hBox6.add(okButton);
        hBox6.add(Box.createHorizontalStrut(10));
        hBox6.add(cancelButton);

        vBox.add(Box.createVerticalStrut(50));
        vBox.add(hBox1);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(hBox2);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(hBox3);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(hBox4);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(hBox5);
        vBox.add(Box.createVerticalStrut(50));
        vBox.add(hBox6);

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
        return "当前记录总共" + scoreService.getScoreRows() + "条";
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
        if (chineseField.getText().trim().equals("")) {
            chineseField.setText("0");
        }
        if (mathField.getText().trim().equals("")) {
            mathField.setText("0");
        }
        if (englishField.getText().trim().equals("")) {
            englishField.setText("0");
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
        if (!isNumber(chineseField.getText().trim())) {
            b = false;
            JOptionPane.showMessageDialog(dialog, "请输入正确的成绩！", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
        if (!isNumber(mathField.getText().trim())) {
            b = false;
            JOptionPane.showMessageDialog(dialog, "请输入正确的成绩！", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
        if (!isNumber(englishField.getText().trim())) {
            b = false;
            JOptionPane.showMessageDialog(dialog, "请输入正确的成绩！", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
        return b;
    }
}
