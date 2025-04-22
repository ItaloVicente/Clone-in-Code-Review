		IContributorResourceAdapter2 {

	private static IContributorResourceAdapter singleton;

	public DefaultContributorResourceAdapter() {
		super();
	}

	public static IContributorResourceAdapter getDefault() {
		if (singleton == null) {
