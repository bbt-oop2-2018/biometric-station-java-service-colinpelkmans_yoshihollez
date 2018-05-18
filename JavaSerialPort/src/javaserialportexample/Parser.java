/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaserialportexample;

/**
 *
 * @author yoshi
 */
class Parser {

    public Parser() {
    }

    void parseData(String dataString) {

        String[] channels = new String[5];
        String[] parsedData = new String[5];

        channels[0] = "AccelX";
        channels[1] = "AccelY";
        channels[2] = "AccelZ";
        channels[3] = "HB";
        channels[4] = "Temp";

        if (dataString.contains("$") && dataString.contains("£")) {
            String foo = dataString.replace("$", "").replace("£", "");
            parsedData = foo.split("_");

            for (int i = 0; i < parsedData.length; i++) {
                SendData(channels[i], parsedData[i]);
            }
        }
    }

    private void SendData(String channel, String data) {
        MqttBroker mqttBroker = new MqttBroker();
        mqttBroker.switchChannel(channel);
        mqttBroker.sendData(data);
        mqttBroker.disconnect();
    }
}
