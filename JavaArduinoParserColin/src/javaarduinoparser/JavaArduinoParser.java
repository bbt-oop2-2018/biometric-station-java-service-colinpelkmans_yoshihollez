package javaarduinoparser;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

public class JavaArduinoParser {

    public static void main(String[] args) {
        int MAX_BYTES = 64;
        SerialPort comPort = SerialPort.getCommPorts()[0];
        comPort.openPort();
        comPort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                    return;
                }

                byte[] newData = new byte[MAX_BYTES];
                char[] message = new char[MAX_BYTES];
                int numRead = comPort.readBytes(newData, newData.length);
                System.out.println("Read " + numRead + " bytes.");
                
                for(int i = 0;i< newData.length;i++){
                    message[i] = (char) (newData[i]);
                    //System.out.println(message[i]);
                }
                String output = new String(newData);
                System.out.println(output);
                if(output.contains("]")){
                    System.out.println("found new line");
                    
                }
            }
        });
    }

}