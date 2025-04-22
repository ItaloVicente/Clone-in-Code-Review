    /**
     * Unregister all contributors for the target type.
     *
     * @param targetType the target type
     */
    public void unregisterContributors(String targetType) {
        contributors.remove(targetType);
        flushLookup();
    }
