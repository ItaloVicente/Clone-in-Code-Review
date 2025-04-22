		if (scheduledUpdate == null) {
			Display current = Display.getCurrent();
			scheduledUpdate = () -> {
				try {
					patchedUpdateItemEnablement();
				} finally {
					scheduledUpdate = null;
				}
			};
			current.asyncExec(scheduledUpdate);
		}
	}

	private void patchedUpdateItemEnablement() {
