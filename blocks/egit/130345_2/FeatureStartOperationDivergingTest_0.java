				gfRepo2, MY_FEATURE, -1);

		expectedException.expect(CoreException.class);
		expectedException.expectMessage(
				"Branches 'develop' and 'origin/develop' have diverged."
						+ "\nAnd branch 'develop' may be fast-forwarded.");
		featureStartOperation.execute(null);
	}

	@Test
	public void testFeatureStart_localDevelopBehindWithFetchOnStart()
			throws Exception {
		GitFlowRepository gfRepo1 = new GitFlowRepository(
				repository1.getRepository());
		GitFlowRepository gfRepo2 = new GitFlowRepository(
				repository2.getRepository());

		init(gfRepo2);
		gfRepo2.getConfig().setFetchOnFeatureStart(true);
		setDevelopRemote(gfRepo1, gfRepo2);

		new PullOperation(Collections.singleton(repository2.getRepository()),
				-1).execute(null);

		repository1.commit("origin/develop is 1 commit ahead");

		FeatureStartOperation featureStartOperation = new FeatureStartOperation(
				gfRepo2, MY_FEATURE, -1);
