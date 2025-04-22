	@Test
	public void testRepoManifestCopyFile_executable() throws Exception {
		try (Git git = new Git(defaultDb)) {
			git.checkout().setName("master").call();
			File f = JGitTestUtil.writeTrashFile(defaultDb
					"content of the executable file");
			f.setExecutable(true);
			git.add().addFilepattern("hello.sh").call();
			git.commit().setMessage("Add binary file").call();
		}

		Repository localDb = createWorkRepository();
		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
				.append("<manifest>")
				.append("<remote name=\"remote1\" fetch=\".\" />")
				.append("<default revision=\"master\" remote=\"remote1\" />")
				.append("<project path=\"foo\" name=\"").append(defaultUri)
				.append("\">")
				.append("<copyfile src=\"hello.sh\" dest=\"copy-hello.sh\" />")
				.append("</project>").append("</manifest>");
		JGitTestUtil.writeTrashFile(localDb
				xmlContent.toString());
		RepoCommand command = new RepoCommand(localDb);
		command.setPath(
				localDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
				.setURI(rootUri).call();

		File hello = new File(localDb.getWorkTree()
		assertTrue("The original file should exist"
		assertTrue("The original file must be executable"
		try (BufferedReader reader = Files.newBufferedReader(hello.toPath()
				UTF_8)) {
			String content = reader.readLine();
			assertEquals("The original file should have expected content"
					"content of the executable file"
		}

		hello = new File(localDb.getWorkTree()
		assertTrue("The destination file should exist"
		assertTrue("The destination file must be executable"
				hello.canExecute());
		try (BufferedReader reader = Files.newBufferedReader(hello.toPath()
				UTF_8)) {
			String content = reader.readLine();
			assertEquals("The destination file should have expected content"
					"content of the executable file"
		}
	}

