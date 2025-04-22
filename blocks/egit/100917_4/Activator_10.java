		private void mayTriggerRefresh(WorkingTreeModifiedEvent event) {
			if (event.isEmpty()) {
				return;
			}
			Repository repo = event.getRepository();
			if (repo == null || repo.isBare()) {
				return; // Should never occur
			}
			File gitDir = repo.getDirectory();
