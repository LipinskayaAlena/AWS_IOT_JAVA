

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Created by Ozkan Can on 10/04/16.
 */
class ExampleCallback implements MqttCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleCallback.class);

    @Override
    public void connectionLost(Throwable cause) {
        LOGGER.info("Connection Lost.", cause);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
    	String mes = new String(message.getPayload());
    	System.out.println(mes);
    	if(mes.charAt(0) == '('){
    		MyForm.textArea.append("Current position: " + mes + "\n");
    	} else 
    		MyForm.textArea.append(mes + "\n");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        LOGGER.info("Completed delivery of message with id {}", token.getMessageId());
    }
}
