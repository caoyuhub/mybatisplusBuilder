package www.cy27.cn.mybatispluscodegeneration.util;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import www.cy27.cn.mybatispluscodegeneration.entity.ProjectPathInfo;

import java.util.ArrayList;
import java.util.List;

public class SettingsUtil {


    public static List<ProjectPathInfo>  getOpenProjectList(AnActionEvent e){
        Project project = e.getProject();
        List<ProjectPathInfo> result = new ArrayList<>();
        Module @NotNull [] modules = ModuleManager.getInstance(project).getModules();
        for (Module module : modules) {
            ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);
            VirtualFile[] contentRoots = moduleRootManager.getContentRoots();
            for(VirtualFile file:contentRoots){
                ProjectPathInfo projectPathInfo = new ProjectPathInfo();
                projectPathInfo.setName(module.getName());
                projectPathInfo.setPath(file.getPath());
                result.add(projectPathInfo);
            }
        }
        return result;
    }
}
