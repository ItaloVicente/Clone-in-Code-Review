	public void updateVisibility(IEclipseContext context, ToolBarManager manager) {
		if (QUEUE_ENABLED) {
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
					IEclipseContext parentContext = renderer.getContext(toolbarModel);
					if (parentContext != null && parentContext.getActiveLeaf() != null) {
						performUpdateVisibility(parentContext.getActiveLeaf());
						manager.update(false); // We are not in a RAT anymore
					}
				};
				current.asyncExec(r);
			} else {
			}
		} else {
			performUpdateVisibility(context);
		}
	}

	private void performUpdateVisibility(IEclipseContext context) {
