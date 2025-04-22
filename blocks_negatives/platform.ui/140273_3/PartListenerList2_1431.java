    /**
     * PartListenerList2 constructor comment.
     */
    public PartListenerList2() {
        super();
    }

    /**
     * Adds an PartListener to the part service.
     */
    public void addPartListener(IPartListener2 l) {
        addListenerObject(l);
    }

    /**
     * Calls a part listener with associated performance event instrumentation
     *
     * @param runnable
     * @param listener
     * @param ref
     * @param string
     */
    private void fireEvent(SafeRunnable runnable, IPartListener2 listener, IWorkbenchPartReference ref, String string) {
    	if (UIStats.isDebugging(UIStats.NOTIFY_PART_LISTENERS)) {
    		label = string + ref.getTitle();
    		UIStats.start(UIStats.NOTIFY_PART_LISTENERS, label);
    	}
    	SafeRunner.run(runnable);
    	if (UIStats.isDebugging(UIStats.NOTIFY_PART_LISTENERS)) {
