    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RetrySpecification {
        private final RetryStrategy strategy;
        private final long interval;
        private final long after;
        private final long maxDuration;
        private final long ceil;

        @JsonCreator
        public RetrySpecification(
                @JsonProperty("strategy") RetryStrategy strategy,
                @JsonProperty("interval") int interval,
                @JsonProperty("after") int after,
                @JsonProperty("max-duration") int maxDuration,
                @JsonProperty("ceil") int ceil) {
            this.strategy = strategy;
            this.interval = interval;
            this.after = after;
            this.maxDuration = maxDuration;
            this.ceil = ceil;
        }

        public RetryStrategy strategy() {
            return this.strategy;
        }

        public long interval() {
            return this.interval;
        }

        public long after() {
            return this.after;
        }

        public long maxDuration() {
            return this.maxDuration;
        }

        public long ceil() { return this.ceil; }

        @Override
        public String toString() {
            return "Retry{" +
                    "strategy=" + strategy() +
                    ", interval=" + interval() +
                    ", after=" + after() +
                    ", max-duration=" + maxDuration() +
                    ", ceil=" + ceil() +
                    "}";
        }
    }

    public enum RetryStrategy {
        EXPONENTIAL("exponential"),
        LINEAR("linear"),
        CONSTANT("constant");

        private final String strategy;

        RetryStrategy(String strategy) {
            this.strategy = strategy;
        }

        @JsonValue
        public String strategy() {
            return strategy;
        }
    }

