package kz.busnet.busnetserver.activities;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @PostMapping("/log")
    public Activity logActivity(@RequestParam String username, @RequestParam String action, @RequestParam String details) {
        return activityService.logActivity(username, action, details);
    }

    @GetMapping
    public List<Activity> getAllActivities() {
        return activityService.getAllActivities();
    }
}
