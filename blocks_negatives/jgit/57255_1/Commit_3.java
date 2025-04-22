		String branchName;
		if (!head.isSymbolic())
			branchName = CLIText.get().branchDetachedHEAD;
		else {
			branchName = head.getTarget().getName();
			if (branchName.startsWith(Constants.R_HEADS))
				branchName = branchName.substring(Constants.R_HEADS.length());
