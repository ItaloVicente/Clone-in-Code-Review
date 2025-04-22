    private static TestPlugin plugin;

    private ResourceBundle resourceBundle;

    private static boolean earlyStartupCalled = false;

    public static final String PLUGIN_ID = "org.eclipse.ui.tests";

    /**
     * The constructor.
     */
    public TestPlugin() {
        plugin = this;
        try {
            resourceBundle = ResourceBundle
                    .getBundle("org.eclipse.ui.tests.TestPluginResources");
        } catch (MissingResourceException x) {
            resourceBundle = null;
        }
    }

    /**
     * Returns the shared instance.
     */
    public static TestPlugin getDefault() {
        return plugin;
    }

    /**
     * Returns the workspace instance.
     */
    public static IWorkspace getWorkspace() {
        return ResourcesPlugin.getWorkspace();
    }

    /**
     * Returns the string from the plugin's resource bundle,
     * or 'key' if not found.
     */
    public static String getResourceString(String key) {
        ResourceBundle bundle = TestPlugin.getDefault().getResourceBundle();
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return key;
        }
    }

    /**
     * Returns the plugin's resource bundle,
     */
    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    /**
     * Returns the image descriptor with the given relative path.
     */
    public ImageDescriptor getImageDescriptor(String relativePath) {
        String iconPath = "icons/";
        try {
