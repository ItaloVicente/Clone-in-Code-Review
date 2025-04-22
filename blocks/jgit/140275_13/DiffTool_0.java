    private DiffFormatter diffFmt;

    private DiffToolManager diffToolMgr;

    @Argument(index = 0
    private AbstractTreeIterator oldTree;

    @Argument(index = 1
    private AbstractTreeIterator newTree;

    private Optional<String> toolName = Optional.empty();

    @Option(name = "--tool"
            "-t" }
    void setToolName(String name) {
        toolName = Optional.of(name);
    }

    @Option(name = "--cached"
    private boolean cached;

    private Optional<Boolean> prompt = Optional.empty();

    @Option(name = "--prompt"
    void setPrompt(@SuppressWarnings("unused") boolean on) {
        prompt = Optional.of(Boolean.TRUE);
    }

    @Option(name = "--no-prompt"
    void noPrompt(@SuppressWarnings("unused") boolean on) {
        prompt = Optional.of(Boolean.FALSE);
    }

    @Option(name = "--tool-help"
    private boolean toolHelp;

    private boolean gui = false;

    @Option(name = "--gui"
    void setGui(@SuppressWarnings("unused") boolean on) {
        gui = true;
    }

    @Option(name = "--no-gui"
    void noGui(@SuppressWarnings("unused") boolean on) {
        gui = false;
    }

    private Optional<Boolean> trustExitCode = Optional.empty();

    @Option(name = "--trust-exit-code"
    void setTrustExitCode(@SuppressWarnings("unused") boolean on) {
        trustExitCode = Optional.of(Boolean.TRUE);
    }

    @Option(name = "--no-trust-exit-code"
    void noTrustExitCode(@SuppressWarnings("unused") boolean on) {
        trustExitCode = Optional.of(Boolean.FALSE);
    }

    @Option(name = "--"
    private TreeFilter pathFilter = TreeFilter.ALL;

    @Override
    protected void init(Repository repository
        super.init(repository
        diffFmt = new DiffFormatter(new BufferedOutputStream(outs));
        diffToolMgr = new DiffToolManager(repository);
    }

    @Override
    protected void run() {
        try {
            if (toolHelp) {
                showToolHelp();
            } else {
                List<DiffEntry> files = getFiles();
                if (files.size() > 0) {
                    compare(files);
                }
            }
        } catch (RevisionSyntaxException | IOException e) {
            throw die(e.getMessage()
        } finally {
            diffFmt.close();
        }
    }

    private void informUserNoTool(List<String> tools) {
        try {
            outw.println(
            outw.println(
            outw.println(

            for (String name : tools) {
            }
            outw.println();
            outw.flush();
        } catch (IOException e) {
            throw new IllegalStateException("Cannot output text"
        }
    }

    private class CountingPromptContinueHandler implements PromptContinueHandler {
        private final int fileIndex;

        private final int fileCount;

        private final String fileName;

        public CountingPromptContinueHandler(int fileIndex
                String fileName) {
            this.fileIndex = fileIndex;
            this.fileCount = fileCount;
            this.fileName = fileName;
        }

        @Override
        public boolean prompt(String toolToLaunchName) {
            try {
                boolean launchCompare = true;
                outw.flush();
                BufferedReader br = new BufferedReader(new InputStreamReader(ins));
                String line = null;
                if ((line = br.readLine()) != null) {
                        launchCompare = false;
                    }
                }
                return launchCompare;
            } catch (IOException e) {
                throw new IllegalStateException("Cannot output text"
            }
        }
    }

    private void compare(List<DiffEntry> files) throws IOException {
        ContentSource.Pair sourcePair = new ContentSource.Pair(source(oldTree)
                source(newTree));
        try {
            for (int fileIndex = 0; fileIndex < files.size(); fileIndex++) {
                DiffEntry ent = files.get(fileIndex);

                String filePath = ent.getNewPath();
                if (filePath.equals(DiffEntry.DEV_NULL)) {
                    filePath = ent.getOldPath();
                }

                try {
                    FileElement local = createFileElement(
                            FileElement.Type.LOCAL
                    FileElement remote = createFileElement(
                            FileElement.Type.REMOTE

                    PromptContinueHandler promptContinueHandler = new CountingPromptContinueHandler(
                            fileIndex + 1

                    Optional<ExecutionResult> optionalResult = diffToolMgr
                            .compare(local
                                    trustExitCode
                                    this::informUserNoTool);

                    if (optionalResult.isPresent()) {
                        ExecutionResult result = optionalResult.get();
                        outw.println(
                                new String(result.getStdout().toByteArray()));
                        outw.flush();
                        errw.println(
                                new String(result.getStderr().toByteArray()));
                        errw.flush();
                    }
                } catch (ToolException e) {
                    outw.println(e.getResultStdout());
                    outw.flush();
                    errw.println(e.getMessage());
                    errw.flush();
                    throw die("external diff died
                            + filePath
                }
            }
        } finally {
            sourcePair.close();
        }
    }

    private void showToolHelp() throws IOException {
        outw.println(
