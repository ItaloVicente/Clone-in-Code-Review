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
				IEclipseContext applicationContext = application.getContext();
				if (applicationContext != null) {
					IEclipseContext activeLeaf = applicationContext.getActiveLeaf();
					performUpdateVisibility(activeLeaf);
				}
			};
			current.asyncExec(r);
		} else {
		}
	}

	private void performUpdateVisibility(IEclipseContext context) {
