		if (branchesToClone == null || branchesToClone.isEmpty()) {
			fetchType = FETCH_TYPE.ALL_BRANCHES;
		} else {
			this.fetchType = FETCH_TYPE.MULTIPLE_BRANCHES;
			this.branchesToClone = branchesToClone;
		}
