				BRANCH, MASTER);
		grvts.init(new NullProgressMonitor());

		IResourceVariant actualBase = getBaseVariant(grvts, changedFile);
		IResourceVariant actualRemote = getRemoteVariant(grvts, changedFile);

		assertVariantMatchCommit(actualBase, initialCommit);
		assertVariantMatchCommit(actualRemote, commitMaster);
