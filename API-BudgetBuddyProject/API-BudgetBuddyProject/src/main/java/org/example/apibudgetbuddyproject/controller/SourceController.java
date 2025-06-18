package org.example.apibudgetbuddyproject.controller;

import org.example.apibudgetbuddyproject.model.Source;
import org.example.apibudgetbuddyproject.service.SourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// defining controller SourceController to handle requests
@RestController
@RequestMapping("/api/sources")
public class SourceController {

    private final SourceService sourceService;

    @Autowired
    public SourceController(SourceService sourceService) {
        this.sourceService = sourceService;
    }

    // controller for getAllSources service
    @GetMapping
    public ResponseEntity<List<Source>> getAllSources() {
        List<Source> categories = sourceService.getAllSources();
        return ResponseEntity.ok(categories);
    }

    // controller for getSourceIdByName service
    @GetMapping("/{name}")
    public ResponseEntity<Integer> getSourceIdByName(@PathVariable String name) {
        int sourceId = sourceService.getSourceIdByName(name);
        if (sourceId != -1) {
            return ResponseEntity.ok(sourceId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // controller for getSourceNameById service
    @GetMapping("/getSourceName/{sourceId}")
    public ResponseEntity<String> getSourceNameById(@PathVariable int sourceId) {
        String sourceName = sourceService.getSourceNameById(sourceId);
        return ResponseEntity.ok(sourceName);
    }
}