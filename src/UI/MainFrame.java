package UI;

import UI.admin.UserEditPanel;
import UI.student.InfoQueryPanel;
import UI.student.ScoreQueryPanel;
import UI.teacher.InfoEditPanel;
import UI.teacher.ScoreEditPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {

    private InfoQueryPanel infoQueryPanel;
    private ScoreQueryPanel scoreQueryPanel;
    private InfoEditPanel infoEditPanel;
    private ScoreEditPanel scoreEditPanel;
    private UserEditPanel userEditPanel;

    private Action infoQuery = new AbstractAction("信息查询") {
        @Override
        public void actionPerformed(ActionEvent e) {
            changePanel(infoQueryPanel);
        }
    };

    private Action scoreQuery = new AbstractAction("成绩查询") {
        @Override
        public void actionPerformed(ActionEvent e) {
            changePanel(scoreQueryPanel);
        }
    };

    private Action infoEdit = new AbstractAction("信息编辑") {
        @Override
        public void actionPerformed(ActionEvent e) {
            changePanel(infoEditPanel);
        }
    };

    private Action scoreEdit = new AbstractAction("成绩编辑") {
        @Override
        public void actionPerformed(ActionEvent e) {
            changePanel(scoreEditPanel);
        }
    };

    private Action userEdit = new AbstractAction("用户管理") {
        @Override
        public void actionPerformed(ActionEvent e) {
            changePanel(userEditPanel);
        }
    };

    private Action version = new AbstractAction("版本") {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(
                    new JPanel(), "当前版本 1.0", "版本", JOptionPane.INFORMATION_MESSAGE);
        }
    };

    private Action about = new AbstractAction("关于") {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(
                    new JPanel(), "Java+Swing+JDBC+MySQL 练手项目", "关于", JOptionPane.INFORMATION_MESSAGE);
        }
    };

    //切换不同的panel
    private void changePanel(JPanel panel) {
        //setContentPane是将现有的panel改为新panel，而add是在现有基础上添加
        this.setContentPane(panel);
        this.revalidate();
//        this.repaint();
    }

    public MainFrame(String category, int username) {

        JMenuBar menuBar = new JMenuBar();

        JMenu studentMenu = new JMenu("学生");
        JMenu teacherMenu = new JMenu("教师");
        JMenu adminMenu = new JMenu("管理员");
        JMenu helpMenu = new JMenu("帮助");

        studentMenu.add(infoQuery);
        studentMenu.add(scoreQuery);

        teacherMenu.add(infoEdit);
        teacherMenu.add(scoreEdit);

        adminMenu.add(userEdit);

        helpMenu.add(version);
        helpMenu.add(about);

        //不同身份的人能使用的功能模块不同
        if (category.equals("学生")) {
            this.infoQueryPanel = new InfoQueryPanel(username);
            this.scoreQueryPanel = new ScoreQueryPanel(username);
            infoEdit.setEnabled(false);
            scoreEdit.setEnabled(false);
            userEdit.setEnabled(false);
        } else if (category.equals("教师")) {
            infoQuery.setEnabled(false);
            scoreQuery.setEnabled(false);
            userEdit.setEnabled(false);
        } else {
            infoQuery.setEnabled(false);
            scoreQuery.setEnabled(false);
            infoEdit.setEnabled(false);
            scoreEdit.setEnabled(false);
        }

        menuBar.add(studentMenu);
        menuBar.add(teacherMenu);
        menuBar.add(adminMenu);
        menuBar.add(helpMenu);

        //frame实例化时会有一个默认的panel，所以无需新建一个空panel
        this.infoEditPanel = new InfoEditPanel();
        this.scoreEditPanel = new ScoreEditPanel();
        this.userEditPanel = new UserEditPanel();

        this.setJMenuBar(menuBar);
        this.setTitle("学生管理系统 [当前用户：" + username + "]");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(930,720);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}
