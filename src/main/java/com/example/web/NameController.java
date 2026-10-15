
package com.example.web;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class NameController {

    private final JdbcTemplate jdbcTemplate;

    public NameController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/update/api")
    public ResponseEntity<?> saveName(@RequestBody Map<String, String> request) {
        String name = request.get("name");

        if (name == null || name.isBlank() || name.length() > 100) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Name must contain 1 to 100 characters"));
        }

        jdbcTemplate.update(
                "INSERT INTO dbo.submitted_names (name) VALUES (?)",
                name.trim()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Name saved successfully"));
    }

    @GetMapping("/update/apihealth/check")
    public ResponseEntity<?> healthCheck() {
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);

            return ResponseEntity.ok(Map.of(
                    "status", "UP",
                    "database", "UP"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(Map.of(
                            "status", "DOWN",
                            "database", "DOWN"
                    ));
        }
    }
}
