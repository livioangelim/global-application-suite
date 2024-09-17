package edu.wgu.d387_sample_code.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/resources")
@CrossOrigin(origins = "http://localhost:4200")
public class ResourcesController {

    private final ExecutorService executorService = Executors.newFixedThreadPool(2);

    @GetMapping("welcome")
    public ResponseEntity<List<String>> getWelcomeMessage() {
        List<String> messages = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(2); //latch for two threads

        //Thread for English message
        executorService.execute(() -> {
            try {
                LocaleResourceReader readerEN = new LocaleResourceReader("en", "US");
                messages.add(readerEN.getWelcomeMessage());
                System.out.println("en_US Message Retrieved");
            } finally {
                latch.countDown();  //decrement latch count
            }
        });

        //Thread for French message
        executorService.execute(() -> {
            try {
                LocaleResourceReader readerFR = new LocaleResourceReader("fr", "CA");
                messages.add(readerFR.getWelcomeMessage());
                System.out.println("fr_CA Message Retrieved");
            } finally {
                latch.countDown();  //decrement latch count
            }
        });

        //Wait for both threads to finish
        try {
            latch.await();  //This will wait until count reaches zero
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(messages);
    }
}