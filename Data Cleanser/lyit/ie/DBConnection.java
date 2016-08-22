/**
 * Author: Luke Toland
 * Class for interacting with database
 */

package emergingtech.lyit.ie;

import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JOptionPane;

public class DBConnection {
	//connect
		public static Connection getConnection() throws ClassNotFoundException, SQLException
		{
			//connect to database
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String serverName = "000.000.000.000";
			String portNumber = "1521";
			String sid = "XE";
			String url = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":" + sid;
			String username = "Emerging";
			String password = "system";
			Connection conn = DriverManager.getConnection(url, username, password);
			
			return conn;
		}
		
		//Insert
		public void insertIntoDB(int id, String f, String l, String a) throws ClassNotFoundException, SQLException
		{
			Connection dbConnection = getConnection();
			Statement statement = null;
			String insertTableSQL = "INSERT INTO CUSTOMERS"
					+ "(CUSTOMER_ID, FIRST_NAME, LAST_NAME, ADDRESS) " + "VALUES"
					+ "("+ id+",'"+f+"','"+l+"','"+a+"')";
			try {
				statement = ((java.sql.Connection) dbConnection).createStatement();
				System.out.println(insertTableSQL);
				// execute insert SQL stetement
				statement.executeUpdate(insertTableSQL);
				System.out.println("Record is inserted into CUSTOMER table!");
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}catch (NumberFormatException e) {
				JOptionPane.showMessageDialog (null, "Please select a function", "DC", JOptionPane.INFORMATION_MESSAGE);
			}  
			finally {
				if (statement != null) {
					statement.close();
				}
				if (dbConnection != null) {
					dbConnection.close();
				}
			}

		}
		
		//Alter strings
		public void alterStrings() throws ClassNotFoundException, SQLException
		{
			//Call stored procedure
			Connection dbConnection = null;
			CallableStatement callableStatement = null;
			String getDBUSERByUserIdSql = "{call ALTER_STRINGS}";

			try {
				dbConnection = getConnection();
				callableStatement = dbConnection.prepareCall(getDBUSERByUserIdSql);
				callableStatement.executeUpdate();
			} catch (SQLException e) {

				System.out.println(e.getMessage());

			} finally {

				if (callableStatement != null) {
					callableStatement.close();
				}

				if (dbConnection != null) {
					dbConnection.close();
				}

			}
		}

		
		//stored
		public void removeDuplicates() throws ClassNotFoundException, SQLException
		{
			//Call stored procedure
			Connection dbConnection = null;
			CallableStatement callableStatement = null;
			String getDBUSERByUserIdSql = "{call REMOVE_DUPLICATES}";

			try {
				dbConnection = getConnection();
				callableStatement = dbConnection.prepareCall(getDBUSERByUserIdSql);
				callableStatement.executeUpdate();
			} catch (SQLException e) {

				System.out.println(e.getMessage());

			} finally {

				if (callableStatement != null) {
					callableStatement.close();
				}

				if (dbConnection != null) {
					dbConnection.close();
				}

			}
		}

		//stored
		public void removeSpaces() throws ClassNotFoundException, SQLException
		{
			//Call stored procedure
			Connection dbConnection = null;
			CallableStatement callableStatement = null;
			String getDBUSERByUserIdSq1l = "{call REMOVE_SPACES}";

			try {
				dbConnection = getConnection();
				callableStatement = dbConnection.prepareCall(getDBUSERByUserIdSq1l);
				callableStatement.executeUpdate();
			} catch (SQLException e) {

				System.out.println(e.getMessage());

			} finally {

				if (callableStatement != null) {
					callableStatement.close();
				}

				if (dbConnection != null) {
					dbConnection.close();
				}

			}
		}

		//stored
		public void removeSymbols() throws ClassNotFoundException, SQLException
		{
			//Call stored procedure
			Connection dbConnection = null;
			CallableStatement callableStatement = null;
			String getDBUSERByUserIdSq1l = "{call REMOVE_SYMBOLS}";

			try {
				dbConnection = getConnection();
				callableStatement = dbConnection.prepareCall(getDBUSERByUserIdSq1l);
				callableStatement.executeUpdate();
			} catch (SQLException e) {

				System.out.println(e.getMessage());

			} finally {

				if (callableStatement != null) {
					callableStatement.close();
				}

				if (dbConnection != null) {
					dbConnection.close();
				}

			}
		}

		//stored
		public void removeOldData() throws ClassNotFoundException, SQLException
		{
			//Call stored procedure
			Connection dbConnection = null;
			CallableStatement callableStatement = null;
			String getDBUSERByUserIdSq1l = "{call REMOVE_OLDDATA}";

			try {
				dbConnection = getConnection();
				callableStatement = dbConnection.prepareCall(getDBUSERByUserIdSq1l);
				callableStatement.executeUpdate();
			} catch (SQLException e) {

				System.out.println(e.getMessage());

			} finally {

				if (callableStatement != null) {
					callableStatement.close();
				}

				if (dbConnection != null) {
					dbConnection.close();
				}

			}
		}
}
