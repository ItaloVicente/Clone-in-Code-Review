
package org.eclipse.jgit.dircache;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.errors.CheckoutConflictException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.IndexWriteException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.EmptyTreeIterator;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.NameConflictTreeWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.WorkingTreeIterator;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.util.FS;

public class DirCacheCheckout {
	private Repository repo;

	private HashMap<String

	private ArrayList<String> conflicts = new ArrayList<String>();

	private ArrayList<String> removed = new ArrayList<String>();

	private ObjectId mergeCommitTree;

	private DirCache dc;

	private DirCacheBuilder builder;

	private NameConflictTreeWalk walk;

	private ObjectId headCommitTree;

	private WorkingTreeIterator workingTree;

	private boolean failOnConflict = true;

	private ArrayList<String> toBeDeleted = new ArrayList<String>();

	public Map<String
		return updated;
	}

	public List<String> getConflicts() {
		return conflicts;
	}

	public List<String> getToBeDeleted() {
		return conflicts;
	}

	public List<String> getRemoved() {
		return removed;
	}

	public DirCacheCheckout(Repository repo
			ObjectId mergeCommitTree
			throws IOException {
		this.repo = repo;
		this.dc = dc;
		this.headCommitTree = headCommitTree;
		this.mergeCommitTree = mergeCommitTree;
		this.workingTree = workingTree;
	}

	public DirCacheCheckout(Repository repo
			ObjectId mergeCommitTree) throws IOException {
		this(repo
				repo.getWorkTree()
	}

	public void preScanTwoTrees() throws CorruptObjectException
		removed.clear();
		updated.clear();
		conflicts.clear();
		walk = new NameConflictTreeWalk(repo);
		builder = dc.builder();

		walk.reset();
		addTree(walk
		addTree(walk
		walk.addTree(new DirCacheBuildIterator(builder));
		walk.addTree(workingTree);

		while (walk.next()) {
			processEntry(walk.getTree(0
					walk.getTree(1
					walk.getTree(2
					walk.getTree(3
			if (walk.isSubtree())
				walk.enterSubtree();
		}
	}

	private void addTree(TreeWalk tw
		if (id == null)
			tw.addTree(new EmptyTreeIterator());
		else
			tw.addTree(id);
	}

	public void prescanOneTree()
			throws MissingObjectException
			CorruptObjectException
		removed.clear();
		updated.clear();
		conflicts.clear();

		builder = dc.builder();

		walk = new NameConflictTreeWalk(repo);
		walk.reset();
		walk.addTree(mergeCommitTree);
		walk.addTree(new DirCacheBuildIterator(builder));
		walk.addTree(workingTree);

		while (walk.next()) {
			processEntry(walk.getTree(0
					walk.getTree(1
					walk.getTree(2
			if (walk.isSubtree())
				walk.enterSubtree();
		}
		conflicts.removeAll(removed);
	}

	void processEntry(CanonicalTreeParser m
			WorkingTreeIterator f) {
		if (m != null) {
			update(m.getEntryPathString()
					m.getEntryFileMode());
		} else {
			if (f != null) {
				if (walk.isDirectoryFileConflict()) {
					conflicts.add(walk.getPathString());
				} else {
					remove(i.getEntryPathString());
					conflicts.remove(i.getEntryPathString());
				}
			} else
				keep(i.getDirCacheEntry());
		}
	}

	public boolean checkout() throws IOException {
		toBeDeleted.clear();
		if (headCommitTree != null)
			preScanTwoTrees();
		else
			prescanOneTree();

		if (!conflicts.isEmpty()) {
			if (failOnConflict) {
				dc.unlock();
				throw new CheckoutConflictException(conflicts.toArray(new String[conflicts.size()]));
			} else
				cleanUpConflicts();
		}

		builder.finish();

		File file=null;
		String last = "";
		for (String r : removed) {
			file = new File(repo.getWorkTree()
			if (!file.delete())
				toBeDeleted.add(r);
			else {
				if (!isSamePrefix(r
					removeEmptyParents(file);
				last = r;
			}
		}
		if (file != null)
			removeEmptyParents(file);

		for (String path : updated.keySet()) {
			file = new File(repo.getWorkTree()
			file.getParentFile().mkdirs();
			file.createNewFile();
			DirCacheEntry entry = dc.getEntry(path);
			checkoutEntry(file
		}


		if (!builder.commit()) {
			dc.unlock();
			throw new IndexWriteException();
		}

		return toBeDeleted == null;
	}

	private static boolean isSamePrefix(String a
		int as = a.lastIndexOf('/');
		int bs = b.lastIndexOf('/');
		return a.substring(0
	}

	 private void removeEmptyParents(File f) {
		File parentFile = f.getParentFile();

		while (!parentFile.equals(repo.getWorkTree())) {
			if (!parentFile.delete())
				break;
			parentFile = parentFile.getParentFile();
		}
	}


	void processEntry(AbstractTreeIterator h
			DirCacheBuildIterator i
		DirCacheEntry dce;

		if (i == null && m == null && h == null)
			return;

		ObjectId iId = (i == null ? null : i.getEntryObjectId());
		ObjectId mId = (m == null ? null : m.getEntryObjectId());
		ObjectId hId = (h == null ? null : h.getEntryObjectId());

		String name = walk.getPathString();



		int ffMask = 0;
		if (h != null)
			ffMask = FileMode.TREE.equals(h.getEntryFileMode()) ? 0xD00 : 0xF00;
		if (i != null)
			ffMask |= FileMode.TREE.equals(i.getEntryFileMode()) ? 0x0D0
					: 0x0F0;
		if (m != null)
			ffMask |= FileMode.TREE.equals(m.getEntryFileMode()) ? 0x00D
					: 0x00F;

		if (((ffMask & 0x222) != 0x000)
				&& (((ffMask & 0x00F) == 0x00D) || ((ffMask & 0x0F0) == 0x0D0) || ((ffMask & 0xF00) == 0xD00))) {

			switch (ffMask) {
				if (isModified(name)) {
					conflict(name
				} else {
					update(name
				}

				break;
				remove(name);
				break;
				remove(name);
				break;
				break;
				conflict(name
				break;
				dce = i.getDirCacheEntry();
				if (hId.equals(mId)) {
					if (isModified(name))
						conflict(name
					else
						update(name
				} else if (!isModified(name))
					update(name
				else
					conflict(name
				break;
				keep(i.getDirCacheEntry());
				break;
				if (hId.equals(iId)) {
					dce = i.getDirCacheEntry();
					if (f == null
							|| f.isModified(dce
									repo.getFS()))
						conflict(name
					else
						remove(name);
				} else
					conflict(name
				break;
				if (!isModified(name))
					update(name
				else
					conflict(name
				break;
			default:
				keep(i.getDirCacheEntry());
			}
			return;
		}

		if ((ffMask & 0x222) == 0)
			return;

		if (i == null) {

			if (h == null)
				update(name
			else if (m == null)
			else
				update(name
		} else {
			dce = i.getDirCacheEntry();
			if (h == null) {

				if (m == null || mId.equals(iId)) {
					if (m==null && walk.isDirectoryFileConflict()) {
						if (dce != null
								&& (f == null || f.isModified(dce
										config_filemode()
							conflict(name
						else
							remove(name);
					} else
						keep(i.getDirCacheEntry());
				} else
					conflict(name
			} else if (m == null) {


				if (hId.equals(iId)) {
					if (f == null
							|| f.isModified(dce
									repo.getFS()))
						conflict(name
					else
						remove(name);
				} else
					conflict(name
			} else {
				if (!hId.equals(mId) && !hId.equals(iId) && !mId.equals(iId))
					conflict(name
				else if (hId.equals(iId) && !mId.equals(iId)) {
					if (dce != null
							&& (f == null || f.isModified(dce
									config_filemode()
						conflict(name
					else
						update(name
				} else {
					keep(i.getDirCacheEntry());
				}
			}
		}
	}

	private void conflict(String path
		conflicts.add(path);

		DirCacheEntry entry;
		if (e != null) {
			entry = new DirCacheEntry(e.getPathString()
			entry.copyMetaData(e);
			builder.add(entry);
		}

		if (h != null && !FileMode.TREE.equals(h.getEntryFileMode())) {
			entry = new DirCacheEntry(h.getEntryPathString()
			entry.setFileMode(h.getEntryFileMode());
			entry.setObjectId(h.getEntryObjectId());
			builder.add(entry);
		}

		if (m != null && !FileMode.TREE.equals(m.getEntryFileMode())) {
			entry = new DirCacheEntry(m.getEntryPathString()
			entry.setFileMode(m.getEntryFileMode());
			entry.setObjectId(m.getEntryObjectId());
			builder.add(entry);
		}
	}

	private void keep(DirCacheEntry e) {
		if (e != null && !FileMode.TREE.equals(e.getFileMode()))
			builder.add(e);
	}

	private void remove(String path) {
		removed.add(path);
	}

	private void update(String path
		if (!FileMode.TREE.equals(mode)) {
			updated.put(path
			DirCacheEntry entry = new DirCacheEntry(path
			entry.setObjectId(mId);
			entry.setFileMode(mode);
			builder.add(entry);
		}
	}

	private Boolean filemode;

	private boolean config_filemode() {
		if (filemode == null) {
			StoredConfig config = repo.getConfig();
			filemode = Boolean.valueOf(config.getBoolean("core"
					"filemode"
		}
		return filemode.booleanValue();
	}

	public void setFailOnConflict(boolean failOnConflict) {
		this.failOnConflict = failOnConflict;
	}

	private void cleanUpConflicts() throws CheckoutConflictException {
		for (String c : conflicts) {
			File conflict = new File(repo.getWorkTree()
			if (!conflict.delete())
				throw new CheckoutConflictException(MessageFormat.format(
						JGitText.get().cannotDeleteFile
			removeEmptyParents(conflict);
		}
		for (String r : removed) {
			File file = new File(repo.getWorkTree()
			file.delete();
			removeEmptyParents(file);
		}
	}

	private boolean isModified(String path) throws CorruptObjectException
		NameConflictTreeWalk tw = new NameConflictTreeWalk(repo);
		tw.reset();
		tw.addTree(new DirCacheIterator(dc));
		tw.addTree(new FileTreeIterator(repo.getWorkTree()
		tw.setRecursive(true);
		tw.setFilter(PathFilter.create(path));
		DirCacheIterator dcIt;
		WorkingTreeIterator wtIt;
		while(tw.next()) {
			dcIt = tw.getTree(0
			wtIt = tw.getTree(1
			if (dcIt == null || wtIt == null)
				return true;
			if (wtIt.isModified(dcIt.getDirCacheEntry()
				return true;
			}
		}
		return false;
	}

	public void checkoutEntry(File f
			boolean config_filemode) throws IOException {
		ObjectLoader ol = repo.open(entry.getObjectId());
		if (ol == null)
			throw new MissingObjectException(entry.getObjectId()
					Constants.TYPE_BLOB);

		byte[] bytes = ol.getCachedBytes();

		File parentDir = f.getParentFile();
		File tmpFile = java.io.File
				.createTempFile("__"+f.getName()
		FileChannel channel = new FileOutputStream(tmpFile).getChannel();
		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		try {
			int j = channel.write(buffer);
			if (j != bytes.length)
				throw new IOException(MessageFormat.format(
						JGitText.get().couldNotWriteFile
		} finally {
			channel.close();
		}
		FS fs = repo.getFS();
		if (config_filemode && fs.supportsExecute()) {
			if (FileMode.EXECUTABLE_FILE.equals(entry.getRawMode())) {
				if (!fs.canExecute(tmpFile))
					fs.setExecute(tmpFile
			} else {
				if (fs.canExecute(tmpFile))
					fs.setExecute(tmpFile
			}
		}
		if (!tmpFile.renameTo(f)) {
			f.delete();
			if (!tmpFile.renameTo(f)) {
				throw new IOException(MessageFormat.format(
						JGitText.get().couldNotWriteFile
						f.getPath()));
			}
		}
		entry.setLastModified(f.lastModified());
		entry.setLength((int) ol.getSize());
	}
}
