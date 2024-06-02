package kz.busnet.busnetserver.activities;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;

    public Activity logActivity(String username, String action, String details) {
        Activity activity = new Activity();
        activity.setUsername(username);
        activity.setAction(action);
        activity.setDetails(details);
        activity.setTimestamp(LocalDateTime.now());
        return activityRepository.save(activity);
    }

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }
}
