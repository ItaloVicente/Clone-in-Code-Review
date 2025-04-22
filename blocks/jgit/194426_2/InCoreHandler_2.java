package org.eclipse.jgit.patch;

import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.InflaterInputStream;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.api.errors.PatchApplyException;
import org.eclipse.jgit.api.errors.PatchFormatException;
import org.eclipse.jgit.attributes.Attributes;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.dircache.DirCacheCheckout.CheckoutMetadata;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.BinaryBlobException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.CoreConfig.EolStreamType;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.patch.FileHeader.PatchType;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.TreeWalk.OperationType;
import org.eclipse.jgit.treewalk.filter.AndTreeFilter;
import org.eclipse.jgit.treewalk.filter.NotIgnoredFilter;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.InCoreHandler;
import org.eclipse.jgit.util.InCoreHandler.StreamLoader;
import org.eclipse.jgit.util.InCoreSupport;
import org.eclipse.jgit.util.LfsFactory;
import org.eclipse.jgit.util.RawParseUtils;
import org.eclipse.jgit.util.TemporaryBuffer;
import org.eclipse.jgit.util.io.BinaryDeltaInputStream;
import org.eclipse.jgit.util.io.BinaryHunkInputStream;
import org.eclipse.jgit.util.sha1.SHA1;

public class Applier implements InCoreSupport {

  private final boolean inCore;
  private InCoreHandler inCoreHandler;
  @Nullable
  protected final RevTree tip;

  @Nullable
  protected final Repository repo;

  protected final ObjectInserter inserter;
  protected ObjectReader reader;
  protected DirCacheBuilder builder;

  protected final boolean shouldUpdateIndex;

  protected Attributes attributes = new Attributes();

  public Applier(Repository local) {
    this(local
  }

  public Applier(Repository local
    this.inCore = false;
    if (local == null) {
      throw new NullPointerException(JGitText.get().repositoryIsRequired);
    }
    repo = local;
    inserter = local.newObjectInserter();
    reader = inserter.newReader();
    this.shouldUpdateIndex = shouldUpdateIndex;
  }

  public Applier(Repository remote
    repo = remote;
    inCore = true;
    this.tip = tip;
    inserter = oi;
    reader = oi.newReader();
    shouldUpdateIndex = true;
  }

  public ObjectId applyPatch(InputStream patchInput) throws PatchFormatException
      PatchApplyException {
    try {
      try {
        inCoreHandler = InCoreSupport.initInCoreHandler(repo
            shouldUpdateIndex
        final org.eclipse.jgit.patch.Patch p = new org.eclipse.jgit.patch.Patch();
        p.parse(patchInput);
        if (!p.getErrors().isEmpty()) {
          throw new PatchFormatException(p.getErrors());
        }
        DirCache cache = inCoreHandler.getCache();
        builder = cache.builder();
        for (org.eclipse.jgit.patch.FileHeader fh : p.getFiles()) {
          ChangeType type = fh.getChangeType();
          File f;
          switch (type) {
            case ADD:
              f = inCore ? null : getFile(fh.getNewPath()
              apply(fh.getNewPath()
              break;
            case MODIFY:
              f = inCore ? null : getFile(fh.getOldPath()
              apply(fh.getOldPath()
              break;
            case DELETE:
              f = getFile(fh.getOldPath()
              if (!f.delete()) {
                throw new PatchApplyException(MessageFormat.format(
                    JGitText.get().cannotDeleteFile
              }
              inCoreHandler.addDeletion(fh.getOldPath()
              break;
            case RENAME:
              f = inCore ? null : getFile(fh.getOldPath()
              File dest = getFile(fh.getNewPath()
              try {
                FileUtils.mkdirs(dest.getParentFile()
                FileUtils.rename(f
                    StandardCopyOption.ATOMIC_MOVE);
              } catch (IOException e) {
                throw new PatchApplyException(MessageFormat.format(
                    JGitText.get().renameFileFailed
              }
              apply(fh.getOldPath()
              break;
            case COPY:
              f = inCore ? null : getFile(fh.getOldPath()
              File target = getFile(fh.getNewPath()
              FileUtils.mkdirs(target.getParentFile()
              Files.copy(f.toPath()
              apply(fh.getOldPath()
          }
        }
      } finally {
        patchInput.close();
        builder = inCoreHandler.finishBuilding(builder);
        return inCoreHandler.writeCache();
      }
    } catch (IOException e) {
      throw new PatchApplyException(MessageFormat.format(
          JGitText.get().patchApplyException
    }
  }

  private File getFile(String path
      throws PatchApplyException {
    File f = new File(repo.getWorkTree()
    if (create) {
      try {
        File parent = f.getParentFile();
        FileUtils.mkdirs(parent
        FileUtils.createNewFile(f);
      } catch (IOException e) {
        throw new PatchApplyException(MessageFormat.format(
            JGitText.get().createNewFileFailed
      }
    }
    return f;
  }

  private void apply(String path
      File f
    if (PatchType.BINARY.equals(fh.getPatchType())) {
      return;
    }
    try {
      TreeWalk walk = inCore ? TreeWalk.forPath(repo
      boolean convertCrLf = false;
      int fileIdx = -1;
      if (!inCore) {
        int cacheIdx = walk.addTree(new DirCacheIterator(cache));
        FileTreeIterator files = new FileTreeIterator(repo);
        convertCrLf = needsCrLfConversion(f
        fileIdx = walk.addTree(files);
        walk.setFilter(AndTreeFilter.create(
            PathFilterGroup.createFromStrings(path)
            new NotIgnoredFilter(fileIdx)));
        walk.setOperationType(OperationType.CHECKIN_OP);
        walk.setRecursive(true);
        files.setDirCacheIterator(walk
      }

      boolean loaded = false;
      EolStreamType streamType = EolStreamType.DIRECT;
      String smudgeFilterCommand = null;
      InCoreHandler.StreamSupplier fileStreamSupplier = null;
      ObjectId fileId = ObjectId.zeroId();
      if (inCore && walk != null) {
        fileId = walk != null ? walk.getObjectId(0) : ObjectId.zeroId();
        ObjectLoader loader = LfsFactory.getInstance().applySmudgeFilter(
            repo
            null);
        fileStreamSupplier = () -> loader.openStream();
        loaded = loader != null && loader.getSize() != 0;
      } else if(walk != null) {
        if (walk.next()) {
          streamType = convertCrLf ? EolStreamType.TEXT_CRLF
              : walk.getEolStreamType(OperationType.CHECKOUT_OP);
          smudgeFilterCommand = walk.getFilterCommand(
              Constants.ATTR_FILTER_TYPE_SMUDGE);
          FileTreeIterator file = walk.getTree(fileIdx
              FileTreeIterator.class);
          fileId = file.getEntryObjectId();
          fileStreamSupplier = file::openEntryStream;
          loaded = true;
        }
      }
      if (!loaded) {
        fileStreamSupplier = () -> new FileInputStream(f);
        if (convertCrLf) {
          streamType = EolStreamType.TEXT_CRLF;
        }
      }

      FileMode fileMode = fh.getNewMode();
      StreamLoader resultStreamLoader;
      boolean safeWrite;
      if (PatchType.GIT_BINARY.equals(fh.getPatchType())) {
        resultStreamLoader = applyBinary(path
        safeWrite = true;
      } else {
        RawText raw = inCoreHandler.getRawText(walk
            convertCrLf);
        resultStreamLoader = applyText(raw
        safeWrite = false;
        if (!inCore) {
          repo.getFS().setExecute(f
        }
      }

      CheckoutMetadata checkOutMetadata = new CheckoutMetadata(streamType
      inCoreHandler.update(fh.getOldId().toObjectId()
          safeWrite
    } catch (IOException | BinaryBlobException e) {
      throw new PatchApplyException(MessageFormat.format(
          JGitText.get().patchApplyException
    }
  }

  private boolean needsCrLfConversion(File f
      throws IOException {
    if (PatchType.GIT_BINARY.equals(fileHeader.getPatchType())) {
      return false;
    }
    if (!hasCrLf(fileHeader)) {
      try (InputStream input = new FileInputStream(f)) {
        return RawText.isCrLfText(input);
      }
    }
    return false;
  }

  private static boolean hasCrLf(org.eclipse.jgit.patch.FileHeader fileHeader) {
    if (PatchType.GIT_BINARY.equals(fileHeader.getPatchType())) {
      return false;
    }
    for (org.eclipse.jgit.patch.HunkHeader header : fileHeader.getHunks()) {
      byte[] buf = header.getBuffer();
      int hunkEnd = header.getEndOffset();
      int lineStart = header.getStartOffset();
      while (lineStart < hunkEnd) {
        int nextLineStart = RawParseUtils.nextLF(buf
        if (nextLineStart > hunkEnd) {
          nextLineStart = hunkEnd;
        }
        if (nextLineStart <= lineStart) {
          break;
        }
        if (nextLineStart - lineStart > 1) {
          char first = (char) (buf[lineStart] & 0xFF);
          if (first == ' ' || first == '-') {
            if (buf[nextLineStart - 2] == '\r') {
              return true;
            }
          }
        }
        lineStart = nextLineStart;
      }
    }
    return false;
  }


  private void initHash(SHA1 hash
    hash.update(Constants.encodedTypeString(Constants.OBJ_BLOB));
    hash.update((byte) ' ');
    hash.update(Constants.encodeASCII(size));
    hash.update((byte) 0);
  }

  private ObjectId hash(File f) throws IOException {
    SHA1 hash = SHA1.newInstance();
    initHash(hash
    try (InputStream input = new FileInputStream(f)) {
      byte[] buf = new byte[8192];
      int n;
      while ((n = input.read(buf)) >= 0) {
        hash.update(buf
      }
    }
    return hash.toObjectId();
  }

  private void checkOid(ObjectId baseId
      String path)
      throws PatchApplyException
    boolean hashOk = false;
    if (id != null) {
      hashOk = baseId.equals(id);
      if (!hashOk && ChangeType.ADD.equals(type)
          && ObjectId.zeroId().equals(baseId)) {
        hashOk = Constants.EMPTY_BLOB_ID.equals(id);
      }
    } else {
      if (!inCore && ObjectId.zeroId().equals(baseId)) {
        hashOk = !f.exists() || f.length() == 0;
      } else {
        hashOk = baseId.equals(hash(f));
      }
    }
    if (!hashOk) {
      throw new PatchApplyException(MessageFormat
          .format(JGitText.get().applyBinaryBaseOidWrong
    }
  }

  private StreamLoader applyBinary(String path
      File f
      throws PatchApplyException
    if (!fh.getOldId().isComplete() || !fh.getNewId().isComplete()) {
      throw new PatchApplyException(MessageFormat
          .format(JGitText.get().applyBinaryOidTooShort
    }
    org.eclipse.jgit.patch.BinaryHunk hunk = fh.getForwardBinaryHunk();
    int start = RawParseUtils.nextLF(hunk.getBuffer()
        hunk.getStartOffset());
    int length = hunk.getEndOffset() - start;
    SHA1 hash = SHA1.newInstance();
    TemporaryBuffer buffer = new TemporaryBuffer.LocalFile(null);
    OutputStream out = buffer;
    if (!inCore) {
      switch (hunk.getType()) {
        case LITERAL_DEFLATED:
          checkOid(fh.getOldId().toObjectId()
              path);
          initHash(hash
          try (InputStream inflated = new SHA1InputStream(hash
              new InflaterInputStream(
                  new BinaryHunkInputStream(
                      new ByteArrayInputStream(
                          hunk.getBuffer()
                          length))))) {
            if (!fh.getNewId().toObjectId().equals(hash.toObjectId())) {
              throw new PatchApplyException(MessageFormat.format(
                  JGitText.get().applyBinaryResultOidWrong
                  path));
            }
            return inCoreHandler.getStreamLoader(() -> inflated
          }
        case DELTA_DEFLATED:
          byte[] base;
          try (InputStream input = inputSupplier.load()) {
            base = IO.readWholeStream(input
          }
          try (BinaryDeltaInputStream input = new BinaryDeltaInputStream(
              base
              new InflaterInputStream(new BinaryHunkInputStream(
                  new ByteArrayInputStream(hunk.getBuffer()
                      start
            long finalSize = input.getExpectedResultSize();
            initHash(hash
            try (SHA1InputStream hashed = new SHA1InputStream(hash
                input)) {
              if (!fh.getNewId().toObjectId()
                  .equals(hash.toObjectId())) {
                throw new PatchApplyException(MessageFormat.format(
                    JGitText.get().applyBinaryResultOidWrong
                    path));
              }
              return inCoreHandler.getStreamLoader(() -> hashed
            }
          }
        default:
          break;
      }
    }
    return null;
  }

  private StreamLoader applyText(RawText rt
      throws IOException
    List<ByteBuffer> oldLines = new ArrayList<>(rt.size());
    for (int i = 0; i < rt.size(); i++) {
      oldLines.add(rt.getRawString(i));
    }
    List<ByteBuffer> newLines = new ArrayList<>(oldLines);
    int afterLastHunk = 0;
    int lineNumberShift = 0;
    int lastHunkNewLine = -1;
    for (org.eclipse.jgit.patch.HunkHeader hh : fh.getHunks()) {

      if (hh.getNewStartLine() <= lastHunkNewLine) {
        throw new PatchApplyException(MessageFormat
            .format(JGitText.get().patchApplyException
      }
      lastHunkNewLine = hh.getNewStartLine();

      byte[] b = new byte[hh.getEndOffset() - hh.getStartOffset()];
      System.arraycopy(hh.getBuffer()
          b.length);
      RawText hrt = new RawText(b);

      List<ByteBuffer> hunkLines = new ArrayList<>(hrt.size());
      for (int i = 0; i < hrt.size(); i++) {
        hunkLines.add(hrt.getRawString(i));
      }

      if (hh.getNewStartLine() == 0) {
        if (fh.getHunks().size() == 1
            && canApplyAt(hunkLines
          newLines.clear();
          break;
        }
        throw new PatchApplyException(MessageFormat
            .format(JGitText.get().patchApplyException
      }
      int applyAt = hh.getNewStartLine() - 1 + lineNumberShift;
      if (applyAt < afterLastHunk && lineNumberShift < 0) {
        applyAt = hh.getNewStartLine() - 1;
        lineNumberShift = 0;
      }
      if (applyAt < afterLastHunk) {
        throw new PatchApplyException(MessageFormat
            .format(JGitText.get().patchApplyException
      }
      boolean applies = false;
      int oldLinesInHunk = hh.getLinesContext()
          + hh.getOldImage().getLinesDeleted();
      if (oldLinesInHunk <= 1) {
        applies = canApplyAt(hunkLines
        if (!applies && lineNumberShift != 0) {
          applyAt = hh.getNewStartLine() - 1;
          applies = applyAt >= afterLastHunk
              && canApplyAt(hunkLines
        }
      } else {
        int maxShift = applyAt - afterLastHunk;
        for (int shift = 0; shift <= maxShift; shift++) {
          if (canApplyAt(hunkLines
            applies = true;
            applyAt -= shift;
            break;
          }
        }
        if (!applies) {
          applyAt = hh.getNewStartLine() - 1 + lineNumberShift;
          maxShift = newLines.size() - applyAt - oldLinesInHunk;
          for (int shift = 1; shift <= maxShift; shift++) {
            if (canApplyAt(hunkLines
              applies = true;
              applyAt += shift;
              break;
            }
          }
        }
      }
      if (!applies) {
        throw new PatchApplyException(MessageFormat
            .format(JGitText.get().patchApplyException
      }
      lineNumberShift = applyAt - hh.getNewStartLine() + 1;
      int sz = hunkLines.size();
      for (int j = 1; j < sz; j++) {
        ByteBuffer hunkLine = hunkLines.get(j);
        if (!hunkLine.hasRemaining()) {
          applyAt++;
          continue;
        }
        switch (hunkLine.array()[hunkLine.position()]) {
          case ' ':
            applyAt++;
            break;
          case '-':
            newLines.remove(applyAt);
            break;
          case '+':
            newLines.add(applyAt++
            break;
          default:
            break;
        }
      }
      afterLastHunk = applyAt;
    }
    if (!isNoNewlineAtEndOfFile(fh)) {
      newLines.add(null);
    }
    if (!rt.isMissingNewlineAtEnd()) {
      oldLines.add(null);
    }
    if (oldLines.equals(newLines)) {
    }

    TemporaryBuffer buffer = new TemporaryBuffer.LocalFile(null);
    try (OutputStream out = buffer) {
      for (Iterator<ByteBuffer> l = newLines.iterator(); l
          .hasNext(); ) {
        ByteBuffer line = l.next();
        if (line == null) {
          break;
        }
        out.write(line.array()
        if (l.hasNext()) {
          out.write('\n');
        }
      }
      return inCoreHandler.getStreamLoader(buffer::openInputStream
    }
  }

  private boolean canApplyAt(List<ByteBuffer> hunkLines
      List<ByteBuffer> newLines
    int sz = hunkLines.size();
    int limit = newLines.size();
    int pos = line;
    for (int j = 1; j < sz; j++) {
      ByteBuffer hunkLine = hunkLines.get(j);
      if (!hunkLine.hasRemaining()) {
        if (pos >= limit || newLines.get(pos).hasRemaining()) {
          return false;
        }
        pos++;
        continue;
      }
      switch (hunkLine.array()[hunkLine.position()]) {
        case ' ':
        case '-':
          if (pos >= limit
              || !newLines.get(pos).equals(slice(hunkLine
            return false;
          }
          pos++;
          break;
        default:
          break;
      }
    }
    return true;
  }

  private ByteBuffer slice(ByteBuffer b
    int newOffset = b.position() + off;
    return ByteBuffer.wrap(b.array()
  }

  private boolean isNoNewlineAtEndOfFile(org.eclipse.jgit.patch.FileHeader fh) {
    List<? extends org.eclipse.jgit.patch.HunkHeader> hunks = fh.getHunks();
    if (hunks == null || hunks.isEmpty()) {
      return false;
    }
    org.eclipse.jgit.patch.HunkHeader lastHunk = hunks.get(hunks.size() - 1);
    byte[] buf = new byte[lastHunk.getEndOffset()
        - lastHunk.getStartOffset()];
    System.arraycopy(lastHunk.getBuffer()
        0
    RawText lhrt = new RawText(buf);
    return lhrt.getString(lhrt.size() - 1)
  }

  private static class SHA1InputStream extends InputStream {

    private final SHA1 hash;

    private final InputStream in;

    SHA1InputStream(SHA1 hash
      this.hash = hash;
      this.in = in;
    }

    @Override
    public int read() throws IOException {
      int b = in.read();
      if (b >= 0) {
        hash.update((byte) b);
      }
      return b;
    }

    @Override
    public int read(byte[] b
      int n = in.read(b
      if (n > 0) {
        hash.update(b
      }
      return n;
    }

    @Override
    public void close() throws IOException {
      in.close();
    }
  }
}
