package www.cy27.cn.mybatispluscodegeneration.entity;

public class ProjectPathInfo {

    /**
     * 项目（模块）名称
     */
    String name;

    /**
     * 绝对路径
     */
    String path;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return getName();
    }
}
