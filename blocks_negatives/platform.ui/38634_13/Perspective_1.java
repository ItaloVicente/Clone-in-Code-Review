    protected HashMap showInTimes = new HashMap();

    protected IMemento memento;

    /**
     * Reference to the part that was previously active
     * when this perspective was deactivated.
     */
    private IWorkbenchPartReference oldPartRef = null;

    protected boolean shouldHideEditorsOnActivate = false;

	protected MPerspective layout;

    /**
     * ViewManager constructor comment.
     */
