		String fullBranchName;
		try {
			fullBranchName = repository.getRepository().getFullBranch();
		} catch (IOException e) {
			throw new CoreException(error(e.getLocalizedMessage(), e));
		}
		String shortBranchName = Repository.shortenRefName(fullBranchName);
		RemoteConfig cfg = PushOperation.getRemote(shortBranchName,
				repository.getRepository().getConfig());
