    public PullCommand setRebase(boolean useRebase) {
        checkCallable();
        if (useRebase) {
            pullRebaseMode = PullRebaseMode.REBASE;
        } else {
            pullRebaseMode = PullRebaseMode.NO_REBASE;
        }
        return this;
    }

