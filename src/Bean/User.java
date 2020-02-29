package Bean;

public class User {

    //学生用户名是学号，1开头，共7位数字；老师9开头，共5位数字；管理员是1994，密码1994
    private int id;
    private int username;
    private String password;
    private String category;
    //标记是否已注册
    private boolean signed;

    //管理员负责用户名和密码的管理，增加新用户，删除用户
    //用户密码管理员是不可见的，用户若忘记密码只能由管理员删除之后用户重新注册
    //数据库中存储的密码仅用作登录比对，没有进行加密

    public User() {
        super();
    }

    public User(int id, int username, String password, String category, boolean signed) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.category = category;
        this.signed = signed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsername() {
        return username;
    }

    public void setUsername(int username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isSigned() {
        return signed;
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username=" + username +
                ", password='" + password + '\'' +
                ", category='" + category + '\'' +
                ", signed=" + signed +
                '}';
    }
}
