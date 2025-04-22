	private static void openInCompareInternal(CompareEditorInput input) {
		CompareUI.openCompareEditor(input);
	}

	private static void openInCompareExternal(CompareEditorInput input) {
		System.out.println(
				"---------------- openInCompare with external tool ------------------"); //$NON-NLS-1$

		final GitMergeEditorInput gitMergeInput = (GitMergeEditorInput) input;

		DiffConteinerJob job = new DiffConteinerJob(
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
			for(IDiffElement diffElement : difContChilds)
			{
				switch (diffElement.getKind()) {
				case Differencer.NO_CHANGE:
					executeExternalToolForChildren(
							(IDiffContainer) diffElement);
					break;
				case Differencer.PSEUDO_CONFLICT:
					break;
				case Differencer.CONFLICTING:
					DiffNode node = (DiffNode) diffElement;
					FileRevisionTypedElement leftRevision = (ResourceEditableRevision) node
							.getLeft();
					IFile leftResource = ((ResourceEditableRevision) node
							.getLeft()).getFile();
					FileRevisionTypedElement rightRevision = (FileRevisionTypedElement) node
							.getRight();
					FileRevisionTypedElement baseRevision = (FileRevisionTypedElement) node
							.getAncestor();
					String mergedCompareFilePath = null;
					String mergedCompareFileName = null;
					String localCompareFilePath = null;
					String remoteCompareFilePath = null;
					String baseCompareFilePath = null;
					String mergeCmd = null;
					boolean prompt = false;
					boolean trustExitCode = true;
					boolean writeToTemp = false;
					boolean keepTemporaries = false;
					File baseDir = null;
					File tempDir = null;
					if (leftResource != null) {
						mergedCompareFilePath = leftResource.getRawLocation()
								.toOSString();
						mergedCompareFileName = leftResource.getName();
						baseDir = leftResource.getRawLocation().toFile()
								.getParentFile();
						System.out.println("mergedCompareFilePath: " //$NON-NLS-1$
								+ mergedCompareFilePath);
					}
					if (mergedCompareFilePath != null && leftRevision != null
							&& rightRevision != null) {
						ITool tool = GitPreferenceRoot.getExternalMergeTool();
						if (tool != null) {
							mergeCmd = tool.getCommand();
							prompt = GitPreferenceRoot
									.getExternalMergeToolAttributeValueBoolean(
											tool.getName(), "prompt"); //$NON-NLS-1$
							trustExitCode = GitPreferenceRoot
									.getExternalMergeToolAttributeValueBoolean(
											tool.getName(), "trustExitCode"); //$NON-NLS-1$
									.getExternalMergeToolAttributeValueBoolean(
											tool.getName(), "keepBackup"); //$NON-NLS-1$*/
							writeToTemp = GitPreferenceRoot
									.getExternalMergeToolAttributeValueBoolean(
											tool.getName(), "writeToTemp"); //$NON-NLS-1$
							keepTemporaries = GitPreferenceRoot
									.getExternalMergeToolAttributeValueBoolean(
											tool.getName(), "keepTemporaries"); //$NON-NLS-1$
							if (prompt) {
								int response = ToolsUtils.askUserAboutToolExecution(
										"mergetool", //$NON-NLS-1$
										"Merging file: " //$NON-NLS-1$
												+ mergedCompareFilePath
												+ "\n\nLaunch '" //$NON-NLS-1$
												+ tool.getName()
												+ "' ?"); //$NON-NLS-1$
								if (response == SWT.NO) {
									break;
								} else if (response == SWT.CANCEL) {
									return;
								}
							}
							if (writeToTemp) {
								tempDir = ToolsUtils
										.createDirectoryForTempFiles();
								baseDir = tempDir;
							}
							localCompareFilePath = ToolsUtils.loadToTempFile(baseDir,
									mergedCompareFileName, "LOCAL", //$NON-NLS-1$
									leftRevision, writeToTemp);
							remoteCompareFilePath = ToolsUtils.loadToTempFile(baseDir,
									mergedCompareFileName, "REMOTE", //$NON-NLS-1$
									rightRevision, writeToTemp);
							baseCompareFilePath = ToolsUtils.loadToTempFile(baseDir,
									mergedCompareFileName, "BASE", //$NON-NLS-1$
									baseRevision, writeToTemp);
						}
					}
					int exitCode = ToolsUtils.executeTool(mergedCompareFilePath,
							localCompareFilePath,
							remoteCompareFilePath, baseCompareFilePath,
							mergeCmd, tempDir);
					if (tempDir != null && !keepTemporaries) {
						ToolsUtils.deleteDirectoryForTempFiles(tempDir);
					}
					if (exitCode == 0) {
						if (!trustExitCode) {
							int response = ToolsUtils.askUserAboutToolExecution(
									"mergetool", //$NON-NLS-1$
									"Merging file: " //$NON-NLS-1$
											+ mergedCompareFilePath
											+ "\n\nWas the merge successful?"); //$NON-NLS-1$
							if (response == SWT.YES) {
								AddCommand addCommand = new Git(repo).add();
								boolean fileAdded = false;
								for (String path : notTracked)
									if (commitFileList.contains(path)) {
										addCommand.addFilepattern(path);
										fileAdded = true;
									}
								if (fileAdded)
									try {
										addCommand.call();
									} catch (Exception e) {
										throw new CoreException(Activator
												.error(e.getMessage(), e));
									}
							} else if (response == SWT.CANCEL) {
								return;
							}
						}
					}
					break;
				}
			}
		}
	}

