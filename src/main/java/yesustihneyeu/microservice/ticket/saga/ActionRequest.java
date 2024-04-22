package yesustihneyeu.microservice.ticket.saga;

import java.util.List;

public record ActionRequest(String name, Progress progress) {

    public record Progress(List<DailyProgress> dailyProgressList) { }

    public record DailyProgress(String name, int duration) { }
}

