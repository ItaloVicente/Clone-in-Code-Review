	@Override
	protected void performOperation() {
		int timeout = Activator.getDefault().getPreferenceStore()
				.getInt(UIPreferences.REMOTE_CONNECTION_TIMEOUT);
		FetchOperationUI op = new FetchOperationUI(getRepository(), getConfig(),
				timeout, false);
		op.start();
