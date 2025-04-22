    private IResourceChangeListener resourceListener;

    /**
     * Indicates if the action builder has been disposed
     */
    private boolean isDisposed = false;

    /**
     * Constructs a new action builder which contributes actions
     * to the given window.
     *
     * @param configurer the action bar configurer for the window
     */
    public WorkbenchActionBuilder(IActionBarConfigurer configurer) {
        super(configurer);
        window = configurer.getWindowConfigurer().getWindow();
    }

    /**
     * Returns the window to which this action builder is contributing.
     */
    private IWorkbenchWindow getWindow() {
        return window;
    }

    /**
     * Hooks listeners on the preference store and the window's page, perspective and selection services.
     */
    private void hookListeners() {

        pageListener = new IPageListener() {
            @Override
