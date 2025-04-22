
	@Test
	public void testDecorationDeletedFile() throws Exception {
		File f = new File(project.getLocation().toFile(), TEST_FILE);
		write(f, "Something");
		project.refreshLocal(IResource.DEPTH_INFINITE, null);
		git.add().addFilepattern(".").call();
		git.commit().setMessage("First commit").call();

		FileUtils.delete(f);

		IDecoratableResource expectedDR = new TestDecoratableResource(project,
				true, false, true, false, Staged.NOT_STAGED);

		waitForIndexDiffUpdate(true);
		IndexDiffData indexDiffData = indexDiffCacheEntry.getIndexDiff();
		IDecoratableResource actualDR = new DecoratableResourceAdapter(
				indexDiffData, project);

		assertEquals(expectedDR, actualDR);
	}

