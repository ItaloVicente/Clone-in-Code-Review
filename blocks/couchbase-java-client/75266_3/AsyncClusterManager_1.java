    @InterfaceStability.Experimental
    Observable<Boolean> upsertUser(String username, UserSettings settings);

    @InterfaceStability.Experimental
    Observable<Boolean> removeUser(String username);

