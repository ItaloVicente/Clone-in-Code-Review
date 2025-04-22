
package org.eclipse.jgit.dircache;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.errors.CheckoutConflictException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectWriter;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryConfig;
import org.eclipse.jgit.lib.Tree;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.NameConflictTreeWalk;

public class DirCacheCheckout {
	private Repository repo;

	private HashMap<String

	private ArrayList<String> conflicts = new ArrayList<String>();

	private ArrayList<String> removed = new ArrayList<String>();

	private Tree mergeCommitTree;

	private DirCache dc;

	private DirCacheBuilder builder;

	private Tree headCommitTree;

	private boolean failOnConflict = true;

	public HashMap<String
		return updated;
	}

	public ArrayList<String> getConflicts() {
		return conflicts;
	}

	public ArrayList<String> getRemoved() {
		return removed;
	}

	public DirCacheCheckout(Repository repo
			Tree mergeCommitTree) throws IOException {
		this.repo = repo;
		this.dc = dc;
		this.headCommitTree = headCommitTree;
		this.mergeCommitTree = mergeCommitTree;
	}

	public void preScanTwoTrees(ObjectId headCommitTree
			DirCache dc
			IOException {
		removed.clear();
		updated.clear();
		conflicts.clear();
		NameConflictTreeWalk tw = new NameConflictTreeWalk(repo);
		builder = dc.builder();

		tw.reset();
		tw.addTree(headCommitTree);
		tw.addTree(mTreeId);
		tw.addTree(new DirCacheBuildIterator(builder));

		while (tw.next()) {
			processEntry(tw.getTree(0
					tw.getTree(1
					tw.getTree(2
			if (tw.isSubtree())
				tw.enterSubtree();
		}
	}

	public void prescanOneTree(DirCache dc
			throws MissingObjectException
			CorruptObjectException
		removed.clear();
		updated.clear();
		conflicts.clear();

		builder = dc.builder();

		NameConflictTreeWalk tw = new NameConflictTreeWalk(repo);
		tw.reset();
		tw.addTree(mTreeId);
		tw.addTree(new DirCacheBuildIterator(builder));

		while (tw.next()) {
			processEntry(tw.getTree(0
					tw.getTree(1
			if (tw.isSubtree())
				tw.enterSubtree();
		}
		conflicts.removeAll(removed);
	}

	void processEntry(CanonicalTreeParser m
		if (m != null) {
			if (!FileMode.TREE.equals(m.getEntryFileMode())) {
				File f = new File(repo.getWorkDir()
				if (f.exists() && !f.isFile())
					checkConflictsWithFile(f);
			}
			update(m.getEntryPathString()
					m.getEntryFileMode());
		} else {
			File f = new File(repo.getWorkDir()
			if (f.exists()) {
				remove(i.getEntryPathString());
				conflicts.remove(i.getEntryPathString());
			} else {
				keep(i.getDirCacheEntry());
			}
		}
	}

	public void checkout() throws IOException {
		if (headCommitTree != null)
			preScanTwoTrees(headCommitTree.getTreeId()
					mergeCommitTree.getTreeId());
		else
			prescanOneTree(dc

		if (!conflicts.isEmpty()) {
			if (failOnConflict) {
				dc.unlock();
				throw new IOException("dirty files exist; refusing to merge.");
			} else
				cleanUpConflicts();
		}

		builder.finish();

		File file;
		for (String p : updated.keySet()) {
			file = new File(repo.getWorkDir()
			file.getParentFile().mkdirs();
			file.createNewFile();
			DirCacheEntry entry = dc.getEntry(p);
			entry.checkoutEntry(repo
			entry.setLastModified(file.lastModified());
			entry.setLength((int) file.length());
		}

		for (String r : removed) {
			file = new File(repo.getWorkDir()
			file.delete();
			removeEmptyParents(file);
		}

		if (!builder.commit()) {
			dc.unlock();
			throw new IOException(
					"couldn't commit the index; refusing to merge");
		}
	}

	private void removeEmptyParents(File f) {
		File parentFile = f.getParentFile();
		while (!parentFile.equals(repo.getWorkDir())) {
			if (parentFile.list().length == 0)
				parentFile.delete();
			else
				break;

			parentFile = parentFile.getParentFile();
		}
	}


	void processEntry(AbstractTreeIterator h
			DirCacheBuildIterator i) throws IOException {
		DirCacheEntry dce;
		ObjectWriter ow = new ObjectWriter(repo);

		ObjectId iId = (i == null ? null : i.getEntryObjectId());
		ObjectId mId = (m == null ? null : m.getEntryObjectId());
		ObjectId hId = (h == null ? null : h.getEntryObjectId());

		String name = (i != null ? i.getEntryPathString() : (h != null ? h
				.getEntryPathString() : m.getEntryPathString()));



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
				if (hId.equals(iId)) {
					dce = i.getDirCacheEntry();
					if (dce.isModified(true
							repo.getWorkDir()
						conflict(i.getDirCacheEntry());
					else
						update(name
				}
				else conflict(i.getDirCacheEntry());
				break;
				remove(name);
				break;
				remove(name);
				break;
				if (!iId.equals(mId))
				  conflict(i.getDirCacheEntry());
				break;
				conflict(i.getDirCacheEntry());
				break;
				dce = i.getDirCacheEntry();
				if (hId.equals(mId)) {
					if (dce != null
							&& dce.isModified(true
									repo.getWorkDir()
						conflict(i.getDirCacheEntry());
					else
						update(name
				} else if (dce != null
						&& !dce.isModified(true
								repo.getWorkDir()
					update(name
				else
					conflict(i.getDirCacheEntry());
				break;
				keep(i.getDirCacheEntry());
				break;
				if (hId.equals(iId)) {
					dce = i.getDirCacheEntry();
					if (dce.isModified(true
							repo.getWorkDir()
						conflict(i.getDirCacheEntry());
					else
						remove(name);
				} else
					conflict(i.getDirCacheEntry());
				break;
				dce = i.getDirCacheEntry();
				if (dce != null
						&& !dce.isModified(true
								repo.getWorkDir()
					update(name
				else
					conflict(i.getDirCacheEntry());
				break;
			default:
				keep(i.getDirCacheEntry());
			}
			return;
		}

		if (i == null) {

			if (h == null)
				update(name
			else if (m == null)
				remove(name);
			else
				update(name
		} else {
			dce = i.getDirCacheEntry();
			if (h == null) {

				if (m == null || mId.equals(iId)) {
					if (hasParentBlob(mergeCommitTree
						if (dce != null
								&& dce.isModified(true
										repo.getWorkDir()
							conflict(i.getDirCacheEntry());
						else
							remove(name);
					} else
						keep(i.getDirCacheEntry());
				} else
					conflict(i.getDirCacheEntry());
			} else if (m == null) {

				if (hId.equals(iId)) {
					if (dce.isModified(true
							repo.getWorkDir()
						conflict(i.getDirCacheEntry());
					else
						remove(name);
				} else
					conflict(i.getDirCacheEntry());
			} else {
				if (!hId.equals(mId) && !hId.equals(iId) && !mId.equals(iId))
					conflict(i.getDirCacheEntry());
				else if (hId.equals(iId) && !mId.equals(iId)) {
					if (dce != null
							&& dce.isModified(true
									repo.getWorkDir()
						conflict(i.getDirCacheEntry());
					else
						update(name
				} else {
					keep(i.getDirCacheEntry());
				}
			}
		}
	}

	private void conflict(DirCacheEntry e) {
		conflicts.add(e.getPathString());

		builder.add(e);
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

	private boolean hasParentBlob(Tree t
		if (name.indexOf("/") == -1)
			return false;

		String parent = name.substring(0
		if (t.findBlobMember(parent) != null)
			return true;
		return hasParentBlob(t
	}

	Boolean filemode;

	private boolean config_filemode() {
		if (filemode == null) {
			RepositoryConfig config = repo.getConfig();
			filemode = Boolean.valueOf(config.getBoolean("core"
					"filemode"
		}
		return filemode.booleanValue();
	}

	private void checkConflictsWithFile(File file) {
		if (file.isDirectory()) {
			ArrayList<String> childFiles = listFiles(file);
			conflicts.addAll(childFiles);
		} else {
			File parent = file.getParentFile();
			while (!parent.equals(repo.getWorkDir())) {
				if (parent.isDirectory())
					break;
				if (parent.exists() && parent.isFile()) {
					conflicts.add(Repository.stripWorkDir(repo.getWorkDir()
							parent));
					break;
				}
				parent = parent.getParentFile();
			}
		}
	}

	private ArrayList<String> listFiles(File file) {
		ArrayList<String> list = new ArrayList<String>();
		listFiles(file
		return list;
	}

	private void listFiles(File dir
		for (File f : dir.listFiles()) {
			if (f.isDirectory())
				listFiles(f
			else {
				list.add(Repository.stripWorkDir(repo.getWorkDir()
			}
		}
	}

	public void setFailOnConflict(boolean failOnConflict) {
		this.failOnConflict = failOnConflict;
	}

	private void cleanUpConflicts() throws CheckoutConflictException {
		for (String c : conflicts) {
			File conflict = new File(repo.getWorkDir()
			if (!conflict.delete())
				throw new CheckoutConflictException(MessageFormat.format(JGitText.get().cannotDeleteFile
			removeEmptyParents(conflict);
		}
		for (String r : removed) {
			File file = new File(repo.getWorkDir()
			file.delete();
			removeEmptyParents(file);
		}
	}
}
