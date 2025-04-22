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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.diff.RawText;
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
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.NameConflictTreeWalk;
import org.eclipse.jgit.treewalk.WorkingTreeIterator;

public class ResolveMerger extends ThreeWayMerger {
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

	private static final int T_FILE = 4;

	private DirCacheBuilder builder;

	private ObjectId resultTree;

	private List<String> unmergedPathes = new ArrayList<String>();

	private List<String> modifiedFiles = new LinkedList<String>();

	private Map<String

	private Map<String

	private Map<String

	private ObjectInserter oi;

	private boolean enterSubtree;

	protected ResolveMerger(Repository local) {
		super(local);
		commitNames = new String[] { "BASE"
		oi = local.newObjectInserter();
	}

	@Override
	protected boolean mergeImpl() throws IOException {
		DirCache dc = getRepository().lockDirCache();
		try {
			builder = dc.builder();
			DirCacheBuildIterator buildIt = new DirCacheBuildIterator(builder);

			tw = new NameConflictTreeWalk(db);
			tw.reset();
			tw.addTree(mergeBase());
			tw.addTree(sourceTrees[0]);
			tw.addTree(sourceTrees[1]);
			tw.addTree(buildIt);
			tw.addTree(new FileTreeIterator(getRepository().getWorkTree()
					getRepository().getFS()));

			while (tw.next()) {
				if (!processEntry(
						tw.getTree(T_BASE
						tw.getTree(T_OURS
						tw.getTree(T_THEIRS
						tw.getTree(T_INDEX
						tw.getTree(T_FILE
					cleanUp();
					return false;
				}
				if (tw.isSubtree() && enterSubtree)
					tw.enterSubtree();
			}

			if (!builder.commit()) {
				cleanUp();
				throw new IndexWriteException();
			}
			builder = null;

			checkout();
			if (getUnmergedPathes().isEmpty()) {
				resultTree = dc.writeTree(oi);
				return true;
			} else {
				resultTree = null;
				return false;
			}
		} finally {
			dc.unlock();
		}
	}

	private void checkout() throws NoWorkTreeException
		for (Map.Entry<String
			File f = new File(db.getWorkTree()
			createDir(f.getParentFile());
			DirCacheCheckout.checkoutEntry(db
					f
					entry.getValue()
			modifiedFiles.add(entry.getKey());
		}
	}

	private void createDir(File f) throws IOException {
		if (!f.isDirectory() && !f.mkdirs()) {
			File p = f;
			while (p != null && !p.exists())
				p = p.getParentFile();
			if (p == null || p.isDirectory())
				throw new IOException(JGitText.get().cannotCreateDirectory);
			p.delete();
			if (!f.mkdirs())
				throw new IOException(JGitText.get().cannotCreateDirectory);
		}
	}

	private void cleanUp() throws NoWorkTreeException
		DirCache dc = db.readDirCache();
		ObjectReader or = db.getObjectDatabase().newReader();
		Iterator<String> mpathsIt=modifiedFiles.iterator();
		while(mpathsIt.hasNext()) {
			String mpath=mpathsIt.next();
			DirCacheEntry entry = dc.getEntry(mpath);
			FileOutputStream fos = new FileOutputStream(new File(db.getWorkTree()
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
		final int modeI = tw.getRawMode(T_INDEX);

		if (nonTree(modeI)
				&& !(tw.idEqual(T_INDEX
			failingPathes.put(tw.getPathString()
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
			DirCacheEntry e=add(tw.getRawPath()
			if (e!=null)
				toBeCheckedOut.put(tw.getPathString()
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
			if (!nonTree(work.getEntryRawMode()) || work.isModified(index.getDirCacheEntry()
				failingPathes.put(tw.getPathString()
				return false;
			}

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

		MergeResult result = MergeAlgorithm.merge(
				getRawText(base.getEntryObjectId()
				getRawText(ours.getEntryObjectId()
				getRawText(theirs.getEntryObjectId()

		File of = new File(db.getWorkTree()
		FileOutputStream fos = new FileOutputStream(of);
		try {
			fmt.formatMerge(fos
					Constants.CHARACTER_ENCODING);
		} finally {
			fos.close();
		}
		if (result.containsConflicts()) {
			add(tw.getRawPath()
			add(tw.getRawPath()
			add(tw.getRawPath()
			mergeResults.put(tw.getPathString()
			return false;
		} else {
			DirCacheEntry dce = new DirCacheEntry(tw.getPathString());
			dce.setFileMode(tw.getFileMode(0));
			dce.setLastModified(of.lastModified());
			dce.setLength((int) of.length());
			InputStream is = new FileInputStream(of);
			try {
				dce.setObjectId(oi.insert(Constants.OBJ_BLOB
						is));
			} finally {
				is.close();
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
		return toBeCheckedOut;
	}

	public Map<String
		return mergeResults;
	}

	public Map<String
		return (failingPathes.size() == 0) ? null : failingPathes;
	}
}
