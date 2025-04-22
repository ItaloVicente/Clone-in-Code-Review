public class ResolveMerger extends ThreeWayMerger implements InCoreSupport {

  public enum MergeFailureReason {
    DIRTY_INDEX
    DIRTY_WORKTREE
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

  protected List<String> unmergedPaths = new ArrayList<>();

  protected Map<String

  protected Map<String

  protected boolean enterSubtree;

  protected boolean inCore;

  protected DirCache dircache;

  protected WorkingTreeIterator workingTreeIterator;

  protected MergeAlgorithm mergeAlgorithm;

  @NonNull
  private ContentMergeStrategy contentStrategy = ContentMergeStrategy.CONFLICT;

  private static MergeAlgorithm getMergeAlgorithm(Config config) {
    SupportedAlgorithm diffAlg = config.getEnum(
        CONFIG_DIFF_SECTION
        HISTOGRAM);
    return new MergeAlgorithm(DiffAlgorithm.getAlgorithm(diffAlg));
  }

  private static String[] defaultCommitNames() {
    return new String[]{"BASE"
  }

  private static final Attributes NO_ATTRIBUTES = new Attributes();

  protected ResolveMerger(Repository local
    super(local);
    Config config = local.getConfig();
    mergeAlgorithm = getMergeAlgorithm(config);
    commitNames = defaultCommitNames();
    this.inCore = inCore;
  }

  protected ResolveMerger(Repository local) {
    this(local
  }

  protected ResolveMerger(ObjectInserter inserter
    super(inserter);
    mergeAlgorithm = getMergeAlgorithm(config);
    commitNames = defaultCommitNames();
    inCore = true;
  }

  @NonNull
  public ContentMergeStrategy getContentMergeStrategy() {
    return contentStrategy;
  }

  public void setContentMergeStrategy(ContentMergeStrategy strategy) {
    contentStrategy = strategy == null ? ContentMergeStrategy.CONFLICT
        : strategy;
  }

  @Override
  protected boolean mergeImpl() throws IOException {
    return mergeTrees(mergeBase()
        false);
  }


  private DirCacheEntry add(byte[] path
      Instant lastMod
    if (p != null && !p.getEntryFileMode().equals(FileMode.TREE)) {
      return ioHandler.addExistingToIndex(p.getEntryObjectId()
          p.getEntryFileMode()
          lastMod
    }
    return null;
  }

  private DirCacheEntry keep(DirCacheEntry e) {
    return ioHandler.addExistingToIndex(e.getObjectId()
        e.getStage()
  }

  protected void addToCheckout(String path
      Attributes[] attributes)
      throws IOException {
    String cleanupSmudgeCommand = tw.getSmudgeCommand(attributes[T_OURS]);
    String checkoutSmudgeCommand = tw.getSmudgeCommand(attributes[T_THEIRS]);
    ioHandler.addToCheckout(path
        attributes[T_THEIRS]
  }

  protected void addDeletion(String path
      Attributes attributes) throws IOException {
    File file =
        isFile && !nonNullRepo().isBare() ? new File(nonNullRepo().getWorkTree()
    String smudgeCommand = tw.getSmudgeCommand(attributes);
    ioHandler.deleteFile(path
  }

  protected boolean processEntry(CanonicalTreeParser base
      CanonicalTreeParser ours
      DirCacheBuildIterator index
      boolean ignoreConflicts
      throws IOException {
    enterSubtree = true;
    final int modeO = tw.getRawMode(T_OURS);
    final int modeT = tw.getRawMode(T_THEIRS);
    final int modeB = tw.getRawMode(T_BASE);
    boolean gitLinkMerging = isGitLink(modeO) || isGitLink(modeT)
        || isGitLink(modeB);
    if (modeO == 0 && modeT == 0 && modeB == 0)
    {
      return true;
    }

    if (isIndexDirty()) {
      return false;
    }

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
      }
      int newMode = mergeFileModes(modeB
      if (newMode != FileMode.MISSING.getBits()) {
        if (newMode == modeO) {
          keep(ourDce);
        } else {
          if (isWorktreeDirty(work
            return false;
          }
          DirCacheEntry e = add(tw.getRawPath()
              DirCacheEntry.STAGE_0
          addToCheckout(tw.getPathString()
        }
        return true;
      }
      if (!ignoreConflicts) {
        add(tw.getRawPath()
        add(tw.getRawPath()
        add(tw.getRawPath()
        unmergedPaths.add(tw.getPathString());
        mergeResults.put(tw.getPathString()
            new MergeResult<>(Collections.emptyList()));
      }
      return true;
    }

    if (modeB == modeT && tw.idEqual(T_BASE
      if (ourDce != null) {
        keep(ourDce);
      }
      return true;
    }

    if (modeB == modeO && tw.idEqual(T_BASE

      if (isWorktreeDirty(work
        return false;
      }
      if (nonTree(modeT)) {
        DirCacheEntry e = add(tw.getRawPath()
            DirCacheEntry.STAGE_0
        if (e != null) {
          addToCheckout(tw.getPathString()
        }
        return true;
      }
      if (tw.getTreeCount() > T_FILE && tw.getRawMode(T_FILE) == 0) {
        return true;
      }
      if (modeT != 0 && modeT == modeB) {
        return true;
      }
      addDeletion(tw.getPathString()
      return true;
    }

    if (tw.isSubtree()) {
      if (nonTree(modeO) != nonTree(modeT)) {
        if (ignoreConflicts) {
          enterSubtree = false;
          return true;
        }
        if (nonTree(modeB)) {
          add(tw.getRawPath()
        }
        if (nonTree(modeO)) {
          add(tw.getRawPath()
        }
        if (nonTree(modeT)) {
          add(tw.getRawPath()
        }
        unmergedPaths.add(tw.getPathString());
        enterSubtree = false;
        return true;
      }

      if (!nonTree(modeO)) {
        return true;
      }

    }

    if (nonTree(modeO) && nonTree(modeT)) {
      boolean worktreeDirty = isWorktreeDirty(work
      if (!attributes[T_OURS].canBeContentMerged() && worktreeDirty) {
        return false;
      }

      if (gitLinkMerging && ignoreConflicts) {
        add(tw.getRawPath()
        return true;
      } else if (gitLinkMerging) {
        add(tw.getRawPath()
        add(tw.getRawPath()
        add(tw.getRawPath()
        MergeResult<SubmoduleConflict> result = createGitLinksMergeResult(
            base
        result.setContainsConflicts(true);
        mergeResults.put(tw.getPathString()
        unmergedPaths.add(tw.getPathString());
        return true;
      } else if (!attributes[T_OURS].canBeContentMerged()) {
        switch (getContentMergeStrategy()) {
          case OURS:
            keep(ourDce);
            return true;
          case THEIRS:
            DirCacheEntry theirEntry = add(tw.getRawPath()
                DirCacheEntry.STAGE_0
            addToCheckout(tw.getPathString()
            return true;
          default:
            break;
        }
        add(tw.getRawPath()
        add(tw.getRawPath()
        add(tw.getRawPath()

        unmergedPaths.add(tw.getPathString());
        return true;
      }

      if (worktreeDirty) {
        return false;
      }

      MergeResult<RawText> result;
      try {
        result = contentMerge(base
            getContentMergeStrategy());
      } catch (BinaryBlobException e) {
        switch (getContentMergeStrategy()) {
          case OURS:
            keep(ourDce);
            return true;
          case THEIRS:
            DirCacheEntry theirEntry = add(tw.getRawPath()
                DirCacheEntry.STAGE_0
            addToCheckout(tw.getPathString()
            return true;
          default:
            result = new MergeResult<>(Collections.emptyList());
            result.setContainsConflicts(true);
            break;
        }
      }
      if (ignoreConflicts) {
        result.setContainsConflicts(false);
      }
      updateIndex(base
      String currentPath = tw.getPathString();
      if (result.containsConflicts() && !ignoreConflicts) {
        unmergedPaths.add(currentPath);
        ioHandler.markAsModified(currentPath);
      }
      addToCheckout(currentPath
    } else if (modeO != modeT) {
      if (((modeO != 0 && !tw.idEqual(T_BASE
          .idEqual(T_BASE
        if (gitLinkMerging && ignoreConflicts) {
          add(tw.getRawPath()
        } else if (gitLinkMerging) {
          add(tw.getRawPath()
          add(tw.getRawPath()
          add(tw.getRawPath()
          MergeResult<SubmoduleConflict> result = createGitLinksMergeResult(
              base
          result.setContainsConflicts(true);
          mergeResults.put(tw.getPathString()
          unmergedPaths.add(tw.getPathString());
        } else {
          MergeResult<RawText> result;
          try {
            result = contentMerge(base
                ContentMergeStrategy.CONFLICT);
          } catch (BinaryBlobException e) {
            result = new MergeResult<>(Collections.emptyList());
            result.setContainsConflicts(true);
          }
          if (ignoreConflicts) {
            result.setContainsConflicts(false);
            updateIndex(base
                attributes[T_OURS]);
          } else {
            add(tw.getRawPath()
                0);
            add(tw.getRawPath()
                0);
            DirCacheEntry e = add(tw.getRawPath()
                DirCacheEntry.STAGE_3

            if (modeO == 0) {
              if (isWorktreeDirty(work
                return false;
              }
              if (nonTree(modeT) && e != null) {
                addToCheckout(tw.getPathString()
                    attributes);
              }
            }

            unmergedPaths.add(tw.getPathString());

            mergeResults.put(tw.getPathString()
          }
        }
      }
    }
    return true;
  }

  private static MergeResult<SubmoduleConflict> createGitLinksMergeResult(
      CanonicalTreeParser base
      CanonicalTreeParser theirs) {
    return new MergeResult<>(Arrays.asList(
        new SubmoduleConflict(
            base == null ? null : base.getEntryObjectId())
        new SubmoduleConflict(
            ours == null ? null : ours.getEntryObjectId())
        new SubmoduleConflict(
            theirs == null ? null : theirs.getEntryObjectId())));
  }

  private MergeResult<RawText> contentMerge(CanonicalTreeParser base
      CanonicalTreeParser ours
      Attributes[] attributes
      throws BinaryBlobException
    RawText baseText = base == null ? RawText.EMPTY_TEXT
        : getRawText(base.getEntryObjectId()
    RawText ourText = ours == null ? RawText.EMPTY_TEXT
        : getRawText(ours.getEntryObjectId()
    RawText theirsText = theirs == null ? RawText.EMPTY_TEXT
        : getRawText(theirs.getEntryObjectId()
    mergeAlgorithm.setContentMergeStrategy(strategy);
    return mergeAlgorithm.merge(RawTextComparator.DEFAULT
        ourText
  }

  private boolean isIndexDirty() {
    if (inCore) {
      return false;
    }

    final int modeI = tw.getRawMode(T_INDEX);
    final int modeO = tw.getRawMode(T_OURS);

    final boolean isDirty = nonTree(modeI)
        && !(modeO == modeI && tw.idEqual(T_INDEX
    if (isDirty) {
      failingPaths
          .put(tw.getPathString()
    }
    return isDirty;
  }

  private boolean isWorktreeDirty(WorkingTreeIterator work
      DirCacheEntry ourDce) throws IOException {
    if (work == null) {
      return false;
    }

    final int modeF = tw.getRawMode(T_FILE);
    final int modeO = tw.getRawMode(T_OURS);

    boolean isDirty;
    if (ourDce != null) {
      isDirty = work.isModified(ourDce
    } else {
      isDirty = work.isModeDifferent(modeO);
      if (!isDirty && nonTree(modeF)) {
        isDirty = !tw.idEqual(T_FILE
      }
    }

    if (isDirty && modeF == FileMode.TYPE_TREE
        && modeO == FileMode.TYPE_MISSING) {
      isDirty = false;
    }
    if (isDirty) {
      failingPaths.put(tw.getPathString()
          MergeFailureReason.DIRTY_WORKTREE);
    }
    return isDirty;
  }

  private void updateIndex(CanonicalTreeParser base
      CanonicalTreeParser ours
      MergeResult<RawText> result
      throws IOException {
    TemporaryBuffer rawMerged = null;
    try {
      rawMerged = doMerge(result);
      File mergedFile = inCore ? null
          : writeMergedFile(rawMerged
      if (result.containsConflicts()) {
        add(tw.getRawPath()
        add(tw.getRawPath()
        add(tw.getRawPath()
        mergeResults.put(tw.getPathString()
        return;
      }

      Instant lastModified =
          mergedFile == null ? null : nonNullRepo().getFS().lastModifiedInstant(mergedFile);
      int length = mergedFile == null ? 0 : (int) mergedFile.length();
      int newMode = mergeFileModes(tw.getRawMode(0)
          tw.getRawMode(2));
      FileMode mode = newMode == FileMode.MISSING.getBits()
          ? FileMode.REGULAR_FILE : FileMode.fromBits(newMode);
      StreamLoader contentLoader = ioHandler.createStreamLoader(rawMerged::openInputStream
          rawMerged.length());
      ioHandler.insertToIndex(contentLoader
          DirCacheEntry.STAGE_0
    } finally {
      if (rawMerged != null) {
        rawMerged.destroy();
      }
    }
  }

  private File writeMergedFile(TemporaryBuffer rawMerged
      Attributes attributes)
      throws IOException {
    File workTree = nonNullRepo().getWorkTree();
    FS fs = nonNullRepo().getFS();
    File of = new File(workTree
    File parentFolder = of.getParentFile();
    if (!fs.exists(parentFolder)) {
      parentFolder.mkdirs();
    }
    StreamLoader contentLoader = ioHandler.createStreamLoader(rawMerged::openInputStream
        rawMerged.length());
    ioHandler.updateFileWithContent(contentLoader
        attributes
    return of;
  }

  private TemporaryBuffer doMerge(MergeResult<RawText> result)
      throws IOException {
    TemporaryBuffer.LocalFile buf = new TemporaryBuffer.LocalFile(
        db != null ? nonNullRepo().getDirectory() : null
    boolean success = false;
    try {
      new MergeFormatter().formatMerge(buf
          Arrays.asList(commitNames)
      buf.close();
      success = true;
    } finally {
      if (!success) {
        buf.destroy();
      }
    }
    return buf;
  }

  private int mergeFileModes(int modeB
    if (modeO == modeT) {
      return modeO;
    }
    if (modeB == modeO)
    {
      return (modeT == FileMode.MISSING.getBits()) ? modeO : modeT;
    }
    if (modeB == modeT)
    {
      return (modeO == FileMode.MISSING.getBits()) ? modeT : modeO;
    }
    return FileMode.MISSING.getBits();
  }

  private RawText getRawText(ObjectId id
      Attributes attributes)
      throws IOException
    if (id.equals(ObjectId.zeroId())) {
      return new RawText(new byte[]{});
    }

    ObjectLoader loader = LfsFactory.getInstance().applySmudgeFilter(
        getRepository()
        attributes.get(Constants.ATTR_MERGE));
    int threshold = PackConfig.DEFAULT_BIG_FILE_THRESHOLD;
    return RawText.load(loader
  }

  private static boolean nonTree(int mode) {
    return mode != 0 && !FileMode.TREE.equals(mode);
  }

  private static boolean isGitLink(int mode) {
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
    return ioHandler.getModifiedFiles();
  }

  public Map<String
    return ioHandler.getToBeCheckedOut();
  }

  public Map<String
    return mergeResults;
  }

  public Map<String
    return failingPaths.isEmpty() ? null : failingPaths;
  }

  public boolean failed() {
    return !failingPaths.isEmpty();
  }

  public void setDirCache(DirCache dc) {
    this.dircache = dc;
  }

  public void setWorkingTreeIterator(WorkingTreeIterator workingTreeIterator) {
    this.workingTreeIterator = workingTreeIterator;
  }


  protected boolean mergeTrees(AbstractTreeIterator baseTree
      RevTree headTree
      throws IOException {
    ioHandler =
        inCore ? InCoreSupport.initRepoIOHandlerForRemote(db
            InCoreSupport.initRepoIOHandlerForLocal(db
    dircache = ioHandler.getLockedDirCache();
    try {
      tw = new NameConflictTreeWalk(db

      tw.addTree(baseTree);
      tw.setHead(tw.addTree(headTree));
      tw.addTree(mergeTree);
      DirCacheBuildIterator buildIt = ioHandler.createDirCacheBuildIterator();
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

      ioHandler.finishBuilding(true);
      if (getUnmergedPaths().isEmpty() && !failed()) {
        resultTree = ioHandler.writeChangesAndCleanUp();
        List<String> failedToDelete = ioHandler.getFailedToDelete();
        for (String f : failedToDelete) {
          failingPaths.put(f
        }
        return failedToDelete.isEmpty();
      }
      resultTree = null;
      return false;
    } finally {
      ioHandler.cleanUpCache();
    }
  }

  protected boolean mergeTreeWalk(TreeWalk treeWalk
      throws IOException {
    boolean hasWorkingTreeIterator = tw.getTreeCount() > T_FILE;
    boolean hasAttributeNodeProvider = treeWalk
        .getAttributesNodeProvider() != null;
    while (treeWalk.next()) {
      Attributes[] attributes = {NO_ATTRIBUTES
          NO_ATTRIBUTES};
      if (hasAttributeNodeProvider) {
        attributes[T_BASE] = treeWalk.getAttributes(T_BASE);
        attributes[T_OURS] = treeWalk.getAttributes(T_OURS);
        attributes[T_THEIRS] = treeWalk.getAttributes(T_THEIRS);
      }
      if (!processEntry(
          treeWalk.getTree(T_BASE
          treeWalk.getTree(T_OURS
          treeWalk.getTree(T_THEIRS
          treeWalk.getTree(T_INDEX
          hasWorkingTreeIterator ? treeWalk.getTree(T_FILE
              WorkingTreeIterator.class) : null
          ignoreConflicts
        ioHandler.cleanUpChanges();
        return false;
      }
      if (treeWalk.isSubtree() && enterSubtree) {
        treeWalk.enterSubtree();
      }
    }
    return true;
  }
