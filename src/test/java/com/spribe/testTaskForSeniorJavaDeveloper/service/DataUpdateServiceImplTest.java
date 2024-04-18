package com.spribe.testTaskForSeniorJavaDeveloper.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class DataUpdateServiceImplTest {

    @Mock
    private CurrencyRateService currencyRateService;

    @InjectMocks
    private DataUpdateServiceImpl dataUpdateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testUpdateData() throws Exception {
        // Given
        doNothing().when(currencyRateService).getAndSaveCurrencyRates(anyString());
        // When
        dataUpdateService.updateData();
        // Then
        verify(currencyRateService, times(1)).getAndSaveCurrencyRates(anyString());

    }

    @Test
    void testRunScheduler() throws InterruptedException {
        // Given
        ScheduledExecutorService executorService = mock(ScheduledExecutorService.class);
        when(Executors.newScheduledThreadPool(1)).thenReturn(executorService);
        // When
        dataUpdateService.runScheduler();
        // Then
        verify(executorService).scheduleAtFixedRate(any(), eq(0L), eq(2L), eq(TimeUnit.HOURS));
    }
}