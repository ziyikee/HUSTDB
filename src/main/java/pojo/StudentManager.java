package pojo;

import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public  class StudentManager {
    private static Student stu = new Student();
    static {
        stu.setScholarship("否");
    }
    Scanner in = new Scanner(System.in);
    public boolean addStudent(SqlSession ss) throws IOException {
        int n;
        System.out.println("请输入需要新增的学生数量：");
        n = in.nextInt();
        if(n<0) {
            return true;
        }
        System.out.println("请按照下面的格式输入学生信息：");
        System.out.println("学号 姓名 性别 年龄 系别");
        while(n>0) {
            stu.setSno(in.next());
            stu.setSname(in.next());
            stu.setSsex(in.next());
            stu.setSage(in.nextInt());
            stu.setSdept(in.next());
            ss.insert("DBMapper.addStudent", stu);
            System.out.println("添加成功！");
            n--;
        }
        return true;
    }

    public void getAllStudent(SqlSession ss)throws IOException{
        List<Student> StudentList = ss.selectList("DBMapper.selectAllStudent");
        for(Student s:StudentList){
            System.out.println(s.toString());
        }
    }
    public void modifyStudent(SqlSession ss)throws IOException{
        System.out.println("字段ID：\n学号：1\t姓名：2\t性别:3\t年龄：4\t系别：5\t");
        System.out.println("请输入需要修改信息的学生学号 修改字段ID 修改值");
        String sno = in.next();
        int id = in.nextInt();
        String newvalue = in.next();
        HashMap<String,Object> map = new HashMap<>();
        map.put("sno",sno);
        map.put("value",newvalue);
        switch (id){
            case 1:
                ss.update("DBMapper.modifySno",map);
                break;
            case 2:
                ss.update("DBMapper.modifySname",map);
                break;
            case 3:
                ss.update("DBMapper.modifySsex",map);
                break;
            case 4:
                ss.update("DBMapper.modifySage",map);
                break;
            case 5:
                ss.update("DBMapper.modifySdept",map);
                break;
            default:
                System.out.println("字段ID错误！");
                break;
        }
    }
    public void deleteStudent (SqlSession ss)throws IOException{
        System.out.println("请输入需要删除的学生数量：");
        int n = in.nextInt();
        if(n<0) {
            return ;
        }
        while(n>0){
            System.out.println("请输入需要删除的学生学号：");
            String sno = in.next();
            Student student= ss.selectOne("DBMapper.selectOneStude",sno);
            if(student == null){
                System.out.println("学号为："+sno+"的学生不存在或已被删除！");
                n--;
                continue;
            }
            int res = ss.delete("DBMapper.deleteStudent",sno);
            if(res ==1) {
                System.out.println("删除成功！");
            }else{
                System.out.println("删除失败！");
            }
            n--;
        }
    }
}
