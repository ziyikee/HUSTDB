package pojo;

import org.apache.ibatis.session.SqlSession;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class DataManager {

    private GradeData  data = new GradeData();
    // 这里
    public  boolean GradeDataQuery (SqlSession ss)throws IOException{
        List<String> cnolist  = ss.selectList("DBMapper.selectCnoList");
        for(String s :cnolist){
            String cname = ss.selectOne("DBMapper.getCnoName",s);
            List<GradeData> gradelist = ss.selectList("DBMapper.getCnoData",s);
            if(gradelist.isEmpty()){
                System.out.println("*"+cname+"*"+"成绩统计：");
                System.out.println("无学生选此门课程");
                continue;
            }
            System.out.println("*"+cname+"*"+"成绩统计：");
            for(GradeData g :gradelist){
                //System.out.println(g.toString());
                String name = g.getSdept();
                HashMap<String,String> deptcno = new HashMap<>();
                deptcno.put("dept",name);
                deptcno.put("cno",s);
                int highcounts = ss.selectOne("DBMapper.getHigh",deptcno);
                int lowcounts = ss.selectOne("DBMapper.getLow",deptcno);
                double highrate = (highcounts*1.0)/g.getCounts();
                DecimalFormat format = new DecimalFormat( "0.00 ");
                highrate = Double.parseDouble(format.format(highrate));
                g.setAverage_grade(Float.parseFloat(format.format(g.getAverage_grade())));
                System.out.println(
                        "{" +
                        "系别：" + name +
                        ", 平均成绩：" + g.getAverage_grade() +
                        ", 最高成绩：" + g.getMax_grade()+
                        ", 最低成绩：" + g.getMin_grade()+
                        ", 优秀率：" + highrate + ",不及格人数："+lowcounts+' '+"总人数："+g.getCounts()+' '+
                        '}');
            }
        }
        return true;
    }
    public boolean StudentDataQuery(SqlSession ss) throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.println("请输入需要查询学生信息的数量：");
        int n = in.nextInt();
        if(n<0) {
            return true;
        }
        Student stu = new Student();
        String sno;
        while(n>0){
            System.out.println("请输入学生学号：");
            sno = in.next();
            stu = ss.selectOne("DBMapper.selectOneStudent",sno);
            if(stu==null){
                System.out.println("该学生不在学生信息表中！");
                n--;
                continue;
            }

            System.out.println("学生基本信息：");
            System.out.println("**"+"学号："+stu.getSno()+",姓名："+stu.getSname()+",性别："+stu.getSsex()
                    +",年龄："+stu.getSage()+",专业："+stu.getSdept()+",奖学金获得情况："+stu.getScholarship()+"**"
            );
            List<Sc> CnameGrade= ss.selectList("DBMapper.selectOneStudentGrade",sno);
            System.out.println("选课成绩信息：");
            if(CnameGrade ==null){
                System.out.println("该同学没有选课成绩记录");
                n--;
                continue;
            }else{
                for(Sc sc:CnameGrade){
                    System.out.println("**课程："+sc.getCno()+",成绩："+sc.getGrade()+"**");
                }
            }
            n--;
        }
        return true;
    }
    public void gradeRank(SqlSession ss)throws IOException{
        List<String> cnolist  = ss.selectList("DBMapper.selectCnoList");
        for(String s :cnolist) {
            String cname = ss.selectOne("DBMapper.getCnoName", s);
            List<String>  sdeptlist = ss.selectList("DBMapper.getSdeptByCno",s);
            if(sdeptlist.isEmpty()){
                System.out.println("*"+cname+"*"+"各系成绩排名：");
                System.out.println("无学生选此门课程");
                continue;
            }
            System.out.println("*"+cname+"*"+"各系成绩排名：");
            for(String sdept : sdeptlist){
                HashMap<String,String> info = new HashMap<>();
                info.put("cno",s);
                info.put("sdept",sdept);
                List<StudentGrade> gradelist = ss.selectList("DBMapper.getRank",info);
                int rank = 1;
                System.out.println("----------------------------------------------");
                System.out.println("系别：*"+sdept+"*");
                System.out.println("学号\t\t姓名\t系别\t课程\t成绩\t排名");
                for(StudentGrade stu : gradelist){
                    System.out.println(stu.getSno()+"\t"+stu.getSname()+"\t"+sdept+"\t\t"+stu.getCname()+"\t"+stu.getGrade()+"\t\t"+rank);
                    rank++;
                }
                System.out.println("----------------------------------------------");
            }

        }
    }

}
