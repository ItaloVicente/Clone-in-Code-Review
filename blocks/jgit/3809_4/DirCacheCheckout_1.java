		ObjectReader objectReader = repo.getObjectDatabase().newReader();
		try {
			if (headCommitTree != null)
				preScanTwoTrees();
			else
				prescanOneTree();
