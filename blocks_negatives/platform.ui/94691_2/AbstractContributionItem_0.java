		if (scheduledUpdate == null) {
			Display current = Display.getCurrent();
			scheduledUpdate = () -> {
				try {
					performUpdateItemEnablement();
				} finally {
					scheduledUpdate = null;
				}
			};
			current.asyncExec(scheduledUpdate);
		}
	}

	private void performUpdateItemEnablement() {
