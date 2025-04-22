	@Test
	public void recordUnreachableRemotes() throws Exception {
		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
			.append("<manifest>")
			.append("<default revision=\"master\" remote=\"remote1\" />")
			.append("<project path=\"base\" name=\"platform/base\" />")
			.append("</manifest>");

		Repository dest = Git.cloneRepository()
				.setURI(db.getDirectory().toURI().toString())
				.setDirectory(createUniqueTestGitDir(true)).setBare(true).call()
				.getRepository();

		assertTrue(dest.isBare());
		RepoCommand cmd = new RepoCommand(dest);

		RevCommit commit = cmd
			.setInputStream(new ByteArrayInputStream(
				xmlContent.toString().getBytes(UTF_8)))
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
			BlobBasedConfig cfg = new BlobBasedConfig(base
			String subUrl = cfg.getString("submodule"
			assertEquals(subUrl
		}

		dest.close();
	}


