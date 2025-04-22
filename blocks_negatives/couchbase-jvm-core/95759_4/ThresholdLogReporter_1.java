    /**
     * Checks if the given span is a zombie span.
     *
     *      "It's not too late for you. My offer still stands: you can join us ... or you can die."
     *          -- Rick
     *
     * @param span the span to check.
     * @return true if it is, false otherwise.
     */
    private boolean isZombie(final ThresholdLogSpan span) {
        return "true".equals(span.getBaggageItem("couchbase.zombie"));
    }

