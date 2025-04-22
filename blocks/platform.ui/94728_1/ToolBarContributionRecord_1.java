		if (!scheduledUpdate.containsKey(context)) {
			performUpdateVisibility(context);
			Display current = Display.getCurrent();
			Runnable r = () -> {
				scheduledUpdate.remove(context);
			};
			current.asyncExec(r);
			scheduledUpdate.put(context, Integer.valueOf(0));
		} else if (scheduledUpdate.get(context).intValue() == 0) {
			scheduledUpdate.put(context, Integer.valueOf(1));
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
		} else {
		}
	}

	private void performUpdateVisibility(IEclipseContext context) {
