  public enum MergeFailureReason {
    DIRTY_INDEX
    DIRTY_WORKTREE
    COULD_NOT_DELETE
  }

  protected NameConflictTreeWalk tw;

  protected String commitNames[];

  protected static final int T_BASE = 0;

  protected static final int T_OURS = 1;

  protected static final int T_THEIRS = 2;

  protected static final int T_INDEX = 3;

  protected static final int T_FILE = 4;

  protected DirCacheBuilder builder;

  protected ObjectId resultTree;

  protected List<String> unmergedPaths = new ArrayList<>();

  protected List<String> modifiedFiles = new LinkedList<>();

  protected Map<String

  protected List<String> toBeDeleted = new ArrayList<>();

  protected Map<String

  protected Map<String

  protected boolean enterSubtree;

  protected boolean inCore;

  protected boolean implicitDirCache;

  protected DirCache dircache;

  protected WorkingTreeIterator workingTreeIterator;

  protected MergeAlgorithm mergeAlgorithm;

  @Option(name = "--in-core-limit"
  private int inCoreLimit = 100;

  private static MergeAlgorithm getMergeAlgorithm(Config config) {
    SupportedAlgorithm diffAlg =
        config.getEnum(CONFIG_DIFF_SECTION
    return new MergeAlgorithm(DiffAlgorithm.getAlgorithm(diffAlg));
  }

  private static String[] defaultCommitNames() {
    return new String[] {"BASE"
  }

  protected ResolveMerger(Repository local
    super(local);
    mergeAlgorithm = getMergeAlgorithm(local.getConfig());
    commitNames = defaultCommitNames();
    this.inCore = inCore;

    if (inCore) {
      implicitDirCache = false;
      dircache = DirCache.newInCore();
    } else {
      implicitDirCache = true;
    }
  }

  protected ResolveMerger(Repository local) {
    this(local
  }

  protected ResolveMerger(ObjectInserter inserter
    super(inserter);
    mergeAlgorithm = getMergeAlgorithm(config);
    commitNames = defaultCommitNames();
    inCore = true;
    implicitDirCache = false;
    dircache = DirCache.newInCore();
  }

  @Override
  protected boolean mergeImpl() throws IOException {
    if (implicitDirCache) dircache = nonNullRepo().lockDirCache();

    try {
      return mergeTrees(mergeBase()
    } finally {
      if (implicitDirCache) dircache.unlock();
    }
  }

  private void checkout() throws NoWorkTreeException
    for (int i = toBeDeleted.size() - 1; i >= 0; i--) {
      String fileName = toBeDeleted.get(i);
      File f = new File(nonNullRepo().getWorkTree()
      if (!f.delete())
        if (!f.isDirectory()) failingPaths.put(fileName
      modifiedFiles.add(fileName);
    }
    for (Map.Entry<String
      DirCacheCheckout.checkoutEntry(db
      modifiedFiles.add(entry.getKey());
    }
  }

  protected void cleanUp() throws NoWorkTreeException
    if (inCore) {
      modifiedFiles.clear();
      return;
    }

    DirCache dc = nonNullRepo().readDirCache();
    Iterator<String> mpathsIt = modifiedFiles.iterator();
    while (mpathsIt.hasNext()) {
      String mpath = mpathsIt.next();
      DirCacheEntry entry = dc.getEntry(mpath);
      if (entry != null) DirCacheCheckout.checkoutEntry(db
      mpathsIt.remove();
    }
  }

  private DirCacheEntry add(byte[] path
    if (p != null && !p.getEntryFileMode().equals(FileMode.TREE)) {
      DirCacheEntry e = new DirCacheEntry(path
      e.setFileMode(p.getEntryFileMode());
      e.setObjectId(p.getEntryObjectId());
      e.setLastModified(lastMod);
      e.setLength(len);
      builder.add(e);
      return e;
    }
    return null;
  }

  private DirCacheEntry keep(DirCacheEntry e) {
    DirCacheEntry newEntry = new DirCacheEntry(e.getPathString()
    newEntry.setFileMode(e.getFileMode());
    newEntry.setObjectId(e.getObjectId());
    newEntry.setLastModified(e.getLastModified());
    newEntry.setLength(e.getLength());
    builder.add(newEntry);
    return newEntry;
  }

  protected boolean processEntry(
      CanonicalTreeParser base
      CanonicalTreeParser ours
      CanonicalTreeParser theirs
      DirCacheBuildIterator index
      WorkingTreeIterator work
      boolean ignoreConflicts
      Attributes attributes)
      throws MissingObjectException
          IOException {
    enterSubtree = true;
    final int modeO = tw.getRawMode(T_OURS);
    final int modeT = tw.getRawMode(T_THEIRS);
    final int modeB = tw.getRawMode(T_BASE);

    if (modeO == 0 && modeT == 0 && modeB == 0)
      return true;

    if (isIndexDirty()) return false;

    DirCacheEntry ourDce = null;

    if (index == null || index.getDirCacheEntry() == null) {
      if (nonTree(modeO)) {
        ourDce = new DirCacheEntry(tw.getRawPath());
        ourDce.setObjectId(tw.getObjectId(T_OURS));
        ourDce.setFileMode(tw.getFileMode(T_OURS));
      }
    } else {
      ourDce = index.getDirCacheEntry();
    }

    if (nonTree(modeO) && nonTree(modeT) && tw.idEqual(T_OURS
      if (modeO == modeT) {
        keep(ourDce);
        return true;
      } else {
        int newMode = mergeFileModes(modeB
        if (newMode != FileMode.MISSING.getBits()) {
          if (newMode == modeO)
            keep(ourDce);
          else {
            if (isWorktreeDirty(work
            DirCacheEntry e = add(tw.getRawPath()
            toBeCheckedOut.put(tw.getPathString()
          }
          return true;
        } else {
          add(tw.getRawPath()
          add(tw.getRawPath()
          add(tw.getRawPath()
          unmergedPaths.add(tw.getPathString());
          mergeResults.put(tw.getPathString()
        }
        return true;
      }
    }

    if (modeB == modeT && tw.idEqual(T_BASE
      if (ourDce != null) keep(ourDce);
      return true;
    }

    if (modeB == modeO && tw.idEqual(T_BASE

      if (isWorktreeDirty(work
      if (nonTree(modeT)) {
        DirCacheEntry e = add(tw.getRawPath()
        if (e != null) toBeCheckedOut.put(tw.getPathString()
        return true;
      } else {
        if (tw.getTreeCount() > T_FILE && tw.getRawMode(T_FILE) == 0) return true;
        toBeDeleted.add(tw.getPathString());
        return true;
      }
    }

    if (tw.isSubtree()) {
      if (nonTree(modeO) && !nonTree(modeT)) {
        if (nonTree(modeB)) add(tw.getRawPath()
        add(tw.getRawPath()
        unmergedPaths.add(tw.getPathString());
        enterSubtree = false;
        return true;
      }
      if (nonTree(modeT) && !nonTree(modeO)) {
        if (nonTree(modeB)) add(tw.getRawPath()
        add(tw.getRawPath()
        unmergedPaths.add(tw.getPathString());
        enterSubtree = false;
        return true;
      }

      if (!nonTree(modeO)) return true;

    }

    if (nonTree(modeO) && nonTree(modeT)) {
      if (isWorktreeDirty(work

      if (isGitLink(modeO) || isGitLink(modeT) || !attributes.canBeContentMerged()) {
        add(tw.getRawPath()
        add(tw.getRawPath()
        add(tw.getRawPath()
        unmergedPaths.add(tw.getPathString());
        return true;
      }

      MergeResult<RawText> result = contentMerge(base
      if (ignoreConflicts) {
        result.setContainsConflicts(false);
      }
      updateIndex(base
      if (result.containsConflicts() && !ignoreConflicts) unmergedPaths.add(tw.getPathString());
      modifiedFiles.add(tw.getPathString());
    } else if (modeO != modeT) {
      if (((modeO != 0 && !tw.idEqual(T_BASE
          || (modeT != 0 && !tw.idEqual(T_BASE

        add(tw.getRawPath()
        add(tw.getRawPath()
        DirCacheEntry e = add(tw.getRawPath()

        if (modeO == 0) {
          if (isWorktreeDirty(work
          if (nonTree(modeT)) {
            if (e != null) toBeCheckedOut.put(tw.getPathString()
          }
        }

        unmergedPaths.add(tw.getPathString());

        mergeResults.put(tw.getPathString()
      }
    }
    return true;
  }

  private MergeResult<RawText> contentMerge(
      CanonicalTreeParser base
      throws IOException {
    RawText baseText =
        base == null ? RawText.EMPTY_TEXT : getRawText(base.getEntryObjectId()
    RawText ourText =
        ours == null ? RawText.EMPTY_TEXT : getRawText(ours.getEntryObjectId()
    RawText theirsText =
        theirs == null ? RawText.EMPTY_TEXT : getRawText(theirs.getEntryObjectId()
    return (mergeAlgorithm.merge(RawTextComparator.DEFAULT
  }

  private boolean isIndexDirty() {
    if (inCore) return false;

    final int modeI = tw.getRawMode(T_INDEX);
    final int modeO = tw.getRawMode(T_OURS);

    final boolean isDirty = nonTree(modeI) && !(modeO == modeI && tw.idEqual(T_INDEX
    if (isDirty) failingPaths.put(tw.getPathString()
    return isDirty;
  }

  private boolean isWorktreeDirty(WorkingTreeIterator work
      throws IOException {
    if (work == null) return false;

    final int modeF = tw.getRawMode(T_FILE);
    final int modeO = tw.getRawMode(T_OURS);

    boolean isDirty;
    if (ourDce != null) isDirty = work.isModified(ourDce
    else {
      isDirty = work.isModeDifferent(modeO);
      if (!isDirty && nonTree(modeF)) isDirty = !tw.idEqual(T_FILE
    }

    if (isDirty && modeF == FileMode.TYPE_TREE && modeO == FileMode.TYPE_MISSING) isDirty = false;
    if (isDirty) failingPaths.put(tw.getPathString()
    return isDirty;
  }

  private void updateIndex(
      CanonicalTreeParser base
      CanonicalTreeParser ours
      CanonicalTreeParser theirs
      MergeResult<RawText> result)
      throws FileNotFoundException
    File mergedFile = !inCore ? writeMergedFile(result) : null;

    if (result.containsConflicts()) {
      add(tw.getRawPath()
      add(tw.getRawPath()
      add(tw.getRawPath()
      mergeResults.put(tw.getPathString()
      return;
    }

    DirCacheEntry dce = new DirCacheEntry(tw.getPathString());

    int newMode = mergeFileModes(tw.getRawMode(0)
    dce.setFileMode(
        newMode == FileMode.MISSING.getBits() ? FileMode.REGULAR_FILE : FileMode.fromBits(newMode));
    if (mergedFile != null) {
      long len = mergedFile.length();
      dce.setLastModified(FS.DETECTED.lastModified(mergedFile));
      dce.setLength((int) len);
      InputStream is = new FileInputStream(mergedFile);
      try {
        dce.setObjectId(getObjectInserter().insert(OBJ_BLOB
      } finally {
        is.close();
      }
    } else dce.setObjectId(insertMergeResult(result));
    builder.add(dce);
  }

  private File writeMergedFile(MergeResult<RawText> result)
      throws FileNotFoundException
    File workTree = nonNullRepo().getWorkTree();
    FS fs = nonNullRepo().getFS();
    File of = new File(workTree
    File parentFolder = of.getParentFile();
    if (!fs.exists(parentFolder)) parentFolder.mkdirs();
    try (OutputStream os = new BufferedOutputStream(new FileOutputStream(of))) {
      new MergeFormatter().formatMerge(os
    }
    return of;
  }

  private ObjectId insertMergeResult(MergeResult<RawText> result) throws IOException {
    TemporaryBuffer.LocalFile buf =
        new TemporaryBuffer.LocalFile(
            db != null ? nonNullRepo().getDirectory() : null
    try {
      new MergeFormatter().formatMerge(buf
      buf.close();
      try (InputStream in = buf.openInputStream()) {
        return getObjectInserter().insert(OBJ_BLOB
      }
    } finally {
      buf.destroy();
    }
  }

  private int mergeFileModes(int modeB
    if (modeO == modeT) return modeO;
    if (modeB == modeO)
      return (modeT == FileMode.MISSING.getBits()) ? modeO : modeT;
    if (modeB == modeT)
      return (modeO == FileMode.MISSING.getBits()) ? modeT : modeO;
    return FileMode.MISSING.getBits();
  }

  private static RawText getRawText(ObjectId id
    if (id.equals(ObjectId.zeroId())) return new RawText(new byte[] {});
    return new RawText(reader.open(id
  }

  private static boolean nonTree(final int mode) {
    return mode != 0 && !FileMode.TREE.equals(mode);
  }

  private static boolean isGitLink(final int mode) {
    return FileMode.GITLINK.equals(mode);
  }

  @Override
  public ObjectId getResultTreeId() {
    return (resultTree == null) ? null : resultTree.toObjectId();
  }

  public void setCommitNames(String[] commitNames) {
    this.commitNames = commitNames;
  }

  public String[] getCommitNames() {
    return commitNames;
  }

  public List<String> getUnmergedPaths() {
    return unmergedPaths;
  }

  public List<String> getModifiedFiles() {
    return modifiedFiles;
  }

  public Map<String
    return toBeCheckedOut;
  }

  public Map<String
    return mergeResults;
  }

  public Map<String
    return (failingPaths.size() == 0) ? null : failingPaths;
  }

  public boolean failed() {
    return failingPaths.size() > 0;
  }

  public void setDirCache(DirCache dc) {
    this.dircache = dc;
    implicitDirCache = false;
  }

  public void setWorkingTreeIterator(WorkingTreeIterator workingTreeIterator) {
    this.workingTreeIterator = workingTreeIterator;
  }

  protected boolean mergeTrees(
      AbstractTreeIterator baseTree
      throws IOException {

    builder = dircache.builder();
    DirCacheBuildIterator buildIt = new DirCacheBuildIterator(builder);

    tw = new NameConflictTreeWalk(db
    tw.addTree(baseTree);
    tw.addTree(headTree);
    tw.addTree(mergeTree);
    int dciPos = tw.addTree(buildIt);
    if (workingTreeIterator != null) {
      tw.addTree(workingTreeIterator);
      workingTreeIterator.setDirCacheIterator(tw
    } else {
      tw.setFilter(TreeFilter.ANY_DIFF);
    }

    if (!mergeTreeWalk(tw
      return false;
    }

    if (!inCore) {
      checkout();

      if (!builder.commit()) {
        cleanUp();
        throw new IndexWriteException();
      }
      builder = null;

    } else {
      builder.finish();
      builder = null;
    }

    if (getUnmergedPaths().isEmpty() && !failed()) {
      resultTree = dircache.writeTree(getObjectInserter());
      return true;
    } else {
      resultTree = null;
      return false;
    }
  }

  protected boolean mergeTreeWalk(TreeWalk treeWalk
    boolean hasWorkingTreeIterator = tw.getTreeCount() > T_FILE;
    boolean hasAttributeNodeProvider = treeWalk.getAttributesNodeProvider() != null;
    while (treeWalk.next()) {
      if (!processEntry(
          treeWalk.getTree(T_BASE
          treeWalk.getTree(T_OURS
          treeWalk.getTree(T_THEIRS
          treeWalk.getTree(T_INDEX
          hasWorkingTreeIterator ? treeWalk.getTree(T_FILE
          ignoreConflicts
          hasAttributeNodeProvider ? treeWalk.getAttributes() : new Attributes())) {
        cleanUp();
        return false;
      }
      if (treeWalk.isSubtree() && enterSubtree) treeWalk.enterSubtree();
    }
    return true;
  }
