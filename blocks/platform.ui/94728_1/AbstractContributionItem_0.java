		if (updatePerformedState == 0) {
			updatePerformedState = 1;
			performUpdateItemEnablement();

			Display current = Display.getCurrent();
			Runnable scheduledUpdate = () -> {
				updatePerformedState = 0;
			};
			current.asyncExec(scheduledUpdate);

		} else if (updatePerformedState == 1) {
			updatePerformedState = 2;
			Display current = Display.getCurrent();
			Runnable scheduledUpdate = () -> {
				performUpdateItemEnablement();
			};
			current.asyncExec(scheduledUpdate);
		} else {
		}
	}

	private void performUpdateItemEnablement() {
