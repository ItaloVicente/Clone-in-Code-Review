	private String getBranchNameWithConsideredDepth() {
		if (depth > 0 && depth < Transport.DEPTH_INFINITE) {
			if (Constants.HEAD.equals(this.branch)) {
				return Constants.MASTER;
			} else {
				return this.branch;
			}
		} else {
		}
	}

