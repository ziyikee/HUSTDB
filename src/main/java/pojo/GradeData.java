package pojo;

public class GradeData {
    public String Sdept;
    public float average_grade;
    public int max_grade;
    public int min_grade;
    public int  counts;

    public void setSdept(String sdept) {
        Sdept = sdept;
    }

    public void setAverage_grade(float average_grade) {
        this.average_grade = average_grade;
    }



    @Override
    public String toString() {
        return "GradeData{" +
                "Sdept='" + Sdept + '\'' +
                ", average_grade=" + average_grade +
                ", max_grade=" + max_grade +
                ", min_grade=" + min_grade +
                ", counts=" + counts +
                '}';
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public String getSdept() {
        return Sdept;
    }

    public float getAverage_grade() {
        return average_grade;
    }

    public float getMax_grade() {
        return max_grade;
    }

    public float getMin_grade() {
        return min_grade;
    }

    public int getCounts() {
        return counts;
    }

    public void setMax_grade(int max_grade) {
        this.max_grade = max_grade;
    }

    public void setMin_grade(int min_grade) {
        this.min_grade = min_grade;
    }
}
