    @Override
    public User getUser(String userid) {
        return Blocking.blockForSingle(asyncClusterManager.getUser(userid).single(), timeout, TIMEOUT_UNIT);
    }

    @Override
    public User getUser(String userid, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncClusterManager.getUser(userid).single(), timeout, timeUnit);
    }

