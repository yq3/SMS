# SMS
Student Management System 学生管理系统 Java+Java Swing+JDBC+MySQL 练手项目

注意：JDBC、C3P0、DBCP 的配置文件中 user 是 root，密码留空，请自行填写自己的密码！

1. 程序的主要功能界面：登录与注册界面、学生界面、教师界面、管理员界面。不同身份的人登陆进去，所能使用的功能模块是有区别的，比如学生登录进去只能看到自己的信息和成绩，而无法进行信息和成绩的编辑操作（那是教师的功能），也无法进行用户信息的编辑（那是管理员的功能）。

2. 程序的逻辑：首先由管理员把本校师生的信息录入用户表（对应 MySQL 数据库中的 user 表），然后师生要使用自己的用户名注册，只有在管理员的用户表中的人才可以进行注册。教师登录后可以对学生信息表（对应 MySQL 数据库中的 info 表）和学生成绩表（对应 MySQL 数据库中的 score 表）进行增删改查的操作。学生登录后只能看到属于自己的信息和成绩。

3. 程序编写的架构主要分为：JDBC 层，负责创建与数据库的连接以及资源的关闭；Bean 层，对应 MySQL 数据库中的3张表；DAO 层，负责与数据库之间的具体操作；Service 层，封装了与数据库之间的各项操作；以及 UI 层，是用 Java Swing 开发的界面。**尽管转移了部分逻辑代码到 Service 层，但是 UI 层中依然包含有大量业务逻辑的代码，这一点做得不够好，以后要注意。**

4. 程序的用户界面比较简单，Java Swing 的原生界面有点不忍直视，采用了系统的 LookAndFeel 之后好了很多。界面的数据呈现是用的 JTable，这个组件还是有很大的改进空间的，可以对它渲染的很好看。不同的 JFrame 进行切换，可以用  `oldFrame.setVisible(false);` 使前一个隐去。而同一个 JFrame 中要切换不同的 JPanel，可以用 `frame.setContentPane(newPanel);` 然后再加上 `frame.revalidate();` 即可，不要用 add，因为 add 是在现有基础上添加，而 setContentPane 是将现有的 panel 改为新 panel，可以实现切换的效果。

5. 对于一些基础的 Java 知识点需要梳理整合一下，避免每次用到都要重新百度。比如 String Integer char 等数据类型的相互间的转换，还有一些特殊的数据类型比如 Date，“yyyy年MM月dd日” 如何转换成 yyyy-MM-dd 格式。

6. MySQL 中的3张表是各自建的，表间没有建立关联，即可能会出现这样一个 bug：同一用户名（学号）在信息表和成绩表中会对应不同的学生姓名。

7. 在添加学生生日信息的时候，使用了 [LGoodDatePicker](https://github.com/LGoodDatePicker/LGoodDatePicker) 控件，在此表示感谢。
