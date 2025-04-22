    public AnalyticsParams priority(boolean priority) {
        return priority(-1);
    }

    private AnalyticsParams priority(int priority) {
        this.priority = priority;
        return this;
    }

    @InterfaceAudience.Private
    public int priority() {
        return priority;
    }

