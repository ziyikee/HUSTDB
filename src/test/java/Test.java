
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import org.apache.ibatis.logging.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import pojo.*;

import static java.lang.Thread.sleep;

public class Test {

    public static void main(String[] args) throws IOException, InterruptedException {
        InputStream config = Resources.getResourceAsStream("mybatis-config.xml");
        if(config ==null){
            System.out.println("读取配置文件失败。。。");
            return ;
        }else{
            System.out.println("读取配置文件成功....");
        }
        SqlSessionFactory ssf = new SqlSessionFactoryBuilder().build(config);
        SqlSession ss = ssf.openSession(true);
        if(ss==null){
            System.out.println("创建数据库连接，开启会话失败。。。");
            return ;
        }else{
            System.out.println("数据库连接成功....");
            System.out.println("会话建立成功,开启事务....");
        }
        Scanner in = new Scanner(System.in);
        StudentManager studentManager = new StudentManager();
        CourseManager courseManager = new CourseManager();
        ScManager scManager = new ScManager();
        DataManager dataManager = new DataManager();
        int op = 1;
        System.out.println("欢迎使用本人出品的学生课程管理系统");

        while(op != 0){
            System.out.println();
            System.out.println("---------------------------------学生管理系统界面---------------------------------------");
            System.out.println("**学生信息管理**");
            System.out.println("1.添加新生信息\t2.修改学生信息\t3.查看所有学生信息\t4.删除学生信息");
            System.out.println("**课程信息管理**");
            System.out.println("5.添加课程信息\t6.修改课程信息\t7.查看所有课程信息\t8.删除未被选的课程");
            System.out.println("**成绩信息管理**");
            System.out.println("9.添加成绩信息\t10.修改成绩信息\t11.查看所有成绩信息\t12.删除成绩信息");
            System.out.println("13.查看各课程按系统计的成绩信息\t14.查看各课程按系排名成绩信息");
            System.out.println("15.查看指定学生的基本信息与选课信息\t0.退出系统");
            System.out.println("---------------------------------------------------------------------------------------");
            System.out.print("请输入您要进行的操作序号：");
            op = in.nextInt();
            switch(op){
                case 1:
                    studentManager.addStudent(ss);
                    break;
                case 2:
                    studentManager.modifyStudent(ss);
                    break;
                case 3:
                    studentManager.getAllStudent(ss);
                    break;
                case 4:
                    studentManager.deleteStudent(ss);
                    break;
                case 5:
                    courseManager.addCourse(ss);
                    break;
                case 6:
                    courseManager.modifyCourse(ss);
                    break;
                case 7:
                    courseManager.getAllCourse(ss);
                    break;
                case 8:
                    courseManager.deleteNotSelectedCourse(ss);
                    break;
                case 9:
                    scManager.addSc(ss);
                    break;
                case 10:
                    scManager.modifygrade(ss);
                    break;
                case 11:
                    scManager.getAllSc(ss);
                    break;
                case 12:
                    scManager.deleteSc(ss);
                    break;
                case 13:
                   dataManager.GradeDataQuery(ss);
                    break;
                case 14:
                    dataManager.gradeRank(ss);
                    break;
                case 15:
                    dataManager.StudentDataQuery(ss);
                    break;
                case 0:
                    System.out.println("Bye Bye!");
                    break;
                default:
                    break;
            }
        }
        ss.close();
    }
}
