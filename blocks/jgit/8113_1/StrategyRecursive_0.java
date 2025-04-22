
package org.eclipse.jgit.merge;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.diff.DiffAlgorithm;
import org.eclipse.jgit.diff.DiffAlgorithm.SupportedAlgorithm;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.diff.Sequence;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuildIterator;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.IndexWriteException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.EmptyTreeIterator;
import org.eclipse.jgit.treewalk.NameConflictTreeWalk;
import org.eclipse.jgit.treewalk.WorkingTreeIterator;
import org.eclipse.jgit.util.FileUtils;


 Recursive-merge implementation in CGit

public class RecursiveMerger extends ResolveMerger {
    public enum RecursiveMergeFailureReason {
	DIRTY_INDEX
	DIRTY_WORKTREE
	COULD_NOT_DELETE
	TOO_MANY_BASES
	RESOLVE_RECURSION_LOOP
    }

    private NameConflictTreeWalk tw;

    private String commitNames[];

    private static final int T_BASE = 0;

    private static final int T_OURS = 1;

    private static final int T_THEIRS = 2;

    private static final int T_INDEX = 3;

    private static final int T_FILE = 4;

    private DirCacheBuilder builder;

    private ObjectId resultTree;

    private List<String> unmergedPaths = new ArrayList<String>();

    private List<String> modifiedFiles = new LinkedList<String>();

    private Map<String

    private Map<String

    private Map<String

    private ObjectInserter oi;

    private boolean enterSubtree;

    private boolean inCore;

    private DirCache dircache;

    private WorkingTreeIterator workingTreeIterator;

    private MergeAlgorithm mergeAlgorithm;


    private final int MAX_BASES = 200;

    private int numBases = 0;

    private RevTree[] baseTrees;

    private AbstractTreeIterator[] baseTreeIterators;

    protected RecursiveMerger(Repository local
	super(local);
	SupportedAlgorithm diffAlg = local.getConfig().getEnum(
		ConfigConstants.CONFIG_DIFF_SECTION
		ConfigConstants.CONFIG_KEY_ALGORITHM
		SupportedAlgorithm.HISTOGRAM);
	mergeAlgorithm = new MergeAlgorithm(DiffAlgorithm.getAlgorithm(diffAlg));
	commitNames = new String[] { "BASE"
	oi = getObjectInserter();
	this.inCore = inCore;

	if (inCore) {
	    dircache = DirCache.newInCore();
	}
    }

    protected RecursiveMerger(Repository local) {
	this(local
    }

    @Override
    public boolean merge(final AnyObjectId... tips) throws IOException {
	if (tips.length > MAX_TIPS_TO_MERGE)
	    return false;
	return super.merge(tips);
    }

    private String tooManyMergeBasesFor = "Too many merge bases for:\n  %s\n  %s found:\n  count %s\n  %s";

    public List<RevCommit> getBaseCommits(final int aIdx
	    throws IncorrectObjectTypeException
	if (sourceCommits[aIdx] == null)
	    throw new IncorrectObjectTypeException(sourceObjects[aIdx]
		    Constants.TYPE_COMMIT);
	if (sourceCommits[bIdx] == null)
	    throw new IncorrectObjectTypeException(sourceObjects[bIdx]
		    Constants.TYPE_COMMIT);
	walk.reset();
	walk.setRevFilter(RevFilter.MERGE_BASE);
	walk.markStart(sourceCommits[aIdx]);
	walk.markStart(sourceCommits[bIdx]);

	List<RevCommit> bases = new ArrayList<RevCommit>();
	int commitIdx = 0;

	for (RevCommit base : walk) {
	    if (commitIdx < MAX_BASES) {
		bases.add(base);
		commitIdx++;
	    } else {
		throw new IOException(String.format(tooManyMergeBasesFor
			"TOO_MANY_BASES"
			sourceCommits[bIdx].name()
			base.name()));
	    }
	}

	this.numBases = commitIdx;
	return bases;
    }

    protected AbstractTreeIterator[] mergeBases() throws IOException {
	final int numsourceTrees = sourceTrees.length;
	int commitIdx = 0;

	RevTree tempTrees[] = new RevTree[MAX_BASES];
	AbstractTreeIterator tempIterators[] = new AbstractTreeIterator[MAX_BASES];

	if (baseTrees == null) {
	    baseTrees = new RevTree[MAX_BASES];
	    baseTreeIterators = new AbstractTreeIterator[MAX_BASES];
	}

	for (int aIdx = 0; aIdx < numsourceTrees; aIdx++) {
	    for (int bIdx = aIdx + 1; bIdx < numsourceTrees; bIdx++) {
		List<RevCommit> baseCommits = getBaseCommits(aIdx
		for (RevCommit baseCommit : baseCommits) {
		    if (commitIdx < MAX_BASES) {
			tempTrees[commitIdx] = baseCommit.getTree();
			tempIterators[commitIdx] = openTree(baseCommit
				.getTree());
			commitIdx++;
		    } else {
			throw new IOException(String.format(
				tooManyMergeBasesFor
				sourceCommits[aIdx].name()
				sourceCommits[bIdx].name()
				Integer.valueOf(commitIdx)
		    }
		}
	    }
	}

	if (commitIdx == 0) {
	    baseTreeIterators[commitIdx] = new EmptyTreeIterator();
	    commitIdx++;
	} else {
	    baseTrees = Arrays.copyOf(tempTrees
	    baseTreeIterators = Arrays.copyOf(tempIterators
	}

	return Arrays.copyOf(baseTreeIterators
    }

    protected List<RevCommit> reverseCommitList(List<RevCommit> commitList) {
	int headSize = commitList.size();
	List<RevCommit> newList = new ArrayList<RevCommit>();

	while (headSize > 0) {
	    newList.add(commitList.get(headSize - 1));
	    headSize--;
	}
	return newList;
    }

    List<RevCommit> commitListInsert(RevCommit item
	List<RevCommit> itemList = new ArrayList<RevCommit>();
	itemList.add(item);
	commitList.addAll(0
	return itemList.isEmpty() ? null : itemList;
    }

    List<RevCommit> getMergeBasesMany(RevCommit one
	List<RevCommit> result = new ArrayList<RevCommit>();

	return result.isEmpty() ? null : result;
    }

    List<RevCommit> getMergeBases(RevCommit one
	    throws IncorrectObjectTypeException
	if (one == null)
	    throw new IncorrectObjectTypeException(one
	if (two == null)
	    throw new IncorrectObjectTypeException(two
	List<RevCommit> mergeBases = new ArrayList<RevCommit>();
	walk.reset();
	walk.setRevFilter(RevFilter.MERGE_BASE);
	walk.markStart(one);
	walk.markStart(two);

	int commitIdx = 0;

	for (RevCommit base : walk) {
	    if (commitIdx < MAX_BASES) {
		mergeBases.add(base);
		commitIdx++;
	    } else {
		throw new IOException(String.format(tooManyMergeBasesFor
			"TOO_MANY_BASES"
			Integer.valueOf(commitIdx)
	    }
	}

	return mergeBases;
    }

    protected boolean mergeRecursiveGeneric(RevCommit head
	    List<RevCommit> baseList) throws IOException {
	boolean clean = false;
	int callDepth = 0;
	boolean implicitDirCache = false;

	if (dircache == null) {
	    dircache = getRepository().lockDirCache();
	    implicitDirCache = true;
	}

	List<RevCommit> commitList = new ArrayList<RevCommit>();

	commitList.addAll(baseList);

	clean = mergeRecursive(callDepth
	if (implicitDirCache) {
	    dircache.unlock();
	}
	return clean;
    }

    boolean mergeRecursive(int callDepth
	    List<RevCommit> commonAncestors) throws IOException {
	boolean clean = false;

	List<RevCommit> mergedCommonAncestors = new ArrayList<RevCommit>();
	RevCommit firstCommonAncestor = null;
	if (commonAncestors == null) {
	    commonAncestors = new ArrayList<RevCommit>();
	}

	if (commonAncestors.isEmpty()) {
	    commonAncestors.addAll(reverseCommitList(getMergeBases(h1
	}

	if (!commonAncestors.isEmpty()) {
	    firstCommonAncestor = commonAncestors.remove(0);
	    mergedCommonAncestors.add(firstCommonAncestor);
	}

	if (commonAncestors.isEmpty()) {
	    resultTree = (firstCommonAncestor == null ? null
		    : firstCommonAncestor.getTree().getId());
	}

	for (RevCommit commonAncestor : commonAncestors) {
	    resultTree = null;
	    RevCommit saved_b1;
	    RevCommit saved_b2;
	    callDepth++;

	    dircache.clear();
	    saved_b1 = h1;
	    saved_b2 = h2;

	    mergeRecursive(callDepth

	    if (resultTree == null) {
		throw new IOException(MessageFormat.format(
			JGitText.get().mergeRecursiveReturnedNoCommit
			callDepth
			commonAncestor.name()));
	    }

	    mergedCommonAncestors.add(commonAncestor);

	    h1 = saved_b1;
	    h2 = saved_b2;
	    callDepth--;

	}

	clean = mergeTrees(resultTree

	return clean;
    }

    @Override
    protected boolean mergeImpl() throws IOException {
	return mergeRecursiveGeneric(sourceCommits[0]
		getBaseCommits(0
    }

    private boolean mergeTrees(AnyObjectId baseTree
	    RevTree mergeTree) throws IOException {
	try {
	    builder = dircache.builder();
	    DirCacheBuildIterator buildIt = new DirCacheBuildIterator(builder);

	    tw = new NameConflictTreeWalk(db);
	    tw.addTree(baseTree);
	    tw.addTree(headTree);
	    tw.addTree(mergeTree);
	    tw.addTree(buildIt);
	    if (workingTreeIterator != null)
		tw.addTree(workingTreeIterator);

	    while (tw.next()) {
		if (!processEntry(
			tw.getTree(T_BASE
			tw.getTree(T_OURS
			tw.getTree(T_THEIRS
			tw.getTree(T_INDEX
			(workingTreeIterator == null) ? null : tw.getTree(
				T_FILE
		    cleanUp();
		    return false;
		}
		if (tw.isSubtree() && enterSubtree)
		    tw.enterSubtree();
	    }

	    if (!inCore) {
		if (!builder.commit()) {
		    cleanUp();
		    throw new IndexWriteException();
		}
		builder = null;

		checkout();
	    } else {
		builder.finish();
		builder = null;
	    }

	    if (getUnmergedPaths().isEmpty()) {
		resultTree = dircache.writeTree(oi);
		return true;
	    } else {
		resultTree = null;
		return false;
	    }
	} finally {
	}
    }

    private void checkout() throws NoWorkTreeException
	ObjectReader r = db.getObjectDatabase().newReader();
	try {
	    for (Map.Entry<String
		    .entrySet()) {
		File f = new File(db.getWorkTree()
		if (entry.getValue() != null) {
		    createDir(f.getParentFile());
		    DirCacheCheckout.checkoutEntry(db
		} else {
		    if (!f.delete())
			failingPaths.put(entry.getKey()
				MergeFailureReason.COULD_NOT_DELETE);
		}
		modifiedFiles.add(entry.getKey());
	    }
	} finally {
	    r.release();
	}
    }

    private void createDir(File f) throws IOException {
	if (!f.isDirectory() && !f.mkdirs()) {
	    File p = f;
	    while (p != null && !p.exists())
		p = p.getParentFile();
	    if (p == null || p.isDirectory())
		throw new IOException(JGitText.get().cannotCreateDirectory);
	    FileUtils.delete(p);
	    if (!f.mkdirs())
		throw new IOException(JGitText.get().cannotCreateDirectory);
	}
    }

    private void cleanUp() throws NoWorkTreeException
	    IOException {
	if (inCore) {
	    modifiedFiles.clear();
	    return;
	}

	DirCache dc = db.readDirCache();
	ObjectReader or = db.getObjectDatabase().newReader();
	Iterator<String> mpathsIt = modifiedFiles.iterator();
	while (mpathsIt.hasNext()) {
	    String mpath = mpathsIt.next();
	    DirCacheEntry entry = dc.getEntry(mpath);
	    FileOutputStream fos = new FileOutputStream(new File(
		    db.getWorkTree()
	    try {
		or.open(entry.getObjectId()).copyTo(fos);
	    } finally {
		fos.close();
	    }
	    mpathsIt.remove();
	}
    }

    private DirCacheEntry add(byte[] path
	if (p != null && !p.getEntryFileMode().equals(FileMode.TREE)) {
	    DirCacheEntry e = new DirCacheEntry(path
	    e.setFileMode(p.getEntryFileMode());
	    e.setObjectId(p.getEntryObjectId());
	    builder.add(e);
	    return e;
	}
	return null;
    }

    private boolean processEntry(CanonicalTreeParser base
	    CanonicalTreeParser ours
	    DirCacheBuildIterator index
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

	if (nonTree(modeO) && nonTree(modeT) && tw.idEqual(T_OURS
	    if (modeO == modeT) {
		add(tw.getRawPath()
		return true;
	    } else {
		int newMode = mergeFileModes(modeB
		if (newMode != FileMode.MISSING.getBits()) {
		    if (newMode == modeO)
			add(tw.getRawPath()
		    else {
			if (isWorktreeDirty())
			    return false;
			DirCacheEntry e = add(tw.getRawPath()
				DirCacheEntry.STAGE_0);
			toBeCheckedOut.put(tw.getPathString()
		    }
		    return true;
		} else {
		    add(tw.getRawPath()
		    add(tw.getRawPath()
		    add(tw.getRawPath()
		    unmergedPaths.add(tw.getPathString());
		    mergeResults.put(
			    tw.getPathString()
			    new MergeResult<RawText>(Collections
				    .<RawText> emptyList()));
		}
		return true;
	    }
	}

	if (nonTree(modeO) && modeB == modeT && tw.idEqual(T_BASE
	    add(tw.getRawPath()
	    return true;
	}

	if (modeB == modeO && tw.idEqual(T_BASE

	    if (isWorktreeDirty())
		return false;
	    if (nonTree(modeT)) {
		DirCacheEntry e = add(tw.getRawPath()
			DirCacheEntry.STAGE_0);
		if (e != null)
		    toBeCheckedOut.put(tw.getPathString()
		return true;
	    } else if (modeT == 0 && modeB != 0) {
		toBeCheckedOut.put(tw.getPathString()
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
	    if (isWorktreeDirty())
		return false;

	    MergeResult<RawText> result = contentMerge(base
	    File of = writeMergedFile(result);
	    updateIndex(base
	    if (result.containsConflicts())
		unmergedPaths.add(tw.getPathString());
	    modifiedFiles.add(tw.getPathString());
	} else if (modeO != modeT) {
	    if (((modeO != 0 && !tw.idEqual(T_BASE
		    .idEqual(T_BASE

		add(tw.getRawPath()
		add(tw.getRawPath()
		DirCacheEntry e = add(tw.getRawPath()
			DirCacheEntry.STAGE_3);

		if (modeO == 0) {
		    if (isWorktreeDirty())
			return false;
		    if (nonTree(modeT)) {
			if (e != null)
			    toBeCheckedOut.put(tw.getPathString()
		    }
		}

		unmergedPaths.add(tw.getPathString());

		mergeResults.put(tw.getPathString()
			contentMerge(base
	    }
	}
	return true;
    }

    private MergeResult<RawText> contentMerge(CanonicalTreeParser base
	    CanonicalTreeParser ours
	    throws IOException {
	RawText baseText = base == null ? RawText.EMPTY_TEXT : getRawText(
		base.getEntryObjectId()
	RawText ourText = ours == null ? RawText.EMPTY_TEXT : getRawText(
		ours.getEntryObjectId()
	RawText theirsText = theirs == null ? RawText.EMPTY_TEXT : getRawText(
		theirs.getEntryObjectId()
	return (mergeAlgorithm.merge(RawTextComparator.DEFAULT
		ourText
    }

    private boolean isIndexDirty() {
	final int modeI = tw.getRawMode(T_INDEX);
	final int modeO = tw.getRawMode(T_OURS);

	final boolean isDirty = nonTree(modeI)
		&& !(tw.idEqual(T_INDEX
	if (isDirty)
	    failingPaths
		    .put(tw.getPathString()
	return isDirty;
    }

    private boolean isWorktreeDirty() {
	if (inCore)
	    return false;

	final int modeF = tw.getRawMode(T_FILE);
	final int modeO = tw.getRawMode(T_OURS);

	final boolean isDirty = nonTree(modeF)
		&& !(tw.idEqual(T_FILE
	if (isDirty)
	    failingPaths.put(tw.getPathString()
		    MergeFailureReason.DIRTY_WORKTREE);
	return isDirty;
    }

    private void updateIndex(CanonicalTreeParser base
	    CanonicalTreeParser ours
	    MergeResult<RawText> result
	    IOException {
	if (result.containsConflicts()) {
	    add(tw.getRawPath()
	    add(tw.getRawPath()
	    add(tw.getRawPath()
	    mergeResults.put(tw.getPathString()
	} else {
	    DirCacheEntry dce = new DirCacheEntry(tw.getPathString());
	    int newMode = mergeFileModes(tw.getRawMode(0)
		    tw.getRawMode(2));
	    dce.setFileMode((newMode == FileMode.MISSING.getBits()) ? FileMode.REGULAR_FILE
		    : FileMode.fromBits(newMode));
	    dce.setLastModified(of.lastModified());
	    dce.setLength((int) of.length());
	    InputStream is = new FileInputStream(of);
	    try {
		dce.setObjectId(oi.insert(Constants.OBJ_BLOB
	    } finally {
		is.close();
		if (inCore)
		    FileUtils.delete(of);
	    }
	    builder.add(dce);
	}
    }

    private File writeMergedFile(MergeResult<RawText> result)
	    throws FileNotFoundException
	MergeFormatter fmt = new MergeFormatter();
	File of = null;
	FileOutputStream fos;
	if (!inCore) {
	    File workTree = db.getWorkTree();
	    if (workTree == null)
		throw new UnsupportedOperationException();

	    of = new File(workTree
	    fos = new FileOutputStream(of);
	    try {
		fmt.formatMerge(fos
			Constants.CHARACTER_ENCODING);
	    } finally {
		fos.close();
	    }
	} else if (!result.containsConflicts()) {
	    of = File.createTempFile("merge_"
	    fos = new FileOutputStream(of);
	    try {
		fmt.formatMerge(fos
			Constants.CHARACTER_ENCODING);
	    } finally {
		fos.close();
	    }
	}
	return of;
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

    private static RawText getRawText(ObjectId id
	    throws IOException {
	if (id.equals(ObjectId.zeroId()))
	    return new RawText(new byte[] {});
	return new RawText(db.open(id
    }

    private static boolean nonTree(final int mode) {
	return mode != 0 && !FileMode.TREE.equals(mode);
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
    }

    public void setWorkingTreeIterator(WorkingTreeIterator workingTreeIterator) {
	this.workingTreeIterator = workingTreeIterator;
    }

    public int getNumBases() {
	return numBases;
    }

    public void setNumBases(int numBases) {
	this.numBases = numBases;
    }
}
