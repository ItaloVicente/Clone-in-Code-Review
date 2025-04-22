	private boolean enabled = true;

	public NewWizardMenu(IWorkbenchWindow window) {
		this(window, null);

	}

	public NewWizardMenu(IWorkbenchWindow window, String id) {
		super(window, id);
		newExampleAction = new NewExampleAction(window);
		newProjectAction = new NewProjectAction(window);
	}

	@Deprecated
