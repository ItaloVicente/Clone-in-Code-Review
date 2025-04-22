	@Test
	public void testWriteShallowRepo() throws Exception {
		StoredConfig config = repo.getConfig();
		config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_COMMIT_GRAPH
		config.setBoolean(ConfigConstants.CONFIG_GC_SECTION
				ConfigConstants.CONFIG_KEY_WRITE_COMMIT_GRAPH

		RevCommit tip = commitChain(2);
		TestRepository.BranchBuilder bb = tr.branch("refs/heads/master");
		bb.update(tip);
		repo.getObjectDatabase().setShallowCommits(Collections.singleton(tip));

		gc.writeCommitGraph(Collections.singleton(tip));
		File graphFile = new File(repo.getObjectsDirectory()
				Constants.INFO_COMMIT_GRAPH);
		assertFalse(graphFile.exists());
	}

