import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCTest {
    public static void main(String[] args) {
        final String host = "127.0.0.1", port = "1521", sid = "orcl", username = "yzd", password = "Oraclebice502";
        Connection conn;
        try{
            conn = JDBCOrcl.getConnection(host, port, sid, username, password);

            System.out.println("数据库连接成功");
            Statement stat = conn.createStatement();
            String sql = "CREATE TABLE student (" +
                    "  id number primary key," +
                    "  name varchar2(20) NOT NULL," +
                    "  pwd varchar2(20) NOT NULL," +
                    "  score number " +
                    ")";
            System.out.println(sql);
//            stat.execute(sql);
//            stat.execute("INSERT INTO student (id, name, pwd, score) VALUES (1, '李四', 'a123456', 59.9)");
//            stat.execute("INSERT INTO student (id, name, pwd, score) VALUES (2, '漳卅能', 'zzn888', 97)");
//            Boolean resBoll = stat.execute("INSERT INTO student (id, name, pwd, score) VALUES (3, '孙悟空', 'mamimalihong', 99)");
//            if(resBoll){
//                System.out.println("执行成功");
//            }else{
//                System.out.println("执行失败");
//            }
//            int resInt = stat.executeUpdate("update student set name = '张州能1' where id = 2");
//            System.out.println("更新了"+resInt+"行");
            ResultSet rs =stat.executeQuery("select * from student");
            //获取数据
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String pwd = rs.getString("pwd");
                Double score = rs.getDouble("score");
                System.out.println("id = " + id+"\tname = " + name+"\tpwd = " + pwd+"\tscore = " + score);
            }
            //有两个优点附加功能：1、预编译，效率高; 2 、安全，避免SQL注入
            PreparedStatement pstate = conn.prepareStatement("select * from " +
                    "student where name = ? and pwd = ?");
            pstate.setString(1, "孙悟空");
            pstate.setString(2, "mamimalihong1");
            rs =pstate.executeQuery();
            if(rs.next()){
                System.out.println("登录成功!!!");
            }else {
                System.out.println("登录失败！！！");
            }

            JDBCOrcl.close(conn, pstate,rs);
            stat.close();

        }catch (Exception e){
            e.printStackTrace();
        }




    }
}
