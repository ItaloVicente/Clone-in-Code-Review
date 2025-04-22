	private ISaveablesLifecycleListener saveablesLifecycleListener = new ISaveablesLifecycleListener() {
		@Override
		public void handleLifecycleEvent(SaveablesLifecycleEvent event) {
			if (event.getEventType() == SaveablesLifecycleEvent.DIRTY_CHANGED) {
				Saveable[] saveables = event.getSaveables();
				viewer.update(saveables, null);
			} else {
				viewer.refresh();
			}
