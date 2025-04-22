    public CherryPick(final Git git,
                      final String targetBranch,
                      final String... commits) {
        this.git = git;
        this.targetBranch = targetBranch;
        this.commits = commits;
    }
