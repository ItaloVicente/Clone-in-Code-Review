	public void updateVisibility(IEclipseContext context) {
		scheduledUpdate.computeIfAbsent(context, c -> {
			Display current = Display.getCurrent();
			Runnable r = () -> {
				try {
					_updateVisibility(context);
				} finally {
					synchronized (scheduledUpdate) {
						scheduledUpdate.remove(context);
					}
				}
			};
			current.asyncExec(r);
			return r;
		});
	}

