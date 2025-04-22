	private DiffFormatter diffFmt;

	private DiffToolManager diffToolMgr;

	@Argument(index = 0, metaVar = "metaVar_treeish")
	private AbstractTreeIterator oldTree;

	@Argument(index = 1, metaVar = "metaVar_treeish")
	private AbstractTreeIterator newTree;

	@Option(name = "--tool", aliases = {
			"-t" }, metaVar = "metaVar_tool", usage = "usage_ToolForDiff")
	private String toolName;

	@Option(name = "--cached", aliases = { "--staged" }, usage = "usage_cached")
	private boolean cached;

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

	@Option(name = "--gui", aliases = { "-g" }, usage = "usage_DiffGuiTool")
	void setGui(@SuppressWarnings("unused") boolean on) {
		gui = BooleanOption.TRUE;
	}

	@Option(name = "--no-gui", usage = "usage_noGui")
	void noGui(@SuppressWarnings("unused") boolean on) {
		gui = BooleanOption.FALSE;
	}

	private BooleanOption trustExitCode = BooleanOption.NOT_DEFINED_FALSE;

	@Option(name = "--trust-exit-code", usage = "usage_trustExitCode")
	void setTrustExitCode(@SuppressWarnings("unused") boolean on) {
		trustExitCode = BooleanOption.TRUE;
	}

	@Option(name = "--no-trust-exit-code", usage = "usage_noTrustExitCode")
	void noTrustExitCode(@SuppressWarnings("unused") boolean on) {
		trustExitCode = BooleanOption.FALSE;
	}

	@Option(name = "--", metaVar = "metaVar_paths", handler = PathTreeFilterHandler.class)
	private TreeFilter pathFilter = TreeFilter.ALL;

	@Override
	protected void init(Repository repository, String gitDir) {
		super.init(repository, gitDir);
		diffFmt = new DiffFormatter(new BufferedOutputStream(outs));
		diffToolMgr = new DiffToolManager(repository);
	}

	@Override
	protected void run() {
		try {
			if (toolHelp) {
				showToolHelp();
			} else {
				boolean showPrompt = diffToolMgr.isPrompt();
				if (prompt.isDefined()) {
					showPrompt = prompt.toBoolean();
				}
				String toolNameToUse = getToolNameToUse();
				List<DiffEntry> files = getFiles();
				if (files.size() > 0) {
					compare(files, showPrompt, toolNameToUse);
				}
			}
		} catch (RevisionSyntaxException | IOException e) {
			throw die(e.getMessage(), e);
		} finally {
			diffFmt.close();
		}
	}

	private String getToolNameToUse() throws IOException {
		String toolNameToUse = toolName;
		if ((toolNameToUse == null) || toolNameToUse.isEmpty()) {
			toolNameToUse = diffToolMgr.getDefaultToolName(gui);
		}
		if ((toolNameToUse == null) || toolNameToUse.isEmpty()) {
			outw.println(
			outw.println(
			outw.println(
			Map<String, IDiffTool> predefTools = diffToolMgr
					.getPredefinedTools(false);
			for (String name : predefTools.keySet()) {
			}
			outw.println();
			outw.flush();
			toolNameToUse = diffToolMgr.getFirstAvailableTool();
		}
		if ((toolNameToUse == null) || toolNameToUse.isEmpty()) {
		}
		return toolNameToUse;
	}

	private void compare(List<DiffEntry> files, boolean showPrompt,
			String toolNameToUse) throws IOException {
		ContentSource.Pair sourcePair = new ContentSource.Pair(source(oldTree),
				source(newTree));
		try {
			for (int fileIndex = 0; fileIndex < files.size(); fileIndex++) {
				DiffEntry ent = files.get(fileIndex);
				String mergedFilePath = ent.getNewPath();
				if (mergedFilePath.equals(DiffEntry.DEV_NULL)) {
					mergedFilePath = ent.getOldPath();
				}
				boolean launchCompare = true;
				if (showPrompt) {
					launchCompare = isLaunchCompare(fileIndex + 1, files.size(),
							mergedFilePath, toolNameToUse);
				}
				if (launchCompare) {
					try {
						FileElement local = createFileElement(
								FileElement.Type.LOCAL, sourcePair, Side.OLD,
								ent);
						FileElement remote = createFileElement(
								FileElement.Type.REMOTE, sourcePair, Side.NEW,
								ent);
						FileElement merged = new FileElement(mergedFilePath,
								FileElement.Type.MERGED);
						ExecutionResult result = diffToolMgr.compare(local,
								remote, merged, toolNameToUse, prompt, gui,
								trustExitCode);
						outw.println(new String(result.getStdout().toByteArray()));
						outw.flush();
						errw.println(
								new String(result.getStderr().toByteArray()));
						errw.flush();
					} catch (ToolException e) {
						outw.println(e.getResultStdout());
						outw.flush();
						errw.println(e.getMessage());
						throw die("external diff died, stopping at " //$NON-NLS-1$
								+ mergedFilePath, e);
					}
				} else {
					break;
				}
			}
		} finally {
			sourcePair.close();
		}
	}

	private boolean isLaunchCompare(int fileIndex, int fileCount,
			String fileName, String toolNamePrompt) throws IOException {
		boolean launchCompare = true;
		outw.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader(ins));
		String line = null;
		if ((line = br.readLine()) != null) {
				launchCompare = false;
			}
		}
		return launchCompare;
	}

	private void showToolHelp() throws IOException {
		outw.println(
		Map<String, IDiffTool> predefTools = diffToolMgr
				.getPredefinedTools(true);
		for (String name : predefTools.keySet()) {
			if (predefTools.get(name).isAvailable()) {
			}
		}
		Map<String, IDiffTool> userTools = diffToolMgr.getUserDefinedTools();
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
		outw.flush();
		return;
	}

	private List<DiffEntry> getFiles()
			throws RevisionSyntaxException, AmbiguousObjectException,
			IncorrectObjectTypeException, IOException {
		diffFmt.setRepository(db);
		if (cached) {
			if (oldTree == null) {
				if (head == null) {
					die(MessageFormat.format(CLIText.get().notATree, HEAD));
				}
				CanonicalTreeParser p = new CanonicalTreeParser();
				try (ObjectReader reader = db.newObjectReader()) {
					p.reset(reader, head);
				}
				oldTree = p;
			}
			newTree = new DirCacheIterator(db.readDirCache());
		} else if (oldTree == null) {
			oldTree = new DirCacheIterator(db.readDirCache());
			newTree = new FileTreeIterator(db);
		} else if (newTree == null) {
			newTree = new FileTreeIterator(db);
		}

		TextProgressMonitor pm = new TextProgressMonitor(errw);
		pm.setDelayStart(2, TimeUnit.SECONDS);
		diffFmt.setProgressMonitor(pm);
		diffFmt.setPathFilter(pathFilter);

		List<DiffEntry> files = diffFmt.scan(oldTree, newTree);
		return files;
	}

	private FileElement createFileElement(FileElement.Type elementType,
			Pair pair, Side side, DiffEntry entry)
			throws NoWorkTreeException, CorruptObjectException, IOException,
			ToolException {
		String entryPath = side == Side.NEW ? entry.getNewPath()
				: entry.getOldPath();
		FileElement fileElement = new FileElement(entryPath, elementType);
		if (!pair.isWorkingTreeSource(side) && !fileElement.isNullPath()) {
			try (RevWalk revWalk = new RevWalk(db);
					TreeWalk treeWalk = new TreeWalk(db,
							revWalk.getObjectReader())) {
				treeWalk.setFilter(
						PathFilterGroup.createFromStrings(entryPath));
				if (side == Side.NEW) {
					newTree.reset();
					treeWalk.addTree(newTree);
				} else {
					oldTree.reset();
					treeWalk.addTree(oldTree);
				}
				if (treeWalk.next()) {
					final EolStreamType eolStreamType = treeWalk
							.getEolStreamType(CHECKOUT_OP);
					final String filterCommand = treeWalk.getFilterCommand(
							Constants.ATTR_FILTER_TYPE_SMUDGE);
					WorkingTreeOptions opt = db.getConfig()
							.get(WorkingTreeOptions.KEY);
					CheckoutMetadata checkoutMetadata = new CheckoutMetadata(
							eolStreamType, filterCommand);
					DirCacheCheckout.checkoutToFile(db, entryPath,
							checkoutMetadata, pair.open(side, entry),
							db.getFS(), opt, fileElement.createTempFile(null));
				} else {
							+ "' in staging area!", null); //$NON-NLS-1$
				}
			}
		}
		return fileElement;
	}

	private ContentSource source(AbstractTreeIterator iterator) {
		if (iterator instanceof WorkingTreeIterator)
			return ContentSource.create((WorkingTreeIterator) iterator);
		return ContentSource.create(db.newObjectReader());
	}
