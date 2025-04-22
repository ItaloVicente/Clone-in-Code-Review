		try (
				Repository remoteDb = createBareRepository();
				Repository tempDb = createWorkRepository()) {
			StringBuilder xmlContent = new StringBuilder();
			xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
					.append("<manifest>")
					.append("<remote name=\"remote1\" fetch=\".\" />")
					.append("<default revision=\"").append(BRANCH)
					.append("\" remote=\"remote1\" />")
					.append("<project path=\"foo\" name=\"").append(defaultUri)
					.append("\" />").append("</manifest>");
			JGitTestUtil.writeTrashFile(tempDb, "manifest.xml",
					xmlContent.toString());
			RepoCommand command = new RepoCommand(remoteDb);
			command.setPath(
					tempDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
					.setURI(rootUri).call();
			File directory = createTempDirectory("testRevisionBare");
			Repository localDb = Git.cloneRepository().setDirectory(directory)
					.setURI(remoteDb.getDirectory().toURI().toString()).call()
					.getRepository();
			String gitlink = localDb.resolve(Constants.HEAD + ":foo").name();
			localDb.close();
			assertEquals("The gitlink is same as remote head",
					oldCommitId.name(), gitlink);
		}
