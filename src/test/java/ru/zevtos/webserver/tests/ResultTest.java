package ru.zevtos.webserver.tests;

import org.junit.Test;
import static org.junit.Assert.*;
import ru.zevtos.webserver.entities.Result;

public class ResultTest {
    
    @Test
    public void testResultBuilder() {
        Result result = Result.builder()
                .id(1L)
                .x(2.0)
                .y(3.0)
                .r(4.0)
                .hit(true)
                .build();
        
        assertEquals(1L, result.getId().longValue());
        assertEquals(2.0, result.getX(), 0.001);
        assertEquals(3.0, result.getY(), 0.001);
        assertEquals(4.0, result.getR(), 0.001);
        assertTrue(result.isHit());
    }
    
    @Test
    public void testResultEquality() {
        Result result1 = Result.builder().id(1L).build();
        Result result2 = Result.builder().id(1L).build();
        Result result3 = Result.builder().id(2L).build();
        
        assertTrue(result1.equals(result2));
        assertFalse(result1.equals(result3));
        assertEquals(result1.hashCode(), result2.hashCode());
    }
    
    @Test
    public void testResultToString() {
        Result result = Result.builder()
                .id(1L)
                .x(2.0)
                .y(3.0)
                .r(4.0)
                .hit(true)
                .build();
        
        String expected = "Result{id=1, x=2.0, y=3.0, r=4.0, hit=true}";
        assertEquals(expected, result.toString());
    }
} 