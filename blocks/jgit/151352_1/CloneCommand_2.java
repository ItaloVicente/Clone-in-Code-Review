		this.fetchType = cloneAllBranches ? FETCH_TYPE.ALL_BRANCHES
				: this.fetchType;
		return this;
	}

	public CloneCommand setMirror(boolean mirror) {
		if (mirror) {
			this.fetchType = FETCH_TYPE.MIRROR;
			setBare(true);
		}
