	private static HashSet<IContributionManager> managersToUpdate = new HashSet<>();
	private static Runnable updater = () -> {
		IContributionManager[] managers = managersToUpdate.toArray(new IContributionManager[managersToUpdate.size()]);
		managersToUpdate.clear();
		queued = false;
		for (IContributionManager manager : managers) {
			manager.update(false);
