
package com.couchbase.client.core.tracing;

import io.opentracing.Scope;
import io.opentracing.Span;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ThresholdLogTracerTest {

    private AssertingThresholdLogTracer tracer;

    @Before
    public void setup() {
        tracer = new AssertingThresholdLogTracer();
    }

    @Test
    public void shouldCompleteSimpleSpanOnClose() {
        assertNull(tracer.activeSpan());
        Scope scope = tracer.buildSpan("span").startActive(true);
        assertNotNull(tracer.activeSpan());
        fakeWork();
        scope.close();
        assertNull(tracer.activeSpan());

        assertEquals(1, tracer.reportedSpans().size());
        Span finished = tracer.reportedSpans().remove(0);
        assertEquals("span", ((ThresholdLogSpan)finished).operationName());
        assertTrue(((ThresholdLogSpan) finished).durationMicros() > 0);
    }

    @Test
    public void shouldNotCompleteSimpleSpanOnClose() {
        assertNull(tracer.activeSpan());
        Scope scope = tracer.buildSpan("span").startActive(false);
        assertNotNull(tracer.activeSpan());
        fakeWork();
        scope.close();
        assertNull(tracer.activeSpan());

        assertEquals(0, tracer.reportedSpans().size());

        scope.span().finish();
        assertNull(tracer.activeSpan());

        assertEquals(1, tracer.reportedSpans().size());
        Span finished = tracer.reportedSpans().remove(0);
        assertEquals("span", ((ThresholdLogSpan)finished).operationName());
        assertTrue(((ThresholdLogSpan) finished).durationMicros() > 0);
    }

    @Test
    public void shouldCompleteSpanWithTags() {
        assertNull(tracer.activeSpan());
        Scope scope = tracer.buildSpan("span")
            .withTag("builder-tag", true)
            .startActive(true);
        assertNotNull(tracer.activeSpan());
        scope.span().setTag("after-tag", "set");
        fakeWork();
        scope.close();
        assertNull(tracer.activeSpan());

        assertEquals(1, tracer.reportedSpans().size());
        Span finished = tracer.reportedSpans().remove(0);
        assertEquals("span", ((ThresholdLogSpan)finished).operationName());
        Map<String, Object> tags = ((ThresholdLogSpan) finished).tags();
        assertEquals(2, tags.size());
        assertEquals("set", tags.get("after-tag"));
        assertEquals(true, tags.get("builder-tag"));
        assertTrue(((ThresholdLogSpan) finished).durationMicros() > 0);
    }

    @Test
    public void shouldCompleteSpanWithBaggage() {
        assertNull(tracer.activeSpan());
        Scope scope = tracer.buildSpan("span").startActive(true);
        assertNotNull(tracer.activeSpan());
        scope.span().setBaggageItem("baggage-item", "baggage-value");
        fakeWork();
        scope.close();
        assertNull(tracer.activeSpan());

        assertEquals(1, tracer.reportedSpans().size());
        Span finished = tracer.reportedSpans().remove(0);
        assertEquals("span", ((ThresholdLogSpan)finished).operationName());
        assertEquals("baggage-value", finished.getBaggageItem("baggage-item"));
        assertTrue(((ThresholdLogSpan) finished).durationMicros() > 0);
    }

    @Test
    public void shouldCompleteClientAndParentSpanImplicit() {
        Scope parent = tracer.buildSpan("parent").startActive(true);
        Scope child = tracer.buildSpan("child").startActive(true);

        fakeWork();

        assertEquals(0, tracer.reportedSpans().size());
        child.close();

        assertEquals(1, tracer.reportedSpans().size());
        parent.close();

        assertEquals(2, tracer.reportedSpans().size());
        for (Span finished : tracer.reportedSpans()) {
            assertTrue(((ThresholdLogSpan) finished).durationMicros() > 0);
        }
    }

    @Test
    public void shouldCompleteClientAndParentSpanExplicit() {
        Scope parent = tracer.buildSpan("parent").startActive(true);
        parent.span().setBaggageItem("baggage-item", "baggage-value");

        Scope child = tracer.buildSpan("child")
            .ignoreActiveSpan()
            .asChildOf(parent.span())
            .startActive(true);

        fakeWork();

        assertEquals(0, tracer.reportedSpans().size());
        child.close();

        assertEquals(1, tracer.reportedSpans().size());
        parent.close();

        assertEquals(2, tracer.reportedSpans().size());
        for (Span finished : tracer.reportedSpans()) {
            assertTrue(((ThresholdLogSpan) finished).durationMicros() > 0);
            assertEquals("baggage-value", finished.getBaggageItem("baggage-item"));
        }
    }

    @Test
    public void shouldPropagateBaggageToChild() {
        Scope parent = tracer.buildSpan("parent").startActive(true);
        parent.span().setBaggageItem("baggage", "item");
        Scope child = tracer.buildSpan("child").startActive(true);

        fakeWork();

        assertEquals(0, tracer.reportedSpans().size());
        child.close();

        assertEquals(1, tracer.reportedSpans().size());
        parent.close();

        assertEquals(2, tracer.reportedSpans().size());
        for (Span finished : tracer.reportedSpans()) {
            assertTrue(((ThresholdLogSpan) finished).durationMicros() > 0);
            assertEquals("item", finished.getBaggageItem("baggage"));
        }
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldNotSupportInject() {
        tracer.inject(null, null, null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldNotSupportExtract() {
        tracer.extract(null, null);
    }

    private static void fakeWork() {
        try {
            Thread.sleep(1);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    class AssertingThresholdLogTracer extends ThresholdLogTracer {

        private final List<Span> reportedSpans = new ArrayList<Span>();

        @Override
        public synchronized void reportSpan(final Span span) {
            reportedSpans.add(span);
        }

        synchronized List<Span> reportedSpans() {
            return reportedSpans;
        }

    }
}
