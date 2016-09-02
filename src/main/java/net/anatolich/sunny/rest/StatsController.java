package net.anatolich.sunny.rest;

import net.anatolich.sunny.domain.SenderStats;
import net.anatolich.sunny.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/stats")
public class StatsController {

    private final StatsService statsService;

    @Autowired
    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @RequestMapping(path = "bySender", method = RequestMethod.GET)
    public SenderStats byDirection() {
        return statsService.countByDirection();
    }
}
