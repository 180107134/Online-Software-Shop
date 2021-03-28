package SoftSale;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JOptionPane;


public class database {

		Settings _settings = Settings.getInstance();

		static Connection _connection = null;
		static Statement _statement = null;
		static PreparedStatement _preparestatement = null;
		private volatile static database _instance;

		public static synchronized database getInstance()
		{
			if (_instance == null)
			{
				_instance = new database();
			}
			
			return _instance;
		}
		
		database()
		{
			try
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
			}
			catch(ClassNotFoundException e)
			{
				JOptionPane.showMessageDialog(null, e.toString());
				System.out.println(e.getMessage());
				return;
			}
			
			try
			{
				Properties connInfo = new Properties();
				connInfo.put("", "");
				connInfo.put("","");
				connInfo.put("charSet", "cp1251");
				
				String USER = "store";
				String PASS = "root";

				_connection = DriverManager.getConnection(_settings.getStoreDatabaseName(), USER, PASS);

				_statement = _connection.createStatement();
				_statement.setQueryTimeout(30);
			}
			catch (SQLException e)
			{
				JOptionPane.showMessageDialog(null, "Error during connection to Oracle");
				System.exit(0);
				System.out.println(e.getMessage());
				
				try
				{
					if (_connection != null)
					{
						_connection.close();
					}
				}
				catch(SQLException e2)
				{
					JOptionPane.showMessageDialog(null, e2.toString());
				}
				
				return;
			}
		}

		public static void updateStudentScore(int student_id) {
			
		}
		
		public static class StudentInfo_t
		{
			int TRANSACTION_ID = 0;
			public String PRODUCT_NAME = "";
			public int QUANTITY = 0;
			public String RATING = "";
			public String TRANSACTION_DATE = "";
			public String APPROVALSTATUS = "";
		}
		
		public static class StudentSearchCriteria_t
		{
			public int MEMBER_ID;
			public String PRODUCT_NAME;
			public String START_DATE;
			public String END_DATE;
			public String APPROVALSTATUS;
						
			public boolean isValid()
			{		
				return true;
			}
			
			public void reset()
			{
				MEMBER_ID = 0;
				PRODUCT_NAME = "";
				START_DATE = "";
				END_DATE = "";
				APPROVALSTATUS = "";
			}
		}

		public boolean findStudentInfo(final StudentSearchCriteria_t criteria, ArrayList<StudentInfo_t> data)
		{
			if (!criteria.isValid())
			{
				return false;
			}
			
			data.clear();
			
			ExpressionHelper helper = new ExpressionHelper();

			helper.setTable("TABLE(pkgDesign.getClientOrders)");
			
			helper.addField("TRANSACTION_ID");			
			helper.addField("PRODUCT_NAME");
			helper.addField("QUANTITY");
			helper.addField("RATING");
			helper.addField("TRANSACTION_DATE");
			helper.addField("APPROVALSTATUS");
			
			if (MainWindow.GLOBAL_MEMBER_ID > 0) {
				if (criteria.MEMBER_ID > 0)
				{
					helper.addCondition("MEMBER_ID", ExpressionHelper.ExpConditionOpr_t.EQUAL,
							String.valueOf(criteria.MEMBER_ID), ExpressionHelper.ExpLogicOpr_t.AND);
				}				
			}
			if (!criteria.PRODUCT_NAME.equals(""))
			{
				helper.addCondition("PRODUCT_NAME", ExpressionHelper.ExpConditionOpr_t.LIKE,
						String.valueOf("'" + criteria.PRODUCT_NAME) + "'", ExpressionHelper.ExpLogicOpr_t.AND);
			}
			
			if (!criteria.START_DATE.equals(""))
			{
				helper.addCondition("TRANSACTION_DATE", ExpressionHelper.ExpConditionOpr_t.GREATER_OR_EQUAL,
						String.valueOf("'" + criteria.START_DATE) + "'", ExpressionHelper.ExpLogicOpr_t.AND);
			}
			if (!criteria.END_DATE.equals(""))
			{
				helper.addCondition("TRANSACTION_DATE", ExpressionHelper.ExpConditionOpr_t.LESS_OR_EQUAL,
						String.valueOf("'" + criteria.END_DATE) + "'", ExpressionHelper.ExpLogicOpr_t.AND);
			}
			if (!criteria.APPROVALSTATUS.equals(""))
			{
				helper.addCondition("APPROVALSTATUS", ExpressionHelper.ExpConditionOpr_t.EQUAL,
						String.valueOf("'" + criteria.APPROVALSTATUS) + "'", ExpressionHelper.ExpLogicOpr_t.AND);
			}
			
			try
			{
				{
					ResultSet rs = _statement.executeQuery(helper.compile());

					while (rs.next())
					{
						StudentInfo_t info = new StudentInfo_t();
						info.TRANSACTION_ID = rs.getInt("TRANSACTION_ID");
						info.PRODUCT_NAME = rs.getString("PRODUCT_NAME");
						info.QUANTITY = rs.getInt("QUANTITY");
						if (rs.getString("RATING")== null) {
							info.RATING = "";
						}
						else {
							info.RATING = rs.getString("RATING");
						}
						try {
							String sDate = rs.getDate("TRANSACTION_DATE").toString();
							sDate = sDate.substring(8, 10) + "-" + sDate.substring(5, 7) + "-" + sDate.substring(0, 4);
							info.TRANSACTION_DATE = sDate;
						} catch (Exception e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						} 

						info.APPROVALSTATUS = rs.getString("APPROVALSTATUS");
						
						data.add(info);
					}
					return true;
				}
			}
			catch(SQLException e)
			{
				e.printStackTrace();
				System.out.println(e.getMessage() + " ");
				return false;
			}
		}
		
		public Vector<String> getProductNameList() {
			Vector<String> v = new Vector<String>();
			String SQL = "";
			try {

				SQL = "SELECT productname FROM Product";					
		
				ResultSet rs = _statement.executeQuery(SQL);
				while (rs.next()) {
					v.addElement(rs.getString(1));
				}
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(null,"Error in getProductName");
				System.exit(0);
				e.printStackTrace();
				return null;
			}
			return v;
		}	
		
		public Vector<String> getRatingList() {
			Vector<String> v = new Vector<String>();
			String SQL = "";
			try {

				SQL = "SELECT description FROM Ratingtype";					
		
				ResultSet rs = _statement.executeQuery(SQL);
				while (rs.next()) {
					v.addElement(rs.getString(1));
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return v;
		}	
		
		public boolean updateClient(
				int MEMBER_ID,
				int TRANSACTION_ID,
				int PRODUCT_ID,
				int QUANTITY, 
				String RATING,
				String APPROVALSTATUS) {
				
			boolean isUpdated = false;
				int value = 0;

				if (MEMBER_ID == 0) {
					try {
						
						_statement.executeUpdate("UPDATE Transactions SET "
								+ "APPROVALSTATUS = '" + APPROVALSTATUS + "' WHERE TRANSACTION_ID = " + TRANSACTION_ID);
						
						isUpdated = true;
					} catch (SQLException e) {
					
					}
				}
				else {
					try {
						
						_statement.executeUpdate("UPDATE Transactions SET "
								+ "QUANTITY = " + QUANTITY + " WHERE TRANSACTION_ID = " + TRANSACTION_ID
								+ " AND MEMBER_ID = " + MEMBER_ID + " AND PRODUCT_ID = " + PRODUCT_ID);
						if (!(RATING == null)) {
							
							ResultSet rs = _statement.executeQuery("SELECT value FROM Rating WHERE MEMBER_ID = " + MEMBER_ID  + " AND PRODUCT_ID = " + PRODUCT_ID);
							
							if (RATING.equals("Highly Dissatisfied")) {
								value = 1;
							}
							if (RATING.equals("Dissatisfied")) {
								value = 2;
							}
							if (RATING.equals("Neutral")) {
								value = 3;
							}
							if (RATING.equals("Satisfied")) {
								value = 4;
							}
							if (RATING.equals("Highly Satisfied")) {
								value = 5;
							}

							if (rs.next()) {
								_statement.executeUpdate("UPDATE Rating SET "
										+ "value = " + value + " WHERE MEMBER_ID = " + MEMBER_ID + " AND PRODUCT_ID = " + PRODUCT_ID);							
							}
							else {
								_preparestatement = _connection.prepareStatement("{call pkgDynamicSQL.InsertRating(?,?,?)}");
								_preparestatement.setInt(1, MEMBER_ID);
								_preparestatement.setInt(2, PRODUCT_ID);
								_preparestatement.setInt(3, value);
								_preparestatement.executeUpdate();
							}
						}
						isUpdated = true;
					}
					catch (SQLException e) {
						
						e.printStackTrace();
						
					}					
				}
				
			return isUpdated;
			
		}
		public String[] getUserInfo(int member_id) {
			String[] obj = new String[6];
			
			try {
				ResultSet rs = _statement.executeQuery("SELECT USERNAME, PASSWORD, EMAIL, FULLNAME, ADDRESS, PHONE FROM MEMBER WHERE MEMBER_ID = " + member_id);
				while (rs.next()) {
					obj[0] = rs.getString("USERNAME");
					obj[1] = rs.getString("PASSWORD");
					obj[2] = rs.getString("EMAIL");
					obj[3] = rs.getString("FULLNAME");
					obj[4] = rs.getString("ADDRESS");
					obj[5] = rs.getString("PHONE");
				}

			}catch (SQLException e) {
				
			}
			
			return obj;
		}
		
		public boolean setUserInfo(int MEMBER_ID, 
								   String USERNAME, 
								   String PASSWORD, 
								   String EMAIL, 
								   String FULLNAME, 
								   String ADDRESS, 
								   String PHONE) {

			
			try {
				_statement.executeUpdate("UPDATE MEMBER SET USERNAME = '" + USERNAME
															+ "', PASSWORD = '" + PASSWORD
															+ "',  EMAIL = '" + EMAIL
															+ "', FULLNAME = '" + FULLNAME
															+ "', ADDRESS = '" + ADDRESS
															+ "', PHONE = '" + PHONE + "' WHERE MEMBER_ID = " + MEMBER_ID);
			}catch (SQLException e) {
				return false;
			}
			
			return true;
		}
		public Object[] getMemberId(String username) {
			Object[] obj = new Object[3];
			int member_id = 0;
			String password = "";
			String user_group = "";
			try {
				
				ResultSet rs = _statement.executeQuery("SELECT member_id, password, User_group FROM Member WHERE username = '" + username + "'");
				while (rs.next()) {
				
					member_id = rs.getInt(1); 
					password = rs.getString(2);
					user_group = rs.getString(3);
					obj[0] = member_id;
					obj[1] = password;
					obj[2] = user_group;
				}
			}
			catch (SQLException e) {
				
				e.printStackTrace();
				
			}				
				
			return obj;
		}
		
		public int getProductId(String PRODUCT_NAME) {
			int product_id = 0;
			try {
				
				ResultSet rs = _statement.executeQuery("SELECT product_id FROM Product WHERE PRODUCTNAME = '" + PRODUCT_NAME + "'");
				while (rs.next()) {
				
					product_id = rs.getInt(1); 

				}
			}
			catch (SQLException e) {
				
				e.printStackTrace();
				
			}
			
		return product_id;
		}
		
		public boolean deleteStudent(int ID) {
			
			boolean isUpdated = false;
			
				try {
					
					_statement.executeUpdate("DELETE FROM STUDENTS WHERE ID = " + ID);
					isUpdated = true;
				}
				catch (SQLException e) {
					
					e.printStackTrace();
					
				}
				
			return isUpdated;
			
		}
		
		public boolean addOrder(
				int MEMBER_ID, 
				int PRODUCT_ID, 
				int QUANTITY, 
				String APPROVALSTATUS, 
				String TRANSACTION_DATE) {
			
			boolean isAdded = false;
			
			try {
				_preparestatement = _connection.prepareStatement("{call pkgDynamicSQL.InsertTransactions(?,?,?,?,?)}");
				_preparestatement.setInt(1, MEMBER_ID);
				_preparestatement.setInt(2, PRODUCT_ID);
				_preparestatement.setInt(3, QUANTITY);
				_preparestatement.setString(4, APPROVALSTATUS);
				_preparestatement.setString(5, TRANSACTION_DATE);
				_preparestatement.executeUpdate();
				
				isAdded = true;
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			
			return isAdded;
		}

	}	

