
	private void addManagerToUpdate(IContributionManager mgr) {
		if (this.mgrToUpdate.isEmpty()) {
			mgrUpdateExecutor.schedule(new Runnable() {

				@Override
				public void run() {
					Collection<IContributionManager> toUpdate = new HashSet<>(mgrToUpdate);
					mgrToUpdate.clear();
					for (IContributionManager mgr : toUpdate) {
						mgr.update(false);
					}
				}
			}, 100, TimeUnit.MILLISECONDS);
		}
		this.mgrToUpdate.add(mgr);
	}

