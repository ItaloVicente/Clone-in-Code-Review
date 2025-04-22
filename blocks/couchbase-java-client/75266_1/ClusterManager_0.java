    Boolean upsertUser(String username, UserSettings settings);

    Boolean upsertUser(String username, UserSettings settings, long timeout, TimeUnit timeUnit);

    Boolean removeUser(String username);

    Boolean removeUser(String username, long timeout, TimeUnit timeUnit);

