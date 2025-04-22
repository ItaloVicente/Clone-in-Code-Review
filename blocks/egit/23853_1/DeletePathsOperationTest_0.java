	@Test
	public void testDeleteSymlink() throws Exception {
		IResource resource = testUtils.addFileToProject(project, "file.txt", "Hello world 1");
		File symlinkA = new File(project.getLocation().toOSString(), "link.txt");
		FileUtils.createSymLink(symlinkA, "file.txt");

		IPath linkPath = project.getFile("link.txt").getLocation();
		deletePaths(Arrays.asList(linkPath));

		File file = resource.getFullPath().toFile();
		File link = linkPath.toFile();
		assertTrue("File should not have been deleted", file.exists());
		assertTrue("Link should have been deleted", link.exists());
	}

