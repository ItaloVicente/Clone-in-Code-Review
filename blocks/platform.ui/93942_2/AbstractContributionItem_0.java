		if (ENABLED_OPTIMIZATION) {
			if (scheduledUpdate == null) {
				Display current = Display.getCurrent();
				if (current == null) {
					_updateItemEnablement();
				} else {
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
		} else {
			_updateItemEnablement();
		}
	}

	protected void _updateItemEnablement() {
