package GUI.Speech;




import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import javax.speech.*;
import javax.speech.recognition.*;
import javax.swing.JOptionPane;

public class speechtest extends ResultAdapter {

    static ConfigurationManager cm;
    static Recognizer recognizer;
    static Microphone microphone;

    public speechtest(String s){
        cm = new ConfigurationManager(speechtest.class.getResource(s));
        recognizer = (Recognizer) cm.lookup("recognizer");


        microphone = (Microphone) cm.lookup("microphone");
        microphone.getPredecessor();
        microphone.startRecording();
    }


    public String run()   {
       recognizer.allocate();
       boolean done=false;
       while(!done){
            //System.out.println("Say: something");
            Result result = recognizer.recognize();
            if (result != null) {
                //System.out.println("get the voice");
                String resultText = result.getBestFinalResultNoFiller();
                //Token resultText = result.getBestToken();
                resultText=resultText.replaceAll("one", "1");
                resultText= resultText.replaceAll("two","2");
                resultText=resultText.replaceAll("three","3");
                resultText= resultText.replaceAll("four","4");
                resultText= resultText.replaceAll("five","5");
                resultText= resultText.replaceAll("six","6");
                resultText= resultText.replaceAll("seven","7");
                resultText=resultText.replaceAll("eight","8");
                resultText= resultText.replaceAll("nine","9");
                resultText= resultText.replaceAll("zero","0");
                resultText=resultText.replaceAll(" point ",".");
                resultText=resultText.replaceAll(" dot ",".");

                //System.out.println("You said:.." + resultText +".."+ '\n');

                String dia="You said: " + resultText + '\n';
                int bl=JOptionPane.showConfirmDialog(null,dia, "Confirm", JOptionPane.YES_NO_OPTION);
                if(bl==JOptionPane.YES_OPTION){
                    done=true;recognizer.deallocate();
                    microphone.clear();
                    return resultText;
                }else{
                    microphone.clear();
                }
            }else {
                //System.out.println("I can't hear what you said.\n");
            }
        }
        //System.out.println("end of voice");
        recognizer.deallocate();
        return null;
     }
}