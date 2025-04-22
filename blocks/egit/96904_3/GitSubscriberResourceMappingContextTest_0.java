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

	@Test
	public void hasNestedDeletion() throws Exception {
		File file1 = testRepo.createFile(iProject,
				"sub/subfolder/file1.sample");
		testRepo.appendContentAndCommit(iProject, file1, "initial content",
				"first commit in master");

		IFile iFile1 = testRepo.getIFile(iProject, file1);

		assertTrue(iFile1.exists());

		IContainer subfolder = iFile1.getParent();

		assertTrue(subfolder instanceof IFolder);
		assertEquals("subfolder", subfolder.getName());

		IContainer sub = subfolder.getParent();

		assertTrue(sub instanceof IFolder);
		assertEquals("sub", sub.getName());

		testRepo.createAndCheckoutBranch(MASTER, BRANCH);
		iFile1.delete(true, new NullProgressMonitor());
		subfolder.delete(true, new NullProgressMonitor());
		sub.delete(true, new NullProgressMonitor());
		try (Git git = new Git(testRepo.getRepository())) {
			git.add()
					.addFilepattern(
							iProject.getName() + "/sub/subfolder/file1.sample")
					.setUpdate(true).call();
		}
		testRepo.commit("Deleted sub/subfolder/file1.sample");

		assertFalse(iFile1.exists());
		assertFalse(subfolder.exists());
		assertFalse(sub.exists());

		RemoteResourceMappingContext context = prepareContext(BRANCH, MASTER);
		boolean hasFile1 = false;
		for (IResource member : context.fetchMembers(iProject,
				new NullProgressMonitor())) {
			if (sub.getName().equals(member.getName())) {
				for (IResource child : context.fetchMembers(sub,
						new NullProgressMonitor())) {
					if (subfolder.getName().equals(child.getName())) {
						for (IResource grandchild : context.fetchMembers(
								subfolder, new NullProgressMonitor())) {
							if (iFile1.getName().equals(grandchild.getName())) {
								hasFile1 = true;
								break;
							}
						}
						break;
					}
				}
				break;
			}
		}
		assertTrue(hasFile1);
		assertFalse(context.hasRemoteChange(iFile1, new NullProgressMonitor()));
		assertTrue(context.hasLocalChange(iFile1, new NullProgressMonitor()));
		assertTrue(
				context.hasLocalChange(subfolder, new NullProgressMonitor()));
		assertTrue(context.hasLocalChange(sub, new NullProgressMonitor()));
	}

