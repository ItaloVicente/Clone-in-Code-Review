	@Test
	public void testDecorationNewFolder() throws Exception {
		IFolder folder = project.getFolder(TEST_FOLDER);
		folder.create(true, true, null);
		IFolder subFolder = folder.getFolder(SUB_FOLDER);
		subFolder.create(true, true, null);

		IDecoratableResource[] expectedDRs = new IDecoratableResource[] {
				new TestDecoratableResource(project, true, false, false, false,
						Staged.NOT_STAGED),
				new TestDecoratableResource(folder, false, false, false, false,
						Staged.NOT_STAGED),
				new TestDecoratableResource(subFolder, false, false, false, false,
						Staged.NOT_STAGED) };

		waitForIndexDiffUpdate(true);
		IDecoratableResource[] actualDRs = DecoratableResourceHelper
				.createDecoratableResources(new IResource[] { project, folder, subFolder });

		for (int i = 0; i < expectedDRs.length; i++)
			assertTrue(expectedDRs[i].equals(actualDRs[i]));
	}

	@Test
	public void testDecorationFolderPrefixOfOtherFolder() throws Exception {
		project.getFolder(TEST_FOLDER).create(true, true, null);
		IFolder testFolder2 = project.getFolder(TEST_FOLDER2);

		IDecoratableResource[] expectedDRs = new IDecoratableResource[] { new TestDecoratableResource(
				testFolder2, true, false, false, false, Staged.NOT_STAGED) };
		waitForIndexDiffUpdate(true);
		IDecoratableResource[] actualDRs = DecoratableResourceHelper
				.createDecoratableResources(new IResource[] { testFolder2 });

		for (int i = 0; i < expectedDRs.length; i++)
			assertTrue(expectedDRs[i].equals(actualDRs[i]));
	}

