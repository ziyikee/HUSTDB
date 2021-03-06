package pojo;

public class Course {
    private String Cno;
    private String Cname;
    private String Cpno;
    private int Ccredit;

    public String getCno() {
        return Cno;
    }

    public void setCno(String cno) {
        Cno = cno;
    }

    public String getCname() {
        return Cname;
    }

    public void setCname(String cname) {
        Cname = cname;
    }

    public String getCpno() {
        return Cpno;
    }

    public void setCpno(String cpno) {
        Cpno = cpno;
    }

    public int getCcredit() {
        return Ccredit;
    }

    public void setCcredit(int ccredit) {
        Ccredit = ccredit;
    }

    @Override
    public String toString() {
        return "Course{" +
                "Cno='" + Cno + '\'' +
                ", Cname='" + Cname + '\'' +
                ", Cpno='" + Cpno + '\'' +
                ", Ccredit=" + Ccredit +
                '}';
    }
}
