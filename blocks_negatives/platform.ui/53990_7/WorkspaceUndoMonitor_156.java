		return new IResourceChangeListener() {
			@Override
			public void resourceChanged(IResourceChangeEvent event) {
				if (operationInProgress != null) {
					return;
				}
				if (event.getType() == IResourceChangeEvent.POST_CHANGE
						|| event.getType() == IResourceChangeEvent.POST_BUILD) {
					incrementChangeCount();
					if (numChanges >= CHANGE_THRESHHOLD) {
						checkOperationHistory();
					}
