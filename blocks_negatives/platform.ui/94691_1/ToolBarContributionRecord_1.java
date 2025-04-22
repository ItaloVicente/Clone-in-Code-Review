		scheduledUpdate.computeIfAbsent(context, c -> {
			Display current = Display.getCurrent();
			Runnable r = () -> {
				try {
					performUpdateVisibility(context);
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

	/**
	 * @param context
	 */
	private void performUpdateVisibility(IEclipseContext context) {
