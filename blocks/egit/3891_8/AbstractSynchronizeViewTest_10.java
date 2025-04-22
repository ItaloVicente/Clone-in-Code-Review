	private static void createAndCommitDotGitignore() throws CoreException,
			UnsupportedEncodingException {
		IProject secondPoject = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(PROJ2);

		IFile gitignore = secondPoject.getFile(".gitignore");
		gitignore.create(
				new ByteArrayInputStream("/.project\n".getBytes(secondPoject
						.getDefaultCharset())), false, null);

		IFile[] commitables = new IFile[] { gitignore };
		ArrayList<IFile> untracked = new ArrayList<IFile>();
		untracked.addAll(Arrays.asList(commitables));

		CommitOperation op = new CommitOperation(commitables,
				new ArrayList<IFile>(), untracked, TestUtil.TESTAUTHOR,
				TestUtil.TESTCOMMITTER, "Add .gitignore file");
		op.execute(null);
	}

