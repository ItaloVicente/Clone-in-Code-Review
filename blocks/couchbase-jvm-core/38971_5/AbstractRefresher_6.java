        registrations = new ConcurrentHashMap<String, String>();
    }

    @Override
    public Observable<Boolean> deregisterBucket(String name) {
        if (registrations.containsKey(name)) {
            registrations.remove(name);
            return Observable.just(true);
        }
        return Observable.just(false);
    }

    @Override
    public Observable<Boolean> registerBucket(String name, String password) {
        if (registrations.containsKey(name)) {
            return Observable.just(false);
        }

        registrations.put(name, password);
        return Observable.just(true);
