	private IAction createItem(String text, final String commandId,
			final IStructuredSelection selection) {
		return new Action(text) {
			@Override
			public void run() {
				CommonUtils.runCommand(commandId, selection);
			}
		};
