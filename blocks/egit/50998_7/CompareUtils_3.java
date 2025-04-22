	private static void openCompareToolExternal(CompareEditorInput input) {
		System.out.println(
				"---------------- openCompareToolExternal ----------------"); //$NON-NLS-1$
		GitCompareFileRevisionEditorInput gitCompareInput = (GitCompareFileRevisionEditorInput) input;
		Repository repository = gitCompareInput.getRepository();
		FileRevisionTypedElement leftRevision = gitCompareInput
				.getLeftRevision();
		IFile leftResource = (IFile) gitCompareInput.getAdapter(IFile.class);
		FileRevisionTypedElement rightRevision = gitCompareInput
				.getRightRevision();
		String mergedAbsoluteFilePath = null;
		String mergedRelativeFilePath = null;
		String mergedFileName = null;
		String localAbsoluteFilePath = null;
		String remoteAbsoluteFilePath = null;
		String baseAbsoluteFilePath = null;
		String diffCmd = null;
		boolean prompt = false;
		boolean writeToTemp = false;
		boolean keepTemporaries = false; // not supported in CGit, TODO:
		File mergedDirPath = null;
		File tempDirPath = null;
		File workDirPath = null;
		if (leftResource != null) {
			mergedAbsoluteFilePath = leftResource.getRawLocation().toOSString();
			mergedFileName = leftResource.getName();
			mergedDirPath = leftResource.getRawLocation().toFile().getParentFile();
		} else if (leftRevision != null) {
			mergedFileName = leftRevision.getName();
			String leftFilePath = leftRevision.getPath();
			if (leftFilePath != null) {
				IFile leftFile = ResourceUtil.getFileForLocation(repository,
						leftFilePath);
				if (leftFile != null) {
					IPath leftPath = leftFile.getRawLocation();
					mergedAbsoluteFilePath = leftPath.toOSString();
					mergedDirPath = leftPath.toFile().getParentFile();
				}
			}
		}
		System.out.println("file: " //$NON-NLS-1$
				+ mergedAbsoluteFilePath);
		workDirPath = repository.getWorkTree();
		ITool tool = GitPreferenceRoot.getExternalDiffTool();
		if (mergedAbsoluteFilePath != null && rightRevision != null && tool != null) {
			mergedRelativeFilePath = rightRevision.getPath();
			diffCmd = tool.getCommand();
			prompt = GitPreferenceRoot.getExternalDiffToolAttributeValueBoolean(
					tool.getName(), "prompt"); //$NON-NLS-1$
			writeToTemp = GitPreferenceRoot
					.getExternalDiffToolAttributeValueBoolean(tool.getName(),
							"writeToTemp"); //$NON-NLS-1$
			keepTemporaries = GitPreferenceRoot
					.getExternalDiffToolAttributeValueBoolean(tool.getName(),
							"keepTemporaries"); //$NON-NLS-1$
			if (prompt) {
				int response = ToolsUtils.askUserAboutToolExecution("difftool", //$NON-NLS-1$
						"Comparing file: " //$NON-NLS-1$
								+ mergedRelativeFilePath + "\n\nLaunch '" //$NON-NLS-1$
								+ tool.getName() + "' ?"); //$NON-NLS-1$
				if (response != SWT.YES) {
					return;
				}
			}
			if (writeToTemp) {
				tempDirPath = ToolsUtils.createDirectoryForTempFiles();
				mergedDirPath = tempDirPath;
			}
			if (leftRevision != null) {
				localAbsoluteFilePath = ToolsUtils.loadToTempFile(mergedDirPath,
						mergedFileName, "LOCAL", //$NON-NLS-1$
						leftRevision, writeToTemp);
			} else {
				localAbsoluteFilePath = mergedAbsoluteFilePath;
			}
			remoteAbsoluteFilePath = ToolsUtils.loadToTempFile(mergedDirPath,
					mergedFileName, "REMOTE", //$NON-NLS-1$
					rightRevision, writeToTemp);
			int exitCode = -1;
			try {
				exitCode = ToolsUtils.executeTool(workDirPath,
						mergedAbsoluteFilePath, localAbsoluteFilePath,
						remoteAbsoluteFilePath, baseAbsoluteFilePath, diffCmd,
						tempDirPath);
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
				ToolsUtils.informUserAboutError("difftool - error", //$NON-NLS-1$
						e.getMessage());
			} finally {
				System.out.println("exitCode: " //$NON-NLS-1$
						+ Integer.toString(exitCode));
				if (tempDirPath != null && !keepTemporaries) {
					ToolsUtils.deleteDirectoryForTempFiles(tempDirPath);
				}
			}
		}
	}

