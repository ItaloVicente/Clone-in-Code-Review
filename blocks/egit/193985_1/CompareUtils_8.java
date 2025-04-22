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

	private static void openCompareToolExternal(IWorkbenchPage workbenchPage,
			Repository repository, CompareEditorInput input) {
		Job job = new Job(UIText.CompareUtils_ExecutingExtDiffTool) {

			@Override
			public IStatus run(IProgressMonitor monitor) {
				if (monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}
				if (!(input instanceof GitCompareFileRevisionEditorInput)) {
					PlatformUI.getWorkbench().getDisplay().asyncExec(
							() -> openCompareToolInternal(workbenchPage,
									input));
					return Status.OK_STATUS;
				}
				GitCompareFileRevisionEditorInput gitCompareInput = (GitCompareFileRevisionEditorInput) input;
				FileRevisionTypedElement leftRevision = gitCompareInput
						.getLeftRevision();
				FileRevisionTypedElement rightRevision = gitCompareInput
						.getRightRevision();
				String changedFilePath = getChangedFilePath(repository, gitCompareInput);

				try {
					Optional<String> toolName = DiffMergeSettings.getDiffToolName(repository, changedFilePath);

					String preferencesCommand = null;
					if (changedFilePath != null) {
						preferencesCommand = DiffMergeSettings
								.getDiffToolCommandFromPreferences(changedFilePath);
					}
					if (!toolName.isPresent()
							&& StringUtils.isEmptyOrNull(preferencesCommand)) {
						PlatformUI.getWorkbench().getDisplay().asyncExec(
								() -> openCompareToolInternal(workbenchPage,
										input));
						return Status.OK_STATUS;
					}

					PromptContinueHandler promptContinueHandler = new FileNamePromptContinueHandler(
							changedFilePath);

					@SuppressWarnings("restriction")
					boolean swapSides = CompareUIPlugin.getDefault()
							.getPreferenceStore()
							.getBoolean(ComparePreferencePage.SWAPPED);
					Type typeLocal;
					Type typeRemote;
					if (swapSides) {
						typeLocal = FileElement.Type.REMOTE;
						typeRemote = FileElement.Type.LOCAL;
					} else {
						typeLocal = FileElement.Type.LOCAL;
						typeRemote = FileElement.Type.REMOTE;
					}

					FileElement local = null;
					if (leftRevision != null) {
						local = new FileElement(changedFilePath,
								typeLocal,
								repository.getWorkTree(),
								leftRevision.getContents());
					} else {
						local = new FileElement(changedFilePath,
								typeLocal,
								repository.getWorkTree());
					}
					FileElement remote = null;
					if (rightRevision != null) {
						remote = new FileElement(changedFilePath,
								typeRemote,
								repository.getWorkTree(),
								rightRevision.getContents());
					} else {
						remote = new FileElement(changedFilePath,
								typeRemote,
								repository.getWorkTree());
					}

					boolean gui = false;
					BooleanTriState trustExitCode = BooleanTriState.UNSET;
					BooleanTriState prompt = BooleanTriState.FALSE;
					DiffTools diffToolMgr = new DiffTools(repository);
					if (preferencesCommand != null) {
						String customToolName = "custom_tool_" //$NON-NLS-1$
								+ DiffMergeSettings
								.getFileExtension(changedFilePath);
						UserDefinedDiffTool tool = new UserDefinedDiffTool(
								customToolName, "", preferencesCommand); //$NON-NLS-1$
						diffToolMgr.compare(local, remote, tool, true);
					} else {
						diffToolMgr.compare(local, remote, toolName, prompt,
								gui, trustExitCode, promptContinueHandler,
								tools -> {
									ToolsUtils.informUser(
											UIText.CompareUtils_NoDiffToolsDefined,
											UIText.CompareUtils_NoDiffToolSpecified);
								});
					}
				} catch (ToolException e) {
					ToolsUtils.informUserAboutError(
							UIText.CompareUtils_ExternalDiffToolDied
									+ changedFilePath,
							e.getMessage());
				} catch (CoreException e) {
					ToolsUtils.informUserAboutError(
							UIText.CompareUtils_DiffToolExecutionFailed
									+ changedFilePath,
							e.getMessage());
				}
				return Status.OK_STATUS;
			}


		};
		job.setUser(true);
		job.schedule();
	}

	private static String getChangedFilePath(
			Repository repository, GitCompareFileRevisionEditorInput gitCompareInput) {
		FileRevisionTypedElement leftRevision = gitCompareInput
				.getLeftRevision();
		IFile leftResource = gitCompareInput.getAdapter(IFile.class);
		String changedFilePath = null;
		if (leftResource != null) {
			changedFilePath = repository.getWorkTree().toPath()
					.relativize(leftResource.getRawLocation().toFile().toPath())
					.toString();
		} else if (leftRevision != null) {
			changedFilePath = leftRevision.getPath();
		}
		if (changedFilePath == null) {
			FileRevisionTypedElement rightRevision = gitCompareInput
					.getRightRevision();
			if (rightRevision != null) {
				changedFilePath = rightRevision.getPath();
			}
		}
		return changedFilePath;
	}

