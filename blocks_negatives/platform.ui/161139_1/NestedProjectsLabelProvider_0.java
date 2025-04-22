					Object[] toUpdate = model.getResourcesWithModifiedSeverity().toArray();
					StructuredViewer[] viewers;
					synchronized (viewersToUpdate) {
						viewers = viewersToUpdate.toArray(new StructuredViewer[0]);
					}
					for (StructuredViewer viewer : viewers) {
						viewer.update(toUpdate, new String[] {});
