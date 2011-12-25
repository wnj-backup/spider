package com.spider;

import java.sql.*;

/**
 * Created by IntelliJ IDEA.
 * User: wnj
 * Date: 2010-11-26
 * Time: 13:48:45
 * To change this template use File | Settings | File Templates.
 */
public class DataService {
    public static final ThreadLocal<Connection> _conn = new ThreadLocal<Connection>();

    public static int save(TuanEntity tuan){
        Connection con=null;
        PreparedStatement pst=null;
        try{
            con=getConnection();
            pst=con.prepareStatement("insert into tuan_base(`name`,`jumpaddr`,`realaddr`,`apiaddr`,`cts`) values (?,?,?,?,now())");
            int index=1;
            pst.setString(index++, tuan.getName() == null? "":tuan.getName());
            pst.setString(index++, tuan.getJumpAddr() == null? "":tuan.getJumpAddr());
            pst.setString(index++, tuan.getRealAddr() == null? "":tuan.getRealAddr());
            pst.setString(index++, tuan.getApiAddr() == null? "":tuan.getApiAddr());
            
            return pst.executeUpdate();
        }catch(Throwable th){
            th.printStackTrace();
        }finally{
            try{
                if(pst!=null){
                    pst.close();
                }
                clearAll();
            }catch(Throwable th){
                th.printStackTrace();
            }
        }
        return -1;
    }
    public static int save2(ApiEntity api){
        Connection con=null;
        PreparedStatement pst=null;
        try{
            con=getConnection();
            pst=con.prepareStatement("insert into tuan_api(`tuanid`,`url`,`cts`) values (?,?,now())");            
            int index=1;
            pst.setInt(index++, api.getTuanid());
            pst.setString(index++, api.getUrl());

            return pst.executeUpdate();
        }catch(Throwable th){
            th.printStackTrace();
        }finally{
            try{
                if(pst!=null){
                    pst.close();
                }
                clearAll();
            }catch(Throwable th){
                th.printStackTrace();
            }
        }
        return -1;
    }

    public static int save3(int cid, String s){
        Connection con=null;
        PreparedStatement pst=null;
        try{
            con=getConnection();
            pst=con.prepareStatement("insert into tuan_city_relation (select id as tuanid,? as cityid from tuan_base where name=?)");
            int index=1;
            pst.setInt(index++, cid);
            pst.setString(index++, s);

            return pst.executeUpdate();
        }catch(Throwable th){
            th.printStackTrace();
        }finally{
            try{
                if(pst!=null){
                    pst.close();
                }
                clearAll();
            }catch(Throwable th){
                th.printStackTrace();
            }
        }
        return -1;
    }

    public static int getCityId(String cityName){
        Connection con=null;
        PreparedStatement pst=null;
        ResultSet set = null;
        try{
            con=getConnection();
            pst=con.prepareStatement("select id from tuan_city where cityname=?");
            int index=1;
            pst.setString(index++, cityName);

            set = pst.executeQuery();
            set.next();
            return set.getInt("id");
        }catch(Throwable th){
            th.printStackTrace();
        }finally{
            try{
                if(set!=null){
                    set.close();
                }
                if(pst!=null){
                    pst.close();
                }
                clearAll();
            }catch(Throwable th){
                th.printStackTrace();
            }
        }
        return -1;
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = _conn.get();
        if(conn == null){
            conn = DriverManager.getConnection("proxool.mess");
            _conn.set(conn);
        }
        return conn;
    }

    public static void clearAll() throws SQLException{
       closeConn();
    }

    private static void closeConn() throws SQLException{
		Connection conn = _conn.get();
		_conn.set(null);
		if (conn != null) {
            if (!conn.isClosed()) {
    			conn.close();
            }
		}
	}    
}
