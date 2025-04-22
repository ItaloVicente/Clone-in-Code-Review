	@Test
	public void testCopyFileBare_executable() throws Exception {
		try (Git git = new Git(defaultDb)) {
			git.checkout().setName(BRANCH).call();
			File f = JGitTestUtil.writeTrashFile(defaultDb
					"content of the executable file");
			f.setExecutable(true);
			git.add().addFilepattern("hello.sh").call();
			git.commit().setMessage("Add binary file").call();
		}

		Repository remoteDb = createBareRepository();
		Repository tempDb = createWorkRepository();

		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
				.append("<manifest>")
				.append("<remote name=\"remote1\" fetch=\".\" />")
				.append("<default revision=\"master\" remote=\"remote1\" />")
				.append("<project path=\"foo\" name=\"").append(defaultUri)
				.append("\" revision=\"").append(BRANCH)
				.append("\" >")
				.append("<copyfile src=\"hello.txt\" dest=\"Hello\" />")
				.append("<copyfile src=\"hello.txt\" dest=\"foo/Hello\" />")
				.append("<copyfile src=\"hello.sh\" dest=\"copy-hello.sh\" />")
				.append("</project>").append("</manifest>");
		JGitTestUtil.writeTrashFile(tempDb
				xmlContent.toString());
		RepoCommand command = new RepoCommand(remoteDb);
		command.setPath(
				tempDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
				.setURI(rootUri).call();
		File directory = createTempDirectory("testCopyFileBare");
		try (Repository localDb = Git.cloneRepository().setDirectory(directory)
				.setURI(remoteDb.getDirectory().toURI().toString()).call()
				.getRepository()) {
			File hello = new File(localDb.getWorkTree()
			assertTrue("The Hello file should exist"
			File foohello = new File(localDb.getWorkTree()
			assertFalse("The foo/Hello file should be skipped"
					foohello.exists());
			try (BufferedReader reader = Files.newBufferedReader(hello.toPath()
					UTF_8)) {
				String content = reader.readLine();
				assertEquals("The Hello file should have expected content"
						"branch world"
			}

			File helloSh = new File(localDb.getWorkTree()
			assertTrue("Destination file should exist"
			assertContents(helloSh.toPath()
			assertTrue("Destination file should be executable"
					helloSh.canExecute());

		}
	}

