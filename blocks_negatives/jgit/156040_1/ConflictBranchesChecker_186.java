    private final Git git;
    private final String branchA;
    private final String branchB;

    public ConflictBranchesChecker(final Git git,
                                   final String branchA,
                                   final String branchB) {
        this.git = checkNotNull("git",
                                git);
        this.branchA = checkNotEmpty("branchA",
                                     branchA);
        this.branchB = checkNotEmpty("branchB",
                                     branchB);
    }

    public List<String> execute() {
        BranchUtil.existsBranch(this.git,
                                this.branchA);

        BranchUtil.existsBranch(this.git,
                                this.branchB);

        List<String> result = new ArrayList<>();

        try {
            final RevCommit commitA = git.getLastCommit(branchA);
            final RevCommit commitB = git.getLastCommit(branchB);

            final RevCommit commonAncestor = git.getCommonAncestorCommit(branchA,
                                                                         branchB);

            ThreeWayMerger merger = MergeStrategy.RECURSIVE.newMerger(git.getRepository(),
                                                                      true);
            merger.setBase(commonAncestor);

            boolean canMerge = merger.merge(commitA,
                                            commitB);

            if (!canMerge) {
                ResolveMerger resolveMerger = (ResolveMerger) merger;
                Map<String, MergeResult<?>> mergeResults = resolveMerger.getMergeResults();
                result.addAll(mergeResults.keySet()
                                      .stream()
                                      .sorted(String::compareToIgnoreCase)
                                      .collect(Collectors.toList()));
            }
        } catch (IOException e) {
            throw new GitException(
                    String.format("Error when checking for conflicts between branches %s and %s: %s",
                                  this.branchA, this.branchB, e));
        }

        return result;
    }
