	@Test
	public void hasDeletion() throws Exception {
		File file1 = testRepo.createFile(iProject, "file1.sample");
		testRepo.appendContentAndCommit(iProject, file1, "initial content",
				"first commit in master");

		IFile iFile1 = testRepo.getIFile(iProject, file1);

		testRepo.createAndCheckoutBranch(MASTER, BRANCH);
		iFile1.delete(true, new NullProgressMonitor());
		try (Git git = new Git(testRepo.getRepository())) {
			git.add()
					.addFilepattern(iProject.getName() + '/' + iFile1.getName())
					.setUpdate(true)
					.call();
		}
		testRepo.commit("Deleted file1.sample");

		RemoteResourceMappingContext context = prepareContext(BRANCH, MASTER);
		boolean hasFile1 = false;
		for (IResource member : context.fetchMembers(iProject,
				new NullProgressMonitor())) {
			if (iFile1.getName().equals(member.getName())) {
				hasFile1 = true;
				break;
			}
		}
		assertTrue(hasFile1);
		assertFalse(context.hasRemoteChange(iFile1, new NullProgressMonitor()));
		assertTrue(context.hasLocalChange(iFile1, new NullProgressMonitor()));
	}

