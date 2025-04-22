
	@Test
	public void testRecordSubmoduleLabels() throws Exception {
		try (
				Repository remoteDb = createBareRepository();
				Repository tempDb = createWorkRepository()) {
			StringBuilder xmlContent = new StringBuilder();
			xmlContent
				.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
				.append("<manifest>")
				.append("<remote name=\"remote1\" fetch=\".\" />")
				.append("<default revision=\"master\" remote=\"remote1\" />")
				.append("<project path=\"test\" ")
					.append("revision=\"master\" ")
					.append("name=\"").append(notDefaultUri).append("\" ")
					.append("groups=\"a1
				.append("</manifest>");
			JGitTestUtil.writeTrashFile(tempDb
				xmlContent.toString());

			RepoCommand command = new RepoCommand(remoteDb);
			command.setPath(tempDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
				.setURI(rootUri)
				.setRecordSubmoduleLabels(true)
				.call();
			File directory = createTempDirectory("testBareRepo");
			try (Repository localDb = Git.cloneRepository()
					.setDirectory(directory)
					.setURI(remoteDb.getDirectory().toURI().toString()).call()
					.getRepository();) {
				File gitmodules = new File(localDb.getWorkTree()
						".gitmodules");
				assertTrue("The .gitmodules file should exist"
						gitmodules.exists());
				FileBasedConfig c = new FileBasedConfig(gitmodules
						FS.DETECTED);
				c.load();

				Set<String> expect = new HashSet<String>();
				expect.add("a1");
				expect.add("a2");
				Set<String> actual = new HashSet<String>();
				actual.addAll(Arrays.asList(c.getStringList("submodule"
				assertEquals("Recorded labels should be the same as in the manifest"
			}
		}
	}

