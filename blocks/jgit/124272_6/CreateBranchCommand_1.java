	public static boolean isInvalidBranchName(String branchName) {
		if (HEAD.equals(branchName)) {
			return true;
		}
	}

