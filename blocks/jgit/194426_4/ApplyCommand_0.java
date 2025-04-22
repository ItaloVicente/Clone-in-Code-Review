  private InputStream in;

  ApplyCommand(Repository local) {
    super(local);
    if (local == null) {
      throw new NullPointerException(JGitText.get().repositoryIsRequired);
    }
  }

  public ApplyCommand setPatch(InputStream in) {
    checkCallable();
    this.in = in;
    return this;
  }

  @Override
  public ApplyResult call() throws GitAPIException
    checkCallable();
    setCallable(false);
    ApplyResult r = new ApplyResult();
    Applier applier = new Applier(repo);
    ObjectId treeId = applier.applyPatch(in);
    if (treeId == null) {
      return null;
    }
    return r;
  }
