    @InterfaceStability.Experimental
    Boolean upsertUser(String username, UserSettings settings);

    @InterfaceStability.Experimental
    Boolean upsertUser(String username, UserSettings settings, long timeout, TimeUnit timeUnit);

    @InterfaceStability.Experimental
    Boolean removeUser(String username);

    @InterfaceStability.Experimental
    Boolean removeUser(String username, long timeout, TimeUnit timeUnit);

    @InterfaceStability.Experimental
    List<User> getUsers();

    @InterfaceStability.Experimental
    List<User> getUsers(long timeout, TimeUnit timeUnit);

