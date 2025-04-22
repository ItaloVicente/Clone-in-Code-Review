	private Repository getCurrentRepo() {
		return currentRepo;
	}

	private void setCurrentRepo(Repository currentRepo) {
		Repository old = getCurrentRepo();
		this.currentRepo = currentRepo;
		if (!(old == null ? currentRepo == null : old.equals(currentRepo))) {
			this.firePropertyChange(this, P_REPOSITORY, old,
					currentRepo);
		}
	}

