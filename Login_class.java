import java.util.Scanner;
import java.sql.*;
public class Login_Class {
	  String url = "jdbc:mysql://localhost:3306/Login_Databas";
	  String user = "root";
      String passwd = "root@root";
      Connection conn = null;
	public Connection connect() {
	      try {
	    	  conn = DriverManager.getConnection(url,user,passwd);
	    	  System.out.println("connection is establish");
	    	  System.out.println("USERNAME:\t"+"PASSWORD:");
	    	  } catch (SQLException e) {
	    		  System.out.println(e.getMessage());
	    		  }
        return conn;
    }
	public void createDatabase() {
		String sql = "create database if not exist Login_Databas";
		try(Connection conn = this.connect();Statement stat = conn.createStatement()){
			stat.executeUpdate(sql);
	        System.out.println("Database create successful");
      } catch (SQLException e) {
		// TODO Auto-generated catch block
    	  System.out.println("Database not created :"+e.getMessage());
    	  e.printStackTrace();
      }
    }
	public void createTable() {
		String sql = "CREATE TABLE IF NOT EXISTS LOGINTEST"
				+"(USERID INT auto_increment  key,"
				+"USERNAME VARCHAR(255) NOT NULL,"
				+"PASSWORD VARCHAR(255) NOT NULL)";
		try(Connection conn = this.connect();Statement stat = conn.createStatement()){
			stat.executeUpdate(sql);
	        System.out.println("table create successful");  
    	  
      } catch (SQLException e) {
		// TODO Auto-generated catch block
    	  System.out.println("Table not created :"+e.getMessage());
    	  e.printStackTrace();
    	 
	}
 }
	public void InsertData(String name, String password) {
		 String sql = "INSERT INTO LOGINTEST(USERNAME,PASSWORD) VALUES(?,?)";
		try(Connection conn = this.connect();PreparedStatement stat = conn.prepareStatement(sql)){
			stat.setString(1, name);
			stat.setString(2, password);
			stat.executeUpdate();
	        System.out.println("data insert  successful");
    	  
    	  
    	  
      } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 }
	public void fatchdata() {
		 String sql = "select * from LOGINTEST";
		try(Connection conn = this.connect();Statement stat = conn.createStatement()){
			try(ResultSet result = stat.executeQuery(sql)){
				while (result.next()) {
					String name = result.getString("USERNAME");
					String pass = result.getString("PASSWORD");
					System.out.println(name+"\t\t"+pass);
				}
			}
			
	        System.out.println("data fatch  successful");
   	  
   	  
   	  
     } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	
	public boolean varify_user(String username,String passwd) {
		PreparedStatement stmt=null;
		ResultSet resultSet = null;
		try{
			   Connection conn = this.connect();
			   String sql="SELECT * FROM LOGINTEST WHERE USERNAME=? and PASSWORD=?";
			   stmt=conn.prepareStatement(sql);
			   stmt.setString(1,username);
			   stmt.setString(2,passwd);             
			   resultSet=stmt.executeQuery();
			   if(resultSet.next()){
//				   String name = resultSet.getString("USERNAME");
//				   String pass = resultSet.getString("PASSWORD");
//				   System.out.println(name+"\t\t"+pass);
				   return true;
			   }
			  //     
			}catch(SQLException ex){
			   System.err.println(ex);
			}finally{
			  if(stmt!=null){
			      try{ 
			         stmt.close();
			      }catch(Exception ex) { 
			    	  System.out.println("Exception massage is:"+ex.getMessage());
			      }
			  }
			 if(conn!=null){
			      try{ 
			         conn.close();
			      }catch(Exception ex) { 
			    	  System.out.println("Exception massage is:"+ex.getMessage());
			      }
			  }
			}
		return false;
	}
	public void Login_user() {
		System.out.println("*************LOGIN PAGE*************\n\n");
		System.out.println("*************Enter Detail*************\n");
		 Scanner sc = new Scanner(System.in);
		 System.out.println("Enter Username : ");
	     String usernam = sc.nextLine();
	     System.out.println("Enter Password : ");
	     String passw = sc.nextLine();
	     if(varify_user(usernam, passw)) {
	    	 System.out.println("Hi! You have login Successfully!! ");
	     }
	     else {
	    	 System.out.println("OOps! You have enter wrong  detail!! ");
		}
		
		
	}
	public static void main(String[] argv) throws SQLException {
		 
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        } 
        catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found !!");
            return;
        }
       
        Login_Class obj = new Login_Class();
        obj.Login_user();
//        obj.InsertData("user123", "pass123");
//        obj.InsertData("user222", "pass212");
       

        
    }

}
