		IProject secondProject = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(PROJ2);

		if (secondProject.exists()) {
			secondProject.delete(true, null);
			TestUtil.waitForJobs(100, 5000);
		}

		desc = ResourcesPlugin.getWorkspace().newProjectDescription(PROJ2);
		desc.setLocation(new Path(new File(myRepository.getWorkTree(), PROJ2)
				.getPath()));
		secondProject.create(desc, null);
		secondProject.open(null);
		TestUtil.waitForJobs(50, 5000);

		assertTrue("Project is not accessible: " + secondProject,
				secondProject.isAccessible());

		IFolder secondfolder = secondProject.getFolder(FOLDER);
		secondfolder.create(false, true, null);
		IFile secondtextFile = secondfolder.getFile(FILE1);
		secondtextFile.create(new ByteArrayInputStream("Hello, world"
				.getBytes(firstProject.getDefaultCharset())), false, null);
		IFile secondtextFile2 = secondfolder.getFile(FILE2);
		secondtextFile2.create(new ByteArrayInputStream("Some more content"
				.getBytes(firstProject.getDefaultCharset())), false, null);

		TestUtil.waitForJobs(50, 5000);
