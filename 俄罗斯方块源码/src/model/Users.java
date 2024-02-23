package model;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JLabel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.mysql.cj.x.protobuf.MysqlxSql.StmtExecute;



import java.sql.*;

public class Users {
	

		
		 static Connection connect = null;
		 static  Statement stmt = null;
		 static ResultSet result = null;
		static String url="jdbc:mysql://localhost:3306/data?useUnicode=true"
	    			+ "&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia"
	    			+ "/Shanghai&zeroDateTimeBehavior=CONVERT_TO_NULL&allowPublicKeyRetrieval=true";
	        //��¼���ݿ���û���������
	        static String username = "root";
	        static String password = "mmnhsnmt";   
	public Users() {
	
	}
	public static int login(String account, String pass) throws ClassNotFoundException, SQLException {//��֤�˺�����
		Class.forName("com.mysql.cj.jdbc.Driver");	          
        connect = DriverManager.getConnection(url,username,password);	                       	 	      
stmt = connect.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);					    	
		result=stmt.executeQuery("select * from bbb");
        	          while(result.next()) {
        	        	  String id=result.getString(2);
        	        	  String word=result.getString(3);	        	 
        	        	  if(account.equals(id)&&pass.equals(word))
        	        		  return result.getInt(1);
        	          }
           return 0;
	}

	public static String getAccount(int id) throws SQLException, ClassNotFoundException {//ͨ��IDȡ�˺�
		Class.forName("com.mysql.cj.jdbc.Driver");	          
        connect = DriverManager.getConnection(url,username,password);	                       	 	      
stmt = connect.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);					    	
		result=stmt.executeQuery("select * from bbb");
        	          while(result.next()) {
        	        	int dd=result.getInt(1);
        	        	if(dd==id)
        	        		return result.getString(2);
        	          }
           return null;
	}

	public static boolean isExist(String account) throws SQLException, ClassNotFoundException {//��֤�˺��Ƿ����
		Class.forName("com.mysql.cj.jdbc.Driver");	          
        connect = DriverManager.getConnection(url,username,password);	                       	 	      
stmt = connect.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);					    	
		result=stmt.executeQuery("select * from bbb");
        	          while(result.next()) {
        	        	  String id=result.getString(2);
        	                	 
        	        	  if(account.equals(id))
        	        		  return true;
        	          }
           return false;
	}

	public static int getUserCount() throws ClassNotFoundException, SQLException {//�û�����
		int sum=0;
		Class.forName("com.mysql.cj.jdbc.Driver");	          
        connect = DriverManager.getConnection(url,username,password);	                       	 	      
stmt = connect.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);					    	
		result=stmt.executeQuery("select * from bbb");
        	          while(result.next()) {
        	        	if(result.getInt(1)!=0)
        	        		sum++;
        	          }
     return sum;   
	}
	public static void addUser(int id, String account, String pass, int maxscore,int time) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");	          
        connect = DriverManager.getConnection(url,username,password);	                       	 	      
stmt = connect.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);					    	
		String sql = "INSERT INTO bbb VALUES (?,?,?,?,?);";
        PreparedStatement statement = connect.prepareStatement(sql);//Ԥ����sql���
        	          
            statement.setInt(1, id);
            statement.setString(2, account);
            statement.setString(3,pass);
            statement.setInt(4, maxscore);
            statement.setInt(5,time);
            statement.executeUpdate();
               
	}

}
