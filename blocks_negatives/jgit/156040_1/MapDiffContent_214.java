    private final Git git;
    private final String branch;
    private final String startCommitId;
    private final String endCommitId;

    public MapDiffContent(final Git git,
                          final String branch,
                          final String startCommitId,
                          final String endCommitId) {
        this.git = checkNotNull("git",
                                git);
        this.branch = checkNotEmpty("branch",
                                    branch);
        this.startCommitId = checkNotEmpty("startCommitId",
                                           startCommitId);
        this.endCommitId = checkNotEmpty("endCommitId",
                                         endCommitId);
    }

    public Map<String, File> execute() {
        BranchUtil.existsBranch(git,
                                branch);

        final RevCommit startCommit = git.getCommit(startCommitId);
        final RevCommit endCommit = git.getCommit(endCommitId);

        if (startCommit == null || endCommit == null) {
            throw new GitException("Given commit ids cannot be found.");
        }

        Map<String, File> content = new HashMap<>();

        final List<DiffEntry> diffs = git.listDiffs(startCommit.getTree(),
                                                    endCommit.getTree());

        diffs.forEach(entry -> {
            if (entry.getChangeType() != DiffEntry.ChangeType.DELETE) {
                try (final InputStream inputStream = git.blobAsInputStream(branch,
                                                                           entry.getNewPath())) {
                    final File file = File.createTempFile("gitz",
                                                          "woot");

                    Files.copy(inputStream,
                               file.toPath(),
                               StandardCopyOption.REPLACE_EXISTING);

                    content.put(entry.getNewPath(),
                                file);
                } catch (IOException e) {
                    throw new GitException("Unable to get content from diffs", e);
                }
            } else {
                content.put(entry.getOldPath(),
                            null);
            }
        });

        return content;
    }
