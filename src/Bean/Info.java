package Bean;

import java.sql.Date;

public class Info {

    private int id;
    private int studentId;
    private String studentName;
    private String gender;
    private String major;
    private int grade;
    private Date birth;

    public Info() {
        super();
    }

    public Info(int id, int studentId, String studentName, String gender, String major, int grade, Date birth) {
        super();
        this.id = id;
        this.studentId = studentId;
        this.studentName = studentName;
        this.gender = gender;
        this.major = major;
        this.grade = grade;
        this.birth = birth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "Info{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", gender='" + gender + '\'' +
                ", major='" + major + '\'' +
                ", grade=" + grade +
                ", birth=" + birth +
                '}';
    }
}
