	@Test
	public void testLinkTargetsInSameRepositoryNotIgnoredByGitResourceDeltaVisitor()
			throws Exception {
		IFile file = project1.getFile("link2rootFile");
		file.createLink(Path.fromOSString(rootFile.getAbsolutePath()), 0, null);
		IFolder folder = project1.getFolder("link2otherFolder");
		folder.createLink(Path.fromOSString(otherFolder.getAbsolutePath()), 0,
				null);
		project1.refreshLocal(IResource.DEPTH_INFINITE, null);
		project1.getFile("link2rootFile").touch(null);
		project1.getFile("link2otherFolder/otherFile.txt").touch(null);
		resourceDeltaTestHelper1.assertChangedResources(
				new String[] { "/project1/.project", "/project1/link2rootFile",
						"/project1/link2otherFolder/otherFile.txt" });
	}

