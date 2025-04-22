	private static void openInCompareExternal(Repository repository,
			CompareEditorInput input) {
		System.out.println(
				"---------------- openInCompare with external tool ------------------"); //$NON-NLS-1$
		GitCompareFileRevisionEditorInput gitCompareInput = (GitCompareFileRevisionEditorInput) input;
		FileRevisionTypedElement leftRevision = gitCompareInput
				.getLeftRevision();
		IFile leftResource = (IFile) gitCompareInput.getAdapter(IFile.class);
		FileRevisionTypedElement rightRevision = gitCompareInput
				.getRightRevision();
		String mergedCompareFilePath = null;
		String mergedCompareFileName = null;
		String localCompareFilePath = null;
		String remoteCompareFilePath = null;
		String baseCompareFilePath = null;
		String diffCmd = null;
		boolean prompt = false;
		boolean writeToTemp = false;
		boolean keepTemporaries = false; // not supported in CGit, TODO:
		File baseDir = null;
		File tempDir = null;
		if (leftResource != null) {
			mergedCompareFilePath = leftResource.getRawLocation().toOSString();
			mergedCompareFileName = leftResource.getName();
			baseDir = leftResource.getRawLocation().toFile().getParentFile();
			System.out.println("mergedCompareFilePath: " //$NON-NLS-1$
					+ mergedCompareFilePath);
		}
		if (mergedCompareFilePath != null
				&& rightRevision != null) {
			ITool tool = GitPreferenceRoot.getExternalDiffTool();
			if (tool != null) {
				diffCmd = tool.getCommand();
				prompt = GitPreferenceRoot
						.getExternalDiffToolAttributeValueBoolean(
								tool.getName(), "prompt"); //$NON-NLS-1$
				writeToTemp = GitPreferenceRoot
						.getExternalDiffToolAttributeValueBoolean(
								tool.getName(), "writeToTemp"); //$NON-NLS-1$
				keepTemporaries = GitPreferenceRoot
						.getExternalDiffToolAttributeValueBoolean(
								tool.getName(), "keepTemporaries"); //$NON-NLS-1$
				if (prompt) {
					int response = ToolsUtils.askUserAboutToolExecution(
							"difftool", //$NON-NLS-1$
							"Comparing file: " //$NON-NLS-1$
									+ mergedCompareFilePath + "\n\nLaunch '" //$NON-NLS-1$
									+ tool.getName() + "' ?"); //$NON-NLS-1$
					if (response != SWT.YES) {
						return;
					}
				}
				if (writeToTemp) {
					tempDir = ToolsUtils.createDirectoryForTempFiles();
					baseDir = tempDir;
				}
				if (leftRevision != null) {
					localCompareFilePath = ToolsUtils.loadToTempFile(baseDir,
							mergedCompareFileName, "LOCAL", //$NON-NLS-1$
							leftRevision, writeToTemp);
				} else {
					localCompareFilePath = mergedCompareFilePath;
					System.out.println("localCompareFilePath: " //$NON-NLS-1$
							+ localCompareFilePath);
				}
				remoteCompareFilePath = ToolsUtils.loadToTempFile(baseDir,
						mergedCompareFileName, "REMOTE", //$NON-NLS-1$
						rightRevision, writeToTemp);
			}
		}
		ToolsUtils.executeTool(mergedCompareFilePath, localCompareFilePath,
				remoteCompareFilePath, baseCompareFilePath, diffCmd, tempDir);
		if (tempDir != null && !keepTemporaries) {
			ToolsUtils.deleteDirectoryForTempFiles(tempDir);
		}
	}

