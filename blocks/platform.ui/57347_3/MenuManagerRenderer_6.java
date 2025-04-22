					display.timerExec(100, () -> {
						Collection<IContributionManager> toUpdate = new LinkedHashSet<>();
						synchronized (mgrToUpdate) {
							toUpdate.addAll(mgrToUpdate);
							mgrToUpdate.clear();
						}
						for (IContributionManager mgr1 : toUpdate) {
							mgr1.update(false);
