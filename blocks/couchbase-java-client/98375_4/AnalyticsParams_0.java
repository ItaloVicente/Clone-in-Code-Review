    public AnalyticsParams priority(boolean priority) {
        return priority(priority ? -1 : 0);
    }

    private AnalyticsParams priority(int priority) {
        this.priority = priority;
        return this;
    }

    @InterfaceAudience.Private
    public int priority() {
        return priority;
    }

