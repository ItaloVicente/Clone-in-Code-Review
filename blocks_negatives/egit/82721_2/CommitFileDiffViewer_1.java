	private IAction createStandardAction(final ActionFactory af) {
		final String text = af.create(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow()).getText();
		IAction action = new Action() {

			@Override
			public String getActionDefinitionId() {
				return af.getCommandId();
			}

			@Override
			public String getId() {
				return af.getId();
			}

			@Override
			public String getText() {
				return text;
			}

			@Override
			public void run() {
				if (af == ActionFactory.SELECT_ALL)
					doSelectAll();
				if (af == ActionFactory.COPY)
					doCopy();
			}
		};
		action.setEnabled(true);
		return action;
	}

