			if (model.isDirty()) {
				this.refreshModelJob = refreshSeverities();
				this.refreshModelJob.thenAccept(model -> {
					Object[] toUpdate = model.getResourcesWithModifiedSeverity().toArray();
					for (StructuredViewer viewer : viewersToUpdate) {
						viewer.update(toUpdate, new String[] {});
					}
				});
