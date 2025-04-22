	protected PageEventAction(String text, IWorkbenchWindow window) {
		super(text);
		if (window == null) {
			throw new IllegalArgumentException();
		}
		this.workbenchWindow = window;
		this.activePage = window.getActivePage();
		this.workbenchWindow.addPageListener(this);
		this.workbenchWindow.getPartService().addPartListener(this);
	}
