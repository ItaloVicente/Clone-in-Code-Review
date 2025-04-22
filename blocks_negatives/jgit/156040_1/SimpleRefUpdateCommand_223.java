    public SimpleRefUpdateCommand(final Git git,
                                  final String branchName,
                                  final RevCommit commit) {
        this.git = git;
        this.name = branchName;
        this.commit = commit;
    }
