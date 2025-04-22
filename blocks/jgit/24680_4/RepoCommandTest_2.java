
	@Test
	public void testRepoManifestGroups() throws Exception {
		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
			.append("<manifest>")
			.append("<remote name=\"remote1\" fetch=\".\" />")
			.append("<default revision=\"master\" remote=\"remote1\" />")
			.append("<project path=\"foo\" name=\"")
			.append(defaultUri)
			.append("\" groups=\"a
			.append("<project path=\"bar\" name=\"")
			.append(notDefaultUri)
			.append("\" groups=\"notdefault\" />")
			.append("<project path=\"a\" name=\"")
			.append(groupAUri)
			.append("\" groups=\"a\" />")
			.append("<project path=\"b\" name=\"")
			.append(groupBUri)
			.append("\" groups=\"b\" />")
			.append("</manifest>");

		Repository localDb = createWorkRepository();
		JGitTestUtil.writeTrashFile(localDb
		RepoCommand command = new RepoCommand(localDb);
		command.setPath(localDb.getWorkTree() + "/manifest.xml")
			.setURI(rootUri)
			.call();
		File file = new File(localDb.getWorkTree() + "/foo/hello.txt");
		assertTrue(file.exists());
		file = new File(localDb.getWorkTree() + "/bar/world.txt");
		assertFalse(file.exists());
		file = new File(localDb.getWorkTree() + "/a/a.txt");
		assertTrue(file.exists());
		file = new File(localDb.getWorkTree() + "/b/b.txt");
		assertTrue(file.exists());

		localDb = createWorkRepository();
		JGitTestUtil.writeTrashFile(localDb
		command = new RepoCommand(localDb);
		command.setPath(localDb.getWorkTree() + "/manifest.xml")
			.setURI(rootUri)
			.setGroups("all
			.call();
		file = new File(localDb.getWorkTree() + "/foo/hello.txt");
		assertFalse(file.exists());
		file = new File(localDb.getWorkTree() + "/bar/world.txt");
		assertTrue(file.exists());
		file = new File(localDb.getWorkTree() + "/a/a.txt");
		assertFalse(file.exists());
		file = new File(localDb.getWorkTree() + "/b/b.txt");
		assertTrue(file.exists());
	}

	private void resolveRelativeUris() {
		defaultUri = defaultGit.getRepository().getDirectory().toURI().toString();
		notDefaultUri = notDefaultGit.getRepository().getDirectory().toURI().toString();
		groupAUri = groupAGit.getRepository().getDirectory().toURI().toString();
		groupBUri = groupBGit.getRepository().getDirectory().toURI().toString();
		int start = 0;
		while (start <= defaultUri.length()) {
			int newStart = defaultUri.indexOf('/'
			String prefix = defaultUri.substring(0
			if (!notDefaultUri.startsWith(prefix) ||
					!groupAUri.startsWith(prefix) ||
					!groupBUri.startsWith(prefix)) {
				start++;
				rootUri = defaultUri.substring(0
				defaultUri = defaultUri.substring(start);
				notDefaultUri = notDefaultUri.substring(start);
				groupAUri = groupAUri.substring(start);
				groupBUri = groupBUri.substring(start);
				return;
			}
			start = newStart;
		}
	}
