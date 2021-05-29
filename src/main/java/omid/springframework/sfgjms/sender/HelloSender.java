package omid.springframework.sfgjms.sender;

import lombok.RequiredArgsConstructor;
import omid.springframework.sfgjms.config.JmsConfig;
import omid.springframework.sfgjms.model.HelloWorldsMessage;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class HelloSender {

    private final JmsTemplate jmsTemplate;

    @Scheduled(fixedRate = 2000)
    public void sendMessage(){

        System.out.println("I'm Sending a message");

        HelloWorldsMessage message = HelloWorldsMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello World!")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, message);

        System.out.println("Message Sent!");

    }

}
