package com.TABA.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class AirPublisher{
	private static MqttClient client;
	public static void main(String[] args) {
		
		String topic1 = "site/air/pressure";
		String topic2 = "site/air/temperature";
		
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
			
			publishMessage(topic1, "air pressure is 990.2mb", 0 , false);
			publishMessage(topic2, "air temperature is 18.5c", 0 , false);
			
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