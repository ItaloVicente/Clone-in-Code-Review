	@Test
	public void testDecorationIgnoredFile() throws Exception {

		write(new File(project.getLocation().toFile(), "Test.dat"), "Something");
		write(new File(project.getLocation().toFile(), TEST_FILE2), "Something");
		write(new File(project.getLocation().toFile(), "Test"), "Something");
		write(new File(project.getLocation().toFile(), ".gitignore"), "Test"); // Test is prefix of TestFile
		project.refreshLocal(IResource.DEPTH_INFINITE, null);
		IResource file = project.findMember("Test.dat");
		IResource gitignore = project.findMember(".gitignore");
		IResource test = project.findMember("Test");
		IDecoratableResource[] expectedDRs = new IDecoratableResource[] {
				new TestDecoratableResource(project, true, false, true, false,
						Staged.NOT_STAGED),
				new TestDecoratableResource(gitignore, false, false, false, false,
						Staged.NOT_STAGED),
				new TestDecoratableResource(file, false, false, false, false,
						Staged.NOT_STAGED),
				new TestDecoratableResource(test, false, true, false, false,
						Staged.NOT_STAGED)
		};
		waitForIndexDiffUpdate(true);
		IndexDiffData indexDiffData = indexDiffCacheEntry.getIndexDiff();
		IDecoratableResource[] actualDRs = {
				new DecoratableResourceAdapter(indexDiffData, project),
				new DecoratableResourceAdapter(indexDiffData, gitignore),
				new DecoratableResourceAdapter(indexDiffData, file),
				new DecoratableResourceAdapter(indexDiffData, test)
		};

		assertArrayEquals(expectedDRs, actualDRs);
	}

	@Test
	public void testDecorationFileInIgnoredFolder() throws Exception {

		FileUtils.mkdir(new File(project.getLocation().toFile(),"dir"));
		write(new File(project.getLocation().toFile(), "dir/file"), "Something");
		write(new File(project.getLocation().toFile(), ".gitignore"), "dir");
		project.refreshLocal(IResource.DEPTH_INFINITE, null);
		IResource dir = project.findMember("dir");
		IResource file = project.findMember("dir/file");
		IResource gitignore = project.findMember(".gitignore");
		IDecoratableResource[] expectedDRs = new IDecoratableResource[] {
				new TestDecoratableResource(project, true, false, true, false,
						Staged.NOT_STAGED),
				new TestDecoratableResource(gitignore, false, false, false, false,
						Staged.NOT_STAGED),
				new TestDecoratableResource(file, false, true, false, false,
						Staged.NOT_STAGED),
				new TestDecoratableResource(dir, false, true, false, false,
						Staged.NOT_STAGED)
		};
		waitForIndexDiffUpdate(true);
		IndexDiffData indexDiffData = indexDiffCacheEntry.getIndexDiff();
		IDecoratableResource[] actualDRs = {
				new DecoratableResourceAdapter(indexDiffData, project),
				new DecoratableResourceAdapter(indexDiffData, gitignore),
				new DecoratableResourceAdapter(indexDiffData, file),
				new DecoratableResourceAdapter(indexDiffData, dir)
		};

		assertArrayEquals(expectedDRs, actualDRs);
	}

