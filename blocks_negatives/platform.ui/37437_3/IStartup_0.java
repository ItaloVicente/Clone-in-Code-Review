    /**
     * Will be called in a separate thread after the workbench initializes.
     * <p>
     * Note that most workbench methods must be called in the UI thread
     * since they may access SWT.  For example, to obtain the current workbench
     * window, use:
     * <code>
     * <pre>
     * final IWorkbench workbench = PlatformUI.getWorkbench();
     * workbench.getDisplay().asyncExec(new Runnable() {
     *   public void run() {
     *     IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
     *     if (window != null) {
     *     }
     *   }
     * });
     * </pre>
     * </code>
     * </p>
     * @see org.eclipse.swt.widgets.Display#asyncExec
     * @see org.eclipse.swt.widgets.Display#syncExec
     */
    public void earlyStartup();
