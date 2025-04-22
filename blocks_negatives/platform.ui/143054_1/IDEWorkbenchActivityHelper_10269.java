    /**
     * Singleton instance.
     */
    private static IDEWorkbenchActivityHelper singleton;

    /**
     * Get the singleton instance of this class.
     * @return the singleton instance of this class.
     * @since 3.0
     */
    public static IDEWorkbenchActivityHelper getInstance() {
        if (singleton == null) {
            singleton = new IDEWorkbenchActivityHelper();
        }
        return singleton;
    }

    /**
     * Create a new <code>IDEWorkbenchActivityHelper</code> which will listen
     * for workspace changes and promote activities accordingly.
     */
    private IDEWorkbenchActivityHelper() {
    	lock = this;
