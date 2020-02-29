package Bean;

public class Score {

    private int id;
    private int studentId;
    private String studentName;
    private int chinese;
    private int math;
    private int english;
    private int sum;

    public Score() {
        super();
    }

    public Score(int id, int studentId, String studentName, int chinese, int math, int english, int sum) {
        super();
        this.id = id;
        this.studentId = studentId;
        this.studentName = studentName;
        this.chinese = chinese;
        this.math = math;
        this.english = english;
        this.sum = sum;
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

    public int getChinese() {
        return chinese;
    }

    public void setChinese(int chinese) {
        this.chinese = chinese;
    }

    public int getMath() {
        return math;
    }

    public void setMath(int math) {
        this.math = math;
    }

    public int getEnglish() {
        return english;
    }

    public void setEnglish(int english) {
        this.english = english;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", StudentName='" + studentName + '\'' +
                ", chinese=" + chinese +
                ", math=" + math +
                ", english=" + english +
                ", sum=" + sum +
                '}';
    }
}
