package yesustihneyeu.microservice.ticket;

import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.eventuate.tram.events.publisher.ResultWithEvents;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final DomainEventPublisher domainEventPublisher;

    public void create(String actionId, String actionName) {
        log.info("create Ticket actionId - {}", actionId);
        ResultWithEvents<TicketEntity> result = TicketEntity.create(actionId, actionName);
        TicketEntity ticket = ticketRepository.save(result.result);
        domainEventPublisher.publish(
                TicketEntity.class.getName(),
                ticket.getId(),
                Map.of("spring.json.type.mapping", "createTicket"),
                result.events
        );
    }


    public void delete(String actionId) {
        TicketEntity ticket = ticketRepository.findByActionId(actionId)
                .orElseThrow(() -> new EntityNotFoundException("ticket not found"));
        ticketRepository.delete(ticket);
        log.info("delete Ticket actionId - {}", actionId);
    }
}
