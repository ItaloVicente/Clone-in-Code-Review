	private final SaveablesTracker saveablesTracker;

	/**
	 * Propagates state changes of the saveable part tracked by this properties
	 * view, to properly update the dirty status. See bug 495567 comment 18.
	 */
	class SaveablesTracker implements ISaveablesLifecycleListener {

		@Override
		public void handleLifecycleEvent(SaveablesLifecycleEvent event) {
			if (currentPart == null || event.getEventType() != SaveablesLifecycleEvent.DIRTY_CHANGED) {
				return;
			}
			Saveable[] saveables = event.getSaveables();
			if (saveables == null) {
				return;
			}
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

