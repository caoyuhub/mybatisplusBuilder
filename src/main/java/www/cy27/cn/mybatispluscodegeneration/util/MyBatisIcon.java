package www.cy27.cn.mybatispluscodegeneration.util;

import com.intellij.ui.IconManager;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class MyBatisIcon {

    private static @NotNull Icon load(@NotNull String path, int cacheKey, int flags) {
        return IconManager.getInstance().loadRasterizedIcon(path, MyBatisIcon.class.getClassLoader(), cacheKey, flags);
    }

//    public static final @NotNull Icon LOGO_22x22 = load("mybatisIcon22x22.png", -100010086, 2);
    public static final @NotNull Icon LOGO_22x22 = new ImageIcon(MyBatisIcon.class.getResource("/mybatisIcon22x22.png"));
    public static final @NotNull Icon LOGO_25x25 = new ImageIcon(MyBatisIcon.class.getResource("/mybatisIcon25x25.png"));
}
