package org.eclipse.jgit.patch;

import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.InflaterInputStream;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.api.errors.FilterFailedException;
import org.eclipse.jgit.api.errors.PatchApplyException;
import org.eclipse.jgit.api.errors.PatchFormatException;
import org.eclipse.jgit.attributes.Attributes;
import org.eclipse.jgit.attributes.FilterCommand;
import org.eclipse.jgit.attributes.FilterCommandRegistry;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.dircache.DirCacheIterator;
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
import org.eclipse.jgit.util.FS.ExecutionResult;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.LfsFactory;
import org.eclipse.jgit.util.RawParseUtils;
import org.eclipse.jgit.util.TemporaryBuffer;
import org.eclipse.jgit.util.TemporaryBuffer.LocalFile;
import org.eclipse.jgit.util.WorkTreeUpdater;
import org.eclipse.jgit.util.WorkTreeUpdater.StreamLoader;
import org.eclipse.jgit.util.WorkTreeUpdater.StreamSupplier;
import org.eclipse.jgit.util.io.BinaryDeltaInputStream;
import org.eclipse.jgit.util.io.BinaryHunkInputStream;
import org.eclipse.jgit.util.io.EolStreamTypeUtil;
import org.eclipse.jgit.util.sha1.SHA1;

public class PatchApplier implements Closeable {

	private final WorkTreeUpdater workTreeUpdater;

	@Nullable
	private final RevTree tip;

	@Nullable
	private final Repository repo;

	private final ObjectInserter inserter;
	private final ObjectReader reader;

	public PatchApplier(Repository repo) {
		this.repo = repo;
		inserter = repo.newObjectInserter();
		reader = inserter.newReader();
		tip = null;
		workTreeUpdater = WorkTreeUpdater.createWorkTreeUpdater(repo
	}

	public PatchApplier(Repository repo
		this.repo = repo;
		this.tip = tip;
		inserter = oi;
		reader = oi.newReader();
		workTreeUpdater = WorkTreeUpdater.createInCoreWorkTreeUpdater(repo
	}

	public static class Result {

		public ObjectId treeId;
		public List<String> paths;
	}

	public Result applyPatch(InputStream patchInput)
			throws PatchFormatException
		Result result = new Result();
		try {
			org.eclipse.jgit.patch.Patch p = new org.eclipse.jgit.patch.Patch();
			p.parse(patchInput);
			if (!p.getErrors().isEmpty()) {
				throw new PatchFormatException(p.getErrors());
			}

 			DirCache dirCache = workTreeUpdater.getLockedDirCache();
			Set<String> modifiedPaths = new HashSet<>();
			for (org.eclipse.jgit.patch.FileHeader fh : p.getFiles()) {
				ChangeType type = fh.getChangeType();
				switch (type) {
					case ADD:
						apply(fh.getNewPath()
						break;
					case MODIFY:
						apply(fh.getOldPath()
						break;
					case DELETE:
						TreeWalk walk = getTreeWalkForFile(fh.getNewPath()
						String smudgeCommand = null;
						Attributes attr = new Attributes();
						if (walk != null) {
							walk.next();
							attr = walk.getAttributes();
							smudgeCommand = walk.getSmudgeCommand(attr);
						}
						workTreeUpdater.deleteFile(fh.getOldPath()
							workTreeUpdater.detectCheckoutStreamType(attr)
						break;
					case RENAME: {
						File src = getFile(fh.getOldPath()
						File dest = getFile(fh.getNewPath()
						workTreeUpdater.renameFile(src
								fh.getNewPath());
						apply(fh.getOldPath()
						break;
					}
					case COPY: {
						File src = getFile(fh.getOldPath()
						File dest = getFile(fh.getNewPath()
						workTreeUpdater.copyFile(src
						apply(fh.getOldPath()
						break;
					}
					}
				if (fh.getChangeType() != ChangeType.DELETE)
					modifiedPaths.add(fh.getNewPath());
				if (fh.getChangeType() != ChangeType.COPY && fh.getChangeType() != ChangeType.ADD)
					modifiedPaths.add(fh.getOldPath());
			}
			workTreeUpdater.writeWorkTreeChanges(false);

			for (int i = 0; i < dirCache.getEntryCount(); i++) {
				DirCacheEntry dce = dirCache.getEntry(i);
				if (!modifiedPaths.contains(dce.getPathString()) || dce.getStage() != DirCacheEntry.STAGE_0)
					workTreeUpdater.addExistingToIndex(dce);
			}

			WorkTreeUpdater.Result res = workTreeUpdater.writeIndexChanges();
			result.treeId = res.treeId;
			result.paths = modifiedPaths.stream().sorted().collect(Collectors.toList());
		} catch (IOException e) {
			throw new PatchApplyException(
					MessageFormat.format(JGitText.get().patchApplyException
		} finally {
			try {
				patchInput.close();
			} catch (IOException e) {
			}
		}
		return result;
	}

	private File getFile(String path
		if (inCore()) {
			return null;
		}
		File f = new File(repo.getWorkTree()
		if (create) {
			try {
				File parent = f.getParentFile();
				FileUtils.mkdirs(parent
				FileUtils.createNewFile(f);
			} catch (IOException e) {
				throw new PatchApplyException(MessageFormat.format(JGitText.get().createNewFileFailed
						e);
			}
		}
		return f;
	}

	@Nullable
	private TreeWalk getTreeWalkForFile(String path
			throws PatchApplyException {
		try {
			if (inCore()) {
				return TreeWalk.forPath(repo
			}
			TreeWalk walk = new TreeWalk(repo);

			int cacheTreeIdx = walk.addTree(new DirCacheIterator(cache));
			FileTreeIterator files = new FileTreeIterator(repo);
			if (FILE_TREE_INDEX != walk.addTree(files))
				throw new IllegalStateException();

			walk.setFilter(AndTreeFilter.create(
					PathFilterGroup.createFromStrings(path)
					new NotIgnoredFilter(FILE_TREE_INDEX)));
			walk.setOperationType(OperationType.CHECKIN_OP);
			walk.setRecursive(true);
			files.setDirCacheIterator(walk
			return walk;
		} catch (IOException e) {
			throw new PatchApplyException(MessageFormat.format(
					JGitText.get().patchApplyException
		}
	}

	private static int FILE_TREE_INDEX = 1;

	private void apply(String path
			org.eclipse.jgit.patch.FileHeader fh) throws PatchApplyException {
		if (PatchType.BINARY.equals(fh.getPatchType())) {
			return;
		}
		try {
			TreeWalk walk = getTreeWalkForFile(path
			boolean loadedFromTreeWalk = false;
			boolean convertCrLf = inCore() || needsCrLfConversion(f
			EolStreamType streamType = convertCrLf ? EolStreamType.TEXT_CRLF : EolStreamType.DIRECT;
			String smudgeFilterCommand = null;
			WorkTreeUpdater.StreamSupplier fileStreamSupplier = null;
			ObjectId fileId = ObjectId.zeroId();
			if (walk == null) {
			} else if (inCore()) {
				fileId = walk.getObjectId(0);
				ObjectLoader loader = LfsFactory.getInstance()
						.applySmudgeFilter(repo
								null);
				byte[] data = loader.getBytes();
				convertCrLf = RawText.isCrLfText(data);
				fileStreamSupplier = () -> new ByteArrayInputStream(data);
				streamType = convertCrLf ? EolStreamType.TEXT_CRLF : EolStreamType.DIRECT;
				smudgeFilterCommand = walk.getFilterCommand(Constants.ATTR_FILTER_TYPE_SMUDGE);
				loadedFromTreeWalk = true;
			} else if (walk.next()) {
				streamType = convertCrLf ? EolStreamType.TEXT_CRLF
						: walk.getEolStreamType(OperationType.CHECKOUT_OP);
				smudgeFilterCommand = walk.getFilterCommand(Constants.ATTR_FILTER_TYPE_SMUDGE);
				FileTreeIterator file = walk.getTree(FILE_TREE_INDEX
				if (file != null) {
					fileId = file.getEntryObjectId();
					fileStreamSupplier = file::openEntryStream;
					loadedFromTreeWalk = true;
				} else {
				}
			}

			if (fileStreamSupplier == null)
				fileStreamSupplier = inCore() ? InputStream::nullInputStream : () -> new FileInputStream(f);

			FileMode fileMode = fh.getNewMode() != null ? fh.getNewMode() : FileMode.REGULAR_FILE;
			ContentStreamLoaders resultStreamLoaders;
			boolean safeWrite;
			if (PatchType.GIT_BINARY.equals(fh.getPatchType())) {
				resultStreamLoaders = applyBinary(path
				safeWrite = true;
			} else {
				String filterCommand =
						walk != null
								? walk.getFilterCommand(
										Constants.ATTR_FILTER_TYPE_CLEAN)
								: null;
				RawText raw = getRawText(f
						loadedFromTreeWalk
				resultStreamLoaders = applyText(raw
				safeWrite = false;
				if (!inCore()) {
					repo.getFS().setExecute(f
				}
			}

			workTreeUpdater.updateFileWithContent(resultStreamLoaders.copy1
					streamType
					safeWrite);
			Instant lastModified = f == null ? null
					: repo.getFS().lastModifiedInstant(f);
			Attributes attributes = walk != null ? walk.getAttributes()
					: new Attributes();
			DirCacheEntry dce = workTreeUpdater.insertToIndex(
					resultStreamLoaders.copy2
					fh.getNewPath().getBytes(StandardCharsets.UTF_8)
					DirCacheEntry.STAGE_0
					(int) resultStreamLoaders.copy2.getSize()
					attributes.get(Constants.ATTR_FILTER));

			if (fh.getNewId() != null && fh.getNewId().isComplete()
					&& !fh.getNewId().toObjectId().equals(dce.getObjectId())) {
				throw new PatchApplyException(MessageFormat.format(
						JGitText.get().applyBinaryResultOidWrong
			}
		} catch (IOException | UnsupportedOperationException e) {
			throw new PatchApplyException(
					MessageFormat.format(JGitText.get().patchApplyException
		}
	}

	@Override
	public void close() throws IOException {
		workTreeUpdater.close();
	}

	private RawText getRawText(@Nullable File file
			ObjectId fileId
			throws IOException {
		if (fromTreeWalk) {
			try (InputStream input = filterClean(repo
					filterCommand)) {
				return new RawText(org.eclipse.jgit.util.IO.readWholeStream(input
			}
		}
		if (convertCrLf) {
			try (InputStream input = EolStreamTypeUtil.wrapInputStream(fileStreamSupplier.load()
					EolStreamType.TEXT_LF)) {
				return new RawText(org.eclipse.jgit.util.IO.readWholeStream(input
			}
		}
		if (inCore() && fileId.equals(ObjectId.zeroId())) {
			return new RawText(new byte[]{});
		}
		return new RawText(file);
	}

	private InputStream filterClean(Repository repository
			boolean convertCrLf
		InputStream input = fromFile;
		if (convertCrLf) {
			input = EolStreamTypeUtil.wrapInputStream(input
		}
		if (org.eclipse.jgit.util.StringUtils.isEmptyOrNull(filterCommand)) {
			return input;
		}
		if (FilterCommandRegistry.isRegistered(filterCommand)) {
			LocalFile buffer = new org.eclipse.jgit.util.TemporaryBuffer.LocalFile(null
					workTreeUpdater.getInCoreFileSizeLimit());
			FilterCommand command = FilterCommandRegistry.createFilterCommand(filterCommand
					input
			while (command.run() != -1) {
			}
			return buffer.openInputStreamWithAutoDestroy();
		}
		org.eclipse.jgit.util.FS fs = repository.getFS();
		ProcessBuilder filterProcessBuilder = fs.runInShell(filterCommand
		filterProcessBuilder.directory(repository.getWorkTree());
		filterProcessBuilder.environment()
				.put(Constants.GIT_DIR_KEY
		ExecutionResult result;
		try {
			result = fs.execute(filterProcessBuilder
		} catch (IOException | InterruptedException e) {
			throw new IOException(new FilterFailedException(e
		}
		int rc = result.getRc();
		if (rc != 0) {
			throw new IOException(
					new FilterFailedException(rc
							org.eclipse.jgit.util.RawParseUtils.decode(result.getStderr().toByteArray(4096))));
		}
		return result.getStdout().openInputStreamWithAutoDestroy();
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

	private ObjectId hash(File f) throws IOException {
		try (FileInputStream fis = new FileInputStream(f);
				SHA1InputStream shaStream = new SHA1InputStream(fis
			shaStream.transferTo(OutputStream.nullOutputStream());
			return shaStream.getHash().toObjectId();
		}
	}

	private void checkOid(ObjectId baseId
			throws PatchApplyException
		boolean hashOk = false;
		if (id != null) {
			hashOk = baseId.equals(id);
			if (!hashOk && ChangeType.ADD.equals(type) && ObjectId.zeroId().equals(baseId)) {
				hashOk = Constants.EMPTY_BLOB_ID.equals(id);
			}
		} else if (!inCore()) {
			if (ObjectId.zeroId().equals(baseId)) {
				hashOk = !f.exists() || f.length() == 0;
			} else {
				hashOk = baseId.equals(hash(f));
			}
		}
		if (!hashOk) {
			throw new PatchApplyException(
					MessageFormat.format(JGitText.get().applyBinaryBaseOidWrong
		}
	}

	private boolean inCore() {
		return tip != null;
	}

	private static class ContentStreamLoaders {

		StreamLoader copy1;
		StreamLoader copy2;

		ContentStreamLoaders(StreamLoader copy1
			this.copy1 = copy1;
			this.copy2 = copy2;
		}
	}

	private ContentStreamLoaders applyBinary(String path
			org.eclipse.jgit.patch.FileHeader fh
			throws PatchApplyException
		if (!fh.getOldId().isComplete() || !fh.getNewId().isComplete()) {
			throw new PatchApplyException(
					MessageFormat.format(JGitText.get().applyBinaryOidTooShort
		}
		org.eclipse.jgit.patch.BinaryHunk hunk = fh.getForwardBinaryHunk();
		int start = RawParseUtils.nextLF(hunk.getBuffer()
		int length = hunk.getEndOffset() - start;
		switch (hunk.getType()) {
			case LITERAL_DEFLATED:
				checkOid(fh.getOldId().toObjectId()
				InputStream inflated1 = new InflaterInputStream(
						new BinaryHunkInputStream(new ByteArrayInputStream(hunk.getBuffer()
				InputStream inflated2 = new InflaterInputStream(
						new BinaryHunkInputStream(new ByteArrayInputStream(hunk.getBuffer()
				return new ContentStreamLoaders(
						WorkTreeUpdater.createStreamLoader(() -> inflated1
						WorkTreeUpdater.createStreamLoader(() -> inflated2
			case DELTA_DEFLATED:
				byte[] base;
				try (InputStream in = inputSupplier.load()) {
					base = IO.readWholeStream(in
				}
				BinaryDeltaInputStream input1 = new BinaryDeltaInputStream(base
						new BinaryHunkInputStream(new ByteArrayInputStream(hunk.getBuffer()
				BinaryDeltaInputStream input2 = new BinaryDeltaInputStream(base
						new BinaryHunkInputStream(new ByteArrayInputStream(hunk.getBuffer()
				long finalSize = input1.getExpectedResultSize();

				return new ContentStreamLoaders(
						WorkTreeUpdater.createStreamLoader(() -> input1
						WorkTreeUpdater.createStreamLoader(() -> input2
			default:
				throw new UnsupportedOperationException(
						MessageFormat.format(JGitText.get().applyBinaryPatchTypeNotSupported
								hunk.getType().name()));
		}
	}

	private ContentStreamLoaders applyText(RawText rt
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
				throw new PatchApplyException(MessageFormat.format(JGitText.get().patchApplyException
			}
			lastHunkNewLine = hh.getNewStartLine();

			byte[] b = new byte[hh.getEndOffset() - hh.getStartOffset()];
			System.arraycopy(hh.getBuffer()
			if (inCore() && hasCrLf(fh)) {
				b = new String(b
						.getBytes(StandardCharsets.UTF_8);
			}
			RawText hrt = new RawText(b);

			List<ByteBuffer> hunkLines = new ArrayList<>(hrt.size());
			for (int i = 0; i < hrt.size(); i++) {
				hunkLines.add(hrt.getRawString(i));
			}

			if (hh.getNewStartLine() == 0) {
				if (fh.getHunks().size() == 1 && canApplyAt(hunkLines
					newLines.clear();
					break;
				}
				throw new PatchApplyException(MessageFormat.format(JGitText.get().patchApplyException
			}
			int applyAt = hh.getNewStartLine() - 1 + lineNumberShift;
			if (applyAt < afterLastHunk && lineNumberShift < 0) {
				applyAt = hh.getNewStartLine() - 1;
				lineNumberShift = 0;
			}
			if (applyAt < afterLastHunk) {
				throw new PatchApplyException(MessageFormat.format(JGitText.get().patchApplyException
			}
			boolean applies = false;
			int oldLinesInHunk = hh.getLinesContext() + hh.getOldImage().getLinesDeleted();
			if (oldLinesInHunk <= 1) {
				applies = canApplyAt(hunkLines
				if (!applies && lineNumberShift != 0) {
					applyAt = hh.getNewStartLine() - 1;
					applies = applyAt >= afterLastHunk && canApplyAt(hunkLines
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
				throw new PatchApplyException(MessageFormat.format(JGitText.get().patchApplyException
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

		TemporaryBuffer buffer = new TemporaryBuffer.LocalFile(null);
		try (OutputStream out = buffer) {
			for (Iterator<ByteBuffer> l = newLines.iterator(); l.hasNext(); ) {
				ByteBuffer line = l.next();
				if (line == null) {
					break;
				}
				out.write(line.array()
				if (l.hasNext()) {
					out.write('\n');
				}
			}
			return new ContentStreamLoaders(
					WorkTreeUpdater.createStreamLoader(buffer::openInputStream
					WorkTreeUpdater.createStreamLoader(buffer::openInputStream
		}
	}

	private boolean canApplyAt(List<ByteBuffer> hunkLines
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
					if (pos >= limit || !newLines.get(pos).equals(slice(hunkLine
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
		byte[] buf = new byte[lastHunk.getEndOffset() - lastHunk.getStartOffset()];
		System.arraycopy(lastHunk.getBuffer()
		RawText lhrt = new RawText(buf);
	}

	private static class SHA1InputStream extends InputStream {

		private final SHA1 hash;

		private final InputStream in;

		SHA1InputStream(InputStream in
			hash = SHA1.newInstance();
			hash.update(Constants.encodedTypeString(Constants.OBJ_BLOB));
			hash.update((byte) ' ');
			hash.update(Constants.encodeASCII(size));
			hash.update((byte) 0);
			this.in = in;
		}

		public SHA1 getHash() { return hash; }

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
