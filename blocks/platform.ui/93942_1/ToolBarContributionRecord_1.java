	public void updateVisibility(IEclipseContext context) {
		if (ENABLED_OPTIMIZATION) {
			synchronized (scheduledUpdate) {
				if (!scheduledUpdate.containsKey(context)) {
					Display current = Display.getCurrent();
					if (current == null) {
						_updateVisibility(context);
					} else {
						Runnable r = () -> {
							try {
								_updateVisibility(context);
							} finally {
								synchronized (scheduledUpdate) {
									scheduledUpdate.remove(context);
								}
							}
						};
						scheduledUpdate.put(context, r);
						current.asyncExec(r);
					}
				}
			}

		} else {
			_updateVisibility(context);
		}
	}

