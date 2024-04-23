package yesustihneyeu.microservice.ticket.event;

public record TicketCreatedEvent(String actionId, Integer cost) implements TicketEvent {
}
