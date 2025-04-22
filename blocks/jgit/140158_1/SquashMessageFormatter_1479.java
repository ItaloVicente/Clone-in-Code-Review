package org.eclipse.jgit.merge;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.diff.DiffAlgorithm.SupportedAlgorithm.HISTOGRAM;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_DIFF_SECTION;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_ALGORITHM;
import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.attributes.Attributes;
import org.eclipse.jgit.diff.DiffAlgorithm;
import org.eclipse.jgit.diff.DiffAlgorithm.SupportedAlgorithm;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.diff.Sequence;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuildIterator;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.dircache.DirCacheCheckout.CheckoutMetadata;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.errors.BinaryBlobException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.IndexWriteException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.CoreConfig.EolStreamType;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.storage.pack.PackConfig;
import org.eclipse.jgit.submodule.SubmoduleConflict;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.NameConflictTreeWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.TreeWalk.OperationType;
import org.eclipse.jgit.treewalk.WorkingTreeIterator;
import org.eclipse.jgit.treewalk.WorkingTreeOptions;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.LfsFactory;
import org.eclipse.jgit.util.LfsFactory.LfsInputStream;
import org.eclipse.jgit.util.TemporaryBuffer;
import org.eclipse.jgit.util.io.EolStreamTypeUtil;

public class ResolveMerger extends ThreeWayMerger {
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

	protected WorkingTreeOptions workingTreeOptions;

	private int inCoreLimit;

	private Map<String

	private static MergeAlgorithm getMergeAlgorithm(Config config) {
		SupportedAlgorithm diffAlg = config.getEnum(
				CONFIG_DIFF_SECTION
				HISTOGRAM);
		return new MergeAlgorithm(DiffAlgorithm.getAlgorithm(diffAlg));
	}

	private static int getInCoreLimit(Config config) {
		return config.getInt(
				ConfigConstants.CONFIG_MERGE_SECTION
	}

	private static String[] defaultCommitNames() {
		return new String[] { "BASE"
	}

	private static final Attributes NO_ATTRIBUTES = new Attributes();

	protected ResolveMerger(Repository local
		super(local);
		Config config = local.getConfig();
		mergeAlgorithm = getMergeAlgorithm(config);
		inCoreLimit = getInCoreLimit(config);
		commitNames = defaultCommitNames();
		this.inCore = inCore;

		if (inCore) {
			implicitDirCache = false;
			dircache = DirCache.newInCore();
		} else {
			implicitDirCache = true;
			workingTreeOptions = local.getConfig().get(WorkingTreeOptions.KEY);
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
		if (implicitDirCache) {
			dircache = nonNullRepo().lockDirCache();
		}
		if (!inCore) {
			checkoutMetadata = new HashMap<>();
		}
		try {
			return mergeTrees(mergeBase()
					false);
		} finally {
			checkoutMetadata = null;
			if (implicitDirCache) {
				dircache.unlock();
			}
		}
	}

	private void checkout() throws NoWorkTreeException
		for (int i = toBeDeleted.size() - 1; i >= 0; i--) {
			String fileName = toBeDeleted.get(i);
			File f = new File(nonNullRepo().getWorkTree()
			if (!f.delete())
				if (!f.isDirectory())
					failingPaths.put(fileName
							MergeFailureReason.COULD_NOT_DELETE);
			modifiedFiles.add(fileName);
		}
		for (Map.Entry<String
				.entrySet()) {
			DirCacheEntry cacheEntry = entry.getValue();
			if (cacheEntry.getFileMode() == FileMode.GITLINK) {
				new File(nonNullRepo().getWorkTree()
			} else {
				DirCacheCheckout.checkoutEntry(db
						checkoutMetadata.get(entry.getKey()));
				modifiedFiles.add(entry.getKey());
			}
		}
	}

	protected void cleanUp() throws NoWorkTreeException
			CorruptObjectException
			IOException {
		if (inCore) {
			modifiedFiles.clear();
			return;
		}

		DirCache dc = nonNullRepo().readDirCache();
		Iterator<String> mpathsIt=modifiedFiles.iterator();
		while(mpathsIt.hasNext()) {
			String mpath = mpathsIt.next();
			DirCacheEntry entry = dc.getEntry(mpath);
			if (entry != null) {
				DirCacheCheckout.checkoutEntry(db
						checkoutMetadata.get(mpath));
			}
			mpathsIt.remove();
		}
	}

	private DirCacheEntry add(byte[] path
			long lastMod
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
		DirCacheEntry newEntry = new DirCacheEntry(e.getRawPath()
				e.getStage());
		newEntry.setFileMode(e.getFileMode());
		newEntry.setObjectId(e.getObjectId());
		newEntry.setLastModified(e.getLastModified());
		newEntry.setLength(e.getLength());
		builder.add(newEntry);
		return newEntry;
	}

	protected void addCheckoutMetadata(String path
			throws IOException {
		if (checkoutMetadata != null) {
			EolStreamType eol = EolStreamTypeUtil.detectStreamType(
					OperationType.CHECKOUT_OP
			CheckoutMetadata data = new CheckoutMetadata(eol
					tw.getFilterCommand(Constants.ATTR_FILTER_TYPE_SMUDGE));
			checkoutMetadata.put(path
		}
	}

	protected void addToCheckout(String path
			Attributes attributes) throws IOException {
		toBeCheckedOut.put(path
		addCheckoutMetadata(path
	}

	protected void addDeletion(String path
			Attributes attributes) throws IOException {
		toBeDeleted.add(path);
		if (isFile) {
			addCheckoutMetadata(path
		}
	}

	protected boolean processEntry(CanonicalTreeParser base
			CanonicalTreeParser ours
			DirCacheBuildIterator index
			boolean ignoreConflicts
			throws MissingObjectException
			CorruptObjectException
		enterSubtree = true;
		final int modeO = tw.getRawMode(T_OURS);
		final int modeT = tw.getRawMode(T_THEIRS);
		final int modeB = tw.getRawMode(T_BASE);

		if (modeO == 0 && modeT == 0 && modeB == 0)
			return true;

		if (isIndexDirty())
			return false;

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
							return false;
						DirCacheEntry e = add(tw.getRawPath()
								DirCacheEntry.STAGE_0
						addToCheckout(tw.getPathString()
					}
					return true;
				} else {
					add(tw.getRawPath()
					add(tw.getRawPath()
					add(tw.getRawPath()
					unmergedPaths.add(tw.getPathString());
					mergeResults.put(
							tw.getPathString()
							new MergeResult<>(Collections
									.<RawText> emptyList()));
				}
				return true;
			}
		}

		if (modeB == modeT && tw.idEqual(T_BASE
			if (ourDce != null)
				keep(ourDce);
			return true;
		}

		if (modeB == modeO && tw.idEqual(T_BASE

			if (isWorktreeDirty(work
				return false;
			if (nonTree(modeT)) {
				DirCacheEntry e = add(tw.getRawPath()
						DirCacheEntry.STAGE_0
				if (e != null) {
					addToCheckout(tw.getPathString()
				}
				return true;
			} else {
				if (tw.getTreeCount() > T_FILE && tw.getRawMode(T_FILE) == 0) {
					return true;
				}
				if (modeT != 0 && modeT == modeB) {
					return true;
				}
				addDeletion(tw.getPathString()
				return true;
			}
		}

		if (tw.isSubtree()) {
			if (nonTree(modeO) && !nonTree(modeT)) {
				if (nonTree(modeB))
					add(tw.getRawPath()
				add(tw.getRawPath()
				unmergedPaths.add(tw.getPathString());
				enterSubtree = false;
				return true;
			}
			if (nonTree(modeT) && !nonTree(modeO)) {
				if (nonTree(modeB))
					add(tw.getRawPath()
				add(tw.getRawPath()
				unmergedPaths.add(tw.getPathString());
				enterSubtree = false;
				return true;
			}

			if (!nonTree(modeO))
				return true;

		}

		if (nonTree(modeO) && nonTree(modeT)) {
			boolean worktreeDirty = isWorktreeDirty(work
			if (!attributes.canBeContentMerged() && worktreeDirty) {
				return false;
			}

			boolean gitlinkConflict = isGitLink(modeO) || isGitLink(modeT);
			if (gitlinkConflict || !attributes.canBeContentMerged()) {
				add(tw.getRawPath()
				add(tw.getRawPath()
				add(tw.getRawPath()

				if (gitlinkConflict) {
					MergeResult<SubmoduleConflict> result = new MergeResult<>(
							Arrays.asList(
									new SubmoduleConflict(base == null ? null
											: base.getEntryObjectId())
									new SubmoduleConflict(ours == null ? null
											: ours.getEntryObjectId())
									new SubmoduleConflict(theirs == null ? null
											: theirs.getEntryObjectId())));
					result.setContainsConflicts(true);
					mergeResults.put(tw.getPathString()
					if (!ignoreConflicts) {
						unmergedPaths.add(tw.getPathString());
					}
				} else {
					unmergedPaths.add(tw.getPathString());
				}
				return true;
			}

			if (worktreeDirty) {
				return false;
			}

			MergeResult<RawText> result = contentMerge(base
					attributes);
			if (ignoreConflicts) {
				result.setContainsConflicts(false);
			}
			updateIndex(base
			String currentPath = tw.getPathString();
			if (result.containsConflicts() && !ignoreConflicts) {
				unmergedPaths.add(currentPath);
			}
			modifiedFiles.add(currentPath);
			addCheckoutMetadata(currentPath
		} else if (modeO != modeT) {
			if (((modeO != 0 && !tw.idEqual(T_BASE
					.idEqual(T_BASE
				MergeResult<RawText> result = contentMerge(base
						attributes);

				add(tw.getRawPath()
				add(tw.getRawPath()
				DirCacheEntry e = add(tw.getRawPath()
						DirCacheEntry.STAGE_3

				if (modeO == 0) {
					if (isWorktreeDirty(work
						return false;
					if (nonTree(modeT)) {
						if (e != null) {
							addToCheckout(tw.getPathString()
						}
					}
				}

				unmergedPaths.add(tw.getPathString());

				mergeResults.put(tw.getPathString()
			}
		}
		return true;
	}

	private MergeResult<RawText> contentMerge(CanonicalTreeParser base
			CanonicalTreeParser ours
			Attributes attributes)
			throws IOException {
		RawText baseText;
		RawText ourText;
		RawText theirsText;

		try {
			baseText = base == null ? RawText.EMPTY_TEXT : getRawText(
							base.getEntryObjectId()
			ourText = ours == null ? RawText.EMPTY_TEXT : getRawText(
							ours.getEntryObjectId()
			theirsText = theirs == null ? RawText.EMPTY_TEXT : getRawText(
							theirs.getEntryObjectId()
		} catch (BinaryBlobException e) {
			MergeResult<RawText> r = new MergeResult<>(Collections.<RawText>emptyList());
			r.setContainsConflicts(true);
			return r;
		}
		return (mergeAlgorithm.merge(RawTextComparator.DEFAULT
				ourText
	}

	private boolean isIndexDirty() {
		if (inCore)
			return false;

		final int modeI = tw.getRawMode(T_INDEX);
		final int modeO = tw.getRawMode(T_OURS);

		final boolean isDirty = nonTree(modeI)
				&& !(modeO == modeI && tw.idEqual(T_INDEX
		if (isDirty)
			failingPaths
					.put(tw.getPathString()
		return isDirty;
	}

	private boolean isWorktreeDirty(WorkingTreeIterator work
			DirCacheEntry ourDce) throws IOException {
		if (work == null)
			return false;

		final int modeF = tw.getRawMode(T_FILE);
		final int modeO = tw.getRawMode(T_OURS);

		boolean isDirty;
		if (ourDce != null)
			isDirty = work.isModified(ourDce
		else {
			isDirty = work.isModeDifferent(modeO);
			if (!isDirty && nonTree(modeF))
				isDirty = !tw.idEqual(T_FILE
		}

		if (isDirty && modeF == FileMode.TYPE_TREE
				&& modeO == FileMode.TYPE_MISSING)
			isDirty = false;
		if (isDirty)
			failingPaths.put(tw.getPathString()
					MergeFailureReason.DIRTY_WORKTREE);
		return isDirty;
	}

	private void updateIndex(CanonicalTreeParser base
			CanonicalTreeParser ours
			MergeResult<RawText> result
			throws FileNotFoundException
			IOException {
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

			DirCacheEntry dce = new DirCacheEntry(tw.getPathString());

			int newMode = mergeFileModes(tw.getRawMode(0)
					tw.getRawMode(2));
			dce.setFileMode(newMode == FileMode.MISSING.getBits()
					? FileMode.REGULAR_FILE : FileMode.fromBits(newMode));
			if (mergedFile != null) {
				dce.setLastModified(
						nonNullRepo().getFS().lastModified(mergedFile));
				dce.setLength((int) mergedFile.length());
			}
			dce.setObjectId(insertMergeResult(rawMerged
			builder.add(dce);
		} finally {
			if (rawMerged != null) {
				rawMerged.destroy();
			}
		}
	}

	private File writeMergedFile(TemporaryBuffer rawMerged
			Attributes attributes)
			throws FileNotFoundException
		File workTree = nonNullRepo().getWorkTree();
		FS fs = nonNullRepo().getFS();
		File of = new File(workTree
		File parentFolder = of.getParentFile();
		if (!fs.exists(parentFolder)) {
			parentFolder.mkdirs();
		}
		EolStreamType streamType = EolStreamTypeUtil.detectStreamType(
				OperationType.CHECKOUT_OP
				attributes);
		try (OutputStream os = EolStreamTypeUtil.wrapOutputStream(
				new BufferedOutputStream(new FileOutputStream(of))
				streamType)) {
			rawMerged.writeTo(os
		}
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

	private ObjectId insertMergeResult(TemporaryBuffer buf
			Attributes attributes) throws IOException {
		InputStream in = buf.openInputStream();
		try (LfsInputStream is = LfsFactory.getInstance().applyCleanFilter(
				getRepository()
				buf.length()
			return getObjectInserter().insert(OBJ_BLOB
		}
	}

	private int mergeFileModes(int modeB
		if (modeO == modeT)
			return modeO;
		if (modeB == modeO)
			return (modeT == FileMode.MISSING.getBits()) ? modeO : modeT;
		if (modeB == modeT)
			return (modeO == FileMode.MISSING.getBits()) ? modeT : modeO;
		return FileMode.MISSING.getBits();
	}

	private RawText getRawText(ObjectId id
			Attributes attributes)
			throws IOException
		if (id.equals(ObjectId.zeroId()))
			return new RawText(new byte[] {});

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
		return modifiedFiles;
	}

	public Map<String
		return toBeCheckedOut;
	}

	public Map<String
		return mergeResults;
	}

	public Map<String
		return (failingPaths.isEmpty()) ? null : failingPaths;
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


	protected boolean mergeTrees(AbstractTreeIterator baseTree
			RevTree headTree
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
			throws IOException {
		boolean hasWorkingTreeIterator = tw.getTreeCount() > T_FILE;
		boolean hasAttributeNodeProvider = treeWalk
				.getAttributesNodeProvider() != null;
		while (treeWalk.next()) {
			if (!processEntry(
					treeWalk.getTree(T_BASE
					treeWalk.getTree(T_OURS
					treeWalk.getTree(T_THEIRS
					treeWalk.getTree(T_INDEX
					hasWorkingTreeIterator ? treeWalk.getTree(T_FILE
							WorkingTreeIterator.class) : null
					ignoreConflicts
							? treeWalk.getAttributes()
							: NO_ATTRIBUTES)) {
				cleanUp();
				return false;
			}
			if (treeWalk.isSubtree() && enterSubtree)
				treeWalk.enterSubtree();
		}
		return true;
	}
}
