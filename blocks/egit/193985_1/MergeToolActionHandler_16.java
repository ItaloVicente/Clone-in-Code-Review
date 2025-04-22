	private static void openMergeToolInternal(CompareEditorInput input) {
		CompareUI.openCompareEditor(input);
	}

	private static void openMergeToolExternal(CompareEditorInput input)
			throws ExecutionException {
		final GitMergeEditorInput gitMergeInput = (GitMergeEditorInput) input;
		DiffContainerJob job = new DiffContainerJob(
				UIText.MergeToolActionHandler_openExternalMergeToolJobName,
				gitMergeInput);
		job.schedule();
		try {
			job.join();
		} catch (InterruptedException e) {
			Thread.interrupted();
			throw new ExecutionException(
					UIText.MergeToolActionHandler_openExternalMergeToolWaitInterrupted,
					e);
		}
		IDiffContainer diffCont = job.getDiffContainer();
		executeExternalToolForChildren(diffCont, job.getRepository());
	}

	private static void executeExternalToolForChildren(
			IDiffContainer diffCont, Repository repo)
			throws ExecutionException {
		if (diffCont != null && diffCont.hasChildren()) {
			IDiffElement[] difContChilds = diffCont.getChildren();
			for (IDiffElement diffElement : difContChilds) {
				int diffKind = diffElement.getKind();
				if (diffKind == Differencer.NO_CHANGE) {
					executeExternalToolForChildren(
							(IDiffContainer) diffElement, repo);
				} else if ((diffKind & Differencer.CONFLICTING) != 0) {
					try {
						mergeModified((DiffNode) diffElement, repo);
					} catch (IOException | CoreException e) {
						throw new ExecutionException(
								UIText.MergeToolActionHandler_externalMergeToolRunFailed,
								e);
					}
				}
			}
		}
	}

	private static void mergeModified(DiffNode node, Repository repo)
			throws IOException, CoreException {
		FileRevisionTypedElement leftRevision = (ResourceEditableRevision)node.getLeft();
		IResource leftResource = ((ResourceEditableRevision)node.getLeft()).getResource();
		FileRevisionTypedElement rightRevision = (FileRevisionTypedElement)node.getRight();
		FileRevisionTypedElement baseRevision = (FileRevisionTypedElement)node.getAncestor();
		String mergedFilePath = null;
		Repository repository = repo;
		if (leftResource != null) {
			IPath relativePath = ResourceUtil.getRepositoryRelativePath(
					leftResource.getRawLocation(), repository);
			mergedFilePath = relativePath == null ? leftResource.getName()
					: relativePath.toOSString();
		} else if (leftRevision != null) {
			mergedFilePath = leftRevision.getPath();
		}
		boolean isMergeSuccessful = true;
		FileElement merged = new FileElement(mergedFilePath,
				FileElement.Type.MERGED, repository.getWorkTree());
		long modifiedBefore = merged.getFile().lastModified();

		try {
			MergeTools mergeTools = new MergeTools(repository);
			Optional<String> toolNameToUse = DiffMergeSettings
					.getMergeToolName(repository, mergedFilePath);
			BooleanTriState prompt = BooleanTriState.FALSE;

			PromptContinueHandler promptContinueHandler = new FileNamePromptContinueHandler(
					mergedFilePath);

			File tempDir = mergeTools.createTempDirectory();
			File tempFilesParent = tempDir != null ? tempDir : repository.getWorkTree();
			FileElement local = createFileElement(leftRevision, mergedFilePath,
					FileElement.Type.LOCAL, repository, tempFilesParent, true);
			FileElement remote = createFileElement(rightRevision,
					mergedFilePath, FileElement.Type.REMOTE, repository,
					tempFilesParent, false);
			FileElement base = createFileElement(baseRevision, mergedFilePath,
					FileElement.Type.BASE, repository, tempFilesParent, false);
			mergeTools.merge(local, remote, merged, base, tempDir,
					toolNameToUse, prompt, false, promptContinueHandler,
					tools -> {
						ToolsUtils.informUser(
								UIText.MergeToolActionHandler_noToolConfiguredDialogTitle,
								UIText.MergeToolActionHandler_noToolConfiguredDialogContent);
					});
		} catch (ToolException e) {
			isMergeSuccessful = false;
			if (e.isCommandExecutionError()) {
				Activator.handleError(
						UIText.MergeToolActionHandler_mergeToolErrorDialogContent,
						e, true);
				return; // abort the merge process
			} else {
				Activator.logWarning("Failed to run external merge tool.", e); //$NON-NLS-1$
			}
		}
		if (isMergeSuccessful) {
			long modifiedAfter = merged.getFile().lastModified();
			if (modifiedBefore == modifiedAfter) {
				int response = ToolsUtils.askUserAboutToolExecution(
						UIText.MergeToolActionHandler_mergeToolNoChangeDialogTitle,
						NLS.bind(
								UIText.MergeToolActionHandler_mergeToolNoChangeDialogContent,
								mergedFilePath));
				if (response == SWT.NO) {
					isMergeSuccessful = false;
				} else if (response == SWT.CANCEL) {
					return; // abort the merge process
				}
			}
		}
		if (isMergeSuccessful && GitPreferenceRoot.autoAddToIndex()) {
			try (Git git = new Git(repository)) {
				git.add().addFilepattern(mergedFilePath).call();
				if (leftResource != null) {
					leftResource.getParent().refreshLocal(IResource.DEPTH_ONE,
							null);
				}
			} catch (GitAPIException e) {
				Activator.handleError(
						UIText.MergeToolActionHandler_mergeToolFailedAddMergedToGit,
						e, true);
			}
		}
	}

	private static class FileNamePromptContinueHandler
			implements PromptContinueHandler {
		private final String fileName;

		public FileNamePromptContinueHandler(String fileName) {
			this.fileName = fileName;
		}

		@Override
		public boolean prompt(String toolName) {
			int response = ToolsUtils.askUserAboutToolExecution(
					UIText.MergeToolActionHandler_mergeToolPromptDialogTitle,
					NLS.bind(
							UIText.MergeToolActionHandler_mergeToolPromptDialogContent,
							fileName, toolName));

			return response == SWT.YES;
		}
	}

	private static FileElement createFileElement(
			FileRevisionTypedElement revision, String path,
			FileElement.Type type, Repository repository, File tempFilesParent,
			boolean createWorktreeElement)
			throws CoreException, IOException {
		FileElement element = null;
		if (revision != null) {
			element = new FileElement(path, type,
					null, revision.getContents());
			element.createTempFile(tempFilesParent);
		} else if (createWorktreeElement) {
			element = new FileElement(path, type,
					repository.getWorkTree());
		}
		return element;
	}

