

package javaanpr.gui.tools;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class ImageFileFilter extends FileFilter {
    public boolean accept(File f) {
        String name = f.getName();
        String type = name.substring(name.lastIndexOf('.')+1,name.length()).toLowerCase();
        return !(!type.equals("bmp") &&
                !type.equals("jpg") &&
                !type.equals("jpeg") &&
                !type.equals("png") &&
                !type.equals("gif") &&
                !f.isDirectory());
    }
    
    public static boolean accept(String name) {
        String type = name.substring(name.lastIndexOf('.')+1,name.length()).toLowerCase();
        return !(!type.equals("bmp") &&
                !type.equals("jpg") &&
                !type.equals("jpeg") &&
                !type.equals("png") &&
                !type.equals("gif"));
    }

    public String getDescription() {
        return "images (*.jpg, *.bmp, *.gif, *.png)"; 
    }
}
