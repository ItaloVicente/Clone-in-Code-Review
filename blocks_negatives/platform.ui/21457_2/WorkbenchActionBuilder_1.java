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
            public void pageActivated(IWorkbenchPage page) {
            }

            public void pageClosed(IWorkbenchPage page) {
            }

            public void pageOpened(IWorkbenchPage page) {
                IAction buildHandler = new BuildAction(page.getWorkbenchWindow(), IncrementalProjectBuilder.INCREMENTAL_BUILD);
            	((RetargetActionWithDefault)buildProjectAction).setDefaultHandler(buildHandler);
            }
        };
        getWindow().addPageListener(pageListener);

        prefListener = new Preferences.IPropertyChangeListener() {
            public void propertyChange(Preferences.PropertyChangeEvent event) {
                if (event.getProperty().equals(
                        ResourcesPlugin.PREF_AUTO_BUILDING)) {
                   	updateBuildActions(false);
                }
            }
        };
        ResourcesPlugin.getPlugin().getPluginPreferences()
                .addPropertyChangeListener(prefListener);

        propPrefListener = new IPropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent event) {
                if (event.getProperty().equals(
