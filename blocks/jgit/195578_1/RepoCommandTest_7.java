	@Test
	public void testInvalidPath() throws Exception {
		Repository remoteDb = createBareRepository();
		Repository tempDb = createWorkRepository();

		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
				.append("<manifest>")
				.append("<remote name=\"remote1\" fetch=\".\" />")
				.append("<default revision=\"master\" remote=\"remote1\" />")
				.append("<project path=\".\" ").append("name=\"")
				.append(defaultUri).append("\" />").append("</manifest>");
		JGitTestUtil.writeTrashFile(tempDb
				xmlContent.toString());

		RepoCommand command = new RepoCommand(remoteDb);
		command.setPath(
				tempDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
				.setURI(rootUri).setRecommendShallow(true);
		assertThrows(ManifestErrorException.class
	}

