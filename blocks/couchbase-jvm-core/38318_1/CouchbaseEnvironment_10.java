    @Override
    public boolean compressionEnabled() {
        return getBoolean("compression.enabled");
    }

    @Override
    public int compressionLowerLimit() {
        int limit = getInt("compression.lowerLimit");
        if (limit < 0) {
            throw new EnvironmentException("The lower compression limit must be at least 0.");
        }
        return limit;
    }

