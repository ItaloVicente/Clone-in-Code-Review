		final String text = factory
				.create(PlatformUI.getWorkbench().getActiveWorkbenchWindow())
				.getText();
		Action result = new Action() {

			@Override
			public String getText() {
				return text;
			}
