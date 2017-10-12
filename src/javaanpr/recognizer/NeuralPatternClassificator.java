
package javaanpr.recognizer;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import javaanpr.intelligence.Intelligence;
import javaanpr.imageanalysis.Char;
import javaanpr.neuralnetwork.NeuralNetwork;


public class NeuralPatternClassificator extends CharacterRecognizer {
    
    private static int normalize_x =
            Intelligence.configurator.getIntProperty("char_normalizeddimensions_x");
    private static int normalize_y =
            Intelligence.configurator.getIntProperty("char_normalizeddimensions_y");
    
   
    
    public NeuralNetwork network;
    
    
    public NeuralPatternClassificator() throws Exception {
        this(false);
    }
    
    public NeuralPatternClassificator(boolean learn) throws Exception {
       
        
        Vector<Integer> dimensions = new Vector<Integer>();
        
       
        int inputLayerSize;
        if (Intelligence.configurator.getIntProperty("char_featuresExtractionMethod")==0)
            inputLayerSize = normalize_x * normalize_y;
        else inputLayerSize = CharacterRecognizer.features.length*4;
        
        
        dimensions.add(inputLayerSize);
        dimensions.add(Intelligence.configurator.getIntProperty("neural_topology"));
        dimensions.add(CharacterRecognizer.alphabet.length);
        this.network = new NeuralNetwork(dimensions);
        
        if (learn) {
            
            learnAlphabet(Intelligence.configurator.getStrProperty("char_learnAlphabetPath"));
        } else {
            
            this.network = new NeuralNetwork(Intelligence.configurator.getPathProperty("char_neuralNetworkPath"));
        }
    }
    
   
    public RecognizedChar recognize(Char imgChar) { 
        imgChar.normalize();
        Vector<Double> output = this.network.test(imgChar.extractFeatures());
        double max = 0.0;
        int indexMax = 0;
        
        RecognizedChar recognized = new RecognizedChar();
        
        for (int i=0; i<output.size(); i++) {
            recognized.addPattern(recognized.new RecognizedPattern(NeuralPatternClassificator.alphabet[i], output.elementAt(i).floatValue()));
        }
        recognized.render();
        recognized.sort(1);
        return recognized;
    }
    

    public NeuralNetwork.SetOfIOPairs.IOPair createNewPair(char chr, Char imgChar) { // uz normalizonvany
        Vector<Double> vectorInput = imgChar.extractFeatures();
        
        
        
        Vector<Double> vectorOutput = new Vector<Double>();
        for (int i=0; i<NeuralPatternClassificator.alphabet.length; i++)
            if (chr == NeuralPatternClassificator.alphabet[i]) vectorOutput.add(1.0); else vectorOutput.add(0.0);
        

        
        return (new NeuralNetwork.SetOfIOPairs.IOPair(vectorInput, vectorOutput));
    }
    
    
    public void learnAlphabet(String path) throws IOException {
        String alphaString = "0123456789abcdefghijklmnopqrstuvwxyz";        
        File folder = new File(path);
        NeuralNetwork.SetOfIOPairs train = new NeuralNetwork.SetOfIOPairs();
        
        for (String fileName : folder.list()) {
            if (alphaString.indexOf(fileName.toLowerCase().charAt(0))==-1)
                continue; 
            
            Char imgChar = new Char(path+File.separator+fileName);
            imgChar.normalize();
            train.addIOPair(this.createNewPair(fileName.toUpperCase().charAt(0), imgChar));
        }
        
        this.network.learn(train,
                Intelligence.configurator.getIntProperty("neural_maxk"),
                Intelligence.configurator.getDoubleProperty("neural_eps"),
                Intelligence.configurator.getDoubleProperty("neural_lambda"),
                Intelligence.configurator.getDoubleProperty("neural_micro")
                );
    }
}
