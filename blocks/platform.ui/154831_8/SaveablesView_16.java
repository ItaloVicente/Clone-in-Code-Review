	private ISaveablesLifecycleListener saveablesLifecycleListener = event -> {
		if (event.getEventType() == SaveablesLifecycleEvent.DIRTY_CHANGED) {
			Saveable[] saveables = event.getSaveables();
			viewer.update(saveables, null);
		} else {
			viewer.refresh();
