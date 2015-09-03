package hive_jdbc.hive_jdbc;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;

public class App 
{
	
	private static Connection con;
	private static Statement stmt;
	private static ResultSet res;

	
	private static String driverName = "org.apache.hive.jdbc.HiveDriver";
	
	public static void establishHiveConnection(String url, String user, String pw) {
		try {
			Class.forName(driverName);
			System.out.println("Trying to connect to hive database...");
			con = DriverManager.getConnection(url, user, pw);
			System.out.println("Connection succesfully established!");
			stmt = con.createStatement();

		} catch (Exception e) {
			 e.printStackTrace();
		}
	}
	
	public static void showTables() throws Exception {
		res = stmt.executeQuery("show tables");
		while (res.next()) {
		  System.out.println(res.getString(1));
		}
	}
	
	public static void describeTable(String table) throws Exception {
		res = stmt.executeQuery("describe " + table);
		while (res.next()) {
		  System.out.println(res.getString(1) + "\t" + res.getString(2));
		}
	}
	

	
	public static String readFile(String filename) {
		String content = null;
	    File file = new File(filename); //for ex foo.txt
	    FileReader reader = null;
	    try {
	        reader = new FileReader(file);
	        char[] chars = new char[(int) file.length()];
	        reader.read(chars);
	        content = new String(chars);
	        reader.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        if(reader !=null){try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
	    }
	    return content;
	}

	
	public static void main(String[] args) {
		if(args.length < 5) {
			System.out.println("Too few arguments.\n" +
								"Usage:\n"+
								"1. Hadoop_Host:port\n" +
								"2. Username\n" +
								"3. Password\n" +
								"4. Database name\n" +
								"5. SQL filename\n");
			return;
		}  
		try {
			 //----- establish connection
			 establishHiveConnection(args[0], args[1], args[2]);			 

			 stmt.executeUpdate("use " + args[3]);
			 StringTokenizer multiTokenizer = new StringTokenizer(readFile(args[4]), ";");
			 while (multiTokenizer.hasMoreTokens())
			 {
			     stmt.executeUpdate(multiTokenizer.nextToken());
			 }
			 

			 
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}