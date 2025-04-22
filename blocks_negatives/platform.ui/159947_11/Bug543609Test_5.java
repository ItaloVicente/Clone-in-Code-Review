	public Bug543609Test() {
		super(Bug543609Test.class.getSimpleName());
	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		IWorkbenchWindow window = openTestWindow();
