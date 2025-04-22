    public Squash(final GitImpl git,
                  final String branch,
                  final String startCommitString,
                  final String squashedCommitMessage) {
        this.git = git;
        this.squashedCommitMessage = squashedCommitMessage;
        this.branch = branch;
        this.startCommitString = startCommitString;
    }
