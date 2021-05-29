package omid.springframework.sfgjms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import omid.springframework.sfgjms.config.JmsConfig;
import omid.springframework.sfgjms.model.HelloWorldsMessage;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class HelloSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;
    @Scheduled(fixedRate = 2000)
    public void sendMessage(){



        HelloWorldsMessage message = HelloWorldsMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello World!")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, message);


    }
    @Scheduled(fixedRate = 2000)
    public void sendAndReciveMessage() throws JMSException {



        HelloWorldsMessage message = HelloWorldsMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello")
                .build();
        Message recievingMsg = jmsTemplate.sendAndReceive(JmsConfig.MY_SEND_RCV_QUEUE, new MessageCreator() {

            @Override
            public Message createMessage(Session session) throws JMSException {

                Message helloMessage = null;
                try {
                    helloMessage = session.createTextMessage(
                            objectMapper.writeValueAsString(message));
                    helloMessage.setStringProperty("_type","omid.springframework.sfgjms.model.HelloWorldsMessage");
                    System.out.println("sending hello");
                    return helloMessage;

                } catch (JsonProcessingException e) {
                    throw new JMSException("boom");
                }
            }
        });
        System.out.println(recievingMsg.getBody(String.class));
    }

}
