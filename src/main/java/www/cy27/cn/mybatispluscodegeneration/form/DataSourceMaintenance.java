package www.cy27.cn.mybatispluscodegeneration.form;

import com.intellij.openapi.ui.Messages;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import www.cy27.cn.mybatispluscodegeneration.entity.DataSource;
import www.cy27.cn.mybatispluscodegeneration.util.DatasourceUtil;
import www.cy27.cn.mybatispluscodegeneration.util.MyBatisIcon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class DataSourceMaintenance extends JFrame {


    // 创建表格模型
    // 准备列名
    String[] columnNames = {"id","名称", "类型",  "JDBC-URL", "账号", "密码", "Schema"};
    DefaultTableModel model = new DefaultTableModel(new Object[][]{}, columnNames);
    JBTable datasourceTable = new JBTable(model);
    JButton addSource = new JButton("添加数据源");
    JButton updateSource = new JButton("修改数据源");
    JButton deleteSource = new JButton("删除数据源");

    public DataSourceMaintenance(){
        // 设置窗口标题
        setTitle("mybatisPlus代码生成器-数据源维护");
        setIconImage(((ImageIcon) MyBatisIcon.LOGO_22x22).getImage());
        // 设置窗口大小
        setSize(800, 500);
        //不允许调整大小
        setResizable(false);
        // 设置窗口位置居中
        setLocationRelativeTo(null);
        // 设置 BoxLayout 为垂直方向
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        JPanel toolsPanel = new JPanel();
        add(toolsPanel);
        toolsPanel.add(addSource);
        toolsPanel.add(updateSource);
        toolsPanel.add(deleteSource);


        //加载表格数据
        loadTableData();
        //table只能单选
        datasourceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // 设置表格的首选大小（可选）
        datasourceTable.setPreferredScrollableViewportSize(new Dimension(800, 400));
        JBScrollPane jbScrollPane = new JBScrollPane(datasourceTable);
        jbScrollPane.setPreferredSize(new Dimension(800, 400));
        add( jbScrollPane);



        //添加事件
        addSource.addActionListener(e -> {
            DataSourceEditing dataSourceEditing = new DataSourceEditing(null);
            dataSourceEditing.setVisible(true);
            dataSourceEditing.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    loadTableData();
                    setEnabled(true);
                }

                @Override
                public void windowClosed(WindowEvent e) {
                    loadTableData();
                    setEnabled(true);
                }
            });
            setEnabled(false);
        });

        //修改事件
        updateSource.addActionListener(e -> {
            int selectedRow = datasourceTable.getSelectedRow();
            if(selectedRow < 0){
                Messages.showErrorDialog("需要先选择一条数据","错误！");
                return;
            }
            TableModel tableModel = datasourceTable.getModel();
            String id = tableModel.getValueAt(datasourceTable.getSelectedRow(), 0).toString();
            DataSourceEditing dataSourceEditing = new DataSourceEditing(id);
            dataSourceEditing.setVisible(true);
            dataSourceEditing.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    loadTableData();
                    setEnabled(true);
                }

                @Override
                public void windowClosed(WindowEvent e) {
                    loadTableData();
                    setEnabled(true);
                }
            });
            setEnabled(false);
        });

        //删除事件
        deleteSource.addActionListener(e -> {
            int selectedRow = datasourceTable.getSelectedRow();
            if(selectedRow < 0){
                Messages.showErrorDialog("需要先选择一条数据","错误！");
                return;
            }
            TableModel tableModel = datasourceTable.getModel();
            String id = tableModel.getValueAt(datasourceTable.getSelectedRow(), 0).toString();

            DataSource dataSource = DatasourceUtil.getById(id);
            int result = Messages.showYesNoDialog("你确定要删除【" + dataSource.getName() + "】吗？",
                    "确认操作",
                    Messages.getWarningIcon());
            if (result == Messages.YES) {
                List<DataSource> allSource = DatasourceUtil.getAllSource();
                for (DataSource source:allSource){
                    if(id.equals(source.getId())){
                        allSource.remove(source);
                        break;
                    }
                }
                DatasourceUtil.updateSource(allSource);
                loadTableData();
            }
        });
    }

    public void loadTableData(){
        List<DataSource> allDataSource = DatasourceUtil.getAllSource();
        Object[][] data = new Object[allDataSource.size()][columnNames.length];
        for (int i = 0; i < allDataSource.size(); i++) {
            data[i][0] = allDataSource.get(i).getId();
            data[i][1] = allDataSource.get(i).getName();
            data[i][2] = allDataSource.get(i).getType();
            data[i][3] = allDataSource.get(i).getUrl();
            data[i][4] = allDataSource.get(i).getUsername();
            data[i][5] = allDataSource.get(i).getPassword();
            data[i][6] = allDataSource.get(i).getSchema();
        }
        model.setDataVector(data,columnNames);
        toFront();

        //隐藏ID
        TableColumn column = datasourceTable.getColumnModel().getColumn(0); // 获取第一列
        column.setMaxWidth(0);
        column.setMinWidth(0);
        column.setPreferredWidth(0);
    }

}
