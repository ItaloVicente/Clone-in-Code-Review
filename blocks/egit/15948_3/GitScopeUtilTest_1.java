	@Test
	public void relatedChangesWithPrompt() throws Exception {
		GitScopeOperationFactory.setFactory(new GitScopeOperationFactory());

		final IFile modelFile = createModelFiles();

		Repository repository = lookupRepository(repositoryFile);
		Activator.getDefault().getIndexDiffCache()
				.getIndexDiffCacheEntry(repository);
		TestUtil.joinJobs(JobFamilies.INDEX_DIFF_CACHE_UPDATE);

		final IResource[] selectedResources = new IResource[] { modelFile };
		UIThreadRunnable.asyncExec(new VoidResult() {
			public void run() {
				try {
					GitScopeUtil.getRelatedChanges(part, selectedResources);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});

		SWTBotShell dialog = bot.shell("Selection Adjustment Required");
		dialog.bot().button(IDialogConstants.OK_LABEL).click();

		IFile modelExtensionsFile = modelFile.getProject().getFile(
				MODEL_EXTENSIONS_FILE);
		addAndCommit(modelExtensionsFile, "add model extensions file");
		addAndCommit(modelFile, "add model file");
		TestUtil.joinJobs(JobFamilies.INDEX_DIFF_CACHE_UPDATE);

		IResource[] relatedChanges = getRelatedChangesInUIThread(selectedResources);
		assertEquals(2, relatedChanges.length);

		assertContainsResourceByName(relatedChanges, MODEL_FILE);
		assertContainsResourceByName(relatedChanges, MODEL_EXTENSIONS_FILE);
	}

