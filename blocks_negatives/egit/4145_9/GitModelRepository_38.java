	private GitModelWorkingTree getLocaWorkingTreeChanges() {
		try {
			return new GitModelWorkingTree(this, pathFilter);
		} catch (IOException e) {
			Activator.logError(e.getMessage(), e);
		}

		return null;
