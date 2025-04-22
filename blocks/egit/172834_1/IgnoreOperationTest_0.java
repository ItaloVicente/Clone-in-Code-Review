	@Test
	public void testIgnoreLinkToFolder() throws Exception {
		assumeTrue(FS.DETECTED.supportsSymlinks());
		IFolder binFolder = project.getProject().getFolder("bin");
		IPath binPath = binFolder.getLocation();
		IPath projectPath = project.getProject().getLocation();
		FS.DETECTED.createSymLink(projectPath.append("sym").toFile(),
				binPath.toOSString());
		project.getProject().refreshLocal(IResource.DEPTH_INFINITE, null);
		IFolder eclipseFolder = project.getProject().getFolder("sym");
		assertEquals(IResource.FOLDER, eclipseFolder.getType());
		assertTrue(eclipseFolder.exists());
		IgnoreOperation operation = executeIgnore(projectPath.append("sym"));

		String content = project.getFileContent(Constants.GITIGNORE_FILENAME);
		assertFalse(operation.isGitignoreOutsideWSChanged());

		operation = executeIgnore(projectPath.append("sym"));

		String content2 = project.getFileContent(Constants.GITIGNORE_FILENAME);
		assertTrue(".gitignore should have an entry for the symlink",
				content.startsWith("/sym"));
		assertTrue("Symlink should be ignored",
				RepositoryUtil.isIgnored(projectPath.append("sym")));
		assertEquals(".gitignore should not have been modified a second time",
				content, content2);
		assertFalse(operation.isGitignoreOutsideWSChanged());
	}

