package yesustihneyeu.microservice.ticket.saga;

import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.SagaCommand;
import org.springframework.stereotype.Component;
import yesustihneyeu.microservice.ticket.TicketService;
import yesustihneyeu.microservice.ticket.command.CreateTicket;
import yesustihneyeu.microservice.ticket.command.DeleteTicket;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withFailure;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;
import static microservice.SagaCommandUtils.mapTo;

@Slf4j
@Component
@RequiredArgsConstructor
public class SagaCommandHandler {

    private final TicketService ticketService;

    public CommandHandlers commandHandler() {
        return SagaCommandHandlersBuilder
                .fromChannel("ticketChannel")
                .onMessage(SagaCommand.class, this::handle)
                .build();
    }

    private Message handle(CommandMessage<SagaCommand> commandMessage) {
        final var sagaCommand = commandMessage.getCommand();
        final var data = sagaCommand.data();
        try {
            switch (sagaCommand.commandType()) {
                case CREATE_TICKET -> {
                    CreateTicket createTicket = mapTo(sagaCommand.data(), CreateTicket.class);
                    ticketService.create(createTicket.actionId(), createTicket.request().name());
                }
                case DELETE_TICKET -> ticketService.delete(mapTo(data, DeleteTicket.class).actionId());
                default -> throw new RuntimeException("unknown saga command type");
            }
            return withSuccess();
        } catch (Exception e) {
            log.error(e.getMessage());
            return withFailure();
        }
    }
}
