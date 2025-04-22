
package com.couchbase.client.core.event.metrics;

import com.couchbase.client.core.event.CouchbaseEvent;
import com.couchbase.client.core.event.EventType;

import java.util.List;


public class LatencyMetricsEvent implements CouchbaseEvent {

    List<Metric> metrics;

    public LatencyMetricsEvent(List<Metric> metrics) {
        this.metrics = metrics;
    }

    public List<Metric> metrics() {
        return metrics;
    }

    @Override
    public EventType type() {
        return EventType.METRIC;
    }

    public static class Metric {

        private final String identifier;
        private final long min;
        private final long max;
        private final long count;
        private final double p99;
        private final double p999;
        private final double p9999;
        private final double p99999;

        public Metric(String identifier, long min, long max, long count, double p99, double p999, double p9999, double p99999) {
            this.identifier = identifier;
            this.min = min;
            this.max = max;
            this.count = count;
            this.p99 = p99;
            this.p999 = p999;
            this.p9999 = p9999;
            this.p99999 = p99999;
        }

        public String identifier() {
            return identifier;
        }

        public long min() {
            return min;
        }

        public long max() {
            return max;
        }

        public long count() {
            return count;
        }

        public double p99() {
            return p99;
        }

        public double p999() {
            return p999;
        }

        public double p9999() {
            return p9999;
        }

        public double p99999() {
            return p99999;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Metric{");
            sb.append("identifier='").append(identifier).append('\'');
            sb.append(", min=").append(min);
            sb.append(", max=").append(max);
            sb.append(", count=").append(count);
            sb.append(", p99=").append(p99);
            sb.append(", p999=").append(p999);
            sb.append(", p9999=").append(p9999);
            sb.append(", p99999=").append(p99999);
            sb.append('}');
            return sb.toString();
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LatencyMetricsEvent{");
        sb.append("metrics=").append(metrics);
        sb.append('}');
        return sb.toString();
    }


}
