		INewWizard {

	private IWorkbench workbench;

	protected IStructuredSelection selection;

	protected BasicNewResourceWizard() {
		super();
	}

	public IStructuredSelection getSelection() {
		return selection;
	}

	public IWorkbench getWorkbench() {
		return workbench;
	}

	@Override
