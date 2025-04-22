    private MergeToolManager mergeToolMgr;

    private Optional<String> toolName = Optional.empty();

    @Option(name = "--tool"
            "-t" }
    void setToolName(String name) {
        toolName = Optional.of(name);
    }

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

    @Argument(required = false
    @Option(name = "--"
    protected List<String> filterPaths;

    @Override
    protected void init(Repository repository
        super.init(repository
        mergeToolMgr = new MergeToolManager(repository);
    }

    enum MergeResult {
        SUCCESSFUL
    }

    @Override
    protected void run() {
        try {
            if (toolHelp) {
                showToolHelp();
            } else {
                Map<String
                if (files.size() > 0) {
                    merge(files);
                } else {
                }
            }
            outw.flush();
        } catch (Exception e) {
            throw die(e.getMessage()
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

    private void merge(Map<String
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
                mergeResult = mergeModified(mergedFilePath);
            } else if ((fileState == StageState.DELETED_BY_US) || (fileState == StageState.DELETED_BY_THEM)) {
                mergeResult = mergeDeleted(mergedFilePath
                        fileState == StageState.DELETED_BY_US);
            } else {
                outw.println(
                mergeResult = MergeResult.ABORT;
            }
        }
    }

    private MergeResult mergeModified(final String mergedFilePath)
            throws Exception {
        outw.flush();

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
            FileElement merged = new FileElement(mergedFilePath
                    Type.MERGED
            DirCache cache = db.readDirCache();
            try (RevWalk revWalk = new RevWalk(db);
                    TreeWalk treeWalk = new TreeWalk(db
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
                            eolStreamType
                    DirCacheEntry entry = treeWalk.getTree(DirCacheIterator.class).getDirCacheEntry();
                    ObjectId id = entry.getObjectId();
                    switch (entry.getStage()) {
                    case DirCacheEntry.STAGE_1:
                        base = new FileElement(mergedFilePath
                        DirCacheCheckout.checkoutToFile(db
                                checkoutMetadata
                                baseSource.open(mergedFilePath
                                opt
                        break;
                    case DirCacheEntry.STAGE_2:
                        local = new FileElement(mergedFilePath
                        DirCacheCheckout.checkoutToFile(db
                                checkoutMetadata
                                localSource.open(mergedFilePath
                                db.getFS()
                                local.createTempFile(tempFilesParent));
                        break;
                    case DirCacheEntry.STAGE_3:
                        remote = new FileElement(mergedFilePath
                        DirCacheCheckout.checkoutToFile(db
                                checkoutMetadata
                                remoteSource.open(mergedFilePath
                                db.getFS()
                                remote.createTempFile(tempFilesParent));
                        break;
                    }
                }
            }
            if ((local == null) || (remote == null)) {
                throw die(
                        "local or remote cannot be found in cache
                                + mergedFilePath);
            }
            long modifiedBefore = merged.getFile().lastModified();
            try {
                Optional<ExecutionResult> optionalResult = mergeToolMgr.merge(
                        local
                        remote
                        this::promptForLaunch
                if (optionalResult.isPresent()) {
                    ExecutionResult result = optionalResult.get();
                    outw.println(new String(
                            result.getStdout().toByteArray()));
                    outw.flush();
                    errw.println(new String(
                            result.getStderr().toByteArray()));
                    errw.flush();
                } else {
                    return MergeResult.ABORT;
                }
            } catch (ToolException e) {
                isMergeSuccessful = false;
                outw.println(e.getResultStdout());
                outw.flush();
                errw.println(e.getMessage());
                errw.flush();
                if (e.isCommandExecutionError()) {
                    throw die("excution error"
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

    private MergeResult mergeDeleted(final String mergedFilePath
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

    private boolean promptForLaunch(String toolNamePrompt) {
        try {
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
        } catch (IOException e) {
            throw new IllegalStateException("Cannot output text"
        }
    }

    private int getDeletedMergeDecision()
            throws IOException {
        final String message = "Use (m)odified or (d)eleted file
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
        Map<String
                .getPredefinedTools(true);
        for (String name : predefTools.keySet()) {
            if (predefTools.get(name).isAvailable()) {
            }
        }
        Map<String
        for (String name : userTools.keySet()) {
                    + userTools.get(name).getCommand());
        }
        outw.println(
                "The following tools are valid
        for (String name : predefTools.keySet()) {
            if (!predefTools.get(name).isAvailable()) {
            }
        }
        outw.println(
                "environment. If run in a terminal-only session
        return;
    }

    private Map<String
            throws RevisionSyntaxException
            GitAPIException {
        Map<String
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
