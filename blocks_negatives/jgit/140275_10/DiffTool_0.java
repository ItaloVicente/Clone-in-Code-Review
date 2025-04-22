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

	private BooleanOption prompt = BooleanOption.DEFAULT_FALSE;

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

	private BooleanOption gui = BooleanOption.DEFAULT_FALSE;

	@Option(name = "--gui", aliases = { "-g" }, usage = "usage_DiffGuiTool")
	void setGui(@SuppressWarnings("unused") boolean on) {
		gui = BooleanOption.TRUE;
	}

	@Option(name = "--no-gui", usage = "usage_noGui")
	void noGui(@SuppressWarnings("unused") boolean on) {
		gui = BooleanOption.FALSE;
	}

	private BooleanOption trustExitCode = BooleanOption.DEFAULT_FALSE;

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
				if (prompt.isConfigured()) {
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
			Map<String, ExternalDiffTool> predefTools = diffToolMgr
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
