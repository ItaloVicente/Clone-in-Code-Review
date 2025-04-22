	private void setFetchType() {
		if (mirror) {
			fetchType = FETCH_TYPE.MIRROR;
			setBare(true);
		} else if (cloneAllBranches) {
			fetchType = FETCH_TYPE.ALL_BRANCHES;
		} else if (branchesToClone != null && !branchesToClone.isEmpty()) {
			fetchType = FETCH_TYPE.MULTIPLE_BRANCHES;
		} else {
			fetchType = FETCH_TYPE.ALL_BRANCHES;
		}
	}

