package www.cy27.cn.mybatispluscodegeneration.action;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;
import www.cy27.cn.mybatispluscodegeneration.enums.DataSourceType;
import www.cy27.cn.mybatispluscodegeneration.form.MainForm;
import www.cy27.cn.mybatispluscodegeneration.util.MyBatisIcon;

import javax.swing.*;

public class MainAction extends AnAction {


    static MainForm mainForm = null;

    public MainAction(){
        super("mybatisPlus代码生成器", "自动生成mybatis plus相关代码", MyBatisIcon.LOGO_22x22);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {



//        Project project = e.getProject();
//        if (project != null) {
//            FileChooserDescriptor descriptor = new FileChooserDescriptor(true, true, false, false, false, false);
//            VirtualFile selectedFolder = FileChooser.chooseFile(descriptor, project, null);
//            if (selectedFolder != null) {
//                String folderPath = selectedFolder.getPath();
//                // 在这里处理选择的文件夹路径
//                System.out.println("选择的文件夹路径: " + folderPath);
//            }
//        }

        if (mainForm == null) {
            // 窗口尚未创建，现在创建
            mainForm = new MainForm(e);
            mainForm.setVisible(true);

            try {
                DataSourceType.load();
            } catch (Exception e2) {
                Messages.showWarningDialog(e2.getMessage(), "驱动加载错误");
            }
        } else {
            // 窗口已存在，直接显示（或尝试置顶）
            mainForm.setVisible(true);
            mainForm.toFront();  // 可选：把窗口提到最前面
            mainForm.requestFocus(); // 可选：获取焦点
        }


    }
}
