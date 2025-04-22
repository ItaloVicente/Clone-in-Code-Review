package org.eclipse.jgit.util;

import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.api.errors.FilterFailedException;
import org.eclipse.jgit.attributes.Attributes;
import org.eclipse.jgit.attributes.FilterCommand;
import org.eclipse.jgit.attributes.FilterCommandRegistry;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.dircache.DirCacheCheckout.CheckoutMetadata;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.errors.BinaryBlobException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IndexWriteException;
import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.CoreConfig.EolStreamType;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ObjectStream;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.merge.ResolveMerger.MergeFailureReason;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.TreeWalk.OperationType;
import org.eclipse.jgit.treewalk.WorkingTreeOptions;
import org.eclipse.jgit.util.FS.ExecutionResult;
import org.eclipse.jgit.util.LfsFactory.LfsInputStream;
import org.eclipse.jgit.util.TemporaryBuffer.LocalFile;
import org.eclipse.jgit.util.io.EolStreamTypeUtil;

public class InCoreHandler {

  @Nullable
  protected final Repository repo;
  protected final ObjectInserter inserter;
  protected ObjectReader reader;
  private final boolean inCore;
  protected final boolean shouldUpdateIndex;
  protected DirCache dirCache = null;
  String smudgeCommand;
  protected WorkingTreeOptions workingTreeOptions;
  protected List<String> modifiedFiles = new LinkedList<>();
  protected Map<String
  private Map<String

  private Map<String

  InCoreHandler(Repository repo
      boolean shouldUpdateIndex
    this.repo = repo;
    this.inserter = oi;
    this.reader = reader;
    this.inCore = inCore;
    this.shouldUpdateIndex = shouldUpdateIndex;
    if (!inCore) {
      workingTreeOptions = repo.getConfig().get(WorkingTreeOptions.KEY);
      this.smudgeCommand = smudgeCommand;
      checkoutMetadata = new HashMap<>();
      cleanupMetadata = new HashMap<>();
    }
  }

  public interface StreamSupplier {

    InputStream load() throws IOException;
  }

  public class StreamLoader extends ObjectLoader {

    private StreamSupplier data;

    private long size;


    public StreamLoader(StreamSupplier data
      this.data = data;
      this.size = length;
    }

    @Override
    public int getType() {
      return Constants.OBJ_BLOB;
    }

    @Override
    public long getSize() {
      return size;
    }

    @Override
    public boolean isLarge() {
      return true;
    }

    @Override
    public byte[] getCachedBytes() throws LargeObjectException {
      throw new LargeObjectException();
    }

    @Override
    public ObjectStream openStream()
        throws IOException {
      return new ObjectStream.Filter(getType()
          new BufferedInputStream(data.load()));
    }
  }

  public StreamLoader getStreamLoader(StreamSupplier supplier
    return new StreamLoader(supplier
  }

  public DirCache getCache() throws IOException {
    if(dirCache==null) {
      if (inCore) {
        dirCache = DirCache.newInCore();
      } else {
        dirCache = repo.readDirCache();
      }
    }
    return dirCache;
  }

  public RawText getRawText(TreeWalk walk
      StreamSupplier fileStreamSupplier
      throws IOException
    if (loaded) {
      String command = walk.getFilterCommand(
          Constants.ATTR_FILTER_TYPE_CLEAN);
      try (InputStream input = filterClean(repo
          fileStreamSupplier.load()
        return new RawText(
            IO.readWholeStream(input
      }
    }
    if (convertCrLf) {
      try (InputStream input = EolStreamTypeUtil.wrapInputStream(
          new FileInputStream(f)
        return new RawText(IO.readWholeStream(input
      }
    }
    if (inCore && fileId.equals(ObjectId.zeroId())) {
      return new RawText(new byte[]{});
    }
    return new RawText(f);
  }

  private InputStream filterClean(Repository repository
      InputStream fromFile
      throws IOException {
    InputStream input = fromFile;
    if (convertCrLf) {
      input = EolStreamTypeUtil.wrapInputStream(input
          EolStreamType.TEXT_LF);
    }
    if (StringUtils.isEmptyOrNull(filterCommand)) {
      return input;
    }
    if (FilterCommandRegistry.isRegistered(filterCommand)) {
      LocalFile buffer = new TemporaryBuffer.LocalFile(null);
      FilterCommand command = FilterCommandRegistry.createFilterCommand(
          filterCommand
      while (command.run() != -1) {
      }
      return buffer.openInputStreamWithAutoDestroy();
    }
    FS fs = repository.getFS();
    ProcessBuilder filterProcessBuilder = fs.runInShell(filterCommand
        new String[0]);
    filterProcessBuilder.directory(repository.getWorkTree());
    filterProcessBuilder.environment().put(Constants.GIT_DIR_KEY
        repository.getDirectory().getAbsolutePath());
    ExecutionResult result;
    try {
      result = fs.execute(filterProcessBuilder
    } catch (IOException | InterruptedException e) {
      throw new IOException(
          new FilterFailedException(e
    }
    int rc = result.getRc();
    if (rc != 0) {
      throw new IOException(new FilterFailedException(rc
          path
          .decode(result.getStderr().toByteArray(4096))));
    }
    return result.getStdout().openInputStreamWithAutoDestroy();
  }


  public ObjectId finishBuilding(DirCacheBuilder builder)
      throws IOException {
    if (!inCore) {
      checkout();

      if (!builder.commit()) {
        cleanUp();
        throw new IndexWriteException();
      }
      return null;

    }
    builder.finish();
    return getCache().writeTree(inserter);
  }

  public void addToCheckout(String path
      Attributes cleanupAttributes
      throws IOException {
    if (!shouldUpdateIndex) {
      return;
    }
    toBeCheckedOut.put(path
    addCheckoutMetadata(cleanupMetadata
    addCheckoutMetadata(checkoutMetadata
  }

  public void addDeletion(String path
      Attributes attributes) throws IOException {
    if (!shouldUpdateIndex) {
      return;
    }
    if (isFile) {
      addCheckoutMetadata(cleanupMetadata
    }
  }

  protected void addCheckoutMetadata(Map<String
      String path
      throws IOException {
    if (inCore || map == null) {
      return;
    }
    EolStreamType eol = EolStreamTypeUtil.detectStreamType(
        OperationType.CHECKOUT_OP
        attributes);
    CheckoutMetadata data = new CheckoutMetadata(eol
        smudgeCommand);
    map.put(path
  }

  private void checkout() throws NoWorkTreeException
    for (Map.Entry<String
        .entrySet()) {
      DirCacheEntry cacheEntry = entry.getValue();
      if (cacheEntry.getFileMode() == FileMode.GITLINK) {
        new File(nonNullRepo().getWorkTree()
      } else {
        DirCacheCheckout.checkoutEntry(repo
            checkoutMetadata.get(entry.getKey()));
        modifiedFiles.add(entry.getKey());
      }
    }
  }

  protected void cleanUp() throws NoWorkTreeException
      IOException {
    if (inCore) {
      modifiedFiles.clear();
      return;
    }

    Iterator<String> mpathsIt = modifiedFiles.iterator();
    while (mpathsIt.hasNext()) {
      String mpath = mpathsIt.next();
      DirCacheEntry entry = getCache().getEntry(mpath);
      if (entry != null) {
        DirCacheCheckout.checkoutEntry(repo
            cleanupMetadata.get(mpath));
      }
      mpathsIt.remove();
    }
  }

  public void update(StreamLoader resultStreamLoader
      CheckoutMetadata checkoutMetadata
      FileMode fileMode)
      throws IOException {
    updateFile(resultStreamLoader
    updateIndex(builder
  }

  private void updateFile(StreamLoader resultStreamLoader
      CheckoutMetadata checkoutMetadata
      throws FileNotFoundException
      IOException {
    if (inCore) {
      return;
    }
    try {
      if (safeWrite) {
        TemporaryBuffer buffer = new TemporaryBuffer.LocalFile(null);
        try {
          OutputStream outputStream = buffer;
          DirCacheCheckout.getContent(repo
              outputStream);
          InputStream bufIn = buffer.openInputStream();
          Files.copy(bufIn
              StandardCopyOption.REPLACE_EXISTING);
        } finally {
          if (buffer != null) {
            buffer.destroy();
          }
        }
      } else {
        OutputStream outputStream = new FileOutputStream(f);
        DirCacheCheckout.getContent(repo
            outputStream);
      }
    } finally {
    }
  }

  private void updateIndex(DirCacheBuilder builder
      File f
    DirCacheEntry dce = new DirCacheEntry(path);

    dce.setFileMode(fileMode == FileMode.MISSING
        ? FileMode.REGULAR_FILE : fileMode);
    if (!inCore) {
      dce.setLastModified(repo.getFS().lastModifiedInstant(f));
      dce.setLength((int) f.length());
    }
    dce.setObjectId(
        insertResult(resultStreamLoader
    builder.add(dce);
  }

  private ObjectId insertResult(StreamLoader resultStreamLoader
      Attributes attributes) throws IOException {
    try (LfsInputStream is = LfsFactory.getInstance().applyCleanFilter(
        repo
        resultStreamLoader.size
        attributes != null ? attributes.get(Constants.ATTR_MERGE) : null)) {
      return inserter.insert(OBJ_BLOB
    }
  }

  public Repository nonNullRepo() {
    if (repo == null) {
      throw new NullPointerException(JGitText.get().repositoryIsRequired);
    }
    return repo;
  }
}
