
package org.eclipse.jgit.treewalk;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetEncoder;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.api.errors.FilterFailedException;
import org.eclipse.jgit.attributes.AttributesNode;
import org.eclipse.jgit.attributes.AttributesRule;
import org.eclipse.jgit.attributes.FilterCommand;
import org.eclipse.jgit.attributes.FilterCommandRegistry;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.ignore.FastIgnoreRule;
import org.eclipse.jgit.ignore.IgnoreNode;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.CoreConfig;
import org.eclipse.jgit.lib.CoreConfig.CheckStat;
import org.eclipse.jgit.lib.CoreConfig.EolStreamType;
import org.eclipse.jgit.lib.CoreConfig.SymLinks;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.submodule.SubmoduleWalk;
import org.eclipse.jgit.treewalk.TreeWalk.OperationType;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FS.ExecutionResult;
import org.eclipse.jgit.util.Holder;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.Paths;
import org.eclipse.jgit.util.RawParseUtils;
import org.eclipse.jgit.util.TemporaryBuffer;
import org.eclipse.jgit.util.TemporaryBuffer.LocalFile;
import org.eclipse.jgit.util.io.AutoLFInputStream;
import org.eclipse.jgit.util.io.EolStreamTypeUtil;
import org.eclipse.jgit.util.sha1.SHA1;

public abstract class WorkingTreeIterator extends AbstractTreeIterator {
	private static final int MAX_EXCEPTION_TEXT_SIZE = 10 * 1024;

	protected static final Entry[] EOF = {};

	static final int BUFFER_SIZE = 2048;

	private static final long MAXIMUM_FILE_SIZE_TO_READ_FULLY = 65536;

	private final IteratorState state;

	private byte[] contentId;

	private int contentIdFromPtr;

	private Entry[] entries;

	private int entryCnt;

	private int ptr;

	private IgnoreNode ignoreNode;

	private Holder<String> cleanFilterCommandHolder;

	private Holder<EolStreamType> eolStreamTypeHolder;

	protected Repository repository;

	private long canonLen = -1;

	private int contentIdOffset;

	protected WorkingTreeIterator(WorkingTreeOptions options) {
		super();
		state = new IteratorState(options);
	}

	protected WorkingTreeIterator(final String prefix
			WorkingTreeOptions options) {
		super(prefix);
		state = new IteratorState(options);
	}

	protected WorkingTreeIterator(WorkingTreeIterator p) {
		super(p);
		state = p.state;
		repository = p.repository;
	}

	protected void initRootIterator(Repository repo) {
		repository = repo;
		Entry entry;
		if (ignoreNode instanceof PerDirectoryIgnoreNode)
			entry = ((PerDirectoryIgnoreNode) ignoreNode).entry;
		else
			entry = null;
		ignoreNode = new RootIgnoreNode(entry
	}

	public void setDirCacheIterator(TreeWalk walk
		state.walk = walk;
		state.dirCacheTree = treeId;
	}

	protected DirCacheIterator getDirCacheIterator() {
		if (state.dirCacheTree >= 0 && state.walk != null) {
			return state.walk.getTree(state.dirCacheTree
					DirCacheIterator.class);
		}
		return null;
	}

	public void setWalkIgnoredDirectories(boolean includeIgnored) {
		state.walkIgnored = includeIgnored;
	}

	public boolean walksIgnoredDirectories() {
		return state.walkIgnored;
	}

	@Override
	public boolean hasId() {
		if (contentIdFromPtr == ptr)
			return true;
		return (mode & FileMode.TYPE_MASK) == FileMode.TYPE_FILE;
	}

	@Override
	public byte[] idBuffer() {
		if (contentIdFromPtr == ptr)
			return contentId;

		if (state.walk != null) {
			DirCacheIterator i = state.walk.getTree(state.dirCacheTree
							DirCacheIterator.class);
			if (i != null) {
				DirCacheEntry ent = i.getDirCacheEntry();
				if (ent != null && compareMetadata(ent) == MetadataDiff.EQUAL
						&& ((ent.getFileMode().getBits()
								& FileMode.TYPE_MASK) != FileMode.TYPE_GITLINK)) {
					contentIdOffset = i.idOffset();
					contentIdFromPtr = ptr;
					return contentId = i.idBuffer();
				}
				contentIdOffset = 0;
			} else {
				contentIdOffset = 0;
			}
		}
		switch (mode & FileMode.TYPE_MASK) {
		case FileMode.TYPE_SYMLINK:
		case FileMode.TYPE_FILE:
			contentIdFromPtr = ptr;
			return contentId = idBufferBlob(entries[ptr]);
		case FileMode.TYPE_GITLINK:
			contentIdFromPtr = ptr;
			return contentId = idSubmodule(entries[ptr]);
		}
		return zeroid;
	}

	@Override
	public boolean isWorkTree() {
		return true;
	}

	protected byte[] idSubmodule(Entry e) {
		if (repository == null)
			return zeroid;
		File directory;
		try {
			directory = repository.getWorkTree();
		} catch (NoWorkTreeException nwte) {
			return zeroid;
		}
		return idSubmodule(directory
	}

	protected byte[] idSubmodule(File directory
		try (Repository submoduleRepo = SubmoduleWalk.getSubmoduleRepository(
				directory
				repository != null ? repository.getFS() : FS.DETECTED)) {
			if (submoduleRepo == null) {
				return zeroid;
			}
			ObjectId head = submoduleRepo.resolve(Constants.HEAD);
			if (head == null) {
				return zeroid;
			}
			byte[] id = new byte[Constants.OBJECT_ID_LENGTH];
			head.copyRawTo(id
			return id;
		} catch (IOException exception) {
			return zeroid;
		}
	}

	private static final byte[] digits = { '0'
			'7'

	private static final byte[] hblob = Constants
			.encodedTypeString(Constants.OBJ_BLOB);

	private byte[] idBufferBlob(Entry e) {
		try {
			final InputStream is = e.openInputStream();
			if (is == null)
				return zeroid;
			try {
				state.initializeReadBuffer();

				final long len = e.getLength();
				InputStream filteredIs = possiblyFilteredInputStream(e
						OperationType.CHECKIN_OP);
				return computeHash(filteredIs
			} finally {
				safeClose(is);
			}
		} catch (IOException err) {
			return zeroid;
		}
	}

	private InputStream possiblyFilteredInputStream(final Entry e
			final InputStream is
		return possiblyFilteredInputStream(e

	}

	private InputStream possiblyFilteredInputStream(final Entry e
			final InputStream is
			throws IOException {
		if (getCleanFilterCommand() == null
				&& getEolStreamType(opType) == EolStreamType.DIRECT) {
			canonLen = len;
			return is;
		}

		if (len <= MAXIMUM_FILE_SIZE_TO_READ_FULLY) {
			ByteBuffer rawbuf = IO.readWholeStream(is
			rawbuf = filterClean(rawbuf.array()
			canonLen = rawbuf.limit();
			return new ByteArrayInputStream(rawbuf.array()
		}

		if (getCleanFilterCommand() == null && isBinary(e)) {
				canonLen = len;
				return is;
			}

		final InputStream lenIs = filterClean(e.openInputStream()
				opType);
		try {
			canonLen = computeLength(lenIs);
		} finally {
			safeClose(lenIs);
		}
		return filterClean(is
	}

	private static void safeClose(InputStream in) {
		try {
			in.close();
		} catch (IOException err2) {
		}
	}

	private static boolean isBinary(Entry entry) throws IOException {
		InputStream in = entry.openInputStream();
		try {
			return RawText.isBinary(in);
		} finally {
			safeClose(in);
		}
	}

	private ByteBuffer filterClean(byte[] src
			throws IOException {
		InputStream in = new ByteArrayInputStream(src);
		try {
			return IO.readWholeStream(filterClean(in
		} finally {
			safeClose(in);
		}
	}

	private InputStream filterClean(InputStream in) throws IOException {
		return filterClean(in
	}

	private InputStream filterClean(InputStream in
			throws IOException {
		in = handleAutoCRLF(in
		String filterCommand = getCleanFilterCommand();
		if (filterCommand != null) {
			if (FilterCommandRegistry.isRegistered(filterCommand)) {
				LocalFile buffer = new TemporaryBuffer.LocalFile(null);
				FilterCommand command = FilterCommandRegistry
						.createFilterCommand(filterCommand
								buffer);
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
				throw new IOException(new FilterFailedException(e
						filterCommand
			}
			int rc = result.getRc();
			if (rc != 0) {
				throw new IOException(new FilterFailedException(rc
						filterCommand
						result.getStdout().toByteArray(MAX_EXCEPTION_TEXT_SIZE)
						RawParseUtils.decode(result.getStderr()
								.toByteArray(MAX_EXCEPTION_TEXT_SIZE))));
			}
			return result.getStdout().openInputStreamWithAutoDestroy();
		}
		return in;
	}

	private InputStream handleAutoCRLF(InputStream in
			throws IOException {
		return EolStreamTypeUtil.wrapInputStream(in
	}

	public WorkingTreeOptions getOptions() {
		return state.options;
	}

	@Override
	public int idOffset() {
		return contentIdOffset;
	}

	@Override
	public void reset() {
		if (!first()) {
			ptr = 0;
			if (!eof())
				parseEntry();
		}
	}

	@Override
	public boolean first() {
		return ptr == 0;
	}

	@Override
	public boolean eof() {
		return ptr == entryCnt;
	}

	@Override
	public void next(int delta) throws CorruptObjectException {
		ptr += delta;
		if (!eof()) {
			parseEntry();
		}
	}

	@Override
	public void back(int delta) throws CorruptObjectException {
		ptr -= delta;
		parseEntry();
	}

	private void parseEntry() {
		final Entry e = entries[ptr];
		mode = e.getMode().getBits();

		final int nameLen = e.encodedNameLen;
		ensurePathCapacity(pathOffset + nameLen
		System.arraycopy(e.encodedName
		pathLen = pathOffset + nameLen;
		canonLen = -1;
		cleanFilterCommandHolder = null;
		eolStreamTypeHolder = null;
	}

	public long getEntryLength() {
		return current().getLength();
	}

	public long getEntryContentLength() throws IOException {
		if (canonLen == -1) {
			long rawLen = getEntryLength();
			if (rawLen == 0)
				canonLen = 0;
			InputStream is = current().openInputStream();
			try {
				possiblyFilteredInputStream(current()
						.getLength());
			} finally {
				safeClose(is);
			}
		}
		return canonLen;
	}

	public long getEntryLastModified() {
		return current().getLastModified();
	}

	public InputStream openEntryStream() throws IOException {
		InputStream rawis = current().openInputStream();
		if (getCleanFilterCommand() == null
				&& getEolStreamType() == EolStreamType.DIRECT)
			return rawis;
		else
			return filterClean(rawis);
	}

	public boolean isEntryIgnored() throws IOException {
		return isEntryIgnored(pathLen);
	}

	protected boolean isEntryIgnored(int pLen) throws IOException {
		return isEntryIgnored(pLen
	}

	private boolean isEntryIgnored(int pLen
			throws IOException {
		final int pOff = 0 < pathOffset ? pathOffset - 1 : pathOffset;
		String pathRel = TreeWalk.pathOf(this.path
		String parentRel = getParentPath(pathRel);

		if (isDirectoryIgnored(parentRel)) {
			return true;
		}

		IgnoreNode rules = getIgnoreNode();
		final Boolean ignored = rules != null
				? rules.checkIgnored(pathRel
				: null;
		if (ignored != null) {
			return ignored.booleanValue();
		}
		return parent instanceof WorkingTreeIterator
				&& ((WorkingTreeIterator) parent).isEntryIgnored(pLen
						fileMode);
	}

	private IgnoreNode getIgnoreNode() throws IOException {
		if (ignoreNode instanceof PerDirectoryIgnoreNode)
			ignoreNode = ((PerDirectoryIgnoreNode) ignoreNode).load();
		return ignoreNode;
	}

	public AttributesNode getEntryAttributesNode() throws IOException {
		if (attributesNode instanceof PerDirectoryAttributesNode)
			attributesNode = ((PerDirectoryAttributesNode) attributesNode)
					.load();
		return attributesNode;
	}

	private static final Comparator<Entry> ENTRY_CMP = new Comparator<Entry>() {
		@Override
		public int compare(Entry a
			return Paths.compare(
					a.encodedName
					b.encodedName
		}
	};

	protected void init(Entry[] list) {
		entries = list;
		int i

		final CharsetEncoder nameEncoder = state.nameEncoder;
		for (i = 0
			final Entry e = entries[i];
			if (e == null)
				continue;
			final String name = e.getName();
				continue;
			if (Constants.DOT_GIT.equals(name))
				continue;
			if (Constants.DOT_GIT_IGNORE.equals(name))
				ignoreNode = new PerDirectoryIgnoreNode(e);
			if (Constants.DOT_GIT_ATTRIBUTES.equals(name))
				attributesNode = new PerDirectoryAttributesNode(e);
			if (i != o)
				entries[o] = e;
			e.encodeName(nameEncoder);
			o++;
		}
		entryCnt = o;
		Arrays.sort(entries

		contentIdFromPtr = -1;
		ptr = 0;
		if (!eof())
			parseEntry();
			pathLen = pathOffset;
	}

	protected Entry current() {
		return entries[ptr];
	}

	public enum MetadataDiff {
		EQUAL

		DIFFER_BY_METADATA

		SMUDGED

		DIFFER_BY_TIMESTAMP
	}

	public boolean isModeDifferent(int rawMode) {
		int modeDiff = getEntryRawMode() ^ rawMode;

		if (modeDiff == 0)
			return false;

		if (getOptions().getSymLinks() == SymLinks.FALSE)
			if (FileMode.SYMLINK.equals(rawMode))
				return false;

		if (!state.options.isFileMode())
			modeDiff &= ~FileMode.EXECUTABLE_FILE.getBits();
		return modeDiff != 0;
	}

	public MetadataDiff compareMetadata(DirCacheEntry entry) {
		if (entry.isAssumeValid())
			return MetadataDiff.EQUAL;

		if (entry.isUpdateNeeded())
			return MetadataDiff.DIFFER_BY_METADATA;

		if (isModeDifferent(entry.getRawMode()))
			return MetadataDiff.DIFFER_BY_METADATA;

		int type = mode & FileMode.TYPE_MASK;
		if (type == FileMode.TYPE_TREE || type == FileMode.TYPE_GITLINK)
			return MetadataDiff.EQUAL;

		if (!entry.isSmudged() && entry.getLength() != (int) getEntryLength())
			return MetadataDiff.DIFFER_BY_METADATA;

		long cacheLastModified = entry.getLastModified();
		long fileLastModified = getEntryLastModified();
		long lastModifiedMillis = fileLastModified % 1000;
		long cacheMillis = cacheLastModified % 1000;
		if (getOptions().getCheckStat() == CheckStat.MINIMAL) {
			fileLastModified = fileLastModified - lastModifiedMillis;
			cacheLastModified = cacheLastModified - cacheMillis;
		} else if (cacheMillis == 0)
			fileLastModified = fileLastModified - lastModifiedMillis;
		else if (lastModifiedMillis == 0)
			cacheLastModified = cacheLastModified - cacheMillis;

		if (fileLastModified != cacheLastModified)
			return MetadataDiff.DIFFER_BY_TIMESTAMP;
		else if (!entry.isSmudged())
			return MetadataDiff.EQUAL;
		else
			return MetadataDiff.SMUDGED;
	}

	public boolean isModified(DirCacheEntry entry
			ObjectReader reader) throws IOException {
		if (entry == null)
			return !FileMode.MISSING.equals(getEntryFileMode());
		MetadataDiff diff = compareMetadata(entry);
		switch (diff) {
		case DIFFER_BY_TIMESTAMP:
			if (forceContentCheck)
				return contentCheck(entry
			else
				return true;
		case SMUDGED:
			return contentCheck(entry
		case EQUAL:
			if (mode == FileMode.SYMLINK.getBits()) {
				return contentCheck(entry
			}
			return false;
		case DIFFER_BY_METADATA:
			if (mode == FileMode.TREE.getBits()
					&& entry.getFileMode().equals(FileMode.GITLINK)) {
				byte[] idBuffer = idBuffer();
				int idOffset = idOffset();
				if (entry.getObjectId().compareTo(idBuffer
					return true;
				} else if (ObjectId.zeroId().compareTo(idBuffer
						idOffset) == 0) {
					return new File(repository.getWorkTree()
							entry.getPathString()).list().length > 0;
				}
				return false;
			} else if (mode == FileMode.SYMLINK.getBits())
				return contentCheck(entry
			return true;
		default:
			throw new IllegalStateException(MessageFormat.format(
					JGitText.get().unexpectedCompareResult
		}
	}

	public FileMode getIndexFileMode(DirCacheIterator indexIter) {
		final FileMode wtMode = getEntryFileMode();
		if (indexIter == null) {
			return wtMode;
		}
		final FileMode iMode = indexIter.getEntryFileMode();
		if (getOptions().isFileMode() && iMode != FileMode.GITLINK && iMode != FileMode.TREE) {
			return wtMode;
		}
		if (!getOptions().isFileMode()) {
			if (FileMode.REGULAR_FILE == wtMode
					&& FileMode.EXECUTABLE_FILE == iMode) {
				return iMode;
			}
			if (FileMode.EXECUTABLE_FILE == wtMode
					&& FileMode.REGULAR_FILE == iMode) {
				return iMode;
			}
		}
		if (FileMode.GITLINK == iMode
				&& FileMode.TREE == wtMode && !getOptions().isDirNoGitLinks()) {
			return iMode;
		}
		if (FileMode.TREE == iMode
				&& FileMode.GITLINK == wtMode) {
			return iMode;
		}
		return wtMode;
	}

	private boolean contentCheck(DirCacheEntry entry
			throws IOException {
		if (getEntryObjectId().equals(entry.getObjectId())) {

			entry.setLength((int) getEntryLength());

			return false;
		} else {
			if (mode == FileMode.SYMLINK.getBits()) {
				return !new File(readSymlinkTarget(current())).equals(
						new File(readContentAsNormalizedString(entry
			}
				return true;

			switch (getEolStreamType()) {
			case DIRECT:
				return true;
			default:
				try {
					ObjectLoader loader = reader.open(entry.getObjectId());
					if (loader == null)
						return true;

					long dcInLen;
					try (InputStream dcIn = new AutoLFInputStream(
							loader.openStream()
						dcInLen = computeLength(dcIn);
					} catch (AutoLFInputStream.IsBinaryException e) {
						return true;
					}

					try (InputStream dcIn = new AutoLFInputStream(
							loader.openStream()
						byte[] autoCrLfHash = computeHash(dcIn
						boolean changed = getEntryObjectId()
								.compareTo(autoCrLfHash
						return changed;
					}
				} catch (IOException e) {
					return true;
				}
			}
		}
	}

	private static String readContentAsNormalizedString(DirCacheEntry entry
			ObjectReader reader) throws MissingObjectException
		ObjectLoader open = reader.open(entry.getObjectId());
		byte[] cachedBytes = open.getCachedBytes();
		return FS.detect().normalize(RawParseUtils.decode(cachedBytes));
	}

	protected String readSymlinkTarget(Entry entry) throws IOException {
		if (!entry.getMode().equals(FileMode.SYMLINK)) {
			throw new java.nio.file.NotLinkException(entry.getName());
		}
		long length = entry.getLength();
		byte[] content = new byte[(int) length];
		try (InputStream is = entry.openInputStream()) {
			int bytesRead = IO.readFully(is
			return FS.detect()
					.normalize(RawParseUtils.decode(content
		}
	}

	private static long computeLength(InputStream in) throws IOException {
		long length = 0;
		for (;;) {
			long n = in.skip(1 << 20);
			if (n <= 0)
				break;
			length += n;
		}
		return length;
	}

	private byte[] computeHash(InputStream in
		SHA1 contentDigest = SHA1.newInstance();
		final byte[] contentReadBuffer = state.contentReadBuffer;

		contentDigest.update(hblob);
		contentDigest.update((byte) ' ');

		long sz = length;
		if (sz == 0) {
			contentDigest.update((byte) '0');
		} else {
			final int bufn = contentReadBuffer.length;
			int p = bufn;
			do {
				contentReadBuffer[--p] = digits[(int) (sz % 10)];
				sz /= 10;
			} while (sz > 0);
			contentDigest.update(contentReadBuffer
		}
		contentDigest.update((byte) 0);

		for (;;) {
			final int r = in.read(contentReadBuffer);
			if (r <= 0)
				break;
			contentDigest.update(contentReadBuffer
			sz += r;
		}
		if (sz != length)
			return zeroid;
		return contentDigest.digest();
	}

	public static abstract class Entry {
		byte[] encodedName;

		int encodedNameLen;

		void encodeName(CharsetEncoder enc) {
			final ByteBuffer b;
			try {
				b = enc.encode(CharBuffer.wrap(getName()));
			} catch (CharacterCodingException e) {
				throw new RuntimeException(MessageFormat.format(
						JGitText.get().unencodeableFile
			}

			encodedNameLen = b.limit();
			if (b.hasArray() && b.arrayOffset() == 0)
				encodedName = b.array();
			else
				b.get(encodedName = new byte[encodedNameLen]);
		}

		@Override
		public String toString() {
		}

		public abstract FileMode getMode();

		public abstract long getLength();

		public abstract long getLastModified();

		public abstract String getName();

		public abstract InputStream openInputStream() throws IOException;
	}

	private static class PerDirectoryIgnoreNode extends IgnoreNode {
		final Entry entry;

		PerDirectoryIgnoreNode(Entry entry) {
			super(Collections.<FastIgnoreRule> emptyList());
			this.entry = entry;
		}

		IgnoreNode load() throws IOException {
			IgnoreNode r = new IgnoreNode();
			try (InputStream in = entry.openInputStream()) {
				r.parse(in);
			}
			return r.getRules().isEmpty() ? null : r;
		}
	}

	private static class RootIgnoreNode extends PerDirectoryIgnoreNode {
		final Repository repository;

		RootIgnoreNode(Entry entry
			super(entry);
			this.repository = repository;
		}

		@Override
		IgnoreNode load() throws IOException {
			IgnoreNode r;
			if (entry != null) {
				r = super.load();
				if (r == null)
					r = new IgnoreNode();
			} else {
				r = new IgnoreNode();
			}

			FS fs = repository.getFS();
			String path = repository.getConfig().get(CoreConfig.KEY)
					.getExcludesFile();
			if (path != null) {
				File excludesfile;
					excludesfile = fs.resolve(fs.userHome()
				else
					excludesfile = fs.resolve(null
				loadRulesFromFile(r
			}

			File exclude = fs.resolve(repository.getDirectory()
					Constants.INFO_EXCLUDE);
			loadRulesFromFile(r

			return r.getRules().isEmpty() ? null : r;
		}

		private static void loadRulesFromFile(IgnoreNode r
				throws FileNotFoundException
			if (FS.DETECTED.exists(exclude)) {
				try (FileInputStream in = new FileInputStream(exclude)) {
					r.parse(in);
				}
			}
		}
	}

	private static class PerDirectoryAttributesNode extends AttributesNode {
		final Entry entry;

		PerDirectoryAttributesNode(Entry entry) {
			super(Collections.<AttributesRule> emptyList());
			this.entry = entry;
		}

		AttributesNode load() throws IOException {
			AttributesNode r = new AttributesNode();
			try (InputStream in = entry.openInputStream()) {
				r.parse(in);
			}
			return r.getRules().isEmpty() ? null : r;
		}
	}


	private static final class IteratorState {
		final WorkingTreeOptions options;

		final CharsetEncoder nameEncoder;

		byte[] contentReadBuffer;

		TreeWalk walk;

		int dirCacheTree = -1;

		boolean walkIgnored = false;

		final Map<String

		IteratorState(WorkingTreeOptions options) {
			this.options = options;
			this.nameEncoder = UTF_8.newEncoder();
		}

		void initializeReadBuffer() {
			if (contentReadBuffer == null) {
				contentReadBuffer = new byte[BUFFER_SIZE];
			}
		}
	}

	public String getCleanFilterCommand() throws IOException {
		if (cleanFilterCommandHolder == null) {
			String cmd = null;
			if (state.walk != null) {
				cmd = state.walk
						.getFilterCommand(Constants.ATTR_FILTER_TYPE_CLEAN);
			}
			cleanFilterCommandHolder = new Holder<>(cmd);
		}
		return cleanFilterCommandHolder.get();
	}

	public EolStreamType getEolStreamType() throws IOException {
		return getEolStreamType(null);
	}

	private EolStreamType getEolStreamType(OperationType opType)
			throws IOException {
		if (eolStreamTypeHolder == null) {
			EolStreamType type = null;
			if (state.walk != null) {
				type = state.walk.getEolStreamType(opType);
				OperationType operationType = opType != null ? opType
						: state.walk.getOperationType();
				if (OperationType.CHECKIN_OP.equals(operationType)
						&& EolStreamType.AUTO_LF.equals(type)
						&& hasCrLfInIndex(getDirCacheIterator())) {
					type = EolStreamType.DIRECT;
				}
			} else {
				switch (getOptions().getAutoCRLF()) {
				case FALSE:
					type = EolStreamType.DIRECT;
					break;
				case TRUE:
				case INPUT:
					type = EolStreamType.AUTO_LF;
					break;
				}
			}
			eolStreamTypeHolder = new Holder<>(type);
		}
		return eolStreamTypeHolder.get();
	}

	private boolean hasCrLfInIndex(DirCacheIterator dirCache) {
		if (dirCache == null) {
			return false;
		}
		DirCacheEntry entry = dirCache.getDirCacheEntry();
		if (FileMode.REGULAR_FILE.equals(entry.getFileMode())) {
			ObjectId blobId = entry.getObjectId();
			if (entry.getStage() > 0
					&& entry.getStage() != DirCacheEntry.STAGE_2) {
				byte[] name = entry.getRawPath();
				int i = 0;
				while (!dirCache.eof()) {
					dirCache.next(1);
					i++;
					entry = dirCache.getDirCacheEntry();
					if (!Arrays.equals(name
						break;
					}
					if (entry.getStage() == DirCacheEntry.STAGE_2) {
						blobId = entry.getObjectId();
						break;
					}
				}
				dirCache.back(i);
			}
			try (ObjectReader reader = repository.newObjectReader()) {
				ObjectLoader loader = reader.open(blobId
				try {
					return RawText.isCrLfText(loader.getCachedBytes());
				} catch (LargeObjectException e) {
					try (InputStream in = loader.openStream()) {
						return RawText.isCrLfText(in);
					}
				}
			} catch (IOException e) {
			}
		}
		return false;
	}

	private boolean isDirectoryIgnored(String pathRel) throws IOException {
		final int pOff = 0 < pathOffset ? pathOffset - 1 : pathOffset;
		final String base = TreeWalk.pathOf(this.path
		final String pathAbs = concatPath(base
		return isDirectoryIgnored(pathRel
	}

	private boolean isDirectoryIgnored(String pathRel
			throws IOException {
		assert pathRel.length() == 0 || (pathRel.charAt(0) != '/'
				&& pathRel.charAt(pathRel.length() - 1) != '/');
		assert pathAbs.length() == 0 || (pathAbs.charAt(0) != '/'
				&& pathAbs.charAt(pathAbs.length() - 1) != '/');
		assert pathAbs.endsWith(pathRel);

		Boolean ignored = state.directoryToIgnored.get(pathAbs);
		if (ignored != null) {
			return ignored.booleanValue();
		}

		final String parentRel = getParentPath(pathRel);
		if (parentRel != null && isDirectoryIgnored(parentRel)) {
			state.directoryToIgnored.put(pathAbs
			return true;
		}

		final IgnoreNode node = getIgnoreNode();
		for (String p = pathRel; node != null
			ignored = node.checkIgnored(p
			if (ignored != null) {
				state.directoryToIgnored.put(pathAbs
				return ignored.booleanValue();
			}
		}

		if (!(this.parent instanceof WorkingTreeIterator)) {
			state.directoryToIgnored.put(pathAbs
			return false;
		}

		final WorkingTreeIterator wtParent = (WorkingTreeIterator) this.parent;
		final String parentRelPath = concatPath(
				TreeWalk.pathOf(this.path
				pathRel);
		assert concatPath(TreeWalk.pathOf(wtParent.path
				Math.max(0
						.equals(pathAbs);
		return wtParent.isDirectoryIgnored(parentRelPath
	}

	private static String getParentPath(String path) {
		final int slashIndex = path.lastIndexOf('/'
		if (slashIndex > 0) {
			return path.substring(path.charAt(0) == '/' ? 1 : 0
		}
	}

	private static String concatPath(String p1
	}
}
