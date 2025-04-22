	@Test
	public void testDecorationNewFileInOneSubfolder() throws Exception {
		IFolder folder = project.getFolder(TEST_FOLDER);
		folder.create(true, true, null);
		IFolder subFolder = folder.getFolder(SUB_FOLDER);
		subFolder.create(true, true, null);
		IFolder subFolder2 = folder.getFolder(SUB_FOLDER2);
		subFolder2.create(true, true, null);
		write(new File(subFolder2.getLocation().toFile().getAbsolutePath(),
				TEST_FILE), "Something");
		project.refreshLocal(IResource.DEPTH_INFINITE, null);
		IResource file = subFolder2.findMember(TEST_FILE);

		IDecoratableResource[] expectedDRs = new IDecoratableResource[] {
				new TestDecoratableResource(project).tracked().dirty(),
				new TestDecoratableResource(folder).dirty(),
				new TestDecoratableResource(subFolder).ignored(),
				new TestDecoratableResource(subFolder2).dirty(),
				new TestDecoratableResource(file) };

		waitForIndexDiffUpdate(true);
		IndexDiffData indexDiffData = indexDiffCacheEntry.getIndexDiff();
		IDecoratableResource[] actualDRs = {
				new DecoratableResourceAdapter(indexDiffData, project),
				new DecoratableResourceAdapter(indexDiffData, folder),
				new DecoratableResourceAdapter(indexDiffData, subFolder),
				new DecoratableResourceAdapter(indexDiffData, subFolder2),
				new DecoratableResourceAdapter(indexDiffData, file) };

		assertArrayEquals(expectedDRs, actualDRs);
	}

