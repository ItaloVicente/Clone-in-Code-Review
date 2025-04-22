		Repository dest = Git.cloneRepository()
				.setURI(db.getDirectory().toURI().toString())
				.setDirectory(createUniqueTestGitDir(true)).setBare(true).call()
				.getRepository();

		assertTrue(dest.isBare());

		RevCommit commit = new RepoCommand(dest)
			.setInputStream(new ByteArrayInputStream(
				xmlContent.toString().getBytes(CHARSET)))
			.setRemoteReader(new IndexedRepos())
			.setURI("platform/")
			.setTargetURI("platform/superproject")
			.setRecordRemoteBranch(true)
			.setIgnoreRemoteFailures(true)
			.setRecordSubmoduleLabels(true)
			.call();

		String idStr = commit.getId().name() + ":" + ".gitmodules";
		ObjectId modId = dest.resolve(idStr);

		try (ObjectReader reader = dest.newObjectReader()) {
			byte[] bytes = reader.open(modId).getCachedBytes(Integer.MAX_VALUE);
			Config base = new Config();
			BlobBasedConfig cfg = new BlobBasedConfig(base, bytes);
			String subUrl = cfg.getString("submodule", "base", "url");
			assertEquals(subUrl, "https://host.com/platform/base");
