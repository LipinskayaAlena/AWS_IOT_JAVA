

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.net.ssl.SSLSocketFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyForm extends JFrame {
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static final int QOS_LEVEL = 0;
    private static final String TOPIC = "MyTopic";
    private static final String TOPIC_2 = "MyTopic_2";
   // private static final String MESSAGE = "Hello World!";
    private static final String MESSAGE_GO = "GO   ";
    private static final String MESSAGE_LEFT = "LEFT ";
    private static final String MESSAGE_RIGHT = "RIGHT";
    private static final String MESSAGE_STOP = "STOP ";
    
    
    private static final long QUIESCE_TIMEOUT = 5000;
	
	private JButton buttonLeft = new JButton("Left");
	private JButton buttonRight = new JButton("Right");
	private JButton buttonGo = new JButton("Go");
	private JButton buttonStop = new JButton("Stop");
	static JTextArea textArea = new JTextArea();
	private JScrollPane scrollPane;
	private MqttClient client;
	private MqttConnectOptions options;
	
	public MyForm() {
		setLayout(null);
        setSize(300, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        scrollPane = new JScrollPane(textArea);
        
        buttonGo.setBounds(15, 20, 250, 50);
        buttonLeft.setBounds(15, 80, 250, 50);
        buttonRight.setBounds(15, 140, 250, 50);
        buttonStop.setBounds(15, 200, 250, 50);
        
        buttonGo.setBackground(Color.RED);
        buttonLeft.setBackground(Color.GREEN);
        buttonRight.setBackground(Color.YELLOW);
        buttonStop.setBackground(Color.PINK);
        scrollPane.setBounds(15, 300 , 250, 200);
        
        add(buttonGo);
        add(buttonLeft);
        add(buttonRight);
        add(buttonStop);
        add(scrollPane);
        
        setVisible(true);
        createClient();
        buttonGo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
	                client.publish(TOPIC, new MqttMessage(MESSAGE_GO.getBytes()));
	                MyForm.textArea.append(MESSAGE_GO + "\n");
				} catch (MqttException e1) {
					e1.printStackTrace();
				}
                
        	}
        });
         
        buttonLeft.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
					//client.connect(options);
            		//client.subscribe(TOPIC, QOS_LEVEL);
	                client.publish(TOPIC, new MqttMessage(MESSAGE_LEFT.getBytes()));
	                MyForm.textArea.append(MESSAGE_LEFT + "\n");
            	} catch (MqttException e1) {
					e1.printStackTrace();
				}
                
        	}
        });
        
        buttonRight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
					//client.connect(options);
					//client.subscribe(TOPIC, QOS_LEVEL);
	                client.publish(TOPIC, new MqttMessage(MESSAGE_RIGHT.getBytes()));
	                MyForm.textArea.append(MESSAGE_RIGHT + "\n");
            	} catch (MqttException e1) {
					e1.printStackTrace();
				}
                
        	}
        });
        
        buttonStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
					//client.connect(options);
					//client.subscribe(TOPIC, QOS_LEVEL);
					
	                client.publish(TOPIC, new MqttMessage(MESSAGE_STOP.getBytes()));
	                MyForm.textArea.append(MESSAGE_STOP + "\n");
            	} catch (MqttException e1) {
					e1.printStackTrace();
				}
                
        	}
        });
        
	}
	
	public void createClient() {
		try {
			IoTConfig config = new IoTConfig("config-example.properties");
			SSLSocketFactory sslSocketFactory = SslUtil.getSocketFactory(
                    config.get(IoTConfig.ConfigFields.AWS_IOT_ROOT_CA_FILENAME),
                    config.get(IoTConfig.ConfigFields.AWS_IOT_CERTIFICATE_FILENAME),
                    config.get(IoTConfig.ConfigFields.AWS_IOT_PRIVATE_KEY_FILENAME));
			options = new MqttConnectOptions();
            options.setSocketFactory(sslSocketFactory);
            options.setCleanSession(true);

            
            final String serverUrl = "ssl://" + config.get(IoTConfig.ConfigFields.AWS_IOT_MQTT_HOST)+":"+config.get(IoTConfig.ConfigFields.AWS_IOT_MQTT_PORT);
            final String clientId = config.get(IoTConfig.ConfigFields.AWS_IOT_MQTT_CLIENT_ID);
            
            client = new MqttClient(serverUrl, clientId);
            client.setCallback(new ExampleCallback());
            
            client.connect(options);
            //client.subscribe(TOPIC, QOS_LEVEL);
            client.subscribe(TOPIC_2, QOS_LEVEL);
		} catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            System.exit(-1);
        }
	}
}
