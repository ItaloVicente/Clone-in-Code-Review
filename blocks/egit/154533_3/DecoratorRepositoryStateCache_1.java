		File gitDir = repository.getDirectory();
		branchLabels.remove(gitDir);
		branchStateLabels.remove(gitDir);
		branchStatesToClear.remove(gitDir);
	}

	public void resetBranchState(Repository repository) {
		branchStatesToClear.put(repository.getDirectory(), Boolean.TRUE);
