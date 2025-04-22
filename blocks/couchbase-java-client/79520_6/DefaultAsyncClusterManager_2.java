    public Observable<User> getUsers(final AuthDomain domain) {
        return getUser(domain,null);
    }

    @Override
    public Observable<User> getUser(final AuthDomain domain, final String userid) {
