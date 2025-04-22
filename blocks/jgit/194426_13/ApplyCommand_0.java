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
    ApplyPatchResult applyResult = patchApplier.applyPatch(in);
    for (File f : applyResult.appliedFiles) {
      r.addUpdatedFile(f);
    }
    return r;
  }
