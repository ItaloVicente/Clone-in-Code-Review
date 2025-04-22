    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NetworkLatencyMetricsEvent");
        sb.append(toMap().toString());
        return sb.toString();
    }

