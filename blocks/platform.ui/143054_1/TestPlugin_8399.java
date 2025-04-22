	private static TestPlugin plugin;

	private ResourceBundle resourceBundle;

	private static boolean earlyStartupCalled = false;

	public static final String PLUGIN_ID = "org.eclipse.ui.tests";

	public TestPlugin() {
		plugin = this;
		try {
			resourceBundle = ResourceBundle
					.getBundle("org.eclipse.ui.tests.TestPluginResources");
		} catch (MissingResourceException x) {
			resourceBundle = null;
		}
	}

	public static TestPlugin getDefault() {
		return plugin;
	}

	public static IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}

	public static String getResourceString(String key) {
		ResourceBundle bundle = TestPlugin.getDefault().getResourceBundle();
		try {
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			return key;
		}
	}

	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	public ImageDescriptor getImageDescriptor(String relativePath) {
		String iconPath = "icons/";
		try {
