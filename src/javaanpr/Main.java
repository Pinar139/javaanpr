
package javaanpr;
import javaanpr.gui.ReportGenerator;
import javaanpr.recognizer.NeuralPatternClassificator;
import java.io.File;
import java.io.IOException;
import javax.swing.UIManager;
import javaanpr.configurator.Configurator;
import javaanpr.gui.windows.FrameComponentInit;
import javaanpr.gui.windows.FrameMain;
import javaanpr.imageanalysis.CarSnapshot;
import javaanpr.imageanalysis.Char;
import javaanpr.intelligence.Intelligence;


public class Main {
    public static ReportGenerator rg = new ReportGenerator();
    public static Intelligence systemLogic;
    public static String helpText = "" ;
    
    public static void newAlphabet(String srcdir, String dstdir) throws Exception { 
        File folder = new File(srcdir);
        
        if (!folder.exists()) throw new IOException("Source folder doesn't exists");
        
        if (!new File(dstdir).exists()) throw new IOException("Destination folder doesn't exists");
        
        int x = Intelligence.configurator.getIntProperty("char_normalizeddimensions_x");
        
        int y = Intelligence.configurator.getIntProperty("char_normalizeddimensions_y");
        
        System.out.println("\nCreating new alphabet ("+x+" x "+y+" px)... \n");
        
        for (String fileName : folder.list()) {
            
            Char c = new Char(srcdir+File.separator+fileName);
            
            c.normalize();
            
            c.saveImage(dstdir+File.separator+fileName);
            
            System.out.println(fileName+" done");
        }
    }
    
   
    public static void learnAlphabet(String destinationFile) throws Exception {
        try {
            
            File f = new File(destinationFile);
            
            f.createNewFile();
            } 
        
        catch (Exception e)
        {
            
            throw new IOException("Can't find the path specified");
        }
        
        System.out.println();
        
        NeuralPatternClassificator npc = new NeuralPatternClassificator(true);
        
        npc.network.saveToXml(destinationFile);
    }
    
    public static void main(String[] args) throws Exception {
        
        if (args.length==0 || (args.length==1 && args[0].equals("-gui"))) {
            
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            FrameComponentInit frameComponentInit = new FrameComponentInit();
            
            Main.systemLogic = new Intelligence(false);
            
            frameComponentInit.dispose(); 
            
            FrameMain mainFrame = new FrameMain();
        } 
        else if (args.length==3 &&
                
                args[0].equals("-recognize") &&
                
                args[1].equals("-i")
                
                ) {
            
            try {
                
                Main.systemLogic = new Intelligence(false);
                
                System.out.println(systemLogic.recognize(new CarSnapshot(args[2])));
            }
            catch (IOException e) 
            {
                System.out.println(e.getMessage());
            }
           } 
        else if (args.length==5 &&
                
                args[0].equals("-recognize") &&
                
                args[1].equals("-i") &&
                
                args[3].equals("-o")
                ) {
           
            try {
                Main.rg = new ReportGenerator(args[4]);
                
                Main.systemLogic = new Intelligence(true); 
                
                Main.systemLogic.recognize(new CarSnapshot(args[2]));
                
                Main.rg.finish();
          } 
            catch (IOException e) 
            {
                System.out.println(e.getMessage());
            }
            
        } 
        
        else if (args.length==3 &&
                
                args[0].equals("-newconfig") &&
                
                args[1].equals("-o")
                ) {
            
            Configurator configurator = new Configurator();
            
            try {
                
                configurator.saveConfiguration(args[2]);
                } 
            catch (IOException e) 
            {
                System.out.println(e.getMessage());
            }
        } else if (args.length==3 &&
                args[0].equals("-newnetwork") &&
                args[1].equals("-o")
                ) {
            
            try 
            {
                 learnAlphabet(args[2]);
            } 
                catch (Exception e) {
                System.out.println(e.getMessage());
            }
            } 
          else if (args.length==5 &&
                args[0].equals("-newalphabet") &&
                args[1].equals("-i") &&
                args[3].equals("-o")
                ) {
         
            try {
                newAlphabet(args[2],args[4]);
           
                } 
            catch (Exception e) {
                                   System.out.println(e.getMessage());
                                }
             }
        else 
        {
           System.out.println(helpText);
        }
    }
}
