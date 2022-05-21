package com.TABA.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class WaterPublisher{
	private static MqttClient client;
	public static void main(String[] args) {
		
		String topic1 = "site/water/pressure";
		String topic2 = "site/water/level";
		
		String broker = "tcp://localhost:1883";
		String clientId = "Publisher";

		MemoryPersistence persistence = new MemoryPersistence();
		
		try {
			client = new MqttClient(broker, clientId, persistence);
			MqttConnectOptions conOpt = new MqttConnectOptions();
			
			conOpt.setCleanSession(true);
			conOpt.setKeepAliveInterval(180);
			
			System.out.println("Connection to broker " + broker);
			client.connect(conOpt);
			System.out.println("Connected");
			
			publishMessage(topic1, "Water pressure is 1400psi", 1 , false);
			publishMessage(topic2, "Water level is 10mtrs", 1 , false);
			publishMessage(topic2, "Water level is 9mtrs", 1 , false);
			publishMessage(topic2, "Water level is 8mtrs", 1 , false);
			publishMessage(topic2, "Water level is 7mtrs", 1 , false);
			publishMessage(topic2, "Water level is 11mtrs", 1 , false);
			
			client.disconnect();
			System.out.println("Disconnected");
			client.close();
			System.exit(0);
		}
		catch (MqttException ex) {
			System.out.println("reason " + ex.getReasonCode());
			System.out.println("msg " + ex.getMessage());
			System.out.println("loc-msg " + ex.getLocalizedMessage());
			System.out.println("cause " + ex.getCause());
			System.out.println("exception " + ex);
			ex.printStackTrace();
		}
	}	
	private static void publishMessage(String topic, String payload, int qos, boolean retained) {

		System.out.println("Publishing message: " + payload + " on topic "+ topic );            

		MqttMessage message = new MqttMessage(payload.getBytes());
		message.setRetained(retained);
		message.setQos(qos);     

		try {

			client.publish(topic, message);

		} catch (MqttException ex) {
			System.out.println("reason " + ex.getReasonCode());
			System.out.println("msg " + ex.getMessage());
			System.out.println("loc-msg " + ex.getLocalizedMessage());
			System.out.println("cause " + ex.getCause());
			System.out.println("exception " + ex);
			ex.printStackTrace();
		}

		System.out.println("Message published");
	}
}