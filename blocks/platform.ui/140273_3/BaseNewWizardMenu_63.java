	public BaseNewWizardMenu(IWorkbenchWindow window, String id) {
		super(id);
		Assert.isNotNull(window);
		this.workbenchWindow = window;
		showDlgAction = ActionFactory.NEW.create(window);
		registerListeners();
