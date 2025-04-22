    protected ConfigurationProvider provider() {
        return provider;
    }

    protected Map<String, String> registrations() {
        return registrations;
    }

    @Override
    public void provider(ConfigurationProvider provider) {
        this.provider = provider;
    }

