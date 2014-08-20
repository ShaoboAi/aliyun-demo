package ace.demo.service;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

public class RDS {
	private static String url = CONF.rdsUrl;
	private static String user = CONF.rdsUser;
	private static String passwd = CONF.rdsPasswd;
	private static String driver = CONF.rdsDriver;
	private static ComboPooledDataSource cpds  = new ComboPooledDataSource();
    private static RDS db = new RDS();;
    public static RDS getInstance(){
        return db;
    }
    private RDS() {
        try {
            cpds.setDriverClass( driver);
            cpds.setJdbcUrl( url );
            cpds.setUser(user);                                  
            cpds.setPassword(passwd);  
            
            cpds.setMinPoolSize(5);                                     
            cpds.setAcquireIncrement(5);
            cpds.setMaxPoolSize(30);
            cpds.setMaxIdleTime(60);
            //System.out.println(cpds.getDataSourceName());
            
        } catch (PropertyVetoException e) {
            e.printStackTrace();
            //log
        }   
    }
    
    public Connection getConn() {  
        try {
            return cpds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    protected void finalize() throws Throwable {
          DataSources.destroy(cpds);
          super.finalize();
    }
    
    public static void closeConn(Connection conn) {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Statement getStmt(Connection conn) {

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            return stmt;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closeStmt(Statement stmt) {
        try {
            if (stmt != null)
                stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static PreparedStatement getPs(Connection conn, String sql) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            return ps;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closePs(PreparedStatement ps) {
        try {
            if (ps != null)
                ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet doQuery(Statement stmt, String sql) {
        try {
            ResultSet rs = stmt.executeQuery(sql);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closeRs(ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}  