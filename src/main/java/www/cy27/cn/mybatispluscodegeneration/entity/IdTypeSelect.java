package www.cy27.cn.mybatispluscodegeneration.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.util.ArrayList;
import java.util.List;

public class IdTypeSelect {
    public IdTypeSelect(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public IdTypeSelect() {
    }

    String name;
    String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return getName();
    }

    public static IdTypeSelect getByCode(String code){
        for(IdTypeSelect type:allType){
            if(type.getCode().equals(code)){
                return type;
            }
        }
        return null;
    }

    public static List<IdTypeSelect> allType = new ArrayList<>();

    static {
        allType.add(new IdTypeSelect("AUTO(数据库ID自增)",IdType.AUTO.name()));
        allType.add(new IdTypeSelect("NONE(未设置主键)",IdType.NONE.name()));
        allType.add(new IdTypeSelect("INPUT(用户手动输入)",IdType.INPUT.name()));
        allType.add(new IdTypeSelect("ASSIGN_ID(默认雪花ID)",IdType.ASSIGN_ID.name()));
        allType.add(new IdTypeSelect("ASSIGN_UUID(默认UUID)",IdType.ASSIGN_UUID.name()));
    }
}
