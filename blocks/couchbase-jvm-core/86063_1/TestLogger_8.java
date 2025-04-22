
package com.couchbase.client.core.logging;

import org.junit.Test;

import static com.couchbase.client.core.logging.RedactableArgument.meta;
import static com.couchbase.client.core.logging.RedactableArgument.system;
import static com.couchbase.client.core.logging.RedactableArgument.user;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class LogRedactionTest {

    @Test
    public void shouldNotRedactLogsWhenDisabled() {
        TestLogger logger = new TestLogger(RedactionLevel.NONE);
        assertEquals(0, logger.entries().size());

        logger.infoRedacted("Some {} stuff {}", meta("meta"), user("user"));
        assertEquals(1, logger.entries().size());

        TestLogger.LogEntry entry = logger.entries().get(0);
        assertEquals(2, entry.args().size());
        assertNull(entry.throwable());
        assertEquals("Some {} stuff {}", entry.format());

        assertEquals("meta", ((RedactableArgument) entry.args().get(0)).message());
        assertEquals("user", ((RedactableArgument) entry.args().get(1)).message());
    }

    @Test
    public void shoulOnlyRedactUserOnPartial() {
        TestLogger logger = new TestLogger(RedactionLevel.PARTIAL);
        assertEquals(0, logger.entries().size());

        logger.infoRedacted("Some {} stuff {}", meta("meta"), user("user"));
        assertEquals(2, logger.entries().size());

        TestLogger.LogEntry entry = logger.entries().get(0);
        assertEquals(CouchbaseLogLevel.INFO, entry.level());
        assertEquals(2, entry.args().size());
        assertNull(entry.throwable());
        assertEquals("Some {} stuff {}", entry.format());

        assertEquals("meta", ((RedactableArgument) entry.args().get(0)).message());
        assertEquals("- REDACTED -", entry.args().get(1));

        TestLogger.LogEntry secondEntry = logger.entries().get(1);
        assertEquals(CouchbaseLogLevel.DEBUG, secondEntry.level());
        assertEquals(2, secondEntry.args().size());
        assertNull(secondEntry.throwable());
        assertEquals("Some {} stuff {}", secondEntry.format());

        assertEquals("meta", ((RedactableArgument) secondEntry.args().get(0)).message());
        assertEquals("user", ((RedactableArgument) secondEntry.args().get(1)).message());
    }

    @Test
    public void shoulOnlyRedactAllIfFull() {
        TestLogger logger = new TestLogger(RedactionLevel.FULL);
        assertEquals(0, logger.entries().size());

        logger.infoRedacted("Some {} stuff {} and {}", meta("meta"), user("user"), system("system"));
        assertEquals(2, logger.entries().size());

        TestLogger.LogEntry entry = logger.entries().get(0);
        assertEquals(CouchbaseLogLevel.INFO, entry.level());
        assertEquals(3, entry.args().size());
        assertNull(entry.throwable());
        assertEquals("Some {} stuff {} and {}", entry.format());

        assertEquals("- REDACTED -", entry.args().get(0));
        assertEquals("- REDACTED -", entry.args().get(1));
        assertEquals("- REDACTED -", entry.args().get(2));

        TestLogger.LogEntry secondEntry = logger.entries().get(1);
        assertEquals(CouchbaseLogLevel.DEBUG, secondEntry.level());
        assertEquals(3, secondEntry.args().size());
        assertNull(secondEntry.throwable());
        assertEquals("Some {} stuff {} and {}", secondEntry.format());

        assertEquals("meta", ((RedactableArgument) secondEntry.args().get(0)).message());
        assertEquals("user", ((RedactableArgument) secondEntry.args().get(1)).message());
        assertEquals("system", ((RedactableArgument) secondEntry.args().get(2)).message());
    }


}
