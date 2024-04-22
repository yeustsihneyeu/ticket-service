package yesustihneyeu.microservice.ticket.command;

import yesustihneyeu.microservice.ticket.saga.ActionRequest;

public record CreateTicket(String actionId, ActionRequest request) {
}

