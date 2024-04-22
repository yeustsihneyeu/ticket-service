package yesustihneyeu.microservice.ticket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    public void create(String actionId, String actionName) {
        log.info("create Ticket actionId - {}", actionId);
        TicketEntity ticket = TicketEntity.create(actionId, actionName);
        ticketRepository.save(ticket);
    }


    public void delete(String actionId) {
        TicketEntity ticket = ticketRepository.findByActionId(actionId)
                .orElseThrow(() -> new EntityNotFoundException("ticket not found"));
        ticketRepository.delete(ticket);
        log.info("delete Ticket actionId - {}", actionId);
    }
}
