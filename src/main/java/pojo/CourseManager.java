package pojo;

import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class CourseManager {
    private static Course course = new Course();
    Scanner in = new Scanner(System.in);
    public boolean addCourse(SqlSession ss) throws IOException {
        int n;
        System.out.println("请输入需要新增的课程数量：");
        n = in.nextInt();
        if(n<0) {
            return true;
        }
        System.out.println("请按照下面的格式输入课程信息：");
        System.out.println("课程号 课程名称 前置课程号(若没有则填null) 课程学分");
        while(n>0) {
            course.setCno(in.next());
            course.setCname(in.next());
            String cpno = in.next();
            cpno = cpno.intern();
            String cpnonull = "null";
            if(cpno == cpnonull ){
                cpno = null;
            }
            course.setCpno(null);
            course.setCcredit(in.nextInt());
            ss.insert("DBMapper.addCourse",course);
            if(cpno == null){
                 ;
            }else{
                HashMap<String,Object> map = new HashMap<>();
                map.put("cname",course.getCname());
                map.put("value",cpno);
                ss.update("DBMapper.modifyCpno", map);
            }
            System.out.println("添加成功！");
            n--;
        }
        getAllCourse(ss);
        return true;
    }
    public void modifyCourse(SqlSession ss)throws IOException{
        System.out.println("字段ID：\n课程名：1\t前置课程号:2\t课程学分：3");
        System.out.println("请输入需要修改信息的课程名称 修改字段ID 修改值");
        String cname = in.next();
        int id = in.nextInt();
        String newvalue = in.next();
        HashMap<String,Object> map = new HashMap<>();
        map.put("cname",cname);
        map.put("value",newvalue);
        switch (id){
            case 1:
                ss.update("DBMapper.modifyCname",map);
                break;
            case 2:
                Course cpno = ss.selectOne("DBMapper.searchCpno",newvalue);
                if(cpno==null){
                    System.out.println("该课程号不存在！");
                }else {
                    ss.update("DBMapper.modifyCpno", map);
                }
                break;
            case 3:
                ss.update("DBMapper.modifyCcredit",map);
                break;
            default:
                System.out.println("字段ID错误！");
                break;
        }
        getAllCourse(ss);
    }
    public void getAllCourse(SqlSession ss)throws IOException{
        List<Course> courselist = ss.selectList("DBMapper.selectAllCourse");
        for(Course c : courselist){
            System.out.println(c.toString());
        }
    }
    public void deleteNotSelectedCourse (SqlSession ss)throws IOException{
        System.out.println("警告：删除未被选的前置课程将会把其后置课程一并删掉！");
        System.out.println("请输入需要删除的课程数量：");
        int n = in.nextInt();
        if(n<0) {
            return ;
        }
        while(n>0){
            System.out.println("请输入需要删除的课程名称：");
            String cname = in.next();
            Course course = ss.selectOne("DBMapper.selectCourseByName",cname);
            if(course == null){
                System.out.println("课程："+cname+" 不存在或已被删除！");
                n--;
                continue;
            }
            int res = ss.delete("DBMapper.deleteCourse",cname);
            if(res ==1) {
                System.out.println("删除成功！");
            }else{
                System.out.println("删除失败！");
            }
            n--;
        }
    }


}
