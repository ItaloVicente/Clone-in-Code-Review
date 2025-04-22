	private String getShortBranch() throws IOException {
		String branch = repository.getBranch();
		Ref head = repository.getRef(Constants.HEAD);
		if (head == null)
			return branch;
		if (!head.isSymbolic())
			return branch.substring(0, 6);
		return branch;
	}

