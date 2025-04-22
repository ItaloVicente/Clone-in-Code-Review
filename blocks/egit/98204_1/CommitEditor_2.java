	private IContributionItem createActionContributionItem(String commandId,
			String title, ImageDescriptor icon) {
		IAction action = new Action(title, icon) {

			@Override
			public void run() {
				CommonUtils.runCommand(commandId,
						new StructuredSelection(getCommit()));
			}
		};
		return new ActionContributionItem(action);
