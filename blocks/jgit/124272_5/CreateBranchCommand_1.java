	public static boolean isInvalidBranchName(String branchName) {
		if (HEAD.equals(branchName)) {
			return true;
		}
		if ((R_HEADS + HEAD).equals(branchName)) {
			return true;
		}
	}

