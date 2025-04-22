			if (model.isDirty()) {
				this.refreshModelJob = refreshSeverities();
				this.refreshModelJob.thenAccept(model -> {
					for (StructuredViewer viewer : viewersToUpdate) {
						viewer.update(model.getResourcesWithModifiedSeverity().toArray(), new String[] {});
					}
				});
