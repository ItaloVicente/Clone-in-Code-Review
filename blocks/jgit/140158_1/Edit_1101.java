
package org.eclipse.jgit.diff;

import static org.eclipse.jgit.diff.DiffEntry.ChangeType.ADD;
import static org.eclipse.jgit.diff.DiffEntry.ChangeType.COPY;
import static org.eclipse.jgit.diff.DiffEntry.ChangeType.DELETE;
import static org.eclipse.jgit.diff.DiffEntry.ChangeType.MODIFY;
import static org.eclipse.jgit.diff.DiffEntry.ChangeType.RENAME;
import static org.eclipse.jgit.diff.DiffEntry.Side.NEW;
import static org.eclipse.jgit.diff.DiffEntry.Side.OLD;
import static org.eclipse.jgit.lib.Constants.encode;
import static org.eclipse.jgit.lib.Constants.encodeASCII;
import static org.eclipse.jgit.lib.FileMode.GITLINK;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.jgit.diff.DiffAlgorithm.SupportedAlgorithm;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.BinaryBlobException;
import org.eclipse.jgit.errors.CancelledException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.patch.FileHeader;
import org.eclipse.jgit.patch.FileHeader.PatchType;
import org.eclipse.jgit.revwalk.FollowFilter;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.pack.PackConfig;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.EmptyTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.WorkingTreeIterator;
import org.eclipse.jgit.treewalk.filter.AndTreeFilter;
import org.eclipse.jgit.treewalk.filter.IndexDiffFilter;
import org.eclipse.jgit.treewalk.filter.NotIgnoredFilter;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.eclipse.jgit.util.LfsFactory;
import org.eclipse.jgit.util.QuotedString;

public class DiffFormatter implements AutoCloseable {
	private static final int DEFAULT_BINARY_FILE_THRESHOLD = PackConfig.DEFAULT_BIG_FILE_THRESHOLD;


	private static final byte[] EMPTY = new byte[] {};

	private final OutputStream out;

	private ObjectReader reader;

	private boolean closeReader;

	private DiffConfig diffCfg;

	private int context = 3;

	private int abbreviationLength = 7;

	private DiffAlgorithm diffAlgorithm;

	private RawTextComparator comparator = RawTextComparator.DEFAULT;

	private int binaryFileThreshold = DEFAULT_BINARY_FILE_THRESHOLD;



	private TreeFilter pathFilter = TreeFilter.ALL;

	private RenameDetector renameDetector;

	private ProgressMonitor progressMonitor;

	private ContentSource.Pair source;

	private Repository repository;

	public DiffFormatter(OutputStream out) {
		this.out = out;
	}

	protected OutputStream getOutputStream() {
		return out;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
		setReader(repository.newObjectReader()
	}

	public void setReader(ObjectReader reader
		setReader(reader
	}

	private void setReader(ObjectReader reader
		close();
		this.closeReader = closeReader;
		this.reader = reader;
		this.diffCfg = cfg.get(DiffConfig.KEY);

		ContentSource cs = ContentSource.create(reader);
		source = new ContentSource.Pair(cs

		if (diffCfg.isNoPrefix()) {
		}
		setDetectRenames(diffCfg.isRenameDetectionEnabled());

		diffAlgorithm = DiffAlgorithm.getAlgorithm(cfg.getEnum(
				ConfigConstants.CONFIG_DIFF_SECTION
				ConfigConstants.CONFIG_KEY_ALGORITHM
				SupportedAlgorithm.HISTOGRAM));
	}

	public void setContext(int lineCount) {
		if (lineCount < 0)
			throw new IllegalArgumentException(
					JGitText.get().contextMustBeNonNegative);
		context = lineCount;
	}

	public void setAbbreviationLength(int count) {
		if (count < 0)
			throw new IllegalArgumentException(
					JGitText.get().abbreviationLengthMustBeNonNegative);
		abbreviationLength = count;
	}

	public void setDiffAlgorithm(DiffAlgorithm alg) {
		diffAlgorithm = alg;
	}

	public void setDiffComparator(RawTextComparator cmp) {
		comparator = cmp;
	}

	public void setBinaryFileThreshold(int threshold) {
		this.binaryFileThreshold = threshold;
	}

	public void setOldPrefix(String prefix) {
		oldPrefix = prefix;
	}

	public String getOldPrefix() {
		return this.oldPrefix;
	}

	public void setNewPrefix(String prefix) {
		newPrefix = prefix;
	}

	public String getNewPrefix() {
		return this.newPrefix;
	}

	public boolean isDetectRenames() {
		return renameDetector != null;
	}

	public void setDetectRenames(boolean on) {
		if (on && renameDetector == null) {
			assertHaveReader();
			renameDetector = new RenameDetector(reader
		} else if (!on)
			renameDetector = null;
	}

	public RenameDetector getRenameDetector() {
		return renameDetector;
	}

	public void setProgressMonitor(ProgressMonitor pm) {
		progressMonitor = pm;
	}

	public void setPathFilter(TreeFilter filter) {
		pathFilter = filter != null ? filter : TreeFilter.ALL;
	}

	public TreeFilter getPathFilter() {
		return pathFilter;
	}

	public void flush() throws IOException {
		out.flush();
	}

	@Override
	public void close() {
		if (reader != null && closeReader) {
			reader.close();
		}
	}

	public List<DiffEntry> scan(AnyObjectId a
			throws IOException {
		assertHaveReader();

		try (RevWalk rw = new RevWalk(reader)) {
			RevTree aTree = a != null ? rw.parseTree(a) : null;
			RevTree bTree = b != null ? rw.parseTree(b) : null;
			return scan(aTree
		}
	}

	public List<DiffEntry> scan(RevTree a
		assertHaveReader();

		AbstractTreeIterator aIterator = makeIteratorFromTreeOrNull(a);
		AbstractTreeIterator bIterator = makeIteratorFromTreeOrNull(b);
		return scan(aIterator
	}

	private AbstractTreeIterator makeIteratorFromTreeOrNull(RevTree tree)
			throws IncorrectObjectTypeException
		if (tree != null) {
			CanonicalTreeParser parser = new CanonicalTreeParser();
			parser.reset(reader
			return parser;
		} else
			return new EmptyTreeIterator();
	}

	public List<DiffEntry> scan(AbstractTreeIterator a
			throws IOException {
		assertHaveReader();

		TreeWalk walk = new TreeWalk(reader);
		walk.addTree(a);
		walk.addTree(b);
		walk.setRecursive(true);

		TreeFilter filter = getDiffTreeFilterFor(a
		if (pathFilter instanceof FollowFilter) {
			walk.setFilter(AndTreeFilter.create(
					PathFilter.create(((FollowFilter) pathFilter).getPath())
					filter));
		} else {
			walk.setFilter(AndTreeFilter.create(pathFilter
		}

		source = new ContentSource.Pair(source(a)

		List<DiffEntry> files = DiffEntry.scan(walk);
		if (pathFilter instanceof FollowFilter && isAdd(files)) {
			a.reset();
			b.reset();
			walk.reset();
			walk.addTree(a);
			walk.addTree(b);
			walk.setFilter(filter);

			if (renameDetector == null)
				setDetectRenames(true);
			files = updateFollowFilter(detectRenames(DiffEntry.scan(walk)));

		} else if (renameDetector != null)
			files = detectRenames(files);

		return files;
	}

	private static TreeFilter getDiffTreeFilterFor(AbstractTreeIterator a
			AbstractTreeIterator b) {
		if (a instanceof DirCacheIterator && b instanceof WorkingTreeIterator)
			return new IndexDiffFilter(0

		if (a instanceof WorkingTreeIterator && b instanceof DirCacheIterator)
			return new IndexDiffFilter(1

		TreeFilter filter = TreeFilter.ANY_DIFF;
		if (a instanceof WorkingTreeIterator)
			filter = AndTreeFilter.create(new NotIgnoredFilter(0)
		if (b instanceof WorkingTreeIterator)
			filter = AndTreeFilter.create(new NotIgnoredFilter(1)
		return filter;
	}

	private ContentSource source(AbstractTreeIterator iterator) {
		if (iterator instanceof WorkingTreeIterator)
			return ContentSource.create((WorkingTreeIterator) iterator);
		return ContentSource.create(reader);
	}

	private List<DiffEntry> detectRenames(List<DiffEntry> files)
			throws IOException {
		renameDetector.reset();
		renameDetector.addAll(files);
		try {
			return renameDetector.compute(reader
		} catch (CancelledException e) {
			return Collections.emptyList();
		}
	}

	private boolean isAdd(List<DiffEntry> files) {
		String oldPath = ((FollowFilter) pathFilter).getPath();
		for (DiffEntry ent : files) {
			if (ent.getChangeType() == ADD && ent.getNewPath().equals(oldPath))
				return true;
		}
		return false;
	}

	private List<DiffEntry> updateFollowFilter(List<DiffEntry> files) {
		String oldPath = ((FollowFilter) pathFilter).getPath();
		for (DiffEntry ent : files) {
			if (isRename(ent) && ent.getNewPath().equals(oldPath)) {
				pathFilter = FollowFilter.create(ent.getOldPath()
				return Collections.singletonList(ent);
			}
		}
		return Collections.emptyList();
	}

	private static boolean isRename(DiffEntry ent) {
		return ent.getChangeType() == RENAME || ent.getChangeType() == COPY;
	}

	public void format(AnyObjectId a
		format(scan(a
	}

	public void format(RevTree a
		format(scan(a
	}

	public void format(AbstractTreeIterator a
			throws IOException {
		format(scan(a
	}

	public void format(List<? extends DiffEntry> entries) throws IOException {
		for (DiffEntry ent : entries)
			format(ent);
	}

	public void format(DiffEntry ent) throws IOException {
		FormatResult res = createFormatResult(ent);
		format(res.header
	}

	private static byte[] writeGitLinkText(AbbreviatedObjectId id) {
		if (ObjectId.zeroId().equals(id.toObjectId())) {
			return EMPTY;
		}
	}

	private String format(AbbreviatedObjectId id) {
		if (id.isComplete() && reader != null) {
			try {
				id = reader.abbreviate(id.toObjectId()
			} catch (IOException cannotAbbreviate) {
			}
		}
		return id.name();
	}

	private static String quotePath(String name) {
		return QuotedString.GIT_PATH.quote(name);
	}

	public void format(FileHeader head
			throws IOException {
		final int start = head.getStartOffset();
		int end = head.getEndOffset();
		if (!head.getHunks().isEmpty())
			end = head.getHunks().get(0).getStartOffset();
		out.write(head.getBuffer()
		if (head.getPatchType() == PatchType.UNIFIED)
			format(head.toEditList()
	}

	public void format(EditList edits
			throws IOException {
		for (int curIdx = 0; curIdx < edits.size();) {
			Edit curEdit = edits.get(curIdx);
			final int endIdx = findCombinedEnd(edits
			final Edit endEdit = edits.get(endIdx);

			int aCur = (int) Math.max(0
			int bCur = (int) Math.max(0
			final int aEnd = (int) Math.min(a.size()
			final int bEnd = (int) Math.min(b.size()

			writeHunkHeader(aCur

			while (aCur < aEnd || bCur < bEnd) {
				if (aCur < curEdit.getBeginA() || endIdx + 1 < curIdx) {
					writeContextLine(a
					if (isEndOfLineMissing(a
						out.write(noNewLine);
					aCur++;
					bCur++;
				} else if (aCur < curEdit.getEndA()) {
					writeRemovedLine(a
					if (isEndOfLineMissing(a
						out.write(noNewLine);
					aCur++;
				} else if (bCur < curEdit.getEndB()) {
					writeAddedLine(b
					if (isEndOfLineMissing(b
						out.write(noNewLine);
					bCur++;
				}

				if (end(curEdit
					curEdit = edits.get(curIdx);
			}
		}
	}

	protected void writeContextLine(RawText text
			throws IOException {
		writeLine(' '
	}

	private static boolean isEndOfLineMissing(RawText text
		return line + 1 == text.size() && text.isMissingNewlineAtEnd();
	}

	protected void writeAddedLine(RawText text
			throws IOException {
		writeLine('+'
	}

	protected void writeRemovedLine(RawText text
			throws IOException {
		writeLine('-'
	}

	protected void writeHunkHeader(int aStartLine
			int bStartLine
		out.write('@');
		out.write('@');
		writeRange('-'
		writeRange('+'
		out.write(' ');
		out.write('@');
		out.write('@');
		out.write('\n');
	}

	private void writeRange(char prefix
			throws IOException {
		out.write(' ');
		out.write(prefix);
		switch (cnt) {
		case 0:
			out.write(encodeASCII(begin - 1));
			out.write('
			out.write('0');
			break;

		case 1:
			out.write(encodeASCII(begin));
			break;

		default:
			out.write(encodeASCII(begin));
			out.write('
			out.write(encodeASCII(cnt));
			break;
		}
	}

	protected void writeLine(final char prefix
			final int cur) throws IOException {
		out.write(prefix);
		text.writeLine(out
		out.write('\n');
	}

	public FileHeader toFileHeader(DiffEntry ent) throws IOException
			CorruptObjectException
		return createFormatResult(ent).header;
	}

	private static class FormatResult {
		FileHeader header;

		RawText a;

		RawText b;
	}

	private FormatResult createFormatResult(DiffEntry ent) throws IOException
			CorruptObjectException
		final FormatResult res = new FormatResult();
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		final EditList editList;
		final FileHeader.PatchType type;

		formatHeader(buf

		if (ent.getOldId() == null || ent.getNewId() == null) {
			editList = new EditList();
			type = PatchType.UNIFIED;
			res.header = new FileHeader(buf.toByteArray()
			return res;
		}

		assertHaveReader();

		RawText aRaw = null;
		RawText bRaw = null;
		if (ent.getOldMode() == GITLINK || ent.getNewMode() == GITLINK) {
			aRaw = new RawText(writeGitLinkText(ent.getOldId()));
			bRaw = new RawText(writeGitLinkText(ent.getNewId()));
		} else {
			try {
				aRaw = open(OLD
				bRaw = open(NEW
			} catch (BinaryBlobException e) {
				formatOldNewPaths(buf
				editList = new EditList();
				type = PatchType.BINARY;
				res.header = new FileHeader(buf.toByteArray()
				return res;
			}
		}

		res.a = aRaw;
		res.b = bRaw;
		editList = diff(res.a
		type = PatchType.UNIFIED;

		switch (ent.getChangeType()) {
			case RENAME:
			case COPY:
				if (!editList.isEmpty())
					formatOldNewPaths(buf
				break;

			default:
				formatOldNewPaths(buf
				break;
		}


		res.header = new FileHeader(buf.toByteArray()
		return res;
	}

	private EditList diff(RawText a
		return diffAlgorithm.diff(comparator
	}

	private void assertHaveReader() {
		if (reader == null) {
			throw new IllegalStateException(JGitText.get().readerIsRequired);
		}
	}

	private RawText open(DiffEntry.Side side
			throws IOException
		if (entry.getMode(side) == FileMode.MISSING)
			return RawText.EMPTY_TEXT;

		if (entry.getMode(side).getObjectType() != Constants.OBJ_BLOB)
			return RawText.EMPTY_TEXT;

		AbbreviatedObjectId id = entry.getId(side);
		if (!id.isComplete()) {
			Collection<ObjectId> ids = reader.resolve(id);
			if (ids.size() == 1) {
				id = AbbreviatedObjectId.fromObjectId(ids.iterator().next());
				switch (side) {
				case OLD:
					entry.oldId = id;
					break;
				case NEW:
					entry.newId = id;
					break;
				}
			} else if (ids.isEmpty())
				throw new MissingObjectException(id
			else
				throw new AmbiguousObjectException(id
		}

		ObjectLoader ldr = LfsFactory.getInstance().applySmudgeFilter(repository
				source.open(side
		return RawText.load(ldr
	}

	protected void formatGitDiffFirstHeaderLine(ByteArrayOutputStream o
			final ChangeType type
			throws IOException {
		o.write(encode(quotePath(oldPrefix + (type == ADD ? newPath : oldPath))));
		o.write(' ');
		o.write(encode(quotePath(newPrefix
				+ (type == DELETE ? oldPath : newPath))));
		o.write('\n');
	}

	private void formatHeader(ByteArrayOutputStream o
			throws IOException {
		final ChangeType type = ent.getChangeType();
		final String oldp = ent.getOldPath();
		final String newp = ent.getNewPath();
		final FileMode oldMode = ent.getOldMode();
		final FileMode newMode = ent.getNewMode();

		formatGitDiffFirstHeaderLine(o

		if ((type == MODIFY || type == COPY || type == RENAME)
				&& !oldMode.equals(newMode)) {
			oldMode.copyTo(o);
			o.write('\n');

			newMode.copyTo(o);
			o.write('\n');
		}

		switch (type) {
		case ADD:
			newMode.copyTo(o);
			o.write('\n');
			break;

		case DELETE:
			oldMode.copyTo(o);
			o.write('\n');
			break;

		case RENAME:
			o.write('\n');

			o.write('\n');

			o.write('\n');
			break;

		case COPY:
			o.write('\n');

			o.write('\n');

			o.write('\n');
			break;

		case MODIFY:
			if (0 < ent.getScore()) {
				o.write('\n');
			}
			break;
		}

		if (ent.getOldId() != null && !ent.getOldId().equals(ent.getNewId())) {
			formatIndexLine(o
		}
	}

	protected void formatIndexLine(OutputStream o
			throws IOException {
				+ format(ent.getNewId())));
		if (ent.getOldMode().equals(ent.getNewMode())) {
			o.write(' ');
			ent.getNewMode().copyTo(o);
		}
		o.write('\n');
	}

	private void formatOldNewPaths(ByteArrayOutputStream o
			throws IOException {
		if (ent.oldId.equals(ent.newId))
			return;

		final String oldp;
		final String newp;

		switch (ent.getChangeType()) {
		case ADD:
			oldp = DiffEntry.DEV_NULL;
			newp = quotePath(newPrefix + ent.getNewPath());
			break;

		case DELETE:
			oldp = quotePath(oldPrefix + ent.getOldPath());
			newp = DiffEntry.DEV_NULL;
			break;

		default:
			oldp = quotePath(oldPrefix + ent.getOldPath());
			newp = quotePath(newPrefix + ent.getNewPath());
			break;
		}

	}

	private int findCombinedEnd(List<Edit> edits
		int end = i + 1;
		while (end < edits.size()
				&& (combineA(edits
			end++;
		return end - 1;
	}

	private boolean combineA(List<Edit> e
		return e.get(i).getBeginA() - e.get(i - 1).getEndA() <= 2 * context;
	}

	private boolean combineB(List<Edit> e
		return e.get(i).getBeginB() - e.get(i - 1).getEndB() <= 2 * context;
	}

	private static boolean end(Edit edit
		return edit.getEndA() <= a && edit.getEndB() <= b;
	}
}
