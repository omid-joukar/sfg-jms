package omid.springframework.sfgjms.listener;

import lombok.RequiredArgsConstructor;
import omid.springframework.sfgjms.config.JmsConfig;
import omid.springframework.sfgjms.model.HelloWorldsMessage;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

/**
 * Created by jt on 2019-07-17.
 */
@RequiredArgsConstructor
@Component
public class HelloMessageListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload HelloWorldsMessage helloWorldMessage,
                       @Headers MessageHeaders headers, Message message){

        //System.out.println("I Got a Message!!!!!");

        // System.out.println(helloWorldMessage);


        // uncomment and view to see retry count in debugger
        // throw new RuntimeException("foo");

    }

    @JmsListener(destination = JmsConfig.MY_SEND_RCV_QUEUE)
    public void listenForHello(@Payload HelloWorldsMessage helloWorldMessage,
                               @Headers MessageHeaders headers, Message message) throws JMSException {

        HelloWorldsMessage payloadMsg = HelloWorldsMessage
                .builder()
                .id(UUID.randomUUID())
                .message("World!!")
                .build();

        jmsTemplate.convertAndSend(message.getJMSReplyTo(), payloadMsg);

    }

}
