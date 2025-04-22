				new TestDecoratableResource(project, true, false, true, false,
						Staged.NOT_STAGED),
				new TestDecoratableResource(file, false, false, false, false,
						Staged.NOT_STAGED) };
		waitForIndexDiffUpdate(true);
		IndexDiffData indexDiffData = indexDiffCacheEntry.getIndexDiff();
		IDecoratableResource[] actualDRs = {
				new DecoratableResourceAdapter(indexDiffData, project),
				new DecoratableResourceAdapter(indexDiffData, file) };

		for (int i = 0; i < expectedDRs.length; i++)
			assertTrue(expectedDRs[i].equals(actualDRs[i]));
	}

	@Test
	public void testDecorationNewFileInSubfolder() throws Exception {
		IFolder folder = project.getFolder(TEST_FOLDER);
		folder.create(true, true, null);
		IFolder subFolder = folder.getFolder(SUB_FOLDER);
		subFolder.create(true, true, null);
		write(new File(subFolder.getLocation().toFile().getAbsolutePath(), TEST_FILE), "Something");
		project.refreshLocal(IResource.DEPTH_INFINITE, null);
		IResource file = subFolder.findMember(TEST_FILE);

		IDecoratableResource[] expectedDRs = new IDecoratableResource[] {
				new TestDecoratableResource(project, true, false, true, false,
						Staged.NOT_STAGED),
				new TestDecoratableResource(folder, false, false, true, false,
