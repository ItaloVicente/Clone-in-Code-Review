    private static ViewsPlugin instance;

    /**
     * Returns the singleton instance.
     *
     * @return the singleton instance.
     */
    public static ViewsPlugin getDefault() {
        return instance;
    }

    /**
     * Creates a new instance of the receiver.
     *
     * @see org.eclipse.core.runtime.Plugin#Plugin()
     */
    public ViewsPlugin() {
        super();
        instance = this;
    }

