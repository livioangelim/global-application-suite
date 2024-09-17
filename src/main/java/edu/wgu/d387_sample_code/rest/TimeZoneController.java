package edu.wgu.d387_sample_code.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/time")
@CrossOrigin(origins = "http://localhost:4200")
public class TimeZoneController {

    @GetMapping("/presentation")
    public ResponseEntity<List<String>> getPresentationTimes() {
        List<String> times = new ArrayList<>();

        ZonedDateTime nowET = ZonedDateTime.now(ZoneId.of("America/New_York"));
        ZonedDateTime nowMT = nowET.withZoneSameInstant(ZoneId.of("America/Denver"));
        ZonedDateTime nowUTC = nowET.withZoneSameInstant(ZoneId.of("UTC"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        times.add("ET: " + nowET.format(formatter));
        times.add("MT: " + nowMT.format(formatter));
        times.add("UTC: " + nowUTC.format(formatter));

        return ResponseEntity.ok(times);
    }
}
