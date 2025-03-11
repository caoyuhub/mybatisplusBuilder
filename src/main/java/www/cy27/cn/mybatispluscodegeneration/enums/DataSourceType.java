package www.cy27.cn.mybatispluscodegeneration.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum DataSourceType {

    DM("dm.jdbc.driver.DmDriver"),
    Mysql("com.mysql.cj.jdbc.Driver"),
    DB2("com.ibm.db2.jcc.DB2Driver"),
    ORACLE("oracle.jdbc.driver.OracleDriver"),
    Postgresql("org.postgresql.Driver"),
    SQLServer("com.microsoft.sqlserver.jdbc.SQLServerDriver"),
    ;


    public static void load() throws ClassNotFoundException {
        DataSourceType[] values = values();
        for(DataSourceType type:values){
            Class.forName(type.getDriver());
        }
    }

    DataSourceType(String driver) {
        this.driver = driver;
    }

    String driver;

    public String getDriver() {
        return driver;
    }

    public static String [] names(){

        DataSourceType[] values = values();

        String []result = new String[values.length];

        for (int i = 0; i < values.length; i++) {
            result[i] = values[i].toString();
        }

        return result;
    }

    public static String namesString(){
        return String.join(",",names());
    }

    public static DataSourceType getByName(String name){
        DataSourceType[] values = values();
        for (DataSourceType type:values){
            if(type.toString().equals(name)){
                return type;
            }
        }

        return null;
    }
}
