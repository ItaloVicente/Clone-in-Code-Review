	private static Runnable updater = new Runnable() {
		@Override
		public void run() {
			IContributionManager[] managers = managersToUpdate
					.toArray(new IContributionManager[managersToUpdate.size()]);
			managersToUpdate.clear();
			queued = false;
			for (IContributionManager manager : managers) {
				manager.update(false);
			}
