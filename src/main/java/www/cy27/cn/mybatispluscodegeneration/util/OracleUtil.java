package www.cy27.cn.mybatispluscodegeneration.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * oracle相关工具诶
 */
public class OracleUtil {

    public static String getTableComments(Connection conn,String schema,String tableName) throws SQLException
    {

        ResultSet rs = null;
        PreparedStatement pstmt = null;
        String sql = "SELECT COMMENTS FROM ALL_TAB_COMMENTS WHERE OWNER = ? AND TABLE_NAME = ? AND COMMENTS IS NOT NULL";
        try  {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, schema);
            pstmt.setString(2, tableName);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("COMMENTS");
            }

        }finally {
            if(rs!=null){
                rs.close();
            }
            if(pstmt!=null){
                pstmt.close();
            }
//            if(conn!=null){
//                conn.close();
//            }
        }

        return null;
    }


    /**
     * 获取单个列的注释
     * @param conn 数据库连接
     * @param schemaName 模式名
     * @param tableName 表名
     * @param columnName 列名
     * @return 列的注释，如果没有则返回 null
     */
    public static String getColumnComment(Connection conn, String schemaName, String tableName, String columnName) throws SQLException {
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        String sql = "SELECT COMMENTS FROM ALL_COL_COMMENTS WHERE OWNER = ? AND TABLE_NAME = ? AND COLUMN_NAME = ? AND COMMENTS IS NOT NULL";
        try  {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, schemaName);
            pstmt.setString(2, tableName);
            pstmt.setString(3, columnName);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("COMMENTS");
            }
        }finally {
            if(rs!=null){
                rs.close();
            }
            if(pstmt!=null){
                pstmt.close();
            }
        }
        return null;
    }
}
