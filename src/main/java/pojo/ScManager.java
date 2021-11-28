package pojo;

import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class ScManager {
    private  Sc sc = new Sc();
    Scanner in = new Scanner(System.in);
    public boolean addSc(SqlSession ss) throws IOException {
        int n;
        System.out.println("请输入需要录入的成绩数量：");
        n = in.nextInt();
        if(n<0) {
            return true;
        }
        System.out.println("请按照下面的格式输入成绩信息：");
        System.out.println("学号 课程号 成绩");
        while(n>0) {
            sc.setSno(in.next());
            sc.setCno(in.next());
            sc.setGrade(in.nextInt());
            ss.insert("DBMapper.addSc", sc);
            System.out.println("添加成功！");
            n--;
        }
        return true;
    }
    public void getAllSc(SqlSession ss)throws IOException{
        List<Sc> ScList = ss.selectList("DBMapper.selectAllSc");
        for(Sc s:ScList){
            System.out.println(s.toString());
        }
    }
    public boolean modifygrade(SqlSession ss) throws IOException{
        int n;
        System.out.println("请输入需要修改的成绩数量：");
        n = in.nextInt();
        if(n<0) {
            return true;
        }
        System.out.println("请按照下面的格式输入成绩信息：");
        System.out.println("学号 课程号 成绩");
        while(n>0) {
            sc.setSno(in.next());
            sc.setCno(in.next());
            sc.setGrade(in.nextInt());
            ss.update("DBMapper.modifySc", sc);
            System.out.println("修改成功！");
            n--;
        }
        return true;
    }
    public void deleteSc (SqlSession ss)throws IOException{
        System.out.println("请输入需要删除的成绩数量：");
        int n = in.nextInt();
        if(n<0) {
            return ;
        }
        while(n>0){
            System.out.println("请输入需要删除的学生学号与课程号：");
            String sno = in.next();
            String cno = in.next();
            HashMap<String,String> info = new HashMap<>();
            info.put("sno",sno);
            info.put("cno",cno);
            Sc sc= ss.selectOne("DBMapper.selectScBySnoAndCno",info);
            if(sc == null){
                System.out.println("学号为："+sno+",课程号为："+cno+"的成绩信息不存在或已被删除！");
                n--;
                continue;
            }
            int res = ss.delete("DBMapper.deleteSc",info);
            if(res ==1) {
                System.out.println("删除成功！");
            }else{
                System.out.println("删除失败！");
            }
            n--;
        }
    }

}
