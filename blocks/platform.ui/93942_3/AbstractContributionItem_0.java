		if (scheduledUpdate == null) {
			Display current = Display.getCurrent();
			scheduledUpdate = () -> {
				try {
					_updateItemEnablement();
				} finally {
					scheduledUpdate = null;
				}
			};
			current.asyncExec(scheduledUpdate);
		}
	}

	protected void _updateItemEnablement() {
