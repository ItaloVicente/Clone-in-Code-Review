	class IndexedRepos implements RepoCommand.RemoteReader {
		Map<String
		IndexedRepos() {
			uriRepoMap = new HashMap<>();
		}

		void put(String u
			uriRepoMap.put(u
		}

		@Override
		public ObjectId sha1(String uri
			if (!uriRepoMap.containsKey(uri)) {
				return null;
			}

			Repository r = uriRepoMap.get(uri);
			try {
				Ref ref = r.findRef(refname);
				if (ref == null) return null;

				ref = r.peel(ref);
				ObjectId id = ref.getObjectId();
				return id;
			} catch (IOException e) {
				throw new InvalidRemoteException(""
			}
		}

		public byte[] readFile(String uri
			throws GitAPIException
			Repository repo = uriRepoMap.get(uri);

			String idStr = refName + ":" + path;
			ObjectId id = repo.resolve(idStr);
			if (id == null) {
				throw new RefNotFoundException(
					String.format("repo %s does not have %s"
			}
			try (ObjectReader reader = repo.newObjectReader()) {
				return reader.open(id).getCachedBytes(Integer.MAX_VALUE);
			}
		}
	}

	@Test
	public void androidSetup() throws Exception {
		Repository child =
  			Git.cloneRepository().setURI(groupADb.getDirectory().toURI().toString())
				.setDirectory(createUniqueTestGitDir(true))
				.setBare(true).call().getRepository();

		Repository dest = Git.cloneRepository()
			.setURI(db.getDirectory().toURI().toString()).setDirectory(createUniqueTestGitDir(true))
			.setBare(true).call().getRepository();

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
			.setInputStream(new ByteArrayInputStream(xmlContent.toString().getBytes(StandardCharsets.UTF_8)))
			.setRemoteReader(repos)
			.setURI("platform/")
			.setTargetURI("platform/superproject")
			.setRecordRemoteBranch(true)
			.setRecordSubmoduleLabels(true)
			.call();

		String idStr = commit.getId().name() + ":" + ".gitmodules";
		ObjectId modId = dest.resolve(idStr);

		try (ObjectReader reader = dest.newObjectReader()) {
			byte[] bytes = reader.open(modId).getCachedBytes(Integer.MAX_VALUE);
			String content = new String(bytes);
			Config base = new Config();
			BlobBasedConfig cfg = new BlobBasedConfig(base
			String subUrl = cfg.getString("submodule"
			assertEquals(subUrl
		}

		child.close();
		dest.close();
	}

	@Test
	public void gerritSetup() throws Exception {
		Repository child =
			Git.cloneRepository().setURI(groupADb.getDirectory().toURI().toString())
				.setDirectory(createUniqueTestGitDir(true))
				.setBare(true).call().getRepository();

		Repository dest = Git.cloneRepository()
			.setURI(db.getDirectory().toURI().toString()).setDirectory(createUniqueTestGitDir(true))
			.setBare(true).call().getRepository();

		assertTrue(dest.isBare());
		assertTrue(child.isBare());

		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
			.append("<manifest>")
			.append("<remote name=\"remote1\" fetch=\".\" />")
			.append("<default revision=\"master\" remote=\"remote1\" />")
			.append("<project path=\"plugins/cookbook\" name=\"plugins/cookbook\" />")
			.append("</manifest>");
		RepoCommand cmd = new RepoCommand(dest);

		IndexedRepos repos = new IndexedRepos();
		repos.put("plugins/cookbook"

		RevCommit commit = cmd
			.setInputStream(new ByteArrayInputStream(xmlContent.toString().getBytes(StandardCharsets.UTF_8)))
			.setRemoteReader(repos)
			.setURI("")
			.setTargetURI("gerrit")
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
			assertEquals(subUrl
		}

		child.close();
		dest.close();
	}

	@Test
	public void absoluteRemoteURL() throws Exception {
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
					.setInputStream(new ByteArrayInputStream(xmlContent.toString().getBytes(StandardCharsets.UTF_8)))
					.setRemoteReader(repos)
					.setURI(baseUrl)
					.setTargetURI("gerrit")
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
				}
				fetchSlash = !fetchSlash;
			} while (fetchSlash);
			baseSlash = !baseSlash;
		} while (baseSlash);
		child.close();
		dest.close();
	}

