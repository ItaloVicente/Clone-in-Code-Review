  private InputStream in;

  private boolean inCore = false;

  @Nullable
  protected final Repository db;

  protected ObjectReader reader;

  protected RevWalk revWalk;

  ApplyCommand(Repository local) {
    super(local);
    this.inCore = false;
    if (local == null) {
      throw new NullPointerException(JGitText.get().repositoryIsRequired);
    }
    db = local;
    ObjectInserter inserter = local.newObjectInserter();
    reader = inserter.newReader();
    revWalk = new RevWalk(reader);
  }

  ApplyCommand(Repository remote
    super(null);
    db = null;
    inCore = true;
    revWalk = base;
    reader = base.getObjectReader();
  }

  public ApplyCommand setPatch(InputStream in) {
    checkCallable();
    this.in = in;
    return this;
  }

  @Override
  public ApplyResult call() throws GitAPIException
      PatchApplyException {
    checkCallable();
    setCallable(false);
    ApplyResult r = new ApplyResult();
      Applier applier = new Applier(repo);
      applier.applyPatch(in);
    return r;
  }
