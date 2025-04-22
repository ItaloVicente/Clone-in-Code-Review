    /**
     * It checks if the commit is present on branch logs. If not it throws a {@link GitException}
     * @param git The git repository
     * @param branch The branch where it is going to do the search
     * @param startCommitString The commit it needs to find
     * @throws {@link GitException} when it cannot find the commit in that branch
     */
    private RevCommit checkIfCommitIsPresentAtBranch(final GitImpl git,
                                                     final String branch,
                                                     final String startCommitString) {
