		if (QUEUE_ENABLED) {
			IEclipseContext context = getContext(modelItem).getActiveLeaf();
			if (!scheduledUpdate.containsKey(context)) {
				performUpdateItemEnablement();
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
					performUpdateItemEnablement();
				};
				current.asyncExec(r);
			} else {
			}
		} else {
			performUpdateItemEnablement();
		}

	}

	private void performUpdateItemEnablement() {
