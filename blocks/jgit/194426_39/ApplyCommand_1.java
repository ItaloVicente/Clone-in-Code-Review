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
  public ApplyResult call() throws GitAPIException {
    checkCallable();
    setCallable(false);
    ApplyResult r = new ApplyResult();
    PatchApplier patchApplier = new PatchApplier(repo);
    Result applyResult = patchApplier.applyPatch(in);
    for (String p : applyResult.paths) {
      r.addUpdatedFile(new File(repo.getWorkTree()
    }
    return r;
  }
