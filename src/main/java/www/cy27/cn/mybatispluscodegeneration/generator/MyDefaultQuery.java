package www.cy27.cn.mybatispluscodegeneration.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.jdbc.DatabaseMetaDataWrapper;
import com.baomidou.mybatisplus.generator.query.DefaultQuery;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MyDefaultQuery extends DefaultQuery {
    public MyDefaultQuery(@NotNull ConfigBuilder configBuilder) {
        super(configBuilder);
    }

    @Override
    protected List<DatabaseMetaDataWrapper.Index> getIndex(String tableName) {
        if(dataSourceConfig.getDbType() == DbType.ORACLE){
            tableName = "\""+tableName+"\"";
        }
        return super.getIndex(tableName);
    }
}
