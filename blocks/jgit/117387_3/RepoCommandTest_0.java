	@Test
	public void runTwiceIsNOP() throws Exception {
		Repository child = Git.cloneRepository()
			.setURI(groupADb.getDirectory().toURI().toString())
			.setDirectory(createUniqueTestGitDir(true)).setBare(true).call()
			.getRepository();

		Repository dest = Git.cloneRepository()
			.setURI(db.getDirectory().toURI().toString())
			.setDirectory(createUniqueTestGitDir(true)).setBare(true).call()
			.getRepository();

		assertTrue(dest.isBare());
		assertTrue(child.isBare());

		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
			.append("<manifest>")
			.append("<remote name=\"remote1\" fetch=\"..\" />")
			.append("<default revision=\"master\" remote=\"remote1\" />")
			.append("<project path=\"base\" name=\"platform/base\" />")
			.append("</manifest>");
		RepoCommand cmd = new RepoCommand(dest);

		IndexedRepos repos = new IndexedRepos();
		repos.put("platform/base"

		RevCommit commit = cmd
			.setInputStream(new ByteArrayInputStream(
				xmlContent.toString().getBytes(UTF_8)))
			.setRemoteReader(repos)
			.setURI("platform/")
			.setTargetURI("platform/superproject")
			.setRecordRemoteBranch(true)
			.setRecordSubmoduleLabels(true)
			.call();

		String firstIdStr = commit.getId().name() + ":" + ".gitmodules";
		commit = new RepoCommand(dest)
			.setInputStream(new ByteArrayInputStream(
				xmlContent.toString().getBytes(UTF_8)))
			.setRemoteReader(repos)
			.setURI("platform/")
			.setTargetURI("platform/superproject")
			.setRecordRemoteBranch(true)
			.setRecordSubmoduleLabels(true)
			.call();
		String idStr = commit.getId().name() + ":" + ".gitmodules";
		assertEquals(firstIdStr
		child.close();
		dest.close();
	}

