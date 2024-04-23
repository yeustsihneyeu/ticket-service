package yesustihneyeu.microservice.ticket;

import io.eventuate.tram.events.publisher.ResultWithEvents;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import yesustihneyeu.microservice.ticket.event.TicketCreatedEvent;

import java.util.Random;
import java.util.UUID;

import static java.util.Collections.singletonList;

@Table(name = "ticket")
@Getter
@Setter
@Entity
public class TicketEntity {

    @Transient
    private static Random random = new Random();

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String actionId;

    @Column(nullable = false)
    private Integer cost;

    public static ResultWithEvents<TicketEntity> create(String actionId, String actionName) {
        TicketEntity ticket = new TicketEntity();
        ticket.setActionId(actionId);
        ticket.setName(actionName);
        ticket.setCost(random.nextInt(1, 100));

        return new ResultWithEvents<>(ticket, singletonList(new TicketCreatedEvent(ticket.actionId, ticket.cost)));
    }

    public TicketRecord toRecord() {
        return new TicketRecord(this.id, this.name, this.actionId, this.cost);
    }

    public record TicketRecord(UUID id, String name, String actionId, Integer cost) {}
}
