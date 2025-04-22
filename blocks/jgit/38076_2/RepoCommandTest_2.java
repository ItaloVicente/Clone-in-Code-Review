		try {
			StringBuilder xmlContent = new StringBuilder();
			xmlContent
					.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
					.append("<manifest>")
					.append("<remote name=\"remote1\" fetch=\".\" />")
					.append("<default revision=\"master\" remote=\"remote1\" />")
					.append("<project path=\"foo\" name=\"").append(defaultUri)
					.append("\" revision=\"").append(BRANCH).append("\" >")
					.append("<copyfile src=\"hello.txt\" dest=\"Hello\" />")
					.append("</project>").append("</manifest>");
			JGitTestUtil.writeTrashFile(tempDb
					xmlContent.toString());
			RepoCommand command = new RepoCommand(remoteDb);
			command.setPath(
					tempDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
					.setURI(rootUri).call();
			File directory = createTempDirectory("testCopyFileBare");
			Repository localDb = Git.cloneRepository().setDirectory(directory)
					.setURI(remoteDb.getDirectory().toURI().toString()).call()
					.getRepository();
			File hello = new File(localDb.getWorkTree()
			localDb.close();
			assertTrue("The Hello file should exist"
			BufferedReader reader = new BufferedReader(new FileReader(hello));
			String content = reader.readLine();
			reader.close();
			assertEquals("The Hello file should have expected content"
					"branch world"
		} finally {
			tempDb.close();
			remoteDb.close();
		}
