package com.couchbase.client.core.event.consumers;

import com.couchbase.client.core.event.CouchbaseEvent;
import com.couchbase.client.core.logging.CouchbaseLogLevel;
import com.couchbase.client.core.logging.CouchbaseLogger;
import com.couchbase.client.core.logging.CouchbaseLoggerFactory;
import com.couchbase.client.core.utils.Events;
import rx.Subscriber;

public class LoggingConsumer extends Subscriber<CouchbaseEvent> {

    private static final CouchbaseLogger LOGGER = CouchbaseLoggerFactory.getInstance(LoggingConsumer.class);

    private final CouchbaseLogLevel level;
    private final OutputFormat outputFormat;

    private LoggingConsumer(CouchbaseLogLevel level, OutputFormat outputFormat) {
        super();
        this.level = level;
        this.outputFormat = outputFormat;
    }

    public static LoggingConsumer create() {
        return create(CouchbaseLogLevel.INFO, OutputFormat.JSON_PRETTY);
    }

    public static LoggingConsumer create(CouchbaseLogLevel level, OutputFormat outputFormat) {
        return new LoggingConsumer(level, outputFormat);
    }

    @Override
    public void onCompleted() {
        LOGGER.trace("Event stream completed in logging consumer.");
    }

    @Override
    public void onError(Throwable ex) {
        LOGGER.warn("Received error in logging consumer.", ex);
    }

    @Override
    public void onNext(CouchbaseEvent event) {
        try {
            switch (outputFormat) {
                case JSON:
                    LOGGER.log(level, Events.toJson(event, false));
                    break;
                case JSON_PRETTY:
                    LOGGER.log(level, Events.toJson(event, true));
                    break;
                case TO_STRING:
                    LOGGER.log(level, event.toString());
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported output format: " + outputFormat.toString());
            }
        } catch (Exception ex) {
            LOGGER.warn("Received error while logging event in logging consumer.", ex);
        }
    }

    public enum OutputFormat {

        JSON,

        JSON_PRETTY,

        TO_STRING
    }

}
