		return null;
	}

	private void stopAndDisconnectCurrentWork() {
		if (refreshProposalsJob != null) {
			refreshProposalsJob.cancel();
		}
	}

	private void proposalsUpdated() {
		tree.setInput(potentialProjects);
		tree.setCheckedElements(this.notAlreadyExistingProjects.toArray());
