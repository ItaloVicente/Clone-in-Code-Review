	@Test
	public void testSyncLocalAndBranch() throws Exception {
		GitResourceVariantTreeSubscriber grvts = createGitResourceVariantTreeSubscriber(
				Constants.HEAD, BRANCH, true);
		grvts.init(new NullProgressMonitor());

		IResourceVariant actualSource = getSourceVariant(grvts, changedFile,
				true);
		IResourceVariant actualBase = getBaseVariant(grvts, changedFile);
		IResourceVariant actualRemote = getRemoteVariant(grvts, changedFile);

		assertVariantIsLocal(actualSource, changedFile);
		assertVariantMatchCommit(actualBase, initialCommit);
		assertVariantMatchCommit(actualRemote, commitBranch);
	}

