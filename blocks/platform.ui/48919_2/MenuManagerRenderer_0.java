
	private void scheduleManagerUpdate(IContributionManager mgr) {
		if (this.mgrsToUpdate.isEmpty()) {
			Display display = context.get(Display.class);
			if (display == null || display.isDisposed()) {
				return;
			}
			display.asyncExec(new Runnable() {
				@Override
				public void run() {
					Collection<IContributionManager> toUpdate = new HashSet<>(mgrsToUpdate);
					mgrsToUpdate.clear();
					for (IContributionManager mgr : toUpdate) {
						mgr.update(false);
					}
				}
			});
		}
		this.mgrsToUpdate.add(mgr);
	}

