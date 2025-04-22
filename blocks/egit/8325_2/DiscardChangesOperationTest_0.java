
		File workdir2 = testUtils.createTempDir("Project2");
		project2 = testUtils.createProjectInLocalFileSystem(workdir2.getParentFile(), "Project2");
		testUtils.addFileToProject(project2, "file.txt", "initial");
		repository2 = new TestRepository(new File(workdir2, Constants.DOT_GIT));
		repository2.connect(project2);

		trackAllFiles(project2, repository2);
		repository2.commit("Initial commit");
