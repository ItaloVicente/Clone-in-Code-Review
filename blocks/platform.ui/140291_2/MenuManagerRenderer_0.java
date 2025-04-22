	private void scheduleManagerUpdate(IContributionManager mgr) {
		boolean workaroundEnabled = Boolean.getBoolean("eclipse.workaround.bug467000"); //$NON-NLS-1$
		if (!workaroundEnabled) {
			mgr.update(false);
			return;
		}
		synchronized (mgrToUpdate) {
			if (this.mgrToUpdate.isEmpty()) {
				Display display = context.get(Display.class);
				if (display != null && !display.isDisposed()) {
					display.timerExec(100, () -> {
						Collection<IContributionManager> toUpdate = new LinkedHashSet<>();
						synchronized (mgrToUpdate) {
							toUpdate.addAll(mgrToUpdate);
							mgrToUpdate.clear();
						}
						for (IContributionManager mgr1 : toUpdate) {
							mgr1.update(false);
						}
					});
				}
				this.mgrToUpdate.add(mgr);
			}
		}
	}
