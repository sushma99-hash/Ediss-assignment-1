package cmu.edu.ds.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that handles the status endpoint for monitoring.
 */
@RestController
@RequestMapping("/status")
public class StatusController {

    /**
     * Handles GET requests to check the status of the service.
     *
     * @return ResponseEntity with "OK" text and 200 status code
     */
    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok("OK");
    }
}