	private Repository getCurrentRepo() {
		return currentRepo;
	}

	private void setCurrentRepo(Repository newRepo) {
		Repository old = getCurrentRepo();
		this.currentRepo = newRepo;

		if (!Objects.equals(old, newRepo)) {
			this.firePropertyChange(this, P_REPOSITORY, old, newRepo);
		}
	}

