		IProject firstProject = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(PROJ1);

		if (firstProject.exists()) {
			firstProject.delete(true, null);
			TestUtil.waitForJobs(100, 5000);
		}
		IProjectDescription desc = ResourcesPlugin.getWorkspace()
				.newProjectDescription(PROJ1);
		desc.setLocation(new Path(new File(myRepository.getWorkTree(), PROJ1)
				.getPath()));
		firstProject.create(desc, null);
		firstProject.open(null);
		TestUtil.waitForJobs(50, 5000);

		assertTrue("Project is not accessible: " + firstProject,
				firstProject.isAccessible());

		IFolder folder = firstProject.getFolder(FOLDER);
		folder.create(false, true, null);
		IFile textFile = folder.getFile(FILE1);
		textFile.create(new ByteArrayInputStream("Hello, world"
				.getBytes(firstProject.getDefaultCharset())), false, null);
		IFile textFile2 = folder.getFile(FILE2);
		textFile2.create(new ByteArrayInputStream("Some more content"
				.getBytes(firstProject.getDefaultCharset())), false, null);
