	public WorkbenchPage(WorkbenchWindow w, IAdaptable input) throws WorkbenchException {
		super();
		init(w, null, input, false);
	}

	public MWindow getWindowModel() {
		return window;

	}

	public void activate(IWorkbenchPart part) {
