
package javaanpr.configurator;

import java.util.*;
import java.io.*;
import java.awt.*;

public class Configurator {
   
    private String fileName = "config.xml";
    private String comment = "This si global configuration file for Automatic Number Plate Recognition System";
    private Properties list;
    public Configurator() {
        list = new Properties();
        this.setIntProperty("photo_adaptivethresholdingradius", 7); 
        this.setDoubleProperty("bandgraph_peakfootconstant", 0.55); 
        this.setDoubleProperty("bandgraph_peakDiffMultiplicationConstant", 0.2);
        this.setIntProperty("carsnapshot_distributormargins", 25);
        this.setIntProperty("carsnapshot_graphrankfilter", 9);
        
        
       
        this.setDoubleProperty("carsnapshotgraph_peakfootconstant", 0.55); 
        this.setDoubleProperty("carsnapshotgraph_peakDiffMultiplicationConstant", 0.1);
        
        
        this.setIntProperty("intelligence_skewdetection", 0);
        
       
        this.setIntProperty("char_normalizeddimensions_x", 8); 
        this.setIntProperty("char_normalizeddimensions_y", 13); 
        this.setIntProperty("char_resizeMethod",1); 
        this.setIntProperty("char_featuresExtractionMethod",0); 
        this.setStrProperty("char_neuralNetworkPath","./resources/neuralnetworks/network_avgres_813_map.xml");
        this.setStrProperty("char_learnAlphabetPath","./resources/alphabets/alphabet_8x13");
        this.setIntProperty("intelligence_classification_method",0); 
     
        
        this.setDoubleProperty("plategraph_peakfootconstant", 0.7); 
        this.setDoubleProperty("plategraph_rel_minpeaksize", 0.86); 
        
       
        this.setDoubleProperty("platehorizontalgraph_peakfootconstant", 0.05);
        this.setIntProperty("platehorizontalgraph_detectionType",1); 
        
        
        this.setDoubleProperty("plateverticalgraph_peakfootconstant", 0.42);
        
        
        this.setIntProperty("intelligence_numberOfBands",3);
        this.setIntProperty("intelligence_numberOfPlates",3);
        this.setIntProperty("intelligence_numberOfChars",20);
        
        this.setIntProperty("intelligence_minimumChars",5);
        this.setIntProperty("intelligence_maximumChars",15);
        
        
        this.setDoubleProperty("intelligence_maxCharWidthDispersion",0.5); 
        this.setDoubleProperty("intelligence_minPlateWidthHeightRatio",0.5);
        this.setDoubleProperty("intelligence_maxPlateWidthHeightRatio",15.0);
        
       
        this.setDoubleProperty("intelligence_minCharWidthHeightRatio",0.1);
        this.setDoubleProperty("intelligence_maxCharWidthHeightRatio",0.92);
        this.setDoubleProperty("intelligence_maxBrightnessCostDispersion", 0.161);
        this.setDoubleProperty("intelligence_maxContrastCostDispersion", 0.1);
        this.setDoubleProperty("intelligence_maxHueCostDispersion", 0.145);
        this.setDoubleProperty("intelligence_maxSaturationCostDispersion", 0.24); 
        this.setDoubleProperty("intelligence_maxHeightCostDispersion", 0.2);
        this.setDoubleProperty("intelligence_maxSimilarityCostDispersion", 100);
        
        
        this.setIntProperty("intelligence_syntaxanalysis",2);
        this.setStrProperty("intelligence_syntaxDescriptionFile","./resources/syntax/syntax.xml");
        
       
        this.setIntProperty("neural_maxk", 8000); 
        this.setDoubleProperty("neural_eps", 0.07); 
        this.setDoubleProperty("neural_lambda", 0.05); 
        this.setDoubleProperty("neural_micro", 0.5); 
       
        this.setIntProperty("neural_topology", 20); 
        
        
        
        this.setStrProperty("help_file_help","./resources/help/help.html");
        this.setStrProperty("help_file_about","./resources/help/about.html");
        this.setStrProperty("reportgeneratorcss","./resources/reportgenerator/style.css");
    }
    public Configurator(String path) {
        this();
        try {
            loadConfiguration(path);
        } catch (Exception ex) {
            System.out.println("Error: Couldn't load configuration file "+path);
            System.exit(1);
        }
    }
    
    public void setConfigurationFileName(String name) {
        this.fileName = name;
    }
    
    public String getConfigurationFileName() {
        return this.fileName;
    }
    
    public String getStrProperty(String name) {
        return list.getProperty(name);
    }
    
    public String getPathProperty(String name) {
        return this.getStrProperty(name).replace('/',File.separatorChar);
        
    }
    
    public void setStrProperty(String name, String value) {
        list.setProperty(name, value);
    }
    
    public int getIntProperty(String name) throws NumberFormatException {
        return Integer.decode(list.getProperty(name));
    }
    
    public void setIntProperty(String name, int value) {
        list.setProperty(name, String.valueOf(value));
    }
    
    public double getDoubleProperty(String name) throws NumberFormatException {
        return Double.parseDouble(list.getProperty(name));
    }
    
    public void setDoubleProperty(String name, double value) {
        list.setProperty(name, String.valueOf(value));
    }
    
    public Color getColorProperty(String name) {
        return new Color(Integer.decode(list.getProperty(name)));
    }
    
    public void setColorProperty(String name, Color value) {
        list.setProperty(name, String.valueOf(value.getRGB()));
    }
    
    public void saveConfiguration() throws IOException {
        FileOutputStream os = new FileOutputStream(fileName);
        list.storeToXML(os, comment);
    }
    
    public void saveConfiguration(String arg_file) throws IOException {
        FileOutputStream os = new FileOutputStream(arg_file);
        list.storeToXML(os, comment);
    }
    
    public void loadConfiguration() throws IOException {
        FileInputStream is = new  FileInputStream(fileName);
        list.loadFromXML(is);
    }
    
    public void loadConfiguration(String arg_file) throws IOException {
        FileInputStream is = new  FileInputStream(arg_file);
        list.loadFromXML(is);
    }
    
}

