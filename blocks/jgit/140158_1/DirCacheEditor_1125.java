
package org.eclipse.jgit.dircache;

import static org.eclipse.jgit.treewalk.TreeWalk.OperationType.CHECKOUT_OP;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.api.errors.FilterFailedException;
import org.eclipse.jgit.attributes.FilterCommand;
import org.eclipse.jgit.attributes.FilterCommandRegistry;
import org.eclipse.jgit.errors.CheckoutConflictException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.IndexWriteException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.events.WorkingTreeModifiedEvent;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.CoreConfig.AutoCRLF;
import org.eclipse.jgit.lib.CoreConfig.EolStreamType;
import org.eclipse.jgit.lib.CoreConfig.SymLinks;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectChecker;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.EmptyTreeIterator;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.NameConflictTreeWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.WorkingTreeIterator;
import org.eclipse.jgit.treewalk.WorkingTreeOptions;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FS.ExecutionResult;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.IntList;
import org.eclipse.jgit.util.RawParseUtils;
import org.eclipse.jgit.util.SystemReader;
import org.eclipse.jgit.util.io.EolStreamTypeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DirCacheCheckout {
	private static Logger LOG = LoggerFactory.getLogger(DirCacheCheckout.class);

	private static final int MAX_EXCEPTION_TEXT_SIZE = 10 * 1024;

	public static class CheckoutMetadata {
		public final EolStreamType eolStreamType;

		public final String smudgeFilterCommand;

		public CheckoutMetadata(EolStreamType eolStreamType
				String smudgeFilterCommand) {
			this.eolStreamType = eolStreamType;
			this.smudgeFilterCommand = smudgeFilterCommand;
		}

		static CheckoutMetadata EMPTY = new CheckoutMetadata(
				EolStreamType.DIRECT
	}

	private Repository repo;

	private HashMap<String

	private ArrayList<String> conflicts = new ArrayList<>();

	private ArrayList<String> removed = new ArrayList<>();

	private ObjectId mergeCommitTree;

	private DirCache dc;

	private DirCacheBuilder builder;

	private NameConflictTreeWalk walk;

	private ObjectId headCommitTree;

	private WorkingTreeIterator workingTree;

	private boolean failOnConflict = true;

	private boolean force = false;

	private ArrayList<String> toBeDeleted = new ArrayList<>();

	private boolean initialCheckout;

	private boolean performingCheckout;

	private ProgressMonitor monitor = NullProgressMonitor.INSTANCE;

	public Map<String
		return updated;
	}

	public List<String> getConflicts() {
		return conflicts;
	}

	public List<String> getToBeDeleted() {
		return toBeDeleted;
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
		this.initialCheckout = !repo.isBare() && !repo.getIndexFile().exists();
	}

	public DirCacheCheckout(Repository repo
			DirCache dc
		this(repo
	}

	public DirCacheCheckout(Repository repo
			ObjectId mergeCommitTree
			throws IOException {
		this(repo
	}

	public DirCacheCheckout(Repository repo
			ObjectId mergeCommitTree) throws IOException {
		this(repo
	}

	public void setProgressMonitor(ProgressMonitor monitor) {
		this.monitor = monitor != null ? monitor : NullProgressMonitor.INSTANCE;
	}

	public void preScanTwoTrees() throws CorruptObjectException
		removed.clear();
		updated.clear();
		conflicts.clear();
		walk = new NameConflictTreeWalk(repo);
		builder = dc.builder();

		addTree(walk
		addTree(walk
		int dciPos = walk.addTree(new DirCacheBuildIterator(builder));
		walk.addTree(workingTree);
		workingTree.setDirCacheIterator(walk

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
		addTree(walk
		int dciPos = walk.addTree(new DirCacheBuildIterator(builder));
		walk.addTree(workingTree);
		workingTree.setDirCacheIterator(walk

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
			WorkingTreeIterator f) throws IOException {
		if (m != null) {
			checkValidPath(m);
			if (i == null) {
				if (f != null && !FileMode.TREE.equals(f.getEntryFileMode())
						&& !f.isEntryIgnored()) {
					if (failOnConflict) {
						conflicts.add(walk.getPathString());
					} else {
						update(m.getEntryPathString()
								m.getEntryFileMode());
					}
				} else
					update(m.getEntryPathString()
						m.getEntryFileMode());
			} else if (f == null || !m.idEqual(i)) {
				update(m.getEntryPathString()
						m.getEntryFileMode());
			} else if (i.getDirCacheEntry() != null) {
				if (f.isModified(i.getDirCacheEntry()
						this.walk.getObjectReader())
						|| i.getDirCacheEntry().getStage() != 0)
					update(m.getEntryPathString()
							m.getEntryFileMode());
				else {
					DirCacheEntry entry = i.getDirCacheEntry();
					if (entry.getLastModified() == 0)
						entry.setLastModified(f.getEntryLastModified());
					keep(entry
				}
			} else
				keep(i.getDirCacheEntry()
		} else {
			if (f != null) {
				if (walk.isDirectoryFileConflict()) {
					conflicts.add(walk.getPathString());
				} else {
					if (i != null) {
						remove(i.getEntryPathString());
						conflicts.remove(i.getEntryPathString());
					} else {
					}
				}
			} else {
			}
		}
	}

	public boolean checkout() throws IOException {
		try {
			return doCheckout();
		} catch (CanceledException ce) {
			throw new IOException(ce);
		} finally {
			try {
				dc.unlock();
			} finally {
				if (performingCheckout) {
					WorkingTreeModifiedEvent event = new WorkingTreeModifiedEvent(
							getUpdated().keySet()
					if (!event.isEmpty()) {
						repo.fireEvent(event);
					}
				}
			}
		}
	}

	private boolean doCheckout() throws CorruptObjectException
			MissingObjectException
			CheckoutConflictException
		toBeDeleted.clear();
		try (ObjectReader objectReader = repo.getObjectDatabase().newReader()) {
			if (headCommitTree != null)
				preScanTwoTrees();
			else
				prescanOneTree();

			if (!conflicts.isEmpty()) {
				if (failOnConflict)
					throw new CheckoutConflictException(conflicts.toArray(new String[0]));
				else
					cleanUpConflicts();
			}

			builder.finish();

			int numTotal = removed.size() + updated.size() + conflicts.size();
			monitor.beginTask(JGitText.get().checkingOutFiles

			performingCheckout = true;
			File file = null;
			String last = null;
			IntList nonDeleted = new IntList();
			for (int i = removed.size() - 1; i >= 0; i--) {
				String r = removed.get(i);
				file = new File(repo.getWorkTree()
				if (!file.delete() && repo.getFS().exists(file)) {
					if (!repo.getFS().isDirectory(file)) {
						nonDeleted.add(i);
						toBeDeleted.add(r);
					}
				} else {
					if (last != null && !isSamePrefix(r
						removeEmptyParents(new File(repo.getWorkTree()
					last = r;
				}
				monitor.update(1);
				if (monitor.isCancelled()) {
					throw new CanceledException(MessageFormat.format(
							JGitText.get().operationCanceled
							JGitText.get().checkingOutFiles));
				}
			}
			if (file != null) {
				removeEmptyParents(file);
			}
			removed = filterOut(removed
			nonDeleted = null;
			Iterator<Map.Entry<String
					.entrySet().iterator();
			Map.Entry<String
			try {
				while (toUpdate.hasNext()) {
					e = toUpdate.next();
					String path = e.getKey();
					CheckoutMetadata meta = e.getValue();
					DirCacheEntry entry = dc.getEntry(path);
					if (FileMode.GITLINK.equals(entry.getRawMode())) {
						checkoutGitlink(path
					} else {
						checkoutEntry(repo
					}
					e = null;

					monitor.update(1);
					if (monitor.isCancelled()) {
						throw new CanceledException(MessageFormat.format(
								JGitText.get().operationCanceled
								JGitText.get().checkingOutFiles));
					}
				}
			} catch (Exception ex) {
				if (e != null) {
					toUpdate.remove();
				}
				while (toUpdate.hasNext()) {
					e = toUpdate.next();
					toUpdate.remove();
				}
				throw ex;
			}
			for (String conflict : conflicts) {
				int entryIdx = dc.findEntry(conflict);
				if (entryIdx >= 0) {
					while (entryIdx < dc.getEntryCount()) {
						DirCacheEntry entry = dc.getEntry(entryIdx);
						if (!entry.getPathString().equals(conflict)) {
							break;
						}
						if (entry.getStage() == DirCacheEntry.STAGE_3) {
							checkoutEntry(repo
									null);
							break;
						}
						++entryIdx;
					}
				}

				monitor.update(1);
				if (monitor.isCancelled()) {
					throw new CanceledException(MessageFormat.format(
							JGitText.get().operationCanceled
							JGitText.get().checkingOutFiles));
				}
			}
			monitor.endTask();

			if (!builder.commit())
				throw new IndexWriteException();
		}
		return toBeDeleted.isEmpty();
	}

	private void checkoutGitlink(String path
			throws IOException {
		File gitlinkDir = new File(repo.getWorkTree()
		FileUtils.mkdirs(gitlinkDir
		FS fs = repo.getFS();
		entry.setLastModified(fs.lastModified(gitlinkDir));
	}

	private static ArrayList<String> filterOut(ArrayList<String> strings
			IntList indicesToRemove) {
		int n = indicesToRemove.size();
		if (n == strings.size()) {
			return new ArrayList<>(0);
		}
		switch (n) {
		case 0:
			return strings;
		case 1:
			strings.remove(indicesToRemove.get(0));
			return strings;
		default:
			int length = strings.size();
			ArrayList<String> result = new ArrayList<>(length - n);
			int j = n - 1;
			int idx = indicesToRemove.get(j);
			for (int i = 0; i < length; i++) {
				if (i == idx) {
					idx = (--j >= 0) ? indicesToRemove.get(j) : -1;
				} else {
					result.add(strings.get(i));
				}
			}
			return result;
		}
	}

	private static boolean isSamePrefix(String a
		int as = a.lastIndexOf('/');
		int bs = b.lastIndexOf('/');
		return a.substring(0
	}

	 private void removeEmptyParents(File f) {
		File parentFile = f.getParentFile();

		while (parentFile != null && !parentFile.equals(repo.getWorkTree())) {
			if (!parentFile.delete())
				break;
			parentFile = parentFile.getParentFile();
		}
	}

	private boolean equalIdAndMode(ObjectId id1
			FileMode mode2) {
		if (!mode1.equals(mode2))
			return false;
		return id1 != null ? id1.equals(id2) : id2 == null;
	}


	void processEntry(CanonicalTreeParser h
			DirCacheBuildIterator i
		DirCacheEntry dce = i != null ? i.getDirCacheEntry() : null;

		String name = walk.getPathString();

		if (m != null)
			checkValidPath(m);

		if (i == null && m == null && h == null) {
			if (walk.isDirectoryFileConflict())
				conflict(name

			return;
		}

		ObjectId iId = (i == null ? null : i.getEntryObjectId());
		ObjectId mId = (m == null ? null : m.getEntryObjectId());
		ObjectId hId = (h == null ? null : h.getEntryObjectId());
		FileMode iMode = (i == null ? null : i.getEntryFileMode());
		FileMode mMode = (m == null ? null : m.getEntryFileMode());
		FileMode hMode = (h == null ? null : h.getEntryFileMode());



		int ffMask = 0;
		if (h != null)
			ffMask = FileMode.TREE.equals(hMode) ? 0xD00 : 0xF00;
		if (i != null)
			ffMask |= FileMode.TREE.equals(iMode) ? 0x0D0 : 0x0F0;
		if (m != null)
			ffMask |= FileMode.TREE.equals(mMode) ? 0x00D : 0x00F;

		if (((ffMask & 0x222) != 0x000)
				&& (((ffMask & 0x00F) == 0x00D) || ((ffMask & 0x0F0) == 0x0D0) || ((ffMask & 0xF00) == 0xD00))) {

			switch (ffMask) {
				if (f != null && isModifiedSubtree_IndexWorkingtree(name)) {
					conflict(name
				} else {
					update(name
				}

				break;
				keep(dce
				break;
				remove(name);
				break;
				if (equalIdAndMode(iId
					keep(dce
				else
					conflict(name
				break;
				break;
				update(name
				break;
				conflict(name
				break;
				if (equalIdAndMode(hId
					if (isModifiedSubtree_IndexWorkingtree(name))
						conflict(name
					else
						update(name
				} else
					conflict(name
				break;
				keep(dce
				break;
				if (equalIdAndMode(hId
					if (f != null
							&& f.isModified(dce
									this.walk.getObjectReader()))
						conflict(name
					else
				else
					conflict(name
				break;
				if (!isModifiedSubtree_IndexWorkingtree(name))
					update(name
				else
					conflict(name
				break;
			default:
				keep(dce
			}
			return;
		}

		if ((ffMask & 0x222) == 0) {
			if (f == null || FileMode.TREE.equals(f.getEntryFileMode())) {
				return;
			} else {
				if (!idEqual(h
					conflict(name
				}
				return;
			}
		}

		if ((ffMask == 0x00F) && f != null && FileMode.TREE.equals(f.getEntryFileMode())) {
			conflict(name
			return;
		}

		if (i == null) {
			if (f != null && !f.isEntryIgnored()) {
				if (!FileMode.GITLINK.equals(mMode)) {
					if (mId == null
							|| !equalIdAndMode(mId
									f.getEntryObjectId()
						conflict(name
						return;
					}
				}
			}


			if (h == null)
				update(name
			else if (m == null)
				if (equalIdAndMode(hId
					if (initialCheckout)
						update(name
					else
						keep(dce
				} else
					conflict(name
			}
		} else {
			if (h == null) {

				if (m == null
						|| !isModified_IndexTree(name
								mergeCommitTree)) {
					if (m==null && walk.isDirectoryFileConflict()) {
						if (dce != null
								&& (f == null || f.isModified(dce
										this.walk.getObjectReader())))
							conflict(name
						else
							remove(name);
					} else
						keep(dce
				} else
					conflict(name
			} else if (m == null) {


				if (iMode == FileMode.GITLINK) {
					remove(name);
				} else {
					if (!isModified_IndexTree(name
							headCommitTree)) {
						if (f != null
								&& f.isModified(dce
										this.walk.getObjectReader())) {

							if (!FileMode.TREE.equals(f.getEntryFileMode())
									&& FileMode.TREE.equals(iMode))
								return;
							else
								conflict(name
						} else
							remove(name);
					} else
						conflict(name
				}
			} else {
				if (!equalIdAndMode(hId
						&& isModified_IndexTree(name
								headCommitTree)
						&& isModified_IndexTree(name
								mergeCommitTree))
					conflict(name
				else

				if (!isModified_IndexTree(name
						headCommitTree)
						&& isModified_IndexTree(name
								mergeCommitTree)) {

					if (dce != null
							&& FileMode.GITLINK.equals(dce.getFileMode())) {

						update(name
					} else if (dce != null
							&& (f != null && f.isModified(dce
									this.walk.getObjectReader()))) {
						conflict(name
					} else {
						update(name
					}
				} else {


					keep(dce
				}
			}
		}
	}

	private static boolean idEqual(AbstractTreeIterator a
			AbstractTreeIterator b) {
		if (a == b) {
			return true;
		}
		if (a == null || b == null) {
			return false;
		}
		return a.getEntryObjectId().equals(b.getEntryObjectId());
	}

	private void conflict(String path
		conflicts.add(path);

		DirCacheEntry entry;
		if (e != null) {
			entry = new DirCacheEntry(e.getPathString()
			entry.copyMetaData(e
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

	private void keep(DirCacheEntry e
			throws IOException {
		if (e != null && !FileMode.TREE.equals(e.getFileMode()))
			builder.add(e);
		if (force) {
			if (f.isModified(e
				checkoutEntry(repo
			}
		}
	}

	private void remove(String path) {
		removed.add(path);
	}

	private void update(String path
			throws IOException {
		if (!FileMode.TREE.equals(mode)) {
			updated.put(path
					walk.getEolStreamType(CHECKOUT_OP)
					walk.getFilterCommand(Constants.ATTR_FILTER_TYPE_SMUDGE)));

			DirCacheEntry entry = new DirCacheEntry(path
			entry.setObjectId(mId);
			entry.setFileMode(mode);
			builder.add(entry);
		}
	}

	public void setFailOnConflict(boolean failOnConflict) {
		this.failOnConflict = failOnConflict;
	}

	public void setForce(boolean force) {
		this.force = force;
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
			if (!file.delete())
				throw new CheckoutConflictException(
						MessageFormat.format(JGitText.get().cannotDeleteFile
								file.getAbsolutePath()));
			removeEmptyParents(file);
		}
	}

	private boolean isModifiedSubtree_IndexWorkingtree(String path)
			throws CorruptObjectException
		try (NameConflictTreeWalk tw = new NameConflictTreeWalk(repo)) {
			int dciPos = tw.addTree(new DirCacheIterator(dc));
			FileTreeIterator fti = new FileTreeIterator(repo);
			tw.addTree(fti);
			fti.setDirCacheIterator(tw
			tw.setRecursive(true);
			tw.setFilter(PathFilter.create(path));
			DirCacheIterator dcIt;
			WorkingTreeIterator wtIt;
			while (tw.next()) {
				dcIt = tw.getTree(0
				wtIt = tw.getTree(1
				if (dcIt == null || wtIt == null)
					return true;
				if (wtIt.isModified(dcIt.getDirCacheEntry()
						this.walk.getObjectReader())) {
					return true;
				}
			}
			return false;
		}
	}

	private boolean isModified_IndexTree(String path
			FileMode iMode
			throws CorruptObjectException
		if (iMode != tMode)
			return true;
		if (FileMode.TREE.equals(iMode)
				&& (iId == null || ObjectId.zeroId().equals(iId)))
			return isModifiedSubtree_IndexTree(path
		else
			return !equalIdAndMode(iId
	}

	private boolean isModifiedSubtree_IndexTree(String path
			throws CorruptObjectException
		try (NameConflictTreeWalk tw = new NameConflictTreeWalk(repo)) {
			tw.addTree(new DirCacheIterator(dc));
			tw.addTree(tree);
			tw.setRecursive(true);
			tw.setFilter(PathFilter.create(path));
			while (tw.next()) {
				AbstractTreeIterator dcIt = tw.getTree(0
						DirCacheIterator.class);
				AbstractTreeIterator treeIt = tw.getTree(1
						AbstractTreeIterator.class);
				if (dcIt == null || treeIt == null)
					return true;
				if (dcIt.getEntryRawMode() != treeIt.getEntryRawMode())
					return true;
				if (!dcIt.getEntryObjectId().equals(treeIt.getEntryObjectId()))
					return true;
			}
			return false;
		}
	}

	@Deprecated
	public static void checkoutEntry(Repository repo
			ObjectReader or) throws IOException {
		checkoutEntry(repo
	}

	public static void checkoutEntry(Repository repo
			ObjectReader or
			CheckoutMetadata checkoutMetadata) throws IOException {
		if (checkoutMetadata == null)
			checkoutMetadata = CheckoutMetadata.EMPTY;
		ObjectLoader ol = or.open(entry.getObjectId());
		File f = new File(repo.getWorkTree()
		File parentDir = f.getParentFile();
		FileUtils.mkdirs(parentDir
		FS fs = repo.getFS();
		WorkingTreeOptions opt = repo.getConfig().get(WorkingTreeOptions.KEY);
		if (entry.getFileMode() == FileMode.SYMLINK
				&& opt.getSymLinks() == SymLinks.TRUE) {
			byte[] bytes = ol.getBytes();
			String target = RawParseUtils.decode(bytes);
			if (deleteRecursive && f.isDirectory()) {
				FileUtils.delete(f
			}
			fs.createSymLink(f
			entry.setLength(bytes.length);
			entry.setLastModified(fs.lastModified(f));
			return;
		}

		String name = f.getName();
		if (name.length() > 200) {
			name = name.substring(0
		}
		File tmpFile = File.createTempFile(
				"._" + name

		EolStreamType nonNullEolStreamType;
		if (checkoutMetadata.eolStreamType != null) {
			nonNullEolStreamType = checkoutMetadata.eolStreamType;
		} else if (opt.getAutoCRLF() == AutoCRLF.TRUE) {
			nonNullEolStreamType = EolStreamType.AUTO_CRLF;
		} else {
			nonNullEolStreamType = EolStreamType.DIRECT;
		}
		try (OutputStream channel = EolStreamTypeUtil.wrapOutputStream(
				new FileOutputStream(tmpFile)
			if (checkoutMetadata.smudgeFilterCommand != null) {
				if (FilterCommandRegistry
						.isRegistered(checkoutMetadata.smudgeFilterCommand)) {
					runBuiltinFilterCommand(repo
							channel);
				} else {
					runExternalFilterCommand(repo
							fs
				}
			} else {
				ol.copyTo(channel);
			}
		}
		if (checkoutMetadata.eolStreamType == EolStreamType.DIRECT
				&& checkoutMetadata.smudgeFilterCommand == null) {
			entry.setLength(ol.getSize());
		} else {
			entry.setLength(tmpFile.length());
		}

		if (opt.isFileMode() && fs.supportsExecute()) {
			if (FileMode.EXECUTABLE_FILE.equals(entry.getRawMode())) {
				if (!fs.canExecute(tmpFile))
					fs.setExecute(tmpFile
			} else {
				if (fs.canExecute(tmpFile))
					fs.setExecute(tmpFile
			}
		}
		try {
			if (deleteRecursive && f.isDirectory()) {
				FileUtils.delete(f
			}
			FileUtils.rename(tmpFile
		} catch (IOException e) {
			throw new IOException(
					MessageFormat.format(JGitText.get().renameFileFailed
							tmpFile.getPath()
					e);
		} finally {
			if (tmpFile.exists()) {
				FileUtils.delete(tmpFile);
			}
		}
		entry.setLastModified(fs.lastModified(f));
	}

	private static void runExternalFilterCommand(Repository repo
			DirCacheEntry entry
			CheckoutMetadata checkoutMetadata
			OutputStream channel) throws IOException {
		ProcessBuilder filterProcessBuilder = fs.runInShell(
				checkoutMetadata.smudgeFilterCommand
		filterProcessBuilder.directory(repo.getWorkTree());
		filterProcessBuilder.environment().put(Constants.GIT_DIR_KEY
				repo.getDirectory().getAbsolutePath());
		ExecutionResult result;
		int rc;
		try {
			result = fs.execute(filterProcessBuilder
			rc = result.getRc();
			if (rc == 0) {
				result.getStdout().writeTo(channel
						NullProgressMonitor.INSTANCE);
			}
		} catch (IOException | InterruptedException e) {
			throw new IOException(new FilterFailedException(e
					checkoutMetadata.smudgeFilterCommand
					entry.getPathString()));
		}
		if (rc != 0) {
			throw new IOException(new FilterFailedException(rc
					checkoutMetadata.smudgeFilterCommand
					entry.getPathString()
					result.getStdout().toByteArray(MAX_EXCEPTION_TEXT_SIZE)
					RawParseUtils.decode(result.getStderr()
							.toByteArray(MAX_EXCEPTION_TEXT_SIZE))));
		}
	}

	private static void runBuiltinFilterCommand(Repository repo
			CheckoutMetadata checkoutMetadata
			OutputStream channel) throws MissingObjectException
		boolean isMandatory = repo.getConfig().getBoolean(
				ConfigConstants.CONFIG_FILTER_SECTION
				ConfigConstants.CONFIG_SECTION_LFS
				ConfigConstants.CONFIG_KEY_REQUIRED
		FilterCommand command = null;
		try {
			command = FilterCommandRegistry.createFilterCommand(
					checkoutMetadata.smudgeFilterCommand
					channel);
		} catch (IOException e) {
			LOG.error(JGitText.get().failedToDetermineFilterDefinition
			if (!isMandatory) {
				ol.copyTo(channel);
			} else {
				throw e;
			}
		}
		if (command != null) {
			while (command.run() != -1) {
			}
		}
	}

	@SuppressWarnings("deprecation")
	private static void checkValidPath(CanonicalTreeParser t)
			throws InvalidPathException {
		ObjectChecker chk = new ObjectChecker()
			.setSafeForWindows(SystemReader.getInstance().isWindows())
			.setSafeForMacOS(SystemReader.getInstance().isMacOS());
		for (CanonicalTreeParser i = t; i != null; i = i.getParent())
			checkValidPathSegment(chk
	}

	private static void checkValidPathSegment(ObjectChecker chk
			CanonicalTreeParser t) throws InvalidPathException {
		try {
			int ptr = t.getNameOffset();
			int end = ptr + t.getNameLength();
			chk.checkPathSegment(t.getEntryPathBuffer()
		} catch (CorruptObjectException err) {
			String path = t.getEntryPathString();
			InvalidPathException i = new InvalidPathException(path);
			i.initCause(err);
			throw i;
		}
	}
}
