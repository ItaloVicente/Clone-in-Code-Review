	private static void openMergeToolInternal(CompareEditorInput input) {
		CompareUI.openCompareEditor(input);
	}

	private static void openMergeToolExternal(CompareEditorInput input) {
		final GitMergeEditorInput gitMergeInput = (GitMergeEditorInput) input;
		DiffContainerJob job = new DiffContainerJob(
				"Prepare filelist for external merge tools", gitMergeInput); //$NON-NLS-1$
		job.schedule();
		try {
			job.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		IDiffContainer diffCont = job.getDiffContainer();
		executeExternalToolForChildren(diffCont);
	}

	private static void executeExternalToolForChildren(
			IDiffContainer diffCont) {
		if (diffCont != null && diffCont.hasChildren()) {
			IDiffElement[] difContChilds = diffCont.getChildren();
			for (IDiffElement diffElement : difContChilds) {
				switch (diffElement.getKind()) {
				case Differencer.NO_CHANGE:
					executeExternalToolForChildren(
							(IDiffContainer) diffElement);
					break;
				case Differencer.PSEUDO_CONFLICT:
					break;
				case Differencer.CONFLICTING:
					try {
						mergeModified((DiffNode) diffElement);
					} catch (IOException | CoreException e) {
						e.printStackTrace();
						return;
					}
					break;
				}
			}
		}
	}

	private static void mergeModified(DiffNode node)
			throws IOException, CoreException {
		BooleanOption prompt = BooleanOption.NOT_DEFINED_FALSE;
		BooleanOption gui = BooleanOption.NOT_DEFINED_FALSE;
		FileRevisionTypedElement leftRevision = (ResourceEditableRevision)node.getLeft();
		IResource leftResource = ((ResourceEditableRevision)node.getLeft()).getResource();
		FileRevisionTypedElement rightRevision = (FileRevisionTypedElement)node.getRight();
		FileRevisionTypedElement baseRevision = (FileRevisionTypedElement)node.getAncestor();
		String mergedFilePath = null;
		String mergedAbsoluteFilePath = null;
		if (leftResource != null) {
			mergedFilePath = leftResource.getName();
			mergedAbsoluteFilePath = leftResource.getRawLocation().toOSString();
		} else if (leftRevision != null) {
			mergedFilePath = leftRevision.getPath();
			mergedAbsoluteFilePath = mergedFilePath;
		}
		Repository repository = null;
		IPath[] paths = new Path[1];
		paths[0] = new Path(mergedAbsoluteFilePath);
		Map<Repository, Collection<String>> pathsByRepository = ResourceUtil
				.splitPathsByRepository(Arrays.asList(paths));
		Set<Repository> repos = pathsByRepository.keySet();
		if (repos.size() >= 1) {
			repository = repos.iterator().next();
		}
		if (repository == null) {
			return;
		}
		boolean isMergeSuccessful = true;
		FileElement merged = new FileElement(mergedFilePath,
				FileElement.Type.MERGED, repository.getWorkTree());
		long modifiedBefore = merged.getFile().lastModified();
		try {
			MergeToolManager mergeToolMgr = new MergeToolManager(repository);
			String toolNameToUse = GitPreferenceRoot.getMergeToolName();
			if ((toolNameToUse == null) || toolNameToUse.isEmpty()) {
				toolNameToUse = mergeToolMgr.getDefaultToolName(gui);
			}
			if ((toolNameToUse == null) || toolNameToUse.isEmpty()) {
				toolNameToUse = mergeToolMgr.getFirstAvailableTool();
			}
			if ((toolNameToUse == null) || toolNameToUse.isEmpty()) {
				throw new ToolException(
						"Unknown merge tool '" + toolNameToUse + "'"); //$NON-NLS-1$ //$NON-NLS-2$
			}
			boolean showPrompt = mergeToolMgr.isPrompt();
			if (showPrompt) {
				int response = ToolsUtils.askUserAboutToolExecution("mergetool", //$NON-NLS-1$
						"Merging file: " //$NON-NLS-1$
								+ mergedFilePath + "\n\nLaunch '" //$NON-NLS-1$
								+ toolNameToUse + "' ?"); //$NON-NLS-1$
				if (response != SWT.YES) {
					return;
				}
			}
			File tempDir = mergeToolMgr.createTempDirectory();
			File tempFilesParent = tempDir != null ? tempDir : repository.getWorkTree();
			FileElement local = createFileElement(leftRevision, mergedFilePath,
					FileElement.Type.LOCAL, repository, tempFilesParent, true);
			FileElement remote = createFileElement(rightRevision,
					mergedFilePath, FileElement.Type.REMOTE, repository,
					tempFilesParent, false);
			FileElement base = createFileElement(baseRevision, mergedFilePath,
					FileElement.Type.BASE, repository, tempFilesParent, false);
			mergeToolMgr.merge(local, remote, merged, base, tempDir,
					toolNameToUse, prompt, gui);
		} catch (ToolException e) {
			isMergeSuccessful = false;
			e.printStackTrace();
			if (e.isCommandExecutionError()) {
				ToolsUtils.informUserAboutError("mergetool - error", //$NON-NLS-1$
						e.getMessage() + "\n\nMerge aborted!"); //$NON-NLS-1$
				return; // abort the merge process
			}
		}
		if (isMergeSuccessful) {
			long modifiedAfter = merged.getFile().lastModified();
			if (modifiedBefore == modifiedAfter) {
				int response = ToolsUtils.askUserAboutToolExecution(
						"mergetool - trustExitCode: false", //$NON-NLS-1$
						mergedFilePath
								+ " seems unchanged.\n\nWas the merge successful?"); //$NON-NLS-1$
				if (response == SWT.NO) {
					isMergeSuccessful = false;
				} else if (response == SWT.CANCEL) {
					return; // abort the merge process
				}
			}
		}
		if (isMergeSuccessful && GitPreferenceRoot.autoAddToIndex()) {
			Git git = new Git(repository);
			try {
				git.add().addFilepattern(mergedFilePath).call();
			} catch (GitAPIException e) {
				e.printStackTrace();
			}
			git.close();
			repository.close();
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
					null, null, revision.getContents());
			element.createTempFile(tempFilesParent);
		} else if (createWorktreeElement) {
			element = new FileElement(path, type,
					repository.getWorkTree());
		}
		return element;
	}

