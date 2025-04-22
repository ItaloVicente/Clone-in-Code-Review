	private Repository getCurrentRepo() {
		return currentRepo;
	}

	private void setCurrentRepo(Repository newRepo) {
		Repository old = getCurrentRepo();
		this.currentRepo = newRepo;

		if(old == null) {
			if (newRepo == null)
				return;
		} else {
			if (old.equals(newRepo))
				return;
		}

		this.firePropertyChange(this, P_REPOSITORY, old,
					newRepo);
	}

