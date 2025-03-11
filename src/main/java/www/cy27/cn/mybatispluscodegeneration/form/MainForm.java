package www.cy27.cn.mybatispluscodegeneration.form;

import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextField;
import com.intellij.ui.table.JBTable;
import com.intellij.util.xml.ui.PsiClassPanel;
import org.jetbrains.annotations.NotNull;
import www.cy27.cn.mybatispluscodegeneration.entity.DataSource;
import www.cy27.cn.mybatispluscodegeneration.entity.GenerationParameters;
import www.cy27.cn.mybatispluscodegeneration.entity.IdTypeSelect;
import www.cy27.cn.mybatispluscodegeneration.entity.ProjectPathInfo;
import www.cy27.cn.mybatispluscodegeneration.service.GeneratorService;
import www.cy27.cn.mybatispluscodegeneration.util.DatasourceUtil;
import www.cy27.cn.mybatispluscodegeneration.util.MyBatisIcon;
import www.cy27.cn.mybatispluscodegeneration.util.ParametersUtil;
import www.cy27.cn.mybatispluscodegeneration.util.SettingsUtil;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class MainForm extends JFrame {


    public AnActionEvent e;

    String title = "MyBatisPlus代码生成工具";

//    当前页面的总宽度
    int width = 1020;
//    当前页面的总高度
    int height = 800;


    //生成模版的服务类
    GeneratorService generatorService;


    //数据源选择相关组件
    JPanel databaseSelectPanel = new JPanel();
    public DefaultComboBoxModel<DataSource> model = new DefaultComboBoxModel<>();
    public ComboBox<DataSource > datasourceSelect = new ComboBox<>(model);
    JButton maintenance = new JButton("数据源维护");
    JButton loadDatasource = new JButton("加载");


    //数据表格相关组件
    String[] columnNames = {"表名","注释"};
    DefaultTableModel tableModel = new DefaultTableModel(new Object[][]{}, columnNames);
    public JBTable datasourceTable = new JBTable(tableModel);
    JBScrollPane jbScrollPane = new JBScrollPane(datasourceTable);


    //设置相关组件

    // 第一行
    DefaultComboBoxModel<ProjectPathInfo> projectComboBoxModel = new DefaultComboBoxModel<>();
    ComboBox<ProjectPathInfo > projectSelect = new ComboBox<>(projectComboBoxModel);
    public JBTextField outputDir = new JBTextField();
    public JBTextField packagePath = new JBTextField();
    public JBTextField author = new JBTextField();

    // 第二行
    public JCheckBox entity = new JCheckBox("Entity");
    public JBTextField entityPackage = new JBTextField();
    public JCheckBox service = new JCheckBox("Service");
    public JBTextField servicePackage = new JBTextField();
    public JCheckBox serviceImpl = new JCheckBox("Service.Impl");
    public JBTextField serviceImplPackage = new JBTextField();

    public JCheckBox mapper = new JCheckBox("Mapper");
    public JBTextField mapperPackage = new JBTextField();
    public JCheckBox controller = new JCheckBox("Controller");
    public JBTextField controllerPackage = new JBTextField();

    // 第三行
    public JBTextField entityParent = new JBTextField();
    public JBTextField entityField = new JBTextField();
    public JBTextField controllerParent = new JBTextField();

    //第四行
    public JCheckBox lombok = new JCheckBox("lombok");
    public JCheckBox restController = new JCheckBox("restController");
    public JCheckBox swagger = new JCheckBox("swagger");
    public JCheckBox springDoc = new JCheckBox("springDoc");
    public JCheckBox resultMap = new JCheckBox("ResultMap");
    public JCheckBox kotlin = new JCheckBox("kotlin");
    public JCheckBox open = new JCheckBox("结束后打开文件夹");
    public ComboBox<IdTypeSelect> idType = new ComboBox<>( IdTypeSelect.allType.toArray(new IdTypeSelect[]{}));

    //第五行
    JButton save = new JButton("保存参数");
    JButton generate = new JButton("生成到项目");
    public JBTextField entitySuffix = new JBTextField();


    public MainForm(AnActionEvent e){
        try{
            this.e = e;
            //当前窗口的设置
            formSettings();

            //数据源选择面板设置
            databaseSelectPanelSettings();

            //加载数据表
            tableInfoSettings(400);

            //设置面板
            settingsPanel(320);

            //填充所有参数
            ParametersUtil.fillFrom(this);
        }catch (Exception ex2){
            ex2.printStackTrace();
            Messages.showErrorDialog(ex2.getMessage(),"错误");
        }
    }

    /**
     * 参数设置面板
     */
    private void settingsPanel(int height){

        JPanel settingsPanel =new JPanel();
        settingsPanel.setPreferredSize(new Dimension(width,height));
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        add(settingsPanel);



        JPanel settingsPanel_1 = addJPanel(settingsPanel,"全局设置");
        settingsPanel_1.add(new JLabel("作者",SwingConstants.LEFT));
        author.setPreferredSize(new Dimension(100,25));
        settingsPanel_1.add(author);
        settingsPanel_1.add(new JLabel("模块",SwingConstants.LEFT));
        settingsPanel_1.add(projectSelect);
        projectSelect.setPreferredSize(new Dimension(200, 25));
        settingsPanel_1.add(new JLabel("输出路径",SwingConstants.LEFT));
        outputDir.setPreferredSize(new Dimension(300,25));
        settingsPanel_1.add(outputDir);
        projectSelect.addActionListener(e -> {
            if(projectSelect.getItem() != null){
                outputDir.setText(projectSelect.getItem().getPath());
            }
        });
        settingsPanel_1.add(new JLabel("包路径",SwingConstants.LEFT));
        packagePath.setPreferredSize(new Dimension(200,25));
        settingsPanel_1.add(packagePath);


        JPanel settingsPanel_2 = addJPanel(settingsPanel,"生成包设置");
        settingsPanel_2.add(entity);
        entityPackage.setPreferredSize(new Dimension(80,25));
        settingsPanel_2.add(entityPackage);

        settingsPanel_2.add(service);
        servicePackage.setPreferredSize(new Dimension(80,25));
        settingsPanel_2.add(servicePackage);

        settingsPanel_2.add(serviceImpl);
        serviceImplPackage.setPreferredSize(new Dimension(160,25));
        settingsPanel_2.add(serviceImplPackage);

        settingsPanel_2.add(mapper);
        mapperPackage.setPreferredSize(new Dimension(80,25));
        settingsPanel_2.add(mapperPackage);

        settingsPanel_2.add(controller);
        controllerPackage.setPreferredSize(new Dimension(80,25));
        settingsPanel_2.add(controllerPackage);


        JPanel settingsPanel_3 = addJPanel(settingsPanel,"父类设置");
        settingsPanel_3.add(new JLabel("实体类父类（完整路径）",SwingConstants.LEFT));
        entityParent.setPreferredSize(new Dimension(180,25));
        settingsPanel_3.add(entityParent);

        settingsPanel_3.add(new JLabel("实体类字段（数据库字段）",SwingConstants.LEFT));
        entityField.setPreferredSize(new Dimension(180,25));

        String toolTipText = "数据库字段逗号隔开 例如：id,user_name,password";
        // 自定义工具提示
        // 创建一个透明的 JWindow 作为工具提示
        JWindow tooltipWindow = new JWindow();
        JLabel tooltipLabel = new JLabel(toolTipText);
        tooltipLabel.setOpaque(true);
        tooltipLabel.setHorizontalAlignment(JLabel.CENTER);
        tooltipLabel.setBorder(BorderFactory.createLineBorder(JBColor.border(), 1));
        tooltipLabel.setBackground(JBColor.background());
        tooltipLabel.setForeground(JBColor.foreground());
        tooltipLabel.setFont(UIManager.getFont("Label.font")); // 使用系统字体

        tooltipWindow.add(tooltipLabel);
        tooltipWindow.pack();

        // 添加焦点监听器
        entityField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // 获得焦点时显示工具提示
                Point location = entityField.getLocationOnScreen();
                tooltipWindow.setLocation(location.x, location.y + entityField.getHeight());
                tooltipWindow.setVisible(true);
            }

            @Override
            public void focusLost(FocusEvent e) {
                // 失去焦点时隐藏工具提示
                tooltipWindow.setVisible(false);
            }
        });

        settingsPanel_3.add(entityField);


        settingsPanel_3.add(new JLabel("控制器父类（完整路径）",SwingConstants.LEFT));
        controllerParent.setPreferredSize(new Dimension(180,25));
        settingsPanel_3.add(controllerParent);

        JPanel settingsPanel_4 = addJPanel(settingsPanel,"选项");
        settingsPanel_4.add(lombok);
        settingsPanel_4.add(restController);
        settingsPanel_4.add(swagger);
        settingsPanel_4.add(springDoc);
        settingsPanel_4.add(kotlin);
        settingsPanel_4.add(resultMap);
        settingsPanel_4.add(open);
        settingsPanel_4.add(new JLabel("    ID类型选择",SwingConstants.LEFT));
        idType.setPreferredSize(new Dimension(220,25));
        settingsPanel_4.add(idType);

        JPanel settingsPanel_5 = addJPanel(settingsPanel,"其他");

        settingsPanel_5.add(new JLabel("实体类后缀",SwingConstants.LEFT));
        entitySuffix.setPreferredSize(new Dimension(80,25));
        settingsPanel_5.add(entitySuffix);

        settingsPanel_5.add(save);
        settingsPanel_5.add(generate);
        save.addActionListener( e -> {
            try{
                ParametersUtil.getParameters(this);
                Messages.showInfoMessage("保存成功","成功");
            }catch (RuntimeException e2){
                e2.printStackTrace();
                Messages.showErrorDialog(e2.getMessage(),"错误");
            }
        });
        generate.addActionListener(e -> {
            try{
                getGeneratorService().execute();
                Messages.showInfoMessage("生成成功","成功");
            }catch (RuntimeException e2){
                e2.printStackTrace();
                Messages.showErrorDialog(e2.getMessage(),"错误");
            }

        });
    }

    private JPanel addJPanel(JPanel mainPanel,String title){
        JPanel settingsPanel = new JPanel();
        mainPanel.add(settingsPanel);
        settingsPanel.setPreferredSize(new Dimension(width, 60));
        settingsPanel.setMaximumSize(new Dimension(width, 60));

        // 设置 TitledBorder
        TitledBorder titledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.lightGray), // 边框线
                title, // 标题文字
                TitledBorder.DEFAULT_JUSTIFICATION, // 标题对齐方式
                TitledBorder.DEFAULT_POSITION, // 标题位置
                new Font("Serif", Font.BOLD, 14), // 标题字体
                Color.white // 标题颜色
        );

        settingsPanel.setBorder(titledBorder);

        return settingsPanel;
    }

    /**
     * 窗口设置
     */
    private void formSettings(){
        // 设置窗口标题
        setTitle(title);
        //不允许调整大小
        setResizable(false);
        // 设置窗口大小
        setSize(width, height);
        // 设置窗口位置居中
        setLocationRelativeTo(null);

        setIconImage(((ImageIcon) MyBatisIcon.LOGO_22x22).getImage());

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        loadSelect();
        loadSettings();
    }

    /**
     * 数据源面板设置
     */
    private void databaseSelectPanelSettings(){

        //吧面板添加到主页面中
        add(databaseSelectPanel);

        //添加一个文字
        JLabel label = new JLabel("选择数据源", JLabel.LEFT);
        databaseSelectPanel.add(label);

        //设置数据源选择下拉框的大小
        datasourceSelect.setPreferredSize(new Dimension(500,25));

        //面板中添加下拉框和按钮
        databaseSelectPanel.add(datasourceSelect);
        databaseSelectPanel.add(loadDatasource);
        databaseSelectPanel.add(maintenance);

        //数据源维护按钮点击
        maintenance.addActionListener(e -> {
            DataSourceMaintenance dataSourceMaintenance = new DataSourceMaintenance();
            dataSourceMaintenance.setVisible(true);
            dataSourceMaintenance.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    loadSelect();
                    setEnabled(true);
                }

                @Override
                public void windowClosed(WindowEvent e) {
                    loadSelect();
                    setEnabled(true);
                }
            });
            setEnabled(false);
        });

        //加载数据按钮点击
        loadDatasource.addActionListener(e -> {
            loadSettings();
            loadTableInfo();
        });
    }

    /**
     * 加载数据表设置
     */
    private void tableInfoSettings(int height){
        datasourceTable.setPreferredScrollableViewportSize(new Dimension(width, height));
        jbScrollPane.setPreferredSize(new Dimension(width, height));
        add(jbScrollPane);
    }

    /**
     * 重新加载下拉框
     */
    public void loadSelect(){
        model.removeAllElements();
        for(DataSource source:DatasourceUtil.getAllSource()){
            model.addElement(source);
        }
        toFront();
    }


    /**
     * 加载表格信息
     */
    public void loadTableInfo(){
        try{
            DataSource datasource = datasourceSelect.getItem();
            setTitle(title +"-"+ datasource);

            if(datasource == null){
                Messages.showErrorDialog("未选择数据源信息","错误");
                return;
            }

            generatorService = new GeneratorService(datasource,this);
            //获取所有的表信息
            List<TableInfo> tableInfoList = generatorService.getTableInfoList();
            Object[][] data = new Object[tableInfoList.size()][2];
            for (int i = 0; i < tableInfoList.size(); i++) {
                data[i][0] = tableInfoList.get(i).getName();
                data[i][1] = tableInfoList.get(i).getComment();
            }
            tableModel.setDataVector(data,columnNames);
        }catch (Exception e){
            e.printStackTrace();
            Messages.showErrorDialog(e.getMessage(),"错误");
        }
    }

    public void loadSettings(){
        projectComboBoxModel.removeAllElements();
        projectComboBoxModel.addAll(SettingsUtil.getOpenProjectList(e));
    }

    public GeneratorService getGeneratorService(){
        if(generatorService == null){
            throw new RuntimeException("未加载数据源。");
        }
        return generatorService;
    }
}
