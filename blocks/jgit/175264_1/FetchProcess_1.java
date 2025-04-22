	private boolean isInitialBranchMissing(Map<String
			String initialBranch) {
		if (StringUtils.isEmptyOrNull(initialBranch)) {
			return false;
		}
		if ((initialBranch.equals(Constants.HEAD)
				&& refsMap.containsKey(initialBranch))
				|| refsMap.containsKey(Constants.R_HEADS + initialBranch)
				|| refsMap.containsKey(Constants.R_TAGS + initialBranch)) {
			return false;
		}
		return true;
	}

