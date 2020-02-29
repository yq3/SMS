package UI.admin;

import Bean.User;
import Service.UserService;
import UI.CommonTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserEditPanel extends JPanel {

    private DefaultTableModel model;
    private CommonTable table;
    private JLabel label;
    private JDialog dialog;
    private JTextField usernameField;
    private JComboBox<String> categoryComboBox;
    private JButton okButton;
    private User user;
    private UserService userService;

    public UserEditPanel() {

        userService = new UserService();

        Object[] col = {"用户", "类别", "已注册"};
        model = new DefaultTableModel(new Object[userService.getUserRows()][3], col);
        table = new CommonTable(model);
        userService.fillUserTable(table);

        JScrollPane upPane = new JScrollPane(table);
        upPane.setPreferredSize(new Dimension(900, 600));

        label = new JLabel(getLabelString());

        JTextField queryField = new JTextField(20);

        JButton newButton = new JButton("添加");
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
                            if (table.isSame(table, usernameField.getText().trim())) {
                                JOptionPane.showMessageDialog(
                                        dialog, "用户名与已有用户名重复！", "提示", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                user = new User();
                                user.setUsername(Integer.parseInt(usernameField.getText().trim()));
                                user.setCategory(categoryComboBox.getSelectedItem().toString());

                                userService.insertService(user);

                                Object[] rowData = {
                                        user.getUsername(),
                                        user.getCategory(),
                                        "否"
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
                        userService.deleteService((Integer) table.getValueAt(table.getSelectedRow(), 0));

                        model.removeRow(table.getSelectedRow());

                        label.setText(getLabelString());
                    }
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

        JLabel usernameLabel = new JLabel("用户");
        JLabel categoryLabel = new JLabel("类别");

        usernameField = new JTextField();

        String[] category = new String[]{null, "学生", "教师", "管理员"};
        categoryComboBox = new JComboBox<String>(category);

        okButton = new JButton("确定");
        JButton cancelButton = new JButton("取消");

        Box hBox1 = Box.createHorizontalBox();
        Box hBox2 = Box.createHorizontalBox();
        Box hBox3 = Box.createHorizontalBox();
        Box vBox = Box.createVerticalBox();

        hBox1.add(usernameLabel);
        hBox1.add(Box.createHorizontalStrut(10));
        hBox1.add(usernameField);

        hBox2.add(categoryLabel);
        hBox2.add(Box.createHorizontalStrut(10));
        hBox2.add(categoryComboBox);

        hBox3.add(okButton);
        hBox3.add(Box.createHorizontalStrut(10));
        hBox3.add(cancelButton);

        vBox.add(Box.createVerticalStrut(80));
        vBox.add(hBox1);
        vBox.add(Box.createVerticalStrut(30));
        vBox.add(hBox2);
        vBox.add(Box.createVerticalStrut(80));
        vBox.add(hBox3);

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
        return "当前记录总共" + userService.getUserRows() + "条";
    }

    //判断填写内容是否为空
    public boolean isNull() {
        boolean b = false;
        if (usernameField.getText().trim().equals("")) {
            b = true;
            JOptionPane.showMessageDialog(dialog, "请输入用户名！", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
        if (categoryComboBox.getSelectedItem() == null) {
            b = true;
            JOptionPane.showMessageDialog(dialog, "请选择类别！", "提示", JOptionPane.INFORMATION_MESSAGE);
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
        if (!isNumber(usernameField.getText().trim())) {
            b = false;
            JOptionPane.showMessageDialog(dialog, "请输入正确的用户名！", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
        return b;
    }
}
