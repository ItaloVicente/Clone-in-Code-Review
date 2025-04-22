package org.eclipse.jgit.api;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.dircache.DirCacheEditor;
import org.eclipse.jgit.dircache.DirCacheEditor.DeletePath;
import org.eclipse.jgit.dircache.DirCacheEditor.PathEdit;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.CheckoutConflictException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryState;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.eclipse.jgit.util.FileUtils;

public class StashApplyCommand extends GitCommand<ObjectId> {

	private static final String DEFAULT_REF = Constants.STASH + "@{0}";

	private static class StashDiffFilter extends TreeFilter {

		@Override
		public boolean include(final TreeWalk walker) {
			final int m = walker.getRawMode(0);
			if (walker.getRawMode(1) != m || !walker.idEqual(1
				return true;
			if (walker.getRawMode(2) != m || !walker.idEqual(2
				return true;
			return false;
		}

		@Override
		public boolean shouldBeRecursive() {
			return false;
		}

		@Override
		public TreeFilter clone() {
			return this;
		}

		@Override
		public String toString() {
			return "STASH_DIFF";
		}
	}

	private String stashRef;

	public StashApplyCommand(final Repository repo) {
		super(repo);
	}

	public StashApplyCommand setStashRef(final String stashRef) {
		this.stashRef = stashRef;
		return this;
	}

	private boolean isEqualEntry(AbstractTreeIterator iter1
			AbstractTreeIterator iter2) {
		if (!iter1.getEntryFileMode().equals(iter2.getEntryFileMode()))
			return false;
		ObjectId id1 = iter1.getEntryObjectId();
		ObjectId id2 = iter2.getEntryObjectId();
		return id1 != null ? id1.equals(id2) : id2 == null;
	}

	private boolean isConflict(AbstractTreeIterator stashIndexIter
			AbstractTreeIterator stashWorkingTreeIter
			AbstractTreeIterator headIter
			AbstractTreeIterator workingTreeIter) {
		boolean indexDirty = indexIter != null
				&& (headIter == null || !isEqualEntry(indexIter

		boolean workingTreeDirty = workingTreeIter != null
				&& (headIter == null || !isEqualEntry(workingTreeIter

		if (indexDirty && stashIndexIter != null && indexIter != null
				&& !isEqualEntry(stashIndexIter
			return true;

		if (workingTreeDirty && stashWorkingTreeIter != null
				&& workingTreeIter != null
				&& !isEqualEntry(stashWorkingTreeIter
			return true;

		return false;
	}

	private ObjectId getHeadTree() throws GitAPIException {
		final ObjectId headTree;
		try {
			headTree = repo.resolve(Constants.HEAD + "^{tree}");
		} catch (IOException e) {
			throw new JGitInternalException(JGitText.get().cannotReadTree
		}
		if (headTree == null)
			throw new NoHeadException(JGitText.get().cannotReadTree);
		return headTree;
	}

	private ObjectId getStashId() throws GitAPIException {
		final String revision = stashRef != null ? stashRef : DEFAULT_REF;
		final ObjectId stashId;
		try {
			stashId = repo.resolve(revision);
		} catch (IOException e) {
			throw new InvalidRefNameException(MessageFormat.format(
					JGitText.get().stashResolveFailed
		}
		if (stashId == null)
			throw new InvalidRefNameException(MessageFormat.format(
					JGitText.get().stashResolveFailed
		return stashId;
	}

	private void scanForConflicts(TreeWalk treeWalk) throws IOException {
		File workingTree = repo.getWorkTree();
		while (treeWalk.next()) {
			AbstractTreeIterator stashIndexIter = treeWalk.getTree(1
					AbstractTreeIterator.class);
			AbstractTreeIterator stashWorkingIter = treeWalk.getTree(2
					AbstractTreeIterator.class);

			AbstractTreeIterator headIter = treeWalk.getTree(3
					AbstractTreeIterator.class);
			AbstractTreeIterator indexIter = treeWalk.getTree(4
					AbstractTreeIterator.class);
			AbstractTreeIterator workingIter = treeWalk.getTree(5
					AbstractTreeIterator.class);

			if (isConflict(stashIndexIter
					indexIter
				String path = treeWalk.getPathString();
				File file = new File(workingTree
				throw new CheckoutConflictException(file.getAbsolutePath());
			}
		}
	}

	private void applyChanges(TreeWalk treeWalk
			DirCacheEditor editor) throws IOException {
		File workingTree = repo.getWorkTree();
		while (treeWalk.next()) {
			String path = treeWalk.getPathString();
			File file = new File(workingTree

			AbstractTreeIterator stashHeadIter = treeWalk.getTree(0
					AbstractTreeIterator.class);
			AbstractTreeIterator stashIndexIter = treeWalk.getTree(1
					AbstractTreeIterator.class);
			AbstractTreeIterator stashWorkingIter = treeWalk.getTree(2
					AbstractTreeIterator.class);

			if (stashWorkingIter != null && stashIndexIter != null) {
				DirCacheEntry entry = cache.getEntry(path);
				if (entry == null)
					entry = new DirCacheEntry(treeWalk.getRawPath());
				entry.setFileMode(stashIndexIter.getEntryFileMode());
				entry.setObjectId(stashIndexIter.getEntryObjectId());
				DirCacheCheckout.checkoutEntry(repo
						treeWalk.getObjectReader());
				final DirCacheEntry updatedEntry = entry;
				editor.add(new PathEdit(path) {

					public void apply(DirCacheEntry ent) {
						ent.copyMetaData(updatedEntry);
					}
				});

				if (!stashWorkingIter.idEqual(stashIndexIter)) {
					entry = new DirCacheEntry(treeWalk.getRawPath());
					entry.setObjectId(stashWorkingIter.getEntryObjectId());
					DirCacheCheckout.checkoutEntry(repo
							treeWalk.getObjectReader());
				}
			} else {
				if (stashIndexIter == null
						|| (stashHeadIter != null && !stashIndexIter
								.idEqual(stashHeadIter)))
					editor.add(new DeletePath(path));
				FileUtils
						.delete(file
			}
		}
	}

	public ObjectId call() throws GitAPIException
			WrongRepositoryStateException {
		checkCallable();

		if (repo.getRepositoryState() != RepositoryState.SAFE)
			throw new WrongRepositoryStateException(MessageFormat.format(
					JGitText.get().stashApplyOnUnsafeRepository
					repo.getRepositoryState()));

		final ObjectId headTree = getHeadTree();
		final ObjectId stashId = getStashId();

		ObjectReader reader = repo.newObjectReader();
		try {
			RevWalk revWalk = new RevWalk(reader);
			RevCommit stashCommit = revWalk.parseCommit(stashId);
			if (stashCommit.getParentCount() != 2)
				throw new JGitInternalException(MessageFormat.format(
						JGitText.get().stashCommitMissingTwoParents
						stashId.name()));

			RevTree stashWorkingTree = stashCommit.getTree();
			RevTree stashIndexTree = revWalk.parseCommit(
					stashCommit.getParent(1)).getTree();
			RevTree stashHeadTree = revWalk.parseCommit(
					stashCommit.getParent(0)).getTree();

			CanonicalTreeParser stashWorkingIter = new CanonicalTreeParser();
			stashWorkingIter.reset(reader
			CanonicalTreeParser stashIndexIter = new CanonicalTreeParser();
			stashIndexIter.reset(reader
			CanonicalTreeParser stashHeadIter = new CanonicalTreeParser();
			stashHeadIter.reset(reader
			CanonicalTreeParser headIter = new CanonicalTreeParser();
			headIter.reset(reader

			DirCache cache = repo.lockDirCache();
			DirCacheEditor editor = cache.editor();
			try {
				DirCacheIterator indexIter = new DirCacheIterator(cache);
				FileTreeIterator workingIter = new FileTreeIterator(repo);

				TreeWalk treeWalk = new TreeWalk(reader);
				treeWalk.setRecursive(true);
				treeWalk.setFilter(new StashDiffFilter());

				treeWalk.addTree(stashHeadIter);
				treeWalk.addTree(stashIndexIter);
				treeWalk.addTree(stashWorkingIter);
				treeWalk.addTree(headIter);
				treeWalk.addTree(indexIter);
				treeWalk.addTree(workingIter);

				scanForConflicts(treeWalk);

				treeWalk.reset();
				stashWorkingIter.reset(reader
				stashIndexIter.reset(reader
				stashHeadIter.reset(reader
				treeWalk.addTree(stashHeadIter);
				treeWalk.addTree(stashIndexIter);
				treeWalk.addTree(stashWorkingIter);

				applyChanges(treeWalk
			} finally {
				editor.commit();
				cache.unlock();
			}
		} catch (JGitInternalException e) {
			throw e;
		} catch (IOException e) {
			throw new JGitInternalException(JGitText.get().stashApplyFailed
		} finally {
			reader.release();
		}
		return stashId;
	}
}
