package com.TABA.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class PressureSubscriber{
	public static void main(String[] args) {
		String broker = "tcp://localhost:1883";
        String clientId = "Subscriber";        
        
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient client = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions conOpt = new MqttConnectOptions();
            
            conOpt.setCleanSession(true);            
            client.setCallback(new SampleSubscriber());
            
            System.out.println("Connecting to broker: " + broker);
            client.connect(conOpt);
            System.out.println("Connected");
            
            client.subscribe("site/+/pressure");
            
        } catch (MqttException ex) {
            System.out.println("reason " + ex.getReasonCode());
            System.out.println("msg " + ex.getMessage());
            System.out.println("loc " + ex.getLocalizedMessage());
            System.out.println("cause " + ex.getCause());
            System.out.println("excep " + ex);
            ex.printStackTrace();
        }
    }
}