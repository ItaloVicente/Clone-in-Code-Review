    /**
     * Create a new instance of the receiver.
     */
    public ProgressRegion() {
    }

    @Inject
    AnimationManager animationManager;

    @Inject
    ContentProviderFactory contentProviderfactory;

    @Inject
    FinishedJobs finishedJobs;

    /**
     * Create the contents of the receiver in the parent. Use the window for the
     * animation item.
     *
     * @param parent
     *            The parent widget of the composite.
     * @param window
     *            The WorkbenchWindow this is in.
     * @return Control
     */
    @PostConstruct
    public Control createContents(Composite parent) {
        GC gc = new GC(parent);
        gc.setAdvanced(true);
        forceHorizontal = !gc.getAdvanced();
        gc.dispose();

        region = new Composite(parent, SWT.NONE) {
