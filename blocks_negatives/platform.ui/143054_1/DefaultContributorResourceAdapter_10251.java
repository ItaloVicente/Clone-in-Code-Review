        IContributorResourceAdapter2 {

    private static IContributorResourceAdapter singleton;

    /**
     * Constructor for DefaultContributorResourceAdapter.
     */
    public DefaultContributorResourceAdapter() {
        super();
    }

    /**
     * Return the default instance used for TaskList adapting.
     * @return the default instance used for TaskList adapting
     */
    public static IContributorResourceAdapter getDefault() {
        if (singleton == null) {
