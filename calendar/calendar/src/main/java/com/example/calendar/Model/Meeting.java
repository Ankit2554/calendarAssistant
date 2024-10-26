package com.example.calendar.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Meeting {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<Integer> participants;


}