	@Test
	public void shouldStagePartialChangeInCompareEditor() throws Exception {
		resetRepositoryToCreateInitialTag();
		changeFilesInProject();
		launchSynchronization(HEAD, HEAD, true);
		setGitChangeSetPresentationModel();
		getCompareEditorForFileInGitChangeSet(FILE1, true).bot();

		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				CommonUtils.runCommand("org.eclipse.compare.copyLeftToRight",
						null);
			}
		});
		bot.activeEditor().save();


		FileRepository repo = lookupRepository(repositoryFile);
		Status status = new Git(repo).status().call();
		assertThat(status.getChanged().size(), is(1));
		assertThat(status.getChanged().iterator().next(), is(PROJ1 + "/"
				+ FOLDER + "/" + FILE1));
	}

	@Test
	public void shouldUnStagePartialChangeInCompareEditor() throws Exception {
		resetRepositoryToCreateInitialTag();
		changeFilesInProject();
		launchSynchronization(HEAD, HEAD, true);
		setGitChangeSetPresentationModel();
		getCompareEditorForFileInGitChangeSet(FILE1, true).bot();

		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				CommonUtils.runCommand("org.eclipse.compare.copyRightToLeft",
						null);
			}
		});
		bot.activeEditor().save();

		FileRepository repo = lookupRepository(repositoryFile);
		Status status = new Git(repo).status().call();
		assertThat(status.getModified().size(), is(1));
		assertThat(status.getModified().iterator().next(), is(PROJ1 + "/"
				+ FOLDER + "/" + FILE2));
	}

