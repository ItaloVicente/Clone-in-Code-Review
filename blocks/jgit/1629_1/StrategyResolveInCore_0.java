package org.eclipse.jgit.merge;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.diff.Sequence;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuildIterator;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.NameConflictTreeWalk;

public class ResolveMergerInCore extends ThreeWayMerger {
	public enum MergeFailureReason {
		DIRTY_INDEX
		DIRTY_WORKTREE
	}

	private NameConflictTreeWalk tw;

	private String commitNames[];

	private static final int T_BASE = 0;

	private static final int T_OURS = 1;

	private static final int T_THEIRS = 2;

	private static final int T_INDEX = 3;

	private DirCacheBuilder builder;

	private ObjectId resultTree;

	private List<String> unmergedPathes = new ArrayList<String>();

	private List<String> modifiedFiles = new LinkedList<String>();

	private Map<String

	private Map<String

	private ObjectInserter oi;

	private boolean enterSubtree;

	private final DirCache dircache;

	protected ResolveMergerInCore(Repository local) {
		super(local);
		commitNames = new String[] { "BASE"
		oi = getObjectInserter();
		dircache = DirCache.newInCore();
	}

	@Override
	protected boolean mergeImpl() throws IOException {

		builder = dircache.builder();
		DirCacheBuildIterator buildIt = new DirCacheBuildIterator(builder);

		tw = new NameConflictTreeWalk(db);
		tw.reset();
		tw.addTree(mergeBase());
		tw.addTree(sourceTrees[0]);
		tw.addTree(sourceTrees[1]);
		tw.addTree(buildIt);

		while (tw.next()) {
			if (!processEntry(
					tw.getTree(T_BASE
					tw.getTree(T_OURS
					tw.getTree(T_THEIRS
					tw.getTree(T_INDEX
				return false;
			}
			if (tw.isSubtree() && enterSubtree)
				tw.enterSubtree();
		}

		builder.finish();
		builder = null;

		if (getUnmergedPathes().isEmpty()) {
			resultTree = dircache.writeTree(oi);
			oi.flush();
			return true;
		} else {
			resultTree = null;
			return false;
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
			DirCacheBuildIterator index)
			throws MissingObjectException
			CorruptObjectException
		enterSubtree = true;
		final int modeO = tw.getRawMode(T_OURS);
		final int modeI = tw.getRawMode(T_INDEX);

		if (nonTree(modeI) && !(tw.idEqual(T_INDEX
			failingPathes.put(tw.getPathString()
					MergeFailureReason.DIRTY_INDEX);
			return false;
		}

		final int modeT = tw.getRawMode(T_THEIRS);
		if (nonTree(modeO) && modeO == modeT && tw.idEqual(T_OURS
			add(tw.getRawPath()
			return true;
		}

		final int modeB = tw.getRawMode(T_BASE);
		if (nonTree(modeO) && modeB == modeT && tw.idEqual(T_BASE
			add(tw.getRawPath()
			return true;
		}

		if (nonTree(modeT) && modeB == modeO && tw.idEqual(T_BASE
			add(tw.getRawPath()
			return true;
		}

		if (tw.isSubtree()) {
			if (nonTree(modeO) && !nonTree(modeT)) {
				if (nonTree(modeB))
					add(tw.getRawPath()
				add(tw.getRawPath()
				unmergedPathes.add(tw.getPathString());
				enterSubtree = false;
				return true;
			}
			if (nonTree(modeT) && !nonTree(modeO)) {
				if (nonTree(modeB))
					add(tw.getRawPath()
				add(tw.getRawPath()
				unmergedPathes.add(tw.getPathString());
				enterSubtree = false;
				return true;
			}

			if (!nonTree(modeO))
				return true;

		}

		if (nonTree(modeO) && nonTree(modeT)) {
			if (!contentMerge(base
				unmergedPathes.add(tw.getPathString());
			}
			modifiedFiles.add(tw.getPathString());
		}
		return true;
	}

	private boolean contentMerge(CanonicalTreeParser base
			CanonicalTreeParser ours
			throws FileNotFoundException
		MergeFormatter fmt = new MergeFormatter();

		MergeResult<RawText> result = MergeAlgorithm.merge(
				RawTextComparator.DEFAULT
				getRawText(base.getEntryObjectId()
				getRawText(ours.getEntryObjectId()
				getRawText(theirs.getEntryObjectId()

		if (result.containsConflicts()) {
			add(tw.getRawPath()
			add(tw.getRawPath()
			add(tw.getRawPath()
			mergeResults.put(tw.getPathString()
			return false;
		} else {
			File of = File.createTempFile("merge_"
			FileOutputStream fos = new FileOutputStream(of);

			try {
				fmt.formatMerge(fos
						Constants.CHARACTER_ENCODING);
			} finally {
				fos.close();
			}
			DirCacheEntry dce = new DirCacheEntry(tw.getPathString());
			dce.setFileMode(tw.getFileMode(0));
			dce.setLastModified(of.lastModified());
			dce.setLength((int) of.length());
			InputStream is = new FileInputStream(of);
			try {
				dce.setObjectId(oi.insert(Constants.OBJ_BLOB
			} finally {
				is.close();
				of.delete();
			}
			builder.add(dce);
			return true;
		}
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

	public List<String> getUnmergedPathes() {
		return unmergedPathes;
	}

	public List<String> getModifiedFiles() {
		return modifiedFiles;
	}

	public Map<String
		return mergeResults;
	}

	public Map<String
		return (failingPathes.size() == 0) ? null : failingPathes;
	}
}
