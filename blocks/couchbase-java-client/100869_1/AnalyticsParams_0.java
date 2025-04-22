    @InterfaceAudience.Private
    public boolean deferred() {
        return deferred;
    }

    private AnalyticsParams deferred(boolean deferred) {
        this.deferred = deferred;
        return this;
    }

