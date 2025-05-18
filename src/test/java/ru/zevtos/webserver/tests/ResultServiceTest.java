package ru.zevtos.webserver.tests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import ru.zevtos.webserver.entities.Result;
import java.util.ArrayList;
import java.util.List;

public class ResultServiceTest {
    
    private List<Result> testResults;
    
    @Before
    public void setUp() {
        testResults = new ArrayList<>();
        testResults.add(Result.builder().id(1L).x(1.0).y(1.0).r(2.0).hit(true).build());
        testResults.add(Result.builder().id(2L).x(-1.0).y(-1.0).r(2.0).hit(false).build());
        testResults.add(Result.builder().id(3L).x(0.0).y(0.0).r(2.0).hit(true).build());
    }
    
    @Test
    public void testCountHits() {
        long hitCount = testResults.stream()
                .filter(Result::isHit)
                .count();
        assertEquals(2, hitCount);
    }
    
    @Test
    public void testFindResultsInRange() {
        List<Result> resultsInRange = testResults.stream()
                .filter(r -> r.getX() >= -1.0 && r.getX() <= 1.0)
                .toList();
        assertEquals(3, resultsInRange.size());
    }
    
    @Test
    public void testAverageRadius() {
        double avgRadius = testResults.stream()
                .mapToDouble(Result::getR)
                .average()
                .orElse(0.0);
        assertEquals(2.0, avgRadius, 0.001);
    }
} 