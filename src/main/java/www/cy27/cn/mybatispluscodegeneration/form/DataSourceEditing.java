package www.cy27.cn.mybatispluscodegeneration.form;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.components.JBTextField;
import www.cy27.cn.mybatispluscodegeneration.entity.DataSource;
import www.cy27.cn.mybatispluscodegeneration.enums.DataSourceType;
import www.cy27.cn.mybatispluscodegeneration.util.DatasourceUtil;
import www.cy27.cn.mybatispluscodegeneration.util.MyBatisIcon;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.UUID;

public class DataSourceEditing extends JFrame {

    DataSource dataSource = null;

    JBTextField nameText = new JBTextField();
    JBTextField typeText = new JBTextField();
    JBTextField urlText = new JBTextField();
    JBTextField usernameText = new JBTextField();
    JBTextField passwordText = new JBTextField();

    JBTextField schemaText = new JBTextField();

    JButton test = new JButton("测试连接");
    JButton save = new JButton("保存连接");

    String initId;

    public DataSourceEditing(String id){
        initId = id;
        dataSource = DatasourceUtil.getById(id);

        String text="添加数据源";

        if(dataSource != null){
            text = "【" + dataSource.getName() + "】修改";
            nameText.setText(dataSource.getName());
            urlText.setText(dataSource.getUrl());
            usernameText.setText(dataSource.getUsername());
            passwordText.setText(dataSource.getPassword());
            typeText.setText(dataSource.getType());
            schemaText.setText(dataSource.getSchema());
        }

        // 设置窗口标题
        setTitle("mybatisPlus代码生成器-" + text);
        setIconImage(((ImageIcon) MyBatisIcon.LOGO_22x22).getImage());
        // 设置窗口大小
        setSize(800, 250);
        // 设置窗口位置居中
        setLocationRelativeTo(null);
        // 设置 BoxLayout 为垂直方向

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setResizable(false);

        typeText.setEnabled(false);

        addRow("名  称 : ",nameText);
        addRow("U R L : ",urlText);
        addRow("Schema(目前针对ORACLE有效，默认为用户名): ",schemaText);
        addRow("用户名 : ",usernameText);
        addRow("密  码 : ",passwordText);
        addRow("类型(自动识别)",typeText);

        //添加两个按钮组件
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setPreferredSize(new Dimension(800, 30)); // 设置首选大小
        panel.setMaximumSize(new Dimension(800, 30)); // 设置最大大小

        panel.add(new JLabel("支持的数据库有："+ DataSourceType.namesString()));

        panel.add(test);
        panel.add(save);
        add(panel);


        test.addActionListener(e -> {
            try{
                if(testConn()){
                    Messages.showInfoMessage("连接成功！","成功");
                }
            }catch (Exception e1){
                Messages.showErrorDialog(e1.getMessage(),"错误");
            }
        });

        save.addActionListener(e -> {
            try{
                if(testConn()){
                    if(ObjectUtil.isEmpty(initId)){
                        //新增
                        List<DataSource> allSource = DatasourceUtil.getAllSource();
                        DataSource data = getDataSource();
                        data.setId(UUID.randomUUID().toString());
                        allSource.add(data);
                        DatasourceUtil.updateSource(allSource);
                    }else{
                        //修改
                        DataSource source = DatasourceUtil.getById(id);
                        DataSource nowSource = getDataSource();
                        source.setPassword(nowSource.getPassword());
                        source.setName(nowSource.getName());
                        source.setUrl(nowSource.getUrl());
                        source.setUsername(nowSource.getUsername());
                        source.setType(nowSource.getType());
                        source.setSchema(nowSource.getSchema());
                        DatasourceUtil.updateSource(DatasourceUtil.getAllSource());
                    }

                    Messages.showInfoMessage("操作成功！","成功了");
                    dispose();
                }
            }catch (Exception e1){
                Messages.showErrorDialog(e1.getMessage(),"错误");
            }
        });
    }


    public boolean testConn(){
        DataSource datasource = getDataSource();
        DataSourceConfig.Builder builder = new DataSourceConfig.Builder(
                datasource.getUrl(),
                datasource.getUsername(),
                datasource.getPassword());
        try {

            typeText.setText(builder.build().getDbType().getDb());
            builder.build().getConn();
            return true;
        }catch (Exception e2){
           throw e2;
        }
    }

    //添加一行组件
    public void addRow(String name, Component component) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS)); // 设置水平布局
        panel.setPreferredSize(new Dimension(800, 30)); // 设置首选大小
        panel.setMaximumSize(new Dimension(800, 30)); // 设置最大大小
        panel.add(new JLabel(name));
        panel.add(component);
        component.setPreferredSize(new Dimension(650, 30));
        add(panel);
    }

    private DataSource getDataSource(){
        DataSource data = new DataSource();
        data.setName(nameText.getText());
        data.setType(typeText.getText());
        data.setUrl(urlText.getText());
        data.setUsername(usernameText.getText());
        data.setPassword(passwordText.getText());
        data.setSchema(schemaText.getText());
        return data;
    }
}
