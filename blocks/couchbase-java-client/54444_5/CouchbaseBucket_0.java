
    @Override
    public int invalidateQueryCache() {
        return Blocking.blockForSingle(
            asyncBucket.invalidateQueryCache(), environment.managementTimeout(), TIMEOUT_UNIT
        );
    }
