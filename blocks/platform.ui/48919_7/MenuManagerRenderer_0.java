
	private void scheduleManagerUpdate(IContributionManager mgr) {
		if (!ECLIPSE_WORKAROUND_BUG467000) {
			mgr.update(false);
			return;
		}
		synchronized (mgrToUpdate) {
			if (this.mgrToUpdate.isEmpty()) {
				Display display = context.get(Display.class);
				if (display != null && !display.isDisposed()) {
					display.timerExec(100, new Runnable() {

						@Override
						public void run() {
							Collection<IContributionManager> toUpdate = new LinkedHashSet<>();
							synchronized (mgrToUpdate) {
								toUpdate.addAll(mgrToUpdate);
								mgrToUpdate.clear();
							}
							for (IContributionManager mgr : toUpdate) {
								mgr.update(false);
							}
					}
					});
				}
				this.mgrToUpdate.add(mgr);
			}
		}
	}
