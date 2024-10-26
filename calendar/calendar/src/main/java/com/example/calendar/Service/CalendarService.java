package com.example.calendar.Service;

import com.example.calendar.Model.EmployeeCalendar;
import com.example.calendar.Model.Meeting;
import com.example.calendar.Model.TimeSlot;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CalendarService {

    private final Map<Integer, EmployeeCalendar> employeeCalendars = new HashMap<>();

    public CalendarService() {
        // Preload some employee calendars
        employeeCalendars.put(1, new EmployeeCalendar(1));
        employeeCalendars.put(2, new EmployeeCalendar(2));
    }

    public String bookMeeting(Integer ownerId, Meeting meeting) {
        EmployeeCalendar calendar = employeeCalendars.get(ownerId);
        if (calendar != null && calendar.bookMeeting(meeting)) {
            return "Meeting booked successfully!";
        } else {
            return "Conflict found, meeting not booked!";
        }
    }

    public List<TimeSlot> findFreeSlots(Integer emp1Id, Integer emp2Id, int duration) {
        EmployeeCalendar calendar1 = employeeCalendars.get(emp1Id);
        EmployeeCalendar calendar2 = employeeCalendars.get(emp2Id);

        LocalDateTime startOfDay = LocalDateTime.now().withHour(9).withMinute(0);
        LocalDateTime endOfDay = LocalDateTime.now().withHour(17).withMinute(0);

        List<TimeSlot> freeSlots1 = calendar1.getFreeSlots(startOfDay, endOfDay, duration);
        List<TimeSlot> freeSlots2 = calendar2.getFreeSlots(startOfDay, endOfDay, duration);

        return findCommonFreeSlots(freeSlots1, freeSlots2, duration);
    }

    public List<Integer> checkConflicts(LocalDateTime startTime, LocalDateTime endTime, List<Integer> participants) {
        List<Integer> conflictedParticipants = new ArrayList<>();
        for (Integer participantId : participants) {
            EmployeeCalendar calendar = employeeCalendars.get(participantId);
            if (calendar != null && calendar.hasConflict(startTime, endTime)) {
                conflictedParticipants.add(participantId);
            }
        }
        return conflictedParticipants;
    }

    private List<TimeSlot> findCommonFreeSlots(List<TimeSlot> slots1, List<TimeSlot> slots2, int duration) {
        List<TimeSlot> commonSlots = new ArrayList<>();
        for (TimeSlot slot1 : slots1) {
            for (TimeSlot slot2 : slots2) {
                if (slot1.getStartTime().isEqual(slot2.getStartTime()) &&
                        slot1.getEndTime().isEqual(slot2.getEndTime())) {
                    commonSlots.add(slot1);
                }
            }
        }
        return commonSlots;
    }
}