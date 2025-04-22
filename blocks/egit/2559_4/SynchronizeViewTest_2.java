	private void launchSynchronization(String srcRepo, String srcRef,
			String dstRepo, String dstRef, boolean includeLocal) {
		launchSynchronization(REPO1, srcRepo, srcRef, dstRepo, dstRef,
				includeLocal);
	}

	private void launchSynchronization(String repo, String srcRepo,
			String srcRef, String dstRepo, String dstRef, boolean includeLocal) {
		bot.shell("Synchronize repository: " + repo + File.separator + ".git")
				.activate();

		if (!includeLocal)
			bot.checkBox(
					UIText.SelectSynchronizeResourceDialog_includeUncommitedChanges)
					.click();

		if (srcRepo != null)
			bot.comboBox(0)
					.setSelection(srcRepo);
		if (srcRef != null)
			bot.comboBox(1).setSelection(srcRef);

		if (dstRepo != null)
			bot.comboBox(2)
					.setSelection(dstRepo);
		if (dstRef != null)
			bot.comboBox(3).setSelection(dstRef);

		SynchronizeFinishHook sfh = new SynchronizeFinishHook();
		Job.getJobManager().addJobChangeListener(sfh);

		bot.button(IDialogConstants.OK_LABEL).click();

		try {
			bot.waitUntil(sfh, 120000);
		} finally {
			Job.getJobManager().removeJobChangeListener(sfh);
		}
	}

	private static class SynchronizeFinishHook extends JobChangeAdapter
			implements ICondition {
		private boolean state = false;

		@SuppressWarnings("restriction") public void done(IJobChangeEvent event) {
			if (event.getJob() instanceof RefreshParticipantJob)
				state = true;
		}

		public boolean test() throws Exception {
			return state;
		}

		public void init(SWTBot swtBot) {
		}

		public String getFailureMessage() {
			return null;
		}

	}

	private SWTBot setPresentationModel(String model) {
		SWTBotView syncView = bot.viewByTitle("Synchronize");
		syncView.toolbarDropDownButton("Show File System Resources").click()
				.menuItem(model).click();

		return syncView.bot();
	}

	private void createEmptyRepository() throws Exception {
		File gitDir = new File(new File(getTestDirectory(), "EmptyRepository"),
				Constants.DOT_GIT);
		gitDir.mkdir();
		Repository myRepository = new FileRepository(gitDir);
		myRepository.create();

		IProject firstProject = ResourcesPlugin.getWorkspace().getRoot()
				.getProject("EmptyProject");

		if (firstProject.exists())
			firstProject.delete(true, null);
		IProjectDescription desc = ResourcesPlugin.getWorkspace()
				.newProjectDescription("EmptyProject");
		desc.setLocation(new Path(new File(myRepository.getWorkTree(),
				"EmptyProject").getPath()));
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
	}

