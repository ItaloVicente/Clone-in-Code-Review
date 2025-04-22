	private String getBranchNameWithConsideredDepth() {
		if (Depth.isSet(this.depth)) {
			if (Constants.HEAD.equals(this.branch)) {
				return Constants.MASTER;
			} else {
				return this.branch;
			}
		} else {
		}
	}

