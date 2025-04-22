    COULD_NOT_DELETE
  }

  protected NameConflictTreeWalk tw;

  protected String[] commitNames;

  protected static final int T_BASE = 0;

  protected static final int T_OURS = 1;

  protected static final int T_THEIRS = 2;

  protected static final int T_INDEX = 3;

  protected static final int T_FILE = 4;

  protected RepoIOHandler ioHandler;

  protected ObjectId resultTree;

