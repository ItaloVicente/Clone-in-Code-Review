    private final FS fs;

    private final File gitDir;

    private final File workTree;

    private final DiffToolConfig config;

    private final Map<String

    private final Map<String

    public DiffToolManager(Repository db) {
        this(db.getFS()
    }

    public DiffToolManager(FS fs
            StoredConfig userConfig) {
        this.fs = fs;
        this.gitDir = gitDir;
        this.workTree = workTree;
        this.config = userConfig.get(DiffToolConfig.KEY);
        predefinedTools = setupPredefinedTools();
        userDefinedTools = setupUserDefinedTools(config
    }

    public Optional<ExecutionResult> compare(FileElement localFile
            FileElement remoteFile
            Optional<Boolean> prompt
            Optional<Boolean> trustExitCode
            PromptContinueHandler promptHandler
            InformNoToolHandler noToolHandler) throws ToolException {

        String toolNameToUse;

        if (toolName.isPresent()) {
            toolNameToUse = toolName.get();
        } else {
            toolNameToUse = getDefaultToolName(gui);

            if(toolNameToUse == null || toolNameToUse.isEmpty()) {
                noToolHandler.inform(new ArrayList<>(predefinedTools.keySet()));
                toolNameToUse = getFirstAvailableTool();
            }
        }

        @SuppressWarnings("boxing")
        boolean doPrompt = prompt.orElse(isPrompt());

        if(doPrompt) {
            if (!promptHandler.prompt(toolNameToUse)) {
                return Optional.empty();
            }
        }

        @SuppressWarnings("boxing")
        boolean trust = trustExitCode.orElse(config.isTrustExitCode());

        return Optional.of(
                compare(localFile
    }

    public ExecutionResult compare(FileElement localFile
            FileElement remoteFile
            IDiffTool tool
            throws ToolException {
        try {
            String command = Utils.prepareCommand(
                    tool.getCommand()
                    remoteFile
            Map<String
                    localFile
                    remoteFile
                    null

            CommandExecutor cmdExec = new CommandExecutor(fs
            return cmdExec.run(command

        } catch (IOException | InterruptedException e) {
            throw new ToolException(e);
        } finally {
            localFile.cleanTemporaries();
            remoteFile.cleanTemporaries();
        }
    }

    public Set<String> getUserDefinedToolNames() {
        return userDefinedTools.keySet();
    }

    public Set<String> getPredefinedToolNames() {
        return predefinedTools.keySet();
    }

    public Set<String> getAllToolNames() {
        String defaultName = getDefaultToolName(false);
        if (defaultName == null) {
            defaultName = getFirstAvailableTool();
        }
        return Utils.createSortedToolSet(defaultName
                getPredefinedToolNames());
    }

    public Map<String
        return userDefinedTools;
    }

    public Map<String
            boolean checkAvailability) {
        if (checkAvailability) {
            for (IDiffTool tool : predefinedTools.values()) {
                PreDefinedDiffTool predefTool = (PreDefinedDiffTool) tool;
                predefTool.setAvailable(
                        Utils.isToolAvailable(fs
                                predefTool.getPath()));
            }
        }
        return predefinedTools;
    }

    public String getFirstAvailableTool() {
        for (IDiffTool tool : predefinedTools.values()) {
            if (Utils.isToolAvailable(fs
                return tool.getName();
            }
        }

        return null;
    }

    public String getDefaultToolName(boolean gui) {
        return gui ? config.getDefaultGuiToolName()
                : config.getDefaultToolName();
    }

    public boolean isPrompt() {
        return config.isPrompt();
    }

    private IDiffTool getTool(final String name) {
        IDiffTool tool = userDefinedTools.get(name);
        if (tool == null) {
            tool = predefinedTools.get(name);
        }
        return tool;
    }

    private Map<String
        Map<String
        for (PreDefinedDiffTools tool : PreDefinedDiffTools.values()) {
            tools.put(tool.name()
                    tool.getPath()
        }
        return tools;
    }

    private Map<String
            Map<String
        Map<String
        Map<String
        for (String name : userTools.keySet()) {
            IDiffTool userTool = userTools.get(name);
            if (userTool.getCommand() != null) {
                tools.put(name
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
