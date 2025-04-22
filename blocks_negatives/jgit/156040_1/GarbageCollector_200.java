    public void execute() {
        try {
            if (!(git.getRepository().getRefDatabase() instanceof RefTreeDatabase)) {
                git._gc().call();
            }
        } catch (GitAPIException | JGitInternalException e) {
            if (this.logger.isDebugEnabled()) {
                this.logger.error("Garbage collector can't perform this operation right now, please try it later.",
                                  e);
            }
        }
    }
