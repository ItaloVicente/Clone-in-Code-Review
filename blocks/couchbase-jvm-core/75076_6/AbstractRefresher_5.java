    @Override
    public Observable<Boolean> registerBucket(String name, String username, String password) {
        LOGGER.debug("Registering Bucket {} for refresh.", name);
        if (registrations.containsKey(name)) {
            return Observable.just(false);
        }

        registrations.put(name, password);
        return Observable.just(true);
    }

