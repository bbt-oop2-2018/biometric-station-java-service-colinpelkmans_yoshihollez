/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaserialportexample;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 *
 * @author yoshi
 */
public class MqqtBroker implements MqttCallback {

    private MqttClient client;
    private String broker = "tcp://labict.be:1883";
    private String clientId;
    private int qos = 2;            // Exactly once
    private MemoryPersistence persistence;
    private MqttConnectOptions connectionOptions;
    private JavaSerialPortExample messageHandler = null;

    private final String BASE_TOPIC = "projectwerk/biometrisch/Yoshi_Colin_data";
    private String channelName;
    private String channelTopic;

    public MqqtBroker(String clientId, String channelName) {
        Random random = new Random();
        this.clientId = clientId + random.nextInt();
        this.channelName = channelName;
        this.channelTopic = BASE_TOPIC;
        setupMqtt();
    }

    MqqtBroker(String mqqtBroker) {
    }

    private void setupMqtt() {
        try {
            persistence = new MemoryPersistence();
            client = new MqttClient(broker, clientId, persistence);
            connectionOptions = new MqttConnectOptions();
            connectionOptions.setCleanSession(true);
            client.connect(connectionOptions);
            client.setCallback(this);
            client.subscribe(channelTopic);
        } catch (MqttException me) {
            System.out.println("Failed to connect to broker");
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }

    public void sendData(String message) {
        try {
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            mqttMessage.setQos(qos);
            client.publish(channelTopic, mqttMessage);
        } catch (MqttException ex) {
            Logger.getLogger(MqqtBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setMessageHandler(JavaSerialPortExample handler) {
        this.messageHandler = handler;
    }

    public void disconnect() {
        try {
            client.disconnect();
            client.close();
        } catch (MqttException ex) {
            Logger.getLogger(MqqtBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void connectionLost(Throwable thrwbl) {
        System.out.println("Lost connection with broker");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {

    }

    void mqqtBroker(String dataString) throws MqttException {
        MqttClient client = new MqttClient("tcp://labict.be:1883", MqttClient.generateClientId());
        client.connect();
        MqttMessage message = new MqttMessage();
        message.setPayload("Hello world from Java".getBytes());
        client.publish("iot_data", message);
        client.disconnect();
    }
}
