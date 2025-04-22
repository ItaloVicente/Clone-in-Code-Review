	private static void openMergeToolInternal(CompareEditorInput input) {
		CompareUI.openCompareEditor(input);
	}

	private static void openMergeToolExternal(CompareEditorInput input) {
		System.out.println(
				"---------------- openMergeToolExternal ----------------"); //$NON-NLS-1$

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
					String mergedAbsoluteFilePath = null;
					String mergedRelativeFilePath = null;
					String mergedFileName = null;
					String localAbsoluteFilePath = null;
					String remoteAbsoluteFilePath = null;
					String baseAbsoluteFilePath = null;
					String mergeCmd = null;
					String mergeBaseLessCmd = null;
					boolean prompt = false;
					boolean trustExitCode = true;
					boolean writeToTemp = false;
					boolean keepTemporaries = false;
					File mergedDirPath = null;
					File tempDirPath = null;
					File workDirPath = null;
					Repository repository = null;
					if (leftResource != null) {
						mergedAbsoluteFilePath = leftResource.getRawLocation()
								.toOSString();
						mergedFileName = leftResource.getName();
						mergedDirPath = leftResource.getRawLocation().toFile()
								.getParentFile();
						System.out.println("file: " //$NON-NLS-1$
								+ mergedAbsoluteFilePath);
					}
					if (mergedAbsoluteFilePath != null) {
						IPath[] paths = new Path[1];
						paths[0] = new Path(mergedAbsoluteFilePath);
						Map<Repository, Collection<String>> pathsByRepository = ResourceUtil
								.splitPathsByRepository(Arrays.asList(paths));
						Set<Repository> repos = pathsByRepository.keySet();
						if (repos.size() == 1) {
							repository = repos.iterator().next();
						}
						if (repository != null) {
							workDirPath = repository.getWorkTree();
						}
					}
					if (mergedAbsoluteFilePath != null && leftRevision != null
							&& rightRevision != null) {
						mergedRelativeFilePath = leftRevision.getPath();
						ITool tool = GitPreferenceRoot.getExternalMergeTool();
						if (tool != null) {
							mergeCmd = tool.getCommand(); // empty or index = 0 is the default command (with $BASE support)
							mergeBaseLessCmd = tool.getCommand(1); // index = 1 is the alternative command (without $BASE support)
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
														+ mergedRelativeFilePath
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
								tempDirPath = ToolsUtils
										.createDirectoryForTempFiles();
								mergedDirPath = tempDirPath;
							}
							localAbsoluteFilePath = ToolsUtils.loadToTempFile(
									mergedDirPath,
									mergedFileName, "LOCAL", //$NON-NLS-1$
									leftRevision, writeToTemp);
							remoteAbsoluteFilePath = ToolsUtils.loadToTempFile(
									mergedDirPath,
									mergedFileName, "REMOTE", //$NON-NLS-1$
									rightRevision, writeToTemp);
							baseAbsoluteFilePath = ToolsUtils.loadToTempFile(
									mergedDirPath,
									mergedFileName, "BASE", //$NON-NLS-1$
									baseRevision, writeToTemp);
						}
					}
					int exitCode = -1;
					try {
						exitCode = ToolsUtils.executeTool(workDirPath,
								mergedAbsoluteFilePath,
								localAbsoluteFilePath, remoteAbsoluteFilePath,
										baseAbsoluteFilePath,
										baseAbsoluteFilePath != null ? mergeCmd
												: mergeBaseLessCmd,
										tempDirPath);
					} catch (IOException | InterruptedException e) {
						e.printStackTrace();
						ToolsUtils.informUserAboutError("mergetool - error", //$NON-NLS-1$
								e.getMessage() + "\n\nMerge aborted!"); //$NON-NLS-1$
						return; // abort the merge process
					} finally {
						System.out.println("exitCode: " //$NON-NLS-1$
								+ Integer.toString(exitCode));
						if (tempDirPath != null && !keepTemporaries) {
							ToolsUtils.deleteDirectoryForTempFiles(tempDirPath);
						}
					}
					boolean addFile = false;
					if (trustExitCode) {
						System.out.println("trustExitCode: true"); //$NON-NLS-1$
						if (exitCode == 0) {
							addFile = true;
						}
					} else {
						System.out.println("trustExitCode: false"); //$NON-NLS-1$
						int response = ToolsUtils.askUserAboutToolExecution(
								"mergetool - trustExitCode: false", //$NON-NLS-1$
								"Merging file: " //$NON-NLS-1$
										+ mergedRelativeFilePath
										+ "\n\nWas the merge successful?"); //$NON-NLS-1$
						if (response == SWT.YES) {
							addFile = true;
						} else if (response == SWT.CANCEL) {
							return; // abort the merge process
						}
					}
					if (repository != null) {
						if (addFile && GitPreferenceRoot.autoAddToIndex()
								&& mergedAbsoluteFilePath != null) {
							System.out.println("addFile: " //$NON-NLS-1$
									+ mergedFileName);
							Git git = new Git(repository);
							try {
								git.add().addFilepattern(mergedRelativeFilePath)
										.call();
							} catch (GitAPIException e) {
								e.printStackTrace();
							}
							git.close();
						}
						repository.close();
					}
					break;
				}
			}
		}
	}

