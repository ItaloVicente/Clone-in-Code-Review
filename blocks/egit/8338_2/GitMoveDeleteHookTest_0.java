	@Test
	public void testMoveFileWithConflictsShouldBeCanceled() throws Exception {
		TestProject project = initRepoInsideProjectInsideWorkspace();
		String filePath = "file.txt";
		IFile file = testUtils.addFileToProject(project.getProject(), filePath, "some text");

		Repository repo = testRepository.getRepository();
		DirCache index = repo.lockDirCache();
		DirCacheBuilder builder = index.builder();
		addUnmergedEntry(filePath, builder);
		builder.commit();

		try {
			file.move(new Path("destination.txt"), false, null);
			fail("Expected move of file with conflicts to fail.");
		} catch (CoreException e) {
			IStatus status = e.getStatus();
			assertNotNull(status);
			assertEquals(IStatus.WARNING, status.getSeverity());
		}

		assertTrue("File should still exist at old location", file.exists());
		DirCache indexAfter = repo.readDirCache();
		DirCacheEntry entry = indexAfter.getEntry(filePath);
		assertEquals("Expected entry to still be in non-zero (conflict) stage",
				DirCacheEntry.STAGE_1, entry.getStage());
	}

	@Test
	public void testMoveFolderWithFileWithConflictsShouldBeCanceled() throws Exception {
		TestProject project = initRepoInsideProjectInsideWorkspace();
		String filePath = "folder/file.txt";
		IFile file = testUtils.addFileToProject(project.getProject(), filePath, "some text");

		Repository repo = testRepository.getRepository();
		DirCache index = repo.lockDirCache();
		DirCacheBuilder builder = index.builder();
		addUnmergedEntry(filePath, builder);
		builder.commit();

		try {
			project.getProject()
					.getFolder("folder")
					.move(project.getProject().getFolder("newfolder")
							.getFullPath(), false, null);
			fail("Expected move of folder with file with conflicts to fail.");
		} catch (CoreException e) {
			IStatus status = e.getStatus();
			assertNotNull(status);
			assertEquals(IStatus.WARNING, status.getSeverity());
		}

		assertTrue("File should still exist at old location", file.exists());
		DirCache indexAfter = repo.readDirCache();
		DirCacheEntry entry = indexAfter.getEntry(filePath);
		assertEquals("Expected entry to still be in non-zero (conflict) stage",
				DirCacheEntry.STAGE_1, entry.getStage());
	}

	private static void addUnmergedEntry(String filePath, DirCacheBuilder builder) {
		DirCacheEntry stage1 = new DirCacheEntry(filePath, DirCacheEntry.STAGE_1);
		DirCacheEntry stage2 = new DirCacheEntry(filePath, DirCacheEntry.STAGE_2);
		DirCacheEntry stage3 = new DirCacheEntry(filePath, DirCacheEntry.STAGE_3);
		stage1.setFileMode(FileMode.REGULAR_FILE);
		stage2.setFileMode(FileMode.REGULAR_FILE);
		stage3.setFileMode(FileMode.REGULAR_FILE);
		builder.add(stage1);
		builder.add(stage2);
		builder.add(stage3);
	}

