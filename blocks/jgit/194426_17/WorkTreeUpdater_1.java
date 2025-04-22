package org.eclipse.jgit.patch;

import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;

import java.io.ByteArrayInputStream;
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
import java.util.Iterator;
import java.util.List;
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

public class PatchApplier {

	private final boolean inCore;
	private WorkTreeUpdater workTreeUpdater;
	@Nullable
	private final RevTree tip;

	@Nullable
	private final Repository repo;

	private final ObjectInserter inserter;
	private ObjectReader reader;

	public PatchApplier(Repository repo) {
		this.inCore = false;
		if (repo == null) {
			throw new NullPointerException(JGitText.get().repositoryIsRequired);
		}
		this.repo = repo;
		inserter = repo.newObjectInserter();
		reader = inserter.newReader();
	}

	public PatchApplier(Repository repo
		this.repo = repo;
		inCore = true;
		this.tip = tip;
		inserter = oi;
		reader = oi.newReader();
	}

	public static class ApplyPatchResult {

		public ObjectId appliedTree;
		public List<File> appliedFiles;
	}

	public ApplyPatchResult applyPatch(InputStream patchInput)
			throws PatchFormatException
		ApplyPatchResult result = new ApplyPatchResult();
		result.appliedFiles = new ArrayList<>();
		try {
			final org.eclipse.jgit.patch.Patch p = new org.eclipse.jgit.patch.Patch();
			p.parse(patchInput);
			if (!p.getErrors().isEmpty()) {
				throw new PatchFormatException(p.getErrors());
			}
			workTreeUpdater = inCore ?
					WorkTreeUpdater.createInCoreWorkTreeUpdater(repo
					WorkTreeUpdater.createWorkTreeUpdater(repo
			DirCache dirCache = workTreeUpdater.getLockedDirCache();
			for (org.eclipse.jgit.patch.FileHeader fh : p.getFiles()) {
				ChangeType type = fh.getChangeType();
				File f = null;
				switch (type) {
					case ADD:
						f = getFile(fh.getNewPath()
						apply(fh.getNewPath()
						break;
					case MODIFY:
						f = getFile(fh.getOldPath()
						apply(fh.getOldPath()
						break;
					case DELETE:
						f = getFile(fh.getOldPath()
						TreeWalk walk = getTreeWalkForFile(fh.getNewPath()
						if (walk != null) {
							walk.next();
							Attributes attributes = walk.getAttributes();
							workTreeUpdater.deleteFile(fh.getOldPath()
									workTreeUpdater.detectCheckoutStreamType(attributes)
									walk.getSmudgeCommand(attributes));
						} else {
							workTreeUpdater.deleteFile(fh.getOldPath()
									workTreeUpdater.detectCheckoutStreamType(new Attributes())
									null);
						}
						break;
					case RENAME:
						f = getFile(fh.getOldPath()
						File dest = getFile(fh.getNewPath()
						workTreeUpdater.renameFile(f
						apply(fh.getOldPath()
						break;
					case COPY:
						f = getFile(fh.getOldPath()
						File target = getFile(fh.getNewPath()
						workTreeUpdater.copyFile(f
						apply(fh.getOldPath()
				}
				if (f != null) {
					result.appliedFiles.add(f);
				}
			}
			patchInput.close();
			workTreeUpdater.writeWorkTreeChanges(false);
			WorkTreeUpdater.Result res = workTreeUpdater.writeIndexChanges();
			result.appliedTree = res.treeId;
		} catch (IOException e) {
			throw new PatchApplyException(
					MessageFormat.format(JGitText.get().patchApplyException
		}
		return result;
	}

	private File getFile(String path
		if (inCore) {
			return null;
		}
		File f = new File(workTreeUpdater.nonNullRepo().getWorkTree()
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

	private static class FileTreeWalk {

		TreeWalk walk;
		int fileTreeIdx = -1;
		int cacheTreeIdx = -1;
	}

	private FileTreeWalk getTreeWalkForFile(String path
			org.eclipse.jgit.patch.FileHeader fh)
			throws PatchApplyException {
		try {
			FileTreeWalk res = new FileTreeWalk();
			res.walk = inCore ? TreeWalk.forPath(repo
			if (!inCore) {
				res.cacheTreeIdx = res.walk.addTree(new DirCacheIterator(cache));
				FileTreeIterator files = new FileTreeIterator(repo);
				res.fileTreeIdx = res.walk.addTree(files);
				res.walk.setFilter(AndTreeFilter.create(PathFilterGroup.createFromStrings(path)
						new NotIgnoredFilter(res.fileTreeIdx)));
				res.walk.setOperationType(OperationType.CHECKIN_OP);
				res.walk.setRecursive(true);
				files.setDirCacheIterator(res.walk
			}
			return res;
		} catch (IOException e) {
			throw new PatchApplyException(
					MessageFormat.format(JGitText.get().patchApplyException
		}
	}

	private void apply(String path
			throws PatchApplyException {
		if (PatchType.BINARY.equals(fh.getPatchType())) {
			return;
		}
		try {
			FileTreeWalk fileTreeWalk = getTreeWalkForFile(path
			TreeWalk walk = fileTreeWalk.walk;
			boolean loaded = false;
			EolStreamType streamType = EolStreamType.DIRECT;
			boolean convertCrLf = inCore ? false : needsCrLfConversion(f
			String smudgeFilterCommand = null;
			WorkTreeUpdater.StreamSupplier fileStreamSupplier = null;
			ObjectId fileId = ObjectId.zeroId();
			if (inCore && walk != null) {
				fileId = walk != null ? walk.getObjectId(0) : ObjectId.zeroId();
				ObjectLoader loader = LfsFactory.getInstance()
						.applySmudgeFilter(repo
				fileStreamSupplier = () -> loader.openStream();
				loaded = loader != null && loader.getSize() != 0;
			} else if (walk != null) {
				if (walk.next()) {
					streamType = convertCrLf ? EolStreamType.TEXT_CRLF
							: walk.getEolStreamType(OperationType.CHECKOUT_OP);
					smudgeFilterCommand = walk.getFilterCommand(Constants.ATTR_FILTER_TYPE_SMUDGE);
					FileTreeIterator file = walk.getTree(fileTreeWalk.fileTreeIdx
					if (file != null) {
						fileId = file.getEntryObjectId();
						fileStreamSupplier = file::openEntryStream;
						loaded = true;
					}
				}
			}
			if (!loaded) {
				fileStreamSupplier = () -> new FileInputStream(f);
				if (convertCrLf) {
					streamType = EolStreamType.TEXT_CRLF;
				}
			}
			FileMode fileMode = fh.getNewMode() != null ? fh.getNewMode() : FileMode.MISSING;
			ContentStreamLoaders resultStreamLoaders;
			boolean safeWrite;
			if (PatchType.GIT_BINARY.equals(fh.getPatchType())) {
				resultStreamLoaders = applyBinary(path
				safeWrite = true;
			} else {
				String filterCommand = walk != null ? walk.getFilterCommand(
						Constants.ATTR_FILTER_TYPE_CLEAN) : null;
				RawText raw = getRawText(f
						filterCommand
				resultStreamLoaders = applyText(raw
				safeWrite = false;
				if (!inCore) {
					workTreeUpdater.nonNullRepo().getFS().setExecute(
							f
				}
			}

			if (resultStreamLoaders != null) {
				workTreeUpdater.updateFileWithContent(resultStreamLoaders.copy1
						smudgeFilterCommand
						fh.getNewPath()
				Instant lastModified =
						f == null ? null : workTreeUpdater.nonNullRepo().getFS().lastModifiedInstant(f);
				Attributes attributes = walk != null ? walk.getAttributes() : new Attributes();
				workTreeUpdater.insertToIndex(resultStreamLoaders.copy2
						fh.getNewPath().getBytes(StandardCharsets.UTF_8)
						DirCacheEntry.STAGE_0
						attributes.get(Constants.ATTR_FILTER));

				if (resultStreamLoaders.hash1 != null) {
					if (!fh.getNewId().toObjectId().equals(resultStreamLoaders.hash1.toObjectId())) {
						throw new PatchApplyException(
								MessageFormat.format(JGitText.get().applyBinaryResultOidWrong
					}
				}
				if (resultStreamLoaders.hash2 != null) {
					if (!fh.getNewId().toObjectId().equals(resultStreamLoaders.hash2.toObjectId())) {
						throw new PatchApplyException(
								MessageFormat.format(JGitText.get().applyBinaryResultOidWrong
					}
				}
			}
		} catch (IOException | UnsupportedOperationException e) {
			throw new PatchApplyException(
					MessageFormat.format(JGitText.get().patchApplyException
		}
	}

	private RawText getRawText(
			File file
			StreamSupplier fileStreamSupplier
			ObjectId fileId
			String path
			boolean loaded
			String filterCommand
			boolean convertCrLf)
			throws IOException {
		if (loaded) {
			try (InputStream input =
					filterClean(repo
				return new RawText(org.eclipse.jgit.util.IO.readWholeStream(input
			}
		}
		if (convertCrLf) {
			try (InputStream input =
					EolStreamTypeUtil.wrapInputStream(fileStreamSupplier.load()
				return new RawText(org.eclipse.jgit.util.IO.readWholeStream(input
			}
		}
		if (inCore && fileId.equals(ObjectId.zeroId())) {
			return new RawText(new byte[]{});
		}
		return new RawText(file);
	}

	private InputStream filterClean(
			Repository repository
			String path
			InputStream fromFile
			boolean convertCrLf
			String filterCommand)
			throws IOException {
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
			FilterCommand command =
					FilterCommandRegistry.createFilterCommand(filterCommand
			while (command.run() != -1) {
			}
			return buffer.openInputStreamWithAutoDestroy();
		}
		org.eclipse.jgit.util.FS fs = repository.getFS();
		ProcessBuilder filterProcessBuilder = fs.runInShell(filterCommand
		filterProcessBuilder.directory(repository.getWorkTree());
		filterProcessBuilder
				.environment()
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
					new FilterFailedException(
							rc
							filterCommand
							path
							result.getStdout().toByteArray(4096)
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
			throws PatchApplyException
		boolean hashOk = false;
		if (id != null) {
			hashOk = baseId.equals(id);
			if (!hashOk && ChangeType.ADD.equals(type) && ObjectId.zeroId().equals(baseId)) {
				hashOk = Constants.EMPTY_BLOB_ID.equals(id);
			}
		} else {
			if (!inCore) {
				if (ObjectId.zeroId().equals(baseId)) {
					hashOk = !f.exists() || f.length() == 0;
				}
			} else {
				hashOk = baseId.equals(hash(f));
			}
		}
		if (!hashOk) {
			throw new PatchApplyException(
					MessageFormat.format(JGitText.get().applyBinaryBaseOidWrong
		}
	}

	private static class ContentStreamLoaders {

		StreamLoader copy1;
		StreamLoader copy2;

		SHA1 hash1;
		SHA1 hash2;

		ContentStreamLoaders(StreamLoader copy1
			this.copy1 = copy1;
			this.copy2 = copy2;
		}

		ContentStreamLoaders(StreamLoader copy1
			this(copy1
			this.hash1 = hash1;
			this.hash2 = hash2;
		}
	}

	private ContentStreamLoaders applyBinary(String path
			org.eclipse.jgit.patch.FileHeader fh
			throws PatchApplyException
		if (inCore) {
			throw new UnsupportedOperationException(JGitText.get().applyBinaryForInCoreNotSupported);
		}
		if (!fh.getOldId().isComplete() || !fh.getNewId().isComplete()) {
			throw new PatchApplyException(
					MessageFormat.format(JGitText.get().applyBinaryOidTooShort
		}
		org.eclipse.jgit.patch.BinaryHunk hunk = fh.getForwardBinaryHunk();
		int start = RawParseUtils.nextLF(hunk.getBuffer()
		int length = hunk.getEndOffset() - start;
		SHA1 hash1 = SHA1.newInstance();
		SHA1 hash2 = SHA1.newInstance();
		switch (hunk.getType()) {
			case LITERAL_DEFLATED:
				checkOid(fh.getOldId().toObjectId()
				initHash(hash1
				InputStream inflated1 = new SHA1InputStream(hash1
						new BinaryHunkInputStream(
								new ByteArrayInputStream(hunk.getBuffer()
				initHash(hash2
				InputStream inflated2 = new SHA1InputStream(hash2
						new BinaryHunkInputStream(
								new ByteArrayInputStream(hunk.getBuffer()
				return new ContentStreamLoaders(
						WorkTreeUpdater.createStreamLoader(() -> inflated1
						WorkTreeUpdater.createStreamLoader(() -> inflated2
			case DELTA_DEFLATED:
				byte[] base;
				try (InputStream in = inputSupplier.load()) {
					base = IO.readWholeStream(in
				}
				BinaryDeltaInputStream input1 = new BinaryDeltaInputStream(base
						new InflaterInputStream(new BinaryHunkInputStream(
								new ByteArrayInputStream(hunk.getBuffer()
				BinaryDeltaInputStream input2 = new BinaryDeltaInputStream(base
						new InflaterInputStream(new BinaryHunkInputStream(
								new ByteArrayInputStream(hunk.getBuffer()
				long finalSize = input1.getExpectedResultSize();
				initHash(hash1
				initHash(hash2
				return new ContentStreamLoaders(
						WorkTreeUpdater.createStreamLoader(() -> new SHA1InputStream(hash1
						hash1
						WorkTreeUpdater.createStreamLoader(() -> new SHA1InputStream(hash2
						hash2);
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
		if (oldLines.equals(newLines)) {
			return null;
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
