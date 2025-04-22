    private final Git git;
    private final String commitId;

    public GetCommit(final Git git,
                     final String commitId) {
        this.git = checkNotNull("git",
                                git);
        this.commitId = checkNotEmpty("commitId",
                                      commitId);
    }

    public RevCommit execute() {
        final Repository repository = git.getRepository();

        try (final RevWalk revWalk = new RevWalk(repository)) {
            final ObjectId id = repository.resolve(this.commitId);
            return id != null ? revWalk.parseCommit(id) : null;
        } catch (Exception e) {
            throw new GitException("Error when trying to get commit", e);
        }
    }
