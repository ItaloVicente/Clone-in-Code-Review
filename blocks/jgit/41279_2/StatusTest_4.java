	}

	private void mergeTestBranchInMaster(Git git
			throws GitAPIException {
		git.merge().include(aCommit.getId()).call();
	}

	private void detachHead(Git git) throws IOException
