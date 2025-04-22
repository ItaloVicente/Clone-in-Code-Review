	@Test
	public void absoluteRemoteURLAbsoluteTargetURL() throws Exception {
		Repository child =
			Git.cloneRepository().setURI(groupADb.getDirectory().toURI().toString())
				.setDirectory(createUniqueTestGitDir(true))
				.setBare(true).call().getRepository();
		Repository dest = Git.cloneRepository()
			.setURI(db.getDirectory().toURI().toString()).setDirectory(createUniqueTestGitDir(true))
			.setBare(true).call().getRepository();
		boolean fetchSlash = false;
		boolean baseSlash = false;
		do {
			do {
				String fetchUrl = fetchSlash ? abs + "/" : abs;
				String baseUrl = baseSlash ? abs + "/" : abs;

				StringBuilder xmlContent = new StringBuilder();
				xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
					.append("<manifest>")
					.append("<remote name=\"origin\" fetch=\"" + fetchUrl + "\" />")
					.append("<default revision=\"master\" remote=\"origin\" />")
					.append("<project path=\"src\" name=\"chromium/src\" />")
					.append("</manifest>");
				RepoCommand cmd = new RepoCommand(dest);

				IndexedRepos repos = new IndexedRepos();
				repos.put(repoUrl

				RevCommit commit = cmd
					.setInputStream(new ByteArrayInputStream(xmlContent.toString().getBytes(UTF_8)))
					.setRemoteReader(repos)
					.setURI(baseUrl)
					.setTargetURI(abs + "/superproject")
					.setRecordRemoteBranch(true)
					.setRecordSubmoduleLabels(true)
					.call();

				String idStr = commit.getId().name() + ":" + ".gitmodules";
				ObjectId modId = dest.resolve(idStr);

				try (ObjectReader reader = dest.newObjectReader()) {
					byte[] bytes = reader.open(modId).getCachedBytes(Integer.MAX_VALUE);
					Config base = new Config();
					BlobBasedConfig cfg = new BlobBasedConfig(base
					String subUrl = cfg.getString("submodule"
					assertEquals("../chromium/src"
				}
				fetchSlash = !fetchSlash;
			} while (fetchSlash);
			baseSlash = !baseSlash;
		} while (baseSlash);
		child.close();
		dest.close();
	}

