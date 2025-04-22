	@Override
	protected void invalidateParent() {
		super.invalidateParent();
		IContributionManager parent = getParent();
		if (parent != null && managersToUpdate.add(parent)) {
			if (!queued) {
				queued = true;
				PlatformUI.getWorkbench().getDisplay().asyncExec(updater);
			}
		}
	}

	private static Runnable updater = new Runnable() {
		public void run() {
			IContributionManager[] managers = managersToUpdate
					.toArray(new IContributionManager[managersToUpdate.size()]);
			managersToUpdate.clear();
			queued = false;
			for (IContributionManager manager : managers) {
				manager.update(false);
			}
		}
	};
	private static HashSet<IContributionManager> managersToUpdate = new HashSet<IContributionManager>();
	private static boolean queued = false;
