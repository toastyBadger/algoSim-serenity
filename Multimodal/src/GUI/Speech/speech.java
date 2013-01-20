package GUI.Speech;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.jsgf.JSGFGrammar;
import edu.cmu.sphinx.jsgf.JSGFGrammarException;
import edu.cmu.sphinx.jsgf.JSGFGrammarParseException;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import java.io.IOException;
import javax.speech.recognition.ResultAdapter;
import javax.swing.JOptionPane;

public class speech extends ResultAdapter {

    public static ConfigurationManager cm;
    public static Recognizer recognizer;
    public static Microphone microphone;
    public static JSGFGrammar jsgfGrammarManager;
    private boolean isDigit = false;
    public static boolean listening = true;
    public static int currentresult = 0;                         //the index of deletion and selection
    public boolean underten = false;

//constructer of this class
    public speech(String s) {
        cm = new ConfigurationManager(speechtest.class.getResource(s));
        recognizer = (Recognizer) cm.lookup("recognizer");
        jsgfGrammarManager = (JSGFGrammar) cm.lookup("jsgfGrammar");
        microphone = (Microphone) cm.lookup("microphone");
        microphone.getPredecessor();
    }

//set to true if the microphone is listening
    public void setListening(boolean b) {
        listening = b;

    }
//set to true if it is only digit input

    public void setDigit(boolean b) {
        isDigit = b;
    }

    //run the recognizer
    public String run() {
        try {
            microphone.initialize();
            microphone.startRecording();
            recognizer.allocate();
//change the grammar of speech recognition
            if (isDigit && underten) {
                jsgfGrammarManager.loadJSGF("speech10");
            } else if (isDigit) {
                jsgfGrammarManager.loadJSGF("speech");
            } else if (underten) {
                jsgfGrammarManager.loadJSGF("graph10");
            } else {
                jsgfGrammarManager.loadJSGF("graph");
            }

            boolean done = false;
            while (!done) {
                System.out.println("Say: something");
                Result result = recognizer.recognize();
                if (result != null) {
                    System.out.println("get the voice");
                    String resultText = result.getBestFinalResultNoFiller();
                    //Token resultText = result.getBestToken();
                    resultText = processDigit(resultText);

                    System.out.println("You said:.." + resultText + ".." + '\n');


                    String dia = "You said: " + resultText + '\n';
                    int bl = JOptionPane.showConfirmDialog(null, dia, "confirm", JOptionPane.YES_NO_OPTION);
                    if (bl == JOptionPane.YES_OPTION) {
                        done = true;
                        setDigit(false);
                        microphone.clear();
                        microphone.stopRecording();
                        recognizer.deallocate();
                        // currentresult= resultText;
                        return resultText;
                    } else {
                        microphone.clear();
                    }

                } else {
                    System.out.println("I can't hear what you said.\n");
                }
            }


            System.out.println("i am listening...");
            microphone.stopRecording();
            recognizer.deallocate();

        } catch (IOException ex) {
            ex.printStackTrace();

        } catch (JSGFGrammarParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSGFGrammarException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

//process digit input, and store the current recognized result of digit part
    private String processDigit(String input) {
        String resultText = input;
        String result2 = null;

        resultText = resultText.replaceAll("one", "1");
        resultText = resultText.replaceAll("two", "2");
        resultText = resultText.replaceAll("three", "3");
        resultText = resultText.replaceAll("four", "4");
        resultText = resultText.replaceAll("five", "5");
        resultText = resultText.replaceAll("six", "6");
        resultText = resultText.replaceAll("seven", "7");
        resultText = resultText.replaceAll("eight", "8");
        resultText = resultText.replaceAll("nine", "9");
        resultText = resultText.replaceAll("zero", "0");
        resultText = resultText.replaceAll("point", ".");
        resultText = resultText.replaceAll("dot", ".");
        if (resultText.contains("delete") || resultText.contains("select")) {
            result2 = resultText;
            result2 = result2.replace("delete", "");
            result2 = result2.replace("select", "");

            result2 = result2.replace(" ", "");
            System.out.println(result2);
            currentresult = Integer.parseInt(result2);
        } else {
            resultText = resultText.replaceAll(" ", "");
        }


        return resultText;
    }

    public void stop() {
        microphone.stopRecording();
    }
}
