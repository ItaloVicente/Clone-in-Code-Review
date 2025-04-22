
	public Depth getDepth() {
		return depth;
	}

	public CloneCommand setDepth(Depth depth) {
		this.depth = depth;
		if (Depth.isSet(this.depth)) {
			this.setCloneAllBranches(false);
			this.setCloneSubmodules(false);
		}
		return this;
	}

