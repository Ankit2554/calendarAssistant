package com.example.calendar.Controller;
import com.example.calendar.Model.Meeting;
import com.example.calendar.Model.TimeSlot;
import com.example.calendar.Service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/calendar")
public class CalendarController {

    @Autowired
    private CalendarService calendarService;

    @PostMapping("/book")
    public ResponseEntity<String> bookMeeting(@RequestBody Map<String, Object> request) {
        Integer ownerId = (Integer) request.get("ownerId");
        Meeting meeting = parseMeeting((Map<String, Object>) request.get("meeting"));

        String response = calendarService.bookMeeting(ownerId, meeting);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/free-slots")
    public ResponseEntity<List<TimeSlot>> findFreeSlots(@RequestBody Map<String, Object> request) {
        Integer employee1Id = (Integer) request.get("employee1Id");
        Integer employee2Id = (Integer) request.get("employee2Id");
        int duration = (Integer) request.get("meetingDuration");

        List<TimeSlot> freeSlots = calendarService.findFreeSlots(employee1Id, employee2Id, duration);
        return ResponseEntity.ok(freeSlots);
    }

    @PostMapping("/check-conflicts")
    public ResponseEntity<List<Integer>> checkMeetingConflicts(@RequestBody Map<String, Object> request) {
        LocalDateTime startTime = LocalDateTime.parse((String) request.get("startTime"));
        LocalDateTime endTime = LocalDateTime.parse((String) request.get("endTime"));
        List<Integer> participants = (List<Integer>) request.get("participants");

        List<Integer> conflicts = calendarService.checkConflicts(startTime, endTime, participants);
        return ResponseEntity.ok(conflicts);
    }

    private Meeting parseMeeting(Map<String, Object> meetingData) {
        Meeting meeting = new Meeting();
        meeting.setStartTime(LocalDateTime.parse((String) meetingData.get("startTime")));
        meeting.setEndTime(LocalDateTime.parse((String) meetingData.get("endTime")));
        meeting.setParticipants((List<Integer>) meetingData.get("participants"));

        return meeting;
    }
}