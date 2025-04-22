	private MergeToolManager mergeToolMgr;

	@Option(name = "--tool", aliases = {
			"-t" }, metaVar = "metaVar_tool", usage = "usage_ToolForMerge")
	private String toolName;

	private BooleanOption prompt = BooleanOption.NOT_DEFINED_FALSE;

	@Option(name = "--prompt", usage = "usage_prompt")
	void setPrompt(@SuppressWarnings("unused") boolean on) {
		prompt = BooleanOption.TRUE;
	}

	@Option(name = "--no-prompt", aliases = { "-y" }, usage = "usage_noPrompt")
	void noPrompt(@SuppressWarnings("unused") boolean on) {
		prompt = BooleanOption.FALSE;
	}

	@Option(name = "--tool-help", usage = "usage_toolHelp")
	private boolean toolHelp;

	private BooleanOption gui = BooleanOption.NOT_DEFINED_FALSE;

	@Option(name = "--gui", aliases = { "-g" }, usage = "usage_MergeGuiTool")
	void setGui(@SuppressWarnings("unused") boolean on) {
		gui = BooleanOption.TRUE;
	}

	@Option(name = "--no-gui", usage = "usage_noGui")
	void noGui(@SuppressWarnings("unused") boolean on) {
		gui = BooleanOption.FALSE;
	}

	@Argument(required = false, index = 0, metaVar = "metaVar_paths")
	@Option(name = "--", metaVar = "metaVar_paths", handler = RestOfArgumentsHandler.class)
	protected List<String> filterPaths;

	@Override
	protected void init(Repository repository, String gitDir) {
		super.init(repository, gitDir);
		mergeToolMgr = new MergeToolManager(repository);
	}

	enum MergeResult {
		SUCCESSFUL, FAILED, ABORT
	}

	@Override
	protected void run() {
		try {
			if (toolHelp) {
				showToolHelp();
			} else {
				boolean showPrompt = mergeToolMgr.isPrompt();
				if (prompt.isDefined()) {
					showPrompt = prompt.toBoolean();
				}
				String toolNameToUse = getToolNameToUse();
				Map<String, StageState> files = getFiles();
				if (files.size() > 0) {
					merge(files, showPrompt, toolNameToUse);
				} else {
				}
			}
			outw.flush();
		} catch (Exception e) {
			throw die(e.getMessage(), e);
		}
	}

	private String getToolNameToUse() throws IOException {
		String toolNameToUse = toolName;
		if ((toolNameToUse == null) || toolNameToUse.isEmpty()) {
			toolNameToUse = mergeToolMgr.getDefaultToolName(gui);
		}
		if ((toolNameToUse == null) || toolNameToUse.isEmpty()) {
			outw.println(
			outw.println(
			outw.println(
			Map<String, IMergeTool> predefTools = mergeToolMgr
					.getPredefinedTools(false);
			for (String name : predefTools.keySet()) {
			}
			outw.println();
			outw.flush();
			toolNameToUse = mergeToolMgr.getFirstAvailableTool();
		}
		if ((toolNameToUse == null) || toolNameToUse.isEmpty()) {
		}
		return toolNameToUse;
	}

	private void merge(Map<String, StageState> files, boolean showPrompt,
			String toolNamePrompt) throws Exception {
		List<String> mergedFilePaths = new ArrayList<>(files.keySet());
		Collections.sort(mergedFilePaths);
		for (String mergedFilePath : mergedFilePaths) {
			outw.println(mergedFilePath);
		}
		outw.flush();
		MergeResult mergeResult = MergeResult.SUCCESSFUL;
		for (String mergedFilePath : mergedFilePaths) {
			if (mergeResult == MergeResult.FAILED) {
				if (!isContinueUnresolvedPaths()) {
					mergeResult = MergeResult.ABORT;
				}
			}
			if (mergeResult == MergeResult.ABORT) {
				break;
			}
			StageState fileState = files.get(mergedFilePath);
			if (fileState == StageState.BOTH_MODIFIED) {
				mergeResult = mergeModified(mergedFilePath, showPrompt,
						toolNamePrompt);
			} else if ((fileState == StageState.DELETED_BY_US) || (fileState == StageState.DELETED_BY_THEM)) {
				mergeResult = mergeDeleted(mergedFilePath,
						fileState == StageState.DELETED_BY_US);
			} else {
				outw.println(
				mergeResult = MergeResult.ABORT;
			}
		}
	}

	private MergeResult mergeModified(final String mergedFilePath,
			final boolean showPrompt, final String toolNamePrompt)
			throws Exception {
		outw.flush();
		boolean launch = true;
		if (showPrompt) {
			launch = isLaunch(toolNamePrompt);
		}
		if (!launch) {
		}
		boolean isMergeSuccessful = true;
		ContentSource baseSource = ContentSource.create(db.newObjectReader());
		ContentSource localSource = ContentSource.create(db.newObjectReader());
		ContentSource remoteSource = ContentSource.create(db.newObjectReader());
		File tempDir = mergeToolMgr.createTempDirectory();
		File tempFilesParent = tempDir != null ? tempDir : db.getWorkTree();
		try {
			FileElement base = null;
			FileElement local = null;
			FileElement remote = null;
			FileElement merged = new FileElement(mergedFilePath,
					Type.MERGED);
			DirCache cache = db.readDirCache();
			try (RevWalk revWalk = new RevWalk(db);
					TreeWalk treeWalk = new TreeWalk(db,
							revWalk.getObjectReader())) {
				treeWalk.setFilter(
						PathFilterGroup.createFromStrings(mergedFilePath));
				DirCacheIterator cacheIter = new DirCacheIterator(cache);
				treeWalk.addTree(cacheIter);
				while (treeWalk.next()) {
					final EolStreamType eolStreamType = treeWalk
							.getEolStreamType(CHECKOUT_OP);
					final String filterCommand = treeWalk.getFilterCommand(
							Constants.ATTR_FILTER_TYPE_SMUDGE);
					WorkingTreeOptions opt = db.getConfig()
							.get(WorkingTreeOptions.KEY);
					CheckoutMetadata checkoutMetadata = new CheckoutMetadata(
							eolStreamType, filterCommand);
					DirCacheEntry entry = treeWalk.getTree(DirCacheIterator.class).getDirCacheEntry();
					ObjectId id = entry.getObjectId();
					switch (entry.getStage()) {
					case DirCacheEntry.STAGE_1:
						base = new FileElement(mergedFilePath, Type.BASE);
						DirCacheCheckout.checkoutToFile(db, mergedFilePath,
								checkoutMetadata,
								baseSource.open(mergedFilePath, id), db.getFS(),
								opt, base.createTempFile(tempFilesParent));
						break;
					case DirCacheEntry.STAGE_2:
						local = new FileElement(mergedFilePath, Type.LOCAL);
						DirCacheCheckout.checkoutToFile(db, mergedFilePath,
								checkoutMetadata,
								localSource.open(mergedFilePath, id),
								db.getFS(), opt,
								local.createTempFile(tempFilesParent));
						break;
					case DirCacheEntry.STAGE_3:
						remote = new FileElement(mergedFilePath, Type.REMOTE);
						DirCacheCheckout.checkoutToFile(db, mergedFilePath,
								checkoutMetadata,
								remoteSource.open(mergedFilePath, id),
								db.getFS(), opt,
								remote.createTempFile(tempFilesParent));
						break;
					}
				}
			}
			if ((local == null) || (remote == null)) {
				throw die(
						"local or remote cannot be found in cache, stopping at " //$NON-NLS-1$
								+ mergedFilePath);
			}
			long modifiedBefore = merged.getFile().lastModified();
			try {
				ExecutionResult executionResult = mergeToolMgr.merge(local,
						remote, merged, base, tempDir, toolName, prompt, gui);
				outw.println(
						new String(executionResult.getStdout().toByteArray()));
				outw.flush();
				errw.println(
						new String(executionResult.getStderr().toByteArray()));
				errw.flush();
			} catch (ToolException e) {
				isMergeSuccessful = false;
				outw.println(e.getResultStdout());
				outw.flush();
				errw.flush();
				if (e.isCommandExecutionError()) {
					errw.println(e.getMessage());
					throw die("excution error", //$NON-NLS-1$
							e);
				}
			}
			if (isMergeSuccessful) {
				long modifiedAfter = merged.getFile().lastModified();
				if (modifiedBefore == modifiedAfter) {
					isMergeSuccessful = isMergeSuccessful();
				}
			}
			if (isMergeSuccessful) {
				addFile(mergedFilePath);
			}
		} finally {
			baseSource.close();
			localSource.close();
			remoteSource.close();
		}
		return isMergeSuccessful ? MergeResult.SUCCESSFUL : MergeResult.FAILED;
	}

	private MergeResult mergeDeleted(final String mergedFilePath,
			final boolean deletedByUs) throws Exception {
		if (deletedByUs) {
		} else {
		}
		int mergeDecision = getDeletedMergeDecision();
		if (mergeDecision == 1) {
			addFile(mergedFilePath);
		} else if (mergeDecision == -1) {
			rmFile(mergedFilePath);
		} else {
			return MergeResult.ABORT;
		}
		return MergeResult.SUCCESSFUL;
	}

	private void addFile(String fileName) throws Exception {
		try (Git git = new Git(db)) {
			git.add().addFilepattern(fileName).call();
		}
	}

	private void rmFile(String fileName) throws Exception {
		try (Git git = new Git(db)) {
			git.rm().addFilepattern(fileName).call();
		}
	}

	private boolean hasUserAccepted(final String message)
			throws IOException {
		boolean yes = true;
		outw.print(message);
		outw.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader(ins));
		String line = null;
		while ((line = br.readLine()) != null) {
				yes = true;
				break;
				yes = false;
				break;
			}
			outw.print(message);
			outw.flush();
		}
		return yes;
	}

	private boolean isContinueUnresolvedPaths() throws IOException {
		return hasUserAccepted(
	}

	private boolean isMergeSuccessful() throws IOException {
	}

	private boolean isLaunch(String toolNamePrompt)
			throws IOException {
		boolean launch = true;
		outw.print(message);
		outw.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader(ins));
		String line = null;
		if ((line = br.readLine()) != null) {
				launch = false;
			}
		}
		return launch;
	}

	private int getDeletedMergeDecision()
			throws IOException {
		final String message = "Use (m)odified or (d)eleted file, or (a)bort? "; //$NON-NLS-1$
		outw.print(message);
		outw.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader(ins));
		String line = null;
		while ((line = br.readLine()) != null) {
				break;
				break;
				break;
			}
			outw.print(message);
			outw.flush();
		}
		return ret;
	}

	private void showToolHelp() throws IOException {
		outw.println(
		Map<String, IMergeTool> predefTools = mergeToolMgr
				.getPredefinedTools(true);
		for (String name : predefTools.keySet()) {
			if (predefTools.get(name).isAvailable()) {
			}
		}
		Map<String, IMergeTool> userTools = mergeToolMgr.getUserDefinedTools();
		for (String name : userTools.keySet()) {
					+ userTools.get(name).getCommand());
		}
		outw.println(
				"The following tools are valid, but not currently available:"); //$NON-NLS-1$
		for (String name : predefTools.keySet()) {
			if (!predefTools.get(name).isAvailable()) {
			}
		}
		outw.println(
				"environment. If run in a terminal-only session, they will fail."); //$NON-NLS-1$
		return;
	}

	private Map<String, StageState> getFiles()
			throws RevisionSyntaxException, NoWorkTreeException,
			GitAPIException {
		Map<String, StageState> files = new TreeMap<>();
		try (Git git = new Git(db)) {
			StatusCommand statusCommand = git.status();
			if (filterPaths != null && filterPaths.size() > 0) {
				for (String path : filterPaths) {
					statusCommand.addPath(path);
				}
			}
			org.eclipse.jgit.api.Status status = statusCommand.call();
			files = status.getConflictingStageState();
		}
		return files;
	}
