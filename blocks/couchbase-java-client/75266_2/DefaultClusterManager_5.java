    @Override
    public Boolean upsertUser(String username, UserSettings settings) {
        return upsertUser(username, settings, timeout, TIMEOUT_UNIT);
    }

    @Override
    public Boolean upsertUser(String username, UserSettings settings, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncClusterManager.upsertUser(username, settings).single(), timeout, timeUnit);
    }

    @Override
    public Boolean removeUser(String username) {
        return removeUser(username, timeout, TIMEOUT_UNIT);
    }

    @Override
    public Boolean removeUser(String username, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncClusterManager.removeUser(username).single(), timeout, timeUnit);
    }

