public class Student {
    private String studentID;
    private String name;
    private String gender;
    private int age;
    private String major;
    private String studentEmail;
    private int startYear;
    private int endYear;

    public Student(String studentID, String name, String gender, int age, String major, String studentEmail, int startYear, int endYear) {
        this.studentID = studentID;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.major = major;
        this.studentEmail = studentEmail;
        this.startYear = startYear;
        this.endYear = endYear;
    }

    //Getter
    public String getStudentID() { return studentID; }
    public String getName() { return name; }
    public String getGender() { return gender; }
    public int getAge() { return age; }
    public String getMajor() { return major; }
    public String getStudentEmail() { return studentEmail; }
    public int getStartYear() { return startYear; }
    public int getEndYear() { return endYear; }

    //Setter
    public void setName(String name) { this.name = name; }
    public void setGender(String gender) { this.gender = gender; }
    public void setAge(int age) { this.age = age; }
    public void setMajor(String major) { this.major = major; }
    public void setStudentEmail(String studentEmail) { this.studentEmail = studentEmail; }
    public void setStartYear(int startYear) { this.startYear = startYear; }
    public void setEndYear(int endYear) { this.endYear = endYear; }
}