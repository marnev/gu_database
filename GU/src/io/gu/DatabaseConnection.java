package io.gu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseConnection {
	String sql = "";
	Connection conn = null;
	Statement stmt = null;

	public DatabaseConnection(String sql) {
		this.sql = sql;

		try {
			
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("yourDatabase", "yourUsername", "yourPassword");
			
			this.stmt = conn.createStatement();
			stmt.execute(sql);
			
			//conn.commit();
			conn.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Fehler im try/catch");
		}
		
	}

	public String getSql() {
		return sql;
	}
	
	public Statement getStmt() {
		return stmt;
	}

	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}
	
	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
}
