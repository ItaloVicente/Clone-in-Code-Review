		try (
				Repository remoteDb1 = createBareRepository();
				Repository remoteDb2 = createBareRepository();
				Repository tempDb = createWorkRepository()) {
			StringBuilder xmlContent = new StringBuilder();
			xmlContent
					.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
					.append("<manifest>")
					.append("<remote name=\"remote1\" fetch=\".\" />")
					.append("<default revision=\"master\" remote=\"remote1\" />")
					.append("<project path=\"foo\" name=\"").append(defaultUri)
					.append("\" />").append("</manifest>");
			JGitTestUtil.writeTrashFile(tempDb, "manifest.xml",
					xmlContent.toString());
			RepoCommand command = new RepoCommand(remoteDb1);
			command
				.setPath(tempDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
				.setURI(rootUri)
				.setTargetBranch("test")
				.call();
			ObjectId branchId = remoteDb1.resolve(
					Constants.R_HEADS + "test^{tree}");
			command = new RepoCommand(remoteDb2);
			command
				.setPath(tempDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
				.setURI(rootUri)
				.call();
			ObjectId defaultId = remoteDb2.resolve(Constants.HEAD + "^{tree}");
			assertEquals(
