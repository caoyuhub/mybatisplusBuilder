package www.cy27.cn.mybatispluscodegeneration.generator;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.jdbc.DatabaseMetaDataWrapper;
import com.baomidou.mybatisplus.generator.query.DefaultQuery;
import org.jetbrains.annotations.NotNull;
import www.cy27.cn.mybatispluscodegeneration.util.OracleUtil;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyDefaultQuery extends DefaultQuery {
    public MyDefaultQuery(@NotNull ConfigBuilder configBuilder) {
        super(configBuilder);
    }

    @Override
    protected List<DatabaseMetaDataWrapper.Index> getIndex(String tableName) {
        if(dataSourceConfig.getDbType() == DbType.ORACLE){
            tableName = "\""+tableName+"\"";
        }
        try{
            return super.getIndex(tableName);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    protected List<DatabaseMetaDataWrapper.Table> getTables() {
        List<DatabaseMetaDataWrapper.Table> tables = super.getTables();
        if(dataSourceConfig.getDbType() == DbType.ORACLE){
            for (DatabaseMetaDataWrapper.Table table:tables ) {
                if(ObjectUtil.isNotEmpty(table.getRemarks())){
                    continue;
                }
                try {
                    String tableComments = OracleUtil.getTableComments(dataSourceConfig.getConn(), dataSourceConfig.getSchemaName(), table.getName());
                    if(ObjectUtil.isNotEmpty(tableComments)){
                        Field remarks = table.getClass().getDeclaredField("remarks");
                        remarks.setAccessible(true);
                        remarks.set(table,tableComments);
                    }
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return tables;
    }

    @Override
    protected Map<String, DatabaseMetaDataWrapper.Column> getColumnsInfo(String tableName) {
        Map<String, DatabaseMetaDataWrapper.Column> columnsInfo = super.getColumnsInfo(tableName);
        if(dataSourceConfig.getDbType() == DbType.ORACLE){
            for (String key:columnsInfo.keySet()) {
                DatabaseMetaDataWrapper.Column column = columnsInfo.get(key);
                if(ObjectUtil.isNotEmpty(column.getRemarks())){
                    continue;
                }
                try {
                    String tableComments = OracleUtil.getColumnComment(dataSourceConfig.getConn(), dataSourceConfig.getSchemaName(), tableName, column.getName());
                    if(ObjectUtil.isNotEmpty(tableComments)){
                        Field remarks = column.getClass().getDeclaredField("remarks");
                        remarks.setAccessible(true);
                        remarks.set(column,tableComments);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return columnsInfo;
    }
}
