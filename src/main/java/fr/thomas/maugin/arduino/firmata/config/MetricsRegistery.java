package fr.thomas.maugin.arduino.firmata.config;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Created by Thomas on 24/10/2015.
 */

@Configuration
public class MetricsRegistery {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetricsRegistery.class);

    @Value("${metrics.console}")
    private Boolean enableConsole;

    @Value("${metrics.jmx}")
    private Boolean enableJmx;

    @Value("${metrics.slf4j}")
    private Boolean enableSlf4j;

    @Value("${metrics.frequency}")
    private Integer frequency;

    @Bean
    public MetricRegistry getMetricRegistry() {
        final MetricRegistry metricRegistry = new MetricRegistry();

        if(enableConsole) {
            final ConsoleReporter reporter = ConsoleReporter.forRegistry(metricRegistry)
                    .convertRatesTo(TimeUnit.SECONDS)
                    .convertDurationsTo(TimeUnit.MILLISECONDS)
                    .build();
            reporter.start(frequency, TimeUnit.SECONDS);
        }
        if(enableJmx) {
            final JmxReporter reporter = JmxReporter.forRegistry(metricRegistry).build();
            reporter.start();
        }

        if(enableSlf4j) {
            final Slf4jReporter reporter = Slf4jReporter.forRegistry(metricRegistry)
                    .outputTo(LoggerFactory.getLogger(MetricsRegistery.class))
                    .convertRatesTo(TimeUnit.SECONDS)
                    .convertDurationsTo(TimeUnit.MILLISECONDS)
                    .build();
            reporter.start(frequency, TimeUnit.SECONDS);
        }
        return metricRegistry;
    }
}
