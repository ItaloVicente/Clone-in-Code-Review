	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(Theme.class
			.getName());

	public static interface State {
		int UNKNOWN = 0;

		int OVERRIDDEN = 1;

		int ADDED_BY_CSS = 2;

		int MODIFIED_BY_USER = 4;
	}

