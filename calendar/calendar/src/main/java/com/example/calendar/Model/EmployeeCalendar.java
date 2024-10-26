package com.example.calendar.Model;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class EmployeeCalendar {
    private final Integer employeeId;
    private final List<Meeting> meetings;

    public EmployeeCalendar(Integer employeeId) {
        this.employeeId = employeeId;
        this.meetings = new ArrayList<>();
    }

    public boolean bookMeeting(Meeting meeting) {
        if (!hasConflict(meeting.getStartTime(), meeting.getEndTime())) {
            meetings.add(meeting);
            return true;
        }
        return false;
    }

    public boolean hasConflict(LocalDateTime startTime, LocalDateTime endTime) {
        for (Meeting meeting : meetings) {
            if (!(endTime.isBefore(meeting.getStartTime()) || startTime.isAfter(meeting.getEndTime()))) {
                return true;
            }
        }
        return false;
    }

    public List<TimeSlot> getFreeSlots(LocalDateTime startRange, LocalDateTime endRange, int duration) {
        List<TimeSlot> freeSlots = new ArrayList<>();
        LocalDateTime slotStart = startRange;
        while (slotStart.plusMinutes(duration).isBefore(endRange) || slotStart.plusMinutes(duration).isEqual(endRange)) {
            boolean conflict = hasConflict(slotStart, slotStart.plusMinutes(duration));
            if (!conflict) {
                freeSlots.add(new TimeSlot(slotStart, slotStart.plusMinutes(duration)));
            }
            slotStart = slotStart.plusMinutes(30);
        }
        return freeSlots;
    }
}