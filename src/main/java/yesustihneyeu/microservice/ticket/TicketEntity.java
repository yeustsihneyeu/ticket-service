package yesustihneyeu.microservice.ticket;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;
import java.util.UUID;

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

    public static TicketEntity create(String actionId, String actionName) {
        TicketEntity ticket = new TicketEntity();
        ticket.setActionId(actionId);
        ticket.setName(actionName);
        ticket.setCost(random.nextInt(1, 100));
        return ticket;
    }

    public TicketRecord toRecord() {
        return new TicketRecord(this.id, this.name, this.actionId, this.cost);
    }

    public record TicketRecord(UUID id, String name, String actionId, Integer cost) {}
}
