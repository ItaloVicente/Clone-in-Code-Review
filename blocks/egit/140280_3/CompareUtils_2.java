	private static void openCompareToolExternal(Repository repository,
			CompareEditorInput input) {
		BooleanOption prompt = BooleanOption.NOT_DEFINED_FALSE;
		BooleanOption gui = BooleanOption.NOT_DEFINED_FALSE;
		BooleanOption trustExitCode = BooleanOption.NOT_DEFINED_FALSE;
		GitCompareFileRevisionEditorInput gitCompareInput = (GitCompareFileRevisionEditorInput) input;
		FileRevisionTypedElement leftRevision = gitCompareInput
				.getLeftRevision();
		IFile leftResource = (IFile) gitCompareInput.getAdapter(IFile.class);
		FileRevisionTypedElement rightRevision = gitCompareInput
				.getRightRevision();
		String mergedFilePath = null;
		if (leftResource != null) {
			mergedFilePath = leftResource.getName();
		} else if (leftRevision != null) {
			mergedFilePath = leftRevision.getPath();
		}
		try {
			DiffToolManager diffToolMgr = new DiffToolManager(repository);
			String toolNameToUse = GitPreferenceRoot.getDiffToolName();
			if ((toolNameToUse == null) || toolNameToUse.isEmpty()) {
				toolNameToUse = diffToolMgr
						.getDefaultToolName(gui);
			}
			if ((toolNameToUse == null) || toolNameToUse.isEmpty()) {
				toolNameToUse = diffToolMgr.getFirstAvailableTool();
			}
			if ((toolNameToUse == null) || toolNameToUse.isEmpty()) {
				throw new ToolException(
						"Unknown diff tool '" + toolNameToUse + "'"); //$NON-NLS-1$ //$NON-NLS-2$
			}
			boolean showPrompt = diffToolMgr.isPrompt();
			if (showPrompt) {
				int response = ToolsUtils.askUserAboutToolExecution("difftool", //$NON-NLS-1$
						"Comparing file: " //$NON-NLS-1$
								+ mergedFilePath + "\n\nLaunch '" //$NON-NLS-1$
								+ toolNameToUse + "' ?"); //$NON-NLS-1$
				if (response != SWT.YES) {
					return;
				}
			}
			FileElement local = null;
			if (leftRevision != null) {
				local = new FileElement(mergedFilePath, FileElement.Type.LOCAL,
						null, null, leftRevision.getContents());
			} else {
				local = new FileElement(mergedFilePath, FileElement.Type.LOCAL, repository.getWorkTree());
			}
			FileElement remote = new FileElement(mergedFilePath,
					FileElement.Type.REMOTE, null, null,
					rightRevision.getContents());
			FileElement merged = new FileElement(mergedFilePath,
					FileElement.Type.MERGED, repository.getWorkTree());
			diffToolMgr.compare(local, remote, merged, toolNameToUse, prompt,
					gui, trustExitCode);
		} catch (ToolException e) {
			e.printStackTrace();
			ToolsUtils.informUserAboutError(
					"external diff died, stopping at " + mergedFilePath, //$NON-NLS-1$
					e.getResultStdout() + e.getMessage());
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

