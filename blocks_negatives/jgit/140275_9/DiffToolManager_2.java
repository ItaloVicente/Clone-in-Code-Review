	private final Repository db;

	private final DiffToolConfig config;

	private final Map<String, IDiffTool> predefinedTools;

	private final Map<String, IDiffTool> userDefinedTools;

	/**
	 * @param db the repository database
	 */
	public DiffToolManager(Repository db) {
		this.db = db;
		config = db.getConfig().get(DiffToolConfig.KEY);
		predefinedTools = setupPredefinedTools();
		userDefinedTools = setupUserDefinedTools(config, predefinedTools);
	}

	/**
	 * @param localFile
	 *            the local file element
	 * @param remoteFile
	 *            the remote file element
	 * @param mergedFile
	 *            the merged file element, it's path equals local or remote
	 *            element path
	 * @param toolName
	 *            the selected tool name (can be null)
	 * @param prompt
	 *            the prompt option
	 * @param gui
	 *            the GUI option
	 * @param trustExitCode
	 *            the "trust exit code" option
	 * @return the execution result from tool
	 * @throws ToolException
	 */
	public ExecutionResult compare(FileElement localFile,
			FileElement remoteFile, FileElement mergedFile,
			String toolName, BooleanOption prompt,
			BooleanOption gui, BooleanOption trustExitCode)
			throws ToolException {
		try {
			String command = Utils.prepareCommand(
					guessTool(toolName, gui).getCommand(), localFile,
					remoteFile, mergedFile, null);
			Map<String, String> env = Utils.prepareEnvironment(db,
					localFile,
					remoteFile,
					mergedFile, null);
			boolean trust = config.isTrustExitCode();
			if (trustExitCode.isDefined()) {
				trust = trustExitCode.toBoolean();
			}
			CommandExecutor cmdExec = new CommandExecutor(db.getFS(), trust);
			return cmdExec.run(command, db.getWorkTree(), env);
		} catch (IOException | InterruptedException e) {
			throw new ToolException(e);
		} finally {
			localFile.cleanTemporaries();
			remoteFile.cleanTemporaries();
			mergedFile.cleanTemporaries();
		}
	}

	/**
	 * @return the tool names
	 */
	public Set<String> getToolNames() {
		return config.getToolNames();
	}

	/**
	 * @return the user defined tools
	 */
	public Map<String, IDiffTool> getUserDefinedTools() {
		return userDefinedTools;
	}

	/**
	 * @param checkAvailability
	 *            true: for checking if tools can be executed; ATTENTION: this
	 *            check took some time, do not execute often (store the map for
	 *            other actions); false: availability is NOT checked:
	 *            isAvailable() returns default false is this case!
	 * @return the predefined tools with optionally checked availability (long
	 *         running operation)
	 */
	public Map<String, IDiffTool> getPredefinedTools(
			boolean checkAvailability) {
		if (checkAvailability) {
			for (IDiffTool tool : predefinedTools.values()) {
				PreDefinedDiffTool predefTool = (PreDefinedDiffTool) tool;
				predefTool.setAvailable(
						Utils.isToolAvailable(db, predefTool.getPath()));
			}
		}
		return predefinedTools;
	}

	/**
	 * @return the name of first available predefined tool or null
	 */
	public String getFirstAvailableTool() {
		String name = null;
		for (IDiffTool tool : predefinedTools.values()) {
			if (Utils.isToolAvailable(db, tool.getPath())) {
				name = tool.getName();
				break;
			}
		}
		return name;
	}

	/**
	 * @param gui
	 *            use the diff.guitool setting ?
	 * @return the default tool name
	 */
	public String getDefaultToolName(BooleanOption gui) {
		return gui.toBoolean() ? config.getDefaultGuiToolName()
				: config.getDefaultToolName();
	}

	/**
	 * @return id prompt enabled?
	 */
	public boolean isPrompt() {
		return config.isPrompt();
	}

	private IDiffTool guessTool(String toolName, BooleanOption gui)
			throws ToolException {
		if ((toolName == null) || toolName.isEmpty()) {
			toolName = getDefaultToolName(gui);
		}
		IDiffTool tool = null;
		if ((toolName != null) && !toolName.isEmpty()) {
			tool = getTool(toolName);
		}
		if (tool == null) {
		}
		return tool;
	}

	private IDiffTool getTool(final String name) {
		IDiffTool tool = userDefinedTools.get(name);
		if (tool == null) {
			tool = predefinedTools.get(name);
		}
		return tool;
	}

	private Map<String, IDiffTool> setupPredefinedTools() {
		Map<String, IDiffTool> tools = new TreeMap<>();
		for (PreDefinedDiffTools tool : PreDefinedDiffTools.values()) {
			tools.put(tool.name(), new PreDefinedDiffTool(tool.name(),
					tool.getPath(), tool.getParameters()));
		}
		return tools;
	}

	private Map<String, IDiffTool> setupUserDefinedTools(DiffToolConfig cfg,
			Map<String, IDiffTool> predefTools) {
		Map<String, IDiffTool> tools = new TreeMap<>();
		Map<String, IDiffTool> userTools = cfg.getTools();
		for (String name : userTools.keySet()) {
			IDiffTool userTool = userTools.get(name);
			if (userTool.getCommand() != null) {
				tools.put(name, userTool);
			} else if (userTool.getPath() != null) {
				PreDefinedDiffTool predefTool = (PreDefinedDiffTool) predefTools
						.get(name);
				if (predefTool != null) {
					predefTool.setPath(userTool.getPath());
				}
			}
		}
		return tools;
	}
