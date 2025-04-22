	private static class FileNamePromptContinueHandler
			implements PromptContinueHandler {
		private final String fileName;

		public FileNamePromptContinueHandler(String fileName) {
			this.fileName = fileName;
		}

		@Override
		public boolean prompt(String toolName) {
			int response = ToolsUtils.askUserAboutToolExecution("difftool", //$NON-NLS-1$
					"Comparing file: " //$NON-NLS-1$
							+ fileName + "\n\nLaunch '" //$NON-NLS-1$
							+ toolName + "' ?"); //$NON-NLS-1$

			return response == SWT.YES;
		}
	}

	private static void openCompareToolExternal(Repository repository,
			CompareEditorInput input) {
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

		Optional<String> toolNameToUse = Optional.ofNullable(GitPreferenceRoot.getDiffToolName());

		Optional<Boolean> unset = Optional.empty();

		try {
			DiffToolManager diffToolMgr = new DiffToolManager(repository);

			PromptContinueHandler promptContinueHandler = new FileNamePromptContinueHandler(
					mergedFilePath);

			FileElement local = null;
			if (leftRevision != null) {
				local = new FileElement(mergedFilePath, FileElement.Type.LOCAL,
						repository.getWorkTree(), leftRevision.getContents());
			} else {
				local = new FileElement(mergedFilePath, FileElement.Type.LOCAL, repository.getWorkTree());
			}
			FileElement remote = new FileElement(mergedFilePath,
					FileElement.Type.REMOTE, repository.getWorkTree(),
					rightRevision.getContents());

			diffToolMgr.compare(local, remote, toolNameToUse, unset, false,
					unset, promptContinueHandler, tools -> {
						ToolsUtils.informUser("No tool configured.", //$NON-NLS-1$
								"No difftool is set. Will try a preconfigured one now. To configure one open the git config settings."); //$NON-NLS-1$
					});
		} catch (ToolException e) {
			e.printStackTrace();
			ToolsUtils.informUserAboutError(
					"external diff died, stopping at " + mergedFilePath, //$NON-NLS-1$
					e.getResultStdout() + e.getMessage());
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

