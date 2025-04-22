        registrations.put(name, new Credential(name, password));
        return Observable.just(true);
    }

    @Override
    public Observable<Boolean> registerBucket(String name, String username, String password) {
        LOGGER.debug("Registering Bucket {} for refresh.", name);
        if (registrations.containsKey(name)) {
            return Observable.just(false);
        }

        registrations.put(name, new Credential(username, password));
