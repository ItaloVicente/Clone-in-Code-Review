    /**
     * Creates the workbench and associates it with the given display and workbench
     * advisor, and runs the workbench UI. This entails processing and dispatching
     * events until the workbench is closed or restarted.
     * <p>
     * This method is intended to be called by the main class (the "application").
     * Fails if the workbench UI has already been created.
     * </p>
     * <p>
     * Use {@link #createDisplay createDisplay} to create the display to pass in.
     * </p>
     * <p>
     * Note that this method is intended to be called by the application
     * (<code>org.eclipse.core.boot.IPlatformRunnable</code>). It must be
     * called exactly once, and early on before anyone else asks
     * <code>getWorkbench()</code> for the workbench.
     * </p>
     *
     * @param display the display to be used for all UI interactions with the workbench
     * @param advisor the application-specific advisor that configures and
     * specializes the workbench
     * @return return code {@link #RETURN_OK RETURN_OK} for normal exit;
     * {@link #RETURN_RESTART RETURN_RESTART} if the workbench was terminated
     * with a call to {@link IWorkbench#restart IWorkbench.restart};
     * {@link #RETURN_UNSTARTABLE RETURN_UNSTARTABLE} if the workbench could
     * not be started;
     * {@link #RETURN_EMERGENCY_CLOSE RETURN_EMERGENCY_CLOSE} if the UI quit
     * because of an emergency; other values reserved for future use
     * @since 3.0
     */
