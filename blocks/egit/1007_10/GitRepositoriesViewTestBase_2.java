	protected static File createProjectAndCommitToRepository() throws Exception {

		File gitDir = new File(new File(testDirectory, REPO1),
				Constants.DOT_GIT);
		gitDir.mkdir();
		Repository myRepository = new Repository(gitDir);
		myRepository.create();

		IProject firstProject = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(PROJ1);

		if (firstProject.exists())
			firstProject.delete(true, null);
		IProjectDescription desc = ResourcesPlugin.getWorkspace()
				.newProjectDescription(PROJ1);
		desc.setLocation(new Path(new File(myRepository.getWorkDir(), PROJ1)
				.getPath()));
		firstProject.create(desc, null);
		firstProject.open(null);

		IFolder folder = firstProject.getFolder(FOLDER);
		folder.create(false, true, null);
		IFile textFile = folder.getFile(FILE1);
		textFile.create(new ByteArrayInputStream("Hello, world"
				.getBytes(firstProject.getDefaultCharset())), false, null);
		IFile textFile2 = folder.getFile(FILE2);
		textFile2.create(new ByteArrayInputStream("Some more content"
				.getBytes(firstProject.getDefaultCharset())), false, null);

		new ConnectProviderOperation(firstProject, gitDir).execute(null);

		IProject secondPoject = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(PROJ2);

		if (secondPoject.exists())
			secondPoject.delete(true, null);

		desc = ResourcesPlugin.getWorkspace().newProjectDescription(PROJ2);
		desc.setLocation(new Path(new File(myRepository.getWorkDir(), PROJ2)
				.getPath()));
		secondPoject.create(desc, null);
		secondPoject.open(null);

		IFolder secondfolder = secondPoject.getFolder(FOLDER);
		secondfolder.create(false, true, null);
		IFile secondtextFile = secondfolder.getFile(FILE1);
		secondtextFile.create(new ByteArrayInputStream("Hello, world"
				.getBytes(firstProject.getDefaultCharset())), false, null);
		IFile secondtextFile2 = secondfolder.getFile(FILE2);
		secondtextFile2.create(new ByteArrayInputStream("Some more content"
				.getBytes(firstProject.getDefaultCharset())), false, null);

		new ConnectProviderOperation(secondPoject, gitDir).execute(null);

		IFile[] commitables = new IFile[] { firstProject.getFile(".project"),
				textFile, textFile2, secondtextFile, secondtextFile2 };
		ArrayList<IFile> untracked = new ArrayList<IFile>();
		untracked.addAll(Arrays.asList(commitables));
		CommitOperation op = new CommitOperation(commitables,
				new ArrayList<IFile>(), untracked,
				"Test Author <test.author@test.com>",
				"Test Committer <test.commiter@test.com>", "Initial commit");
		op.execute(null);

		createStableBranch(myRepository);
		touchAndSubmit(null);
		return gitDir;
	}

	protected static File createRemoteRepository(File repositoryDir)
			throws Exception {
		Repository myRepository = org.eclipse.egit.core.Activator.getDefault()
				.getRepositoryCache().lookupRepository(repositoryDir);
		File gitDir = new File(testDirectory, REPO2);
		Repository myRemoteRepository = new Repository(gitDir);
		myRemoteRepository.create();

		createStableBranch(myRepository);

		myRepository.getConfig().setString("remote", "push", "pushurl",
				"file:///" + myRemoteRepository.getDirectory().getPath());
		myRepository.getConfig().setString("remote", "push", "push",
				"+refs/heads/*:refs/heads/*");
		myRepository.getConfig().setString(ConfigConstants.CONFIG_CORE_SECTION,
				null, ConfigConstants.CONFIG_KEY_REPO_FORMAT_VERSION, "0");

		myRepository.getConfig().save();
		PushConfiguredRemoteAction pa = new PushConfiguredRemoteAction(
				myRepository, "push");

		pa.run(null, false);

		try {
			RefUpdate op = myRepository.updateRef("refs/heads/stable");
			op.setRefLogMessage("branch deleted", //$NON-NLS-1$
					false);
			op.setForceUpdate(true);
			op.delete();
		} catch (IOException ioe) {
			throw new InvocationTargetException(ioe);
		}
		return myRemoteRepository.getDirectory();
	}

	protected static void createStableBranch(Repository myRepository)
			throws IOException {
		String newRefName = "refs/heads/stable";
		RefUpdate updateRef = myRepository.updateRef(newRefName);
		Ref sourceBranch = myRepository.getRef("refs/heads/master");
		ObjectId startAt = sourceBranch.getObjectId();
		String startBranch = myRepository
				.shortenRefName(sourceBranch.getName());
		updateRef.setNewObjectId(startAt);
		updateRef
				.setRefLogMessage("branch: Created from " + startBranch, false); //$NON-NLS-1$
		updateRef.update();
	}

	@After
	public void afterBase() {
		new Eclipse().reset();
	}

