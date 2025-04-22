	private boolean isInvalidBranchName(String n) {
		if (HEAD.equals(n)) {
			return true;
		}
		if ((R_HEADS + HEAD).equals(n)) {
			return true;
		}
	}

