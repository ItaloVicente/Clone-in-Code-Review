	private boolean shouldReturnNoWindowError(final IStatus result) {
		final boolean[] shouldReturn = new boolean[1];
		shouldReturn[0] = result.isOK() && application.getSelectedElement().getChildren().isEmpty();
		if (shouldReturn[0]) {
			display.syncExec(new Runnable() {
				@Override
				public void run() {
					shouldReturn[0] = display.getMenuBar() == null;
				}
			});
		}
		return shouldReturn[0];
	}

