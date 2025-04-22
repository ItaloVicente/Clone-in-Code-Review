    private final Git git;
    private final String sourceBranch;
    private final String targetBranch;
    private final String commonAncestorCommitId;
    private final String mergeCommitId;

    public RevertMerge(final Git git,
                       final String sourceBranch,
                       final String targetBranch,
                       final String commonAncestorCommitId,
                       final String mergeCommitId) {
        this.git = checkNotNull("git",
                                git);
        this.sourceBranch = checkNotEmpty("sourceBranch",
                                          sourceBranch);
        this.targetBranch = checkNotEmpty("targetBranch",
                                          targetBranch);
        this.commonAncestorCommitId = checkNotEmpty("commonAncestorCommitId",
                                                    commonAncestorCommitId);
        this.mergeCommitId = checkNotEmpty("mergeCommitId",
                                           mergeCommitId);
    }

    public boolean execute() {
        BranchUtil.existsBranch(git,
                                sourceBranch);
        BranchUtil.existsBranch(git,
                                targetBranch);

        final RevCommit lastSourceCommit = git.getLastCommit(sourceBranch);
        final RevCommit lastTargetCommit = git.getLastCommit(targetBranch);

        boolean isDone = false;

        if (canRevert(lastSourceCommit,
                      lastTargetCommit)) {

            git.commit(targetBranch,
                       MessageCommitInfo.createRevertMergeMessage(sourceBranch),
                       false,
                       lastTargetCommit.getParent(0),
                       new RevertCommitContent(targetBranch));

            final RevCommit newLastTargetCommit = git.getLastCommit(targetBranch);

            final List<RevCommit> parents = Stream.of(lastSourceCommit,
                                                      newLastTargetCommit).collect(Collectors.toList());

            final Map<String, File> contents = git.mapDiffContent(targetBranch,
                                                                  lastTargetCommit.getName(),
                                                                  newLastTargetCommit.getName());

            git.commit(sourceBranch,
                       MessageCommitInfo.createMergeMessage(targetBranch),
                       false,
                       lastSourceCommit,
                       new MergeCommitContent(contents,
                                              parents));

            git.commit(sourceBranch,
                       MessageCommitInfo.createFixMergeReversionMessage(),
                       false,
                       git.getLastCommit(sourceBranch).getParent(0),
                       new RevertCommitContent(sourceBranch));

            isDone = true;
        }

        return isDone;
    }

    private boolean canRevert(final RevCommit lastSourceCommit,
                              final RevCommit lastTargetCommit) {
        return lastTargetCommit.getParentCount() > 1 &&
                lastTargetCommit.getName().equals(mergeCommitId) &&
                lastTargetCommit.getParent(0).getName().equals(commonAncestorCommitId) &&
                lastTargetCommit.getParent(1).getName().equals(lastSourceCommit.getName());
    }
