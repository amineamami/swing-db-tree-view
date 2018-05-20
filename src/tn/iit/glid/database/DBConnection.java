package tn.iit.glid.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class DBConnection {

	private Connection connection = null;

	public boolean openConnection(String username, String password, String sid) {
		try {
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:" + sid, username, password);
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	public void closeConnection() {
		if (!connection.equals(null)) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<String> getTables() {
		List<String> tableNames = new ArrayList<String>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select table_name from user_tables");
			while (resultSet.next()) {
				tableNames.add(resultSet.getString("table_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableNames;
	}

	public List<String> getTableColumns(String tableName) {

		List<String> columnNames = new ArrayList<String>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement
					.executeQuery("select column_name from user_tab_cols where table_name='" + tableName + "'");
			while (resultSet.next()) {
				columnNames.add(resultSet.getString("column_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return columnNames;
	}

	public List<String> getViews() {
		List<String> viewNames = new ArrayList<String>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select view_name from user_views");
			while (resultSet.next()) {
				viewNames.add(resultSet.getString("view_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return viewNames;
	}

	public List<String> getViewColumns(String viewName) {
		List<String> viewColumns = new ArrayList<String>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement
					.executeQuery("select column_name from user_tab_cols where table_name='" + viewName + "'");
			while (resultSet.next()) {
				viewColumns.add(resultSet.getString("column_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return viewColumns;
	}

	public List<String> getTableConstraints(String tableName) {
		List<String> tableConstraints = new ArrayList<String>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement
					.executeQuery("select constraint_name from all_constraints where table_name='" + tableName + "'");
			while (resultSet.next()) {
				tableConstraints.add(resultSet.getString("constraint_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableConstraints;
	}

	public List<String> getIndexes() {
		List<String> indexes = new ArrayList<String>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select index_name from user_indexes");
			while (resultSet.next()) {
				indexes.add(resultSet.getString("index_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return indexes;
	}

	public List<String> getSequences() {
		List<String> sequences = new ArrayList<String>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select sequence_name from USER_SEQUENCES");
			while (resultSet.next()) {
				sequences.add(resultSet.getString("sequence_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sequences;
	}

	public List<String> getProcedures() {
		List<String> procedureNames = new ArrayList<String>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select procedure_name from user_procedures");
			while (resultSet.next()) {
				procedureNames.add(resultSet.getString("procedure_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return procedureNames;
	}

	public List<String> getTriggers() {
		List<String> triggerName = new ArrayList<String>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select trigger_name from user_triggers");
			while (resultSet.next()) {
				triggerName.add(resultSet.getString("trigger_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return triggerName;
	}

	public ResultSet executeAnyQuery(String query) {
		ResultSet resultSet = null;
		try {
			Statement statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
		}
		return resultSet;
	}

	public ResultSet getExecutePlan(String query) {
		ResultSet resultSet = null;
		try {
			Statement statement = connection.createStatement();
			statement.execute("explain plan for " + query);
			resultSet = statement
					.executeQuery("select plan_table_output from table(dbms_xplan.display('plan_table',null,'basic'))");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	public ResultSet getTableData(String tableName) {
		ResultSet resultSet = null;
		try {
			Statement statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from " + tableName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	public static void main(String[] args) {
		DBConnection connection  = new DBConnection();
		
		ArrayList<String> arr = new ArrayList<String>();
		
	
	}

}
