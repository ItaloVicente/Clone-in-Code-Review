    List<User> getUsers(AuthDomain domain, long timeout, TimeUnit timeUnit);

    @InterfaceStability.Experimental
    User getUser(AuthDomain domain, String userid);


    @InterfaceStability.Experimental
    User getUser(AuthDomain domain, String userid, long timeout, TimeUnit timeUnit);

