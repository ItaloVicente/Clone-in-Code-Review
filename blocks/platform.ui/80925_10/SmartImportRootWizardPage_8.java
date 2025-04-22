		return null;
	}

	private void stopAndDisconnectCurrentWork() {
		if (refreshProposalsJob != null) {
			refreshProposalsJob.cancel();
		}
		if (jobMonitor != null) {
			jobMonitor.setCanceled(true);
			jobMonitor.removeProgresListener(wizardProgressMonitor.get());
			jobMonitor.removeProgresListener(delegateMonitor);
		}
		if (wizardProgressMonitor != null) {
			wizardProgressMonitor.get().setCanceled(true);
		}
	}

	private void proposalsUpdated() {
		tree.setInput(potentialProjects);
		tree.setCheckedElements(this.notAlreadyExistingProjects.toArray());
