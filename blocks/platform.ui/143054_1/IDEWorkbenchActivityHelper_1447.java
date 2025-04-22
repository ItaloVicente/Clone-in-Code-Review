	private static IDEWorkbenchActivityHelper singleton;

	public static IDEWorkbenchActivityHelper getInstance() {
		if (singleton == null) {
			singleton = new IDEWorkbenchActivityHelper();
		}
		return singleton;
	}

	private IDEWorkbenchActivityHelper() {
		lock = this;
