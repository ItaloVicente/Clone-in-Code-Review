	private final SaveablesTracker saveablesTracker;

	class SaveablesTracker implements ISaveablesLifecycleListener {

		@Override
		public void handleLifecycleEvent(SaveablesLifecycleEvent event) {
			if (currentPart == null || event.getEventType() != SaveablesLifecycleEvent.DIRTY_CHANGED) {
				return;
			}
			Saveable[] saveables = event.getSaveables();
			for (Saveable saveable : saveables) {
				if (new DefaultSaveable(PropertySheet.this).equals(saveable)) {
					return;
				}
			}

			if (event.getSource() instanceof SaveablesList) {
				SaveablesList saveablesList = (SaveablesList) event.getSource();
				for (Saveable saveable : saveables) {
					IWorkbenchPart[] parts = saveablesList.getPartsForSaveable(saveable);
					for (IWorkbenchPart part : parts) {
						if (PropertySheet.this.currentPart == part) {
							firePropertyChange(IWorkbenchPartConstants.PROP_DIRTY);
							return;
						}
					}
				}
			}
		}

	}

