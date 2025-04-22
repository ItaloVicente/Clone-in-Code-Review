	void setInterestingPaths(Set<String> interestingPaths) {
		if (interestingPaths != null)
			this.markTreeFilter = PathFilterGroup.createFromStrings(interestingPaths);
		else
			this.markTreeFilter = TreeFilter.ALL;
		this.diff = null;
	}

