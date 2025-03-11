package www.cy27.cn.mybatispluscodegeneration.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONArray;
import www.cy27.cn.mybatispluscodegeneration.entity.DataSource;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DatasourceUtil {
    // 获取当前用户的主目录
    static final File config = new File(System.getProperty("user.home")+"/.cy27-mybatisPlus");

    static List<DataSource> data = null;

    /**
     * 更新内存数据
     */
    private static void updateData(){
        JSONArray array = new JSONArray(FileUtil.readString(config, StandardCharsets.UTF_8));
        data = array.toList(DataSource.class);
    }

    static {
        if(!config.exists()){
            FileUtil.writeString("[]",config,"UTF-8");
        }
    }


    /**
     * 通过ID查询数据源
     * @param id
     * @return
     */
    public static DataSource getById(String id){
        List<DataSource> allSource = getAllSource();
        for (DataSource dataSource:allSource){
            if(dataSource.getId().equals(id)){
                return dataSource;
            }
        }
        return null;
    }

    /**
     * 获取配置文件中的所有数据源
     * @return
     */
    public static List<DataSource> getAllSource(){
        if(data == null){
            updateData();
        }
        return data;
    }


    /**
     * 添加数据源
     * @param dataSource
     */
    public static void addSource(DataSource dataSource){
        JSONArray array = new JSONArray(FileUtil.readString(config, StandardCharsets.UTF_8));
        array.add(dataSource);
        FileUtil.writeString(array.toJSONString(1),config,"UTF-8");
        updateData();
    }

    /**
     * 修改数据源
     * @param list
     */
    public static void updateSource(List<DataSource> list){
        FileUtil.writeString(new JSONArray(list).toJSONString(1),config,"UTF-8");
        updateData();
    }

}
