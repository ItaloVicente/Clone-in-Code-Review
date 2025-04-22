    public List<User> getUsers(AuthDomain domain, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncClusterManager.getUsers(domain).toList().single(), timeout, timeUnit);
    }

    @Override
    public User getUser(AuthDomain domain, String userid) {
        return Blocking.blockForSingle(asyncClusterManager.getUser(domain, userid).single(), timeout, TIMEOUT_UNIT);
    }

    @Override
    public User getUser(AuthDomain domain, String userid, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncClusterManager.getUser(domain, userid).single(), timeout, timeUnit);
