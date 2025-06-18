package org.example.apibudgetbuddyproject.service;

import org.example.apibudgetbuddyproject.exception.ResourceNotFoundException;
import org.example.apibudgetbuddyproject.model.Source;
import org.example.apibudgetbuddyproject.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// defining SourceService as a Service class
@Service
public class SourceService {

    private final SourceRepository sourceRepository;

    // repository for source data access
    @Autowired
    public SourceService(SourceRepository sourceRepository) {
        this.sourceRepository = sourceRepository;
    }

    // service to retrieve all sources from database
    public List<Source> getAllSources() {
        return sourceRepository.findAll();
    }

    // service to get sourceId by its name
    public int getSourceIdByName(String name) {
        return sourceRepository.findByName(name)
                .map(Source::getId)
                .orElseThrow(() -> new ResourceNotFoundException("Source not found with name: " + name));
    }

    // service to get source name by its id
    public String getSourceNameById(int sourceId) {
        Optional<Source> sourceOptional = sourceRepository.findById(sourceId);

        // checks if source with such an id exists (if yes, it returns its name)
        if (sourceOptional.isPresent()) {
            Source source = sourceOptional.get();
            return source.getName();
        } else {
            throw new ResourceNotFoundException("Source not found with ID: " + sourceId);
        }
    }
}