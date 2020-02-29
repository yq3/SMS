package UI;

import Bean.User;
import Service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> categoryComboBox;
    private int usernameInt;
    private User user;
    private UserService userService;

    public LoginFrame() {

        JLabel accountLabel = new JLabel("用户");
        JLabel passwordLabel = new JLabel("密码");
        JLabel categoryLabel = new JLabel("类别");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        String[] category = new String[]{null, "学生", "教师", "管理员"};
        categoryComboBox = new JComboBox<String>(category);

        userService = new UserService();

        JButton loginButton = new JButton("登录");
        JButton signButton = new JButton("注册");

        Box hBox1 = Box.createHorizontalBox();
        Box hBox2 = Box.createHorizontalBox();
        Box hBox3 = Box.createHorizontalBox();
        Box hBox4 = Box.createHorizontalBox();
        Box hBox5 = Box.createHorizontalBox();
        Box hBox6 = Box.createHorizontalBox();
        Box hBox7 = Box.createHorizontalBox();
        Box hBox8 = Box.createHorizontalBox();
        Box vBox = Box.createVerticalBox();

        //布局各个容器，并设置水平和竖直的间距
        hBox1.add(accountLabel);
        hBox1.add(Box.createHorizontalStrut(10));
        hBox1.add(usernameField);

        hBox2.add(Box.createHorizontalStrut(50));
        hBox2.add(hBox1, BorderLayout.CENTER);
        hBox2.add(Box.createHorizontalStrut(50));

        hBox3.add(passwordLabel);
        hBox3.add(Box.createHorizontalStrut(10));
        hBox3.add(passwordField);

        hBox4.add(Box.createHorizontalStrut(50));
        hBox4.add(hBox3, BorderLayout.CENTER);
        hBox4.add(Box.createHorizontalStrut(50));

        hBox5.add(loginButton);
        hBox5.add(Box.createHorizontalStrut(30));
        hBox5.add(signButton);

        hBox6.add(Box.createHorizontalStrut(50));
        hBox6.add(hBox5, BorderLayout.CENTER);
        hBox6.add(Box.createHorizontalStrut(50));

        hBox7.add(categoryLabel);
        hBox7.add(Box.createHorizontalStrut(30));
        hBox7.add(categoryComboBox);

        hBox8.add(Box.createHorizontalStrut(50));
        hBox8.add(hBox7, BorderLayout.CENTER);
        hBox8.add(Box.createHorizontalStrut(50));

        vBox.add(Box.createVerticalStrut(30));
        vBox.add(hBox2);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(hBox4);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(hBox8);
        vBox.add(Box.createVerticalStrut(30));
        vBox.add(hBox6);
        vBox.add(Box.createVerticalStrut(30));

        this.add(vBox, BorderLayout.CENTER);
        this.setTitle("登录");
        this.pack();                                           //先pack() 再setLocationRelativeTo(null) 就可以居中
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        signButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sign();
            }
        });
    }

    public void login() {
        if (!isNull() && correctDataType()) {
            usernameInt = Integer.parseInt(usernameField.getText().trim());
            if (userService.searchService(usernameInt) == 1) {
                if (userService.checkService(usernameInt, String.valueOf(passwordField.getPassword()))) {
                    new MainFrame(categoryComboBox.getSelectedItem().toString(), usernameInt);
                    this.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(
                            this, "用户名或密码错误！", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            if (userService.searchService(usernameInt) == 2) {
                JOptionPane.showMessageDialog(
                        this, "尚未注册！", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
            if (userService.searchService(usernameInt) == 3) {
                JOptionPane.showMessageDialog(
                        this, "用户名不存在！请联系管理员！", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public void sign() {
        if (!isNull() && correctDataType()) {
            usernameInt = Integer.parseInt(usernameField.getText().trim());
            if (userService.searchService(usernameInt) == 1) {
                JOptionPane.showMessageDialog(
                        this, "用户名已注册！", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
            if (userService.searchService(usernameInt) == 2) {
                user = new User();
                user.setUsername(usernameInt);
                user.setPassword(String.valueOf(passwordField.getPassword()));
                userService.updateService(user);
                JOptionPane.showMessageDialog(
                        this, "注册成功！请重新登录！", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
            if (userService.searchService(usernameInt) == 3) {
                JOptionPane.showMessageDialog(
                        this, "用户名不存在！请联系管理员！", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    //判断填写内容是否为空
    public boolean isNull() {
        boolean b = false;
        if (usernameField.getText().trim().equals("")) {
            b = true;
            JOptionPane.showMessageDialog(
                    this, "请输入用户名！", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
        if (String.valueOf(passwordField.getPassword()).trim().equals("")) {
            b = true;
            JOptionPane.showMessageDialog(
                    this, "请输入密码！", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
        if (categoryComboBox.getSelectedItem() == null) {
            b = true;
            JOptionPane.showMessageDialog(
                    this, "请选择类别！", "提示", JOptionPane.INFORMATION_MESSAGE);
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
            JOptionPane.showMessageDialog(
                    this, "请输入正确的用户名！", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
        if (usernameField.getText().trim().length() != 7 & categoryComboBox.getSelectedItem() == "学生") {
            b = false;
            JOptionPane.showMessageDialog(
                    this, "用户名或类别错误！", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
        if (usernameField.getText().trim().length() != 5 & categoryComboBox.getSelectedItem() == "教师") {
            b = false;
            JOptionPane.showMessageDialog(
                    this, "用户名或类别错误！", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
        if (!usernameField.getText().trim().equals("1994") & categoryComboBox.getSelectedItem() == "管理员") {
            b = false;
            JOptionPane.showMessageDialog(
                    this, "用户名或类别错误！", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
        return b;
    }

}
