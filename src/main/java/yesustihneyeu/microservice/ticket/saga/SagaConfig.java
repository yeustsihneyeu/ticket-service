package yesustihneyeu.microservice.ticket.saga;

import io.eventuate.tram.sagas.participant.SagaCommandDispatcher;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcherFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SagaConfig {

    @Bean
    public SagaCommandDispatcher ticketCommandDispatcher(
            SagaCommandHandler ticketCommandHandler, SagaCommandDispatcherFactory factory) {
        return factory.make("ticketCommandDispatcher", ticketCommandHandler.commandHandler());
    }
}
