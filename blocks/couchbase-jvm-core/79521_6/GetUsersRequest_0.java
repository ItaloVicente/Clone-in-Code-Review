    private static final String PATH_PREFIX = "/settings/rbac/users";

    private final String userId;
    private final String domain;


    public static GetUsersRequest users(String username, String password) {
        return new GetUsersRequest(username, password, null, null);
    }

    public static GetUsersRequest usersFromDomain(String username, String password, String domain) {
        return new GetUsersRequest(username, password, domain, null);
    }

    public static GetUsersRequest user(String username, String password, String domain, String userId) {
        return new GetUsersRequest(username, password, domain, userId);
    }

    private GetUsersRequest(String username, String password, String domain, String userId) {
