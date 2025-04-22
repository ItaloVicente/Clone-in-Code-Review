
		assertEquals(2, countCommits(repository));
		assertTrue(new File(repository.getDirectory() + "/../" + fileName).exists());
	}

	@Test
	public void testFeatureFinishSquash() throws Exception {
		String fileName = "theFirstFile.txt";
		String fileName2 = "theSecondFile.txt";

		Repository repository = testRepository.getRepository();
		GitFlowRepository gfRepo = init("testFeatureFinishSquash\n\nfirst commit\n");

		new FeatureStartOperation(gfRepo, MY_FEATURE).execute(null);

		String branchName = gfRepo.getConfig().getFeatureBranchName(MY_FEATURE);
		addFileAndCommit(fileName, "adding first file on feature branch");
		addFileAndCommit(fileName2, "adding second file on feature branch");
		FeatureFinishOperation featureFinishOperation = new FeatureFinishOperation(gfRepo);
		featureFinishOperation.setSquash(true);
		featureFinishOperation.execute(null);
		assertEquals(gfRepo.getConfig().getDevelopFull(),
				repository.getFullBranch());
		assertEquals(null, findBranch(repository, branchName));

		assertEquals(1, countCommits(repository));
		assertTrue(new File(repository.getDirectory() + "/../" + fileName).exists());
		assertTrue(new File(repository.getDirectory() + "/../" + fileName2).exists());

		Status status = Git.wrap(repository).status().call();
		assertTrue(status.hasUncommittedChanges());
	}

	private int countCommits(Repository repository) throws GitAPIException,
			NoHeadException {
		int count = 0;
		Iterable<RevCommit> commits = Git.wrap(repository).log().call();
		Iterator<RevCommit> iterator = commits.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			count++;
		}
		return count;
