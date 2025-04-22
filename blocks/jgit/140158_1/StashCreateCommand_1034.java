package org.eclipse.jgit.api;

import static org.eclipse.jgit.treewalk.TreeWalk.OperationType.CHECKOUT_OP;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.StashApplyFailureException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.dircache.DirCacheCheckout.CheckoutMetadata;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.CheckoutConflictException;
import org.eclipse.jgit.events.WorkingTreeModifiedEvent;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.CoreConfig.EolStreamType;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryState;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.merge.ResolveMerger;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;

public class StashApplyCommand extends GitCommand<ObjectId> {


	private String stashRef;

	private boolean restoreIndex = true;

	private boolean restoreUntracked = true;

	private boolean ignoreRepositoryState;

	private MergeStrategy strategy = MergeStrategy.RECURSIVE;

	public StashApplyCommand(Repository repo) {
		super(repo);
	}

	public StashApplyCommand setStashRef(String stashRef) {
		this.stashRef = stashRef;
		return this;
	}

	public StashApplyCommand ignoreRepositoryState(boolean willIgnoreRepositoryState) {
		this.ignoreRepositoryState = willIgnoreRepositoryState;
		return this;
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

	@Override
	public ObjectId call() throws GitAPIException
			WrongRepositoryStateException
			StashApplyFailureException {
		checkCallable();

		if (!ignoreRepositoryState
				&& repo.getRepositoryState() != RepositoryState.SAFE)
			throw new WrongRepositoryStateException(MessageFormat.format(
					JGitText.get().stashApplyOnUnsafeRepository
					repo.getRepositoryState()));

		try (ObjectReader reader = repo.newObjectReader();
				RevWalk revWalk = new RevWalk(reader)) {

			ObjectId headCommit = repo.resolve(Constants.HEAD);
			if (headCommit == null)
				throw new NoHeadException(JGitText.get().stashApplyWithoutHead);

			final ObjectId stashId = getStashId();
			RevCommit stashCommit = revWalk.parseCommit(stashId);
			if (stashCommit.getParentCount() < 2
					|| stashCommit.getParentCount() > 3)
				throw new JGitInternalException(MessageFormat.format(
						JGitText.get().stashCommitIncorrectNumberOfParents
						stashId.name()
						Integer.valueOf(stashCommit.getParentCount())));

			ObjectId stashIndexCommit = revWalk.parseCommit(stashCommit
					.getParent(1));
			ObjectId stashHeadCommit = stashCommit.getParent(0);
			ObjectId untrackedCommit = null;
			if (restoreUntracked && stashCommit.getParentCount() == 3)
				untrackedCommit = revWalk.parseCommit(stashCommit.getParent(2));

			ResolveMerger merger = (ResolveMerger) strategy.newMerger(repo);
			merger.setCommitNames(new String[] { "stashed HEAD"
			merger.setBase(stashHeadCommit);
			merger.setWorkingTreeIterator(new FileTreeIterator(repo));
			boolean mergeSucceeded = merger.merge(headCommit
			List<String> modifiedByMerge = merger.getModifiedFiles();
			if (!modifiedByMerge.isEmpty()) {
				repo.fireEvent(
						new WorkingTreeModifiedEvent(modifiedByMerge
			}
			if (mergeSucceeded) {
				DirCache dc = repo.lockDirCache();
				DirCacheCheckout dco = new DirCacheCheckout(repo
						dc
				dco.setFailOnConflict(true);
				if (restoreIndex) {
					ResolveMerger ixMerger = (ResolveMerger) strategy
							.newMerger(repo
					ixMerger.setCommitNames(new String[] { "stashed HEAD"
							"HEAD"
					ixMerger.setBase(stashHeadCommit);
					boolean ok = ixMerger.merge(headCommit
					if (ok) {
						resetIndex(revWalk
								.parseTree(ixMerger.getResultTreeId()));
					} else {
						throw new StashApplyFailureException(
								JGitText.get().stashApplyConflict);
					}
				}

				if (untrackedCommit != null) {
					ResolveMerger untrackedMerger = (ResolveMerger) strategy
							.newMerger(repo
					untrackedMerger.setCommitNames(new String[] {
							"null"
					untrackedMerger.setBase(null);
					boolean ok = untrackedMerger.merge(headCommit
							untrackedCommit);
					if (ok) {
						try {
							RevTree untrackedTree = revWalk
									.parseTree(untrackedCommit);
							resetUntracked(untrackedTree);
						} catch (CheckoutConflictException e) {
							throw new StashApplyFailureException(
									JGitText.get().stashApplyConflict
						}
					} else {
						throw new StashApplyFailureException(
								JGitText.get().stashApplyConflict);
					}
				}
			} else {
				throw new StashApplyFailureException(
						JGitText.get().stashApplyConflict);
			}
			return stashId;

		} catch (JGitInternalException e) {
			throw e;
		} catch (IOException e) {
			throw new JGitInternalException(JGitText.get().stashApplyFailed
		}
	}

	@Deprecated
	public void setApplyIndex(boolean applyIndex) {
		this.restoreIndex = applyIndex;
	}

	public StashApplyCommand setRestoreIndex(boolean restoreIndex) {
		this.restoreIndex = restoreIndex;
		return this;
	}

	public StashApplyCommand setStrategy(MergeStrategy strategy) {
		this.strategy = strategy;
		return this;
	}

	@Deprecated
	public void setApplyUntracked(boolean applyUntracked) {
		this.restoreUntracked = applyUntracked;
	}

	public StashApplyCommand setRestoreUntracked(boolean restoreUntracked) {
		this.restoreUntracked = restoreUntracked;
		return this;
	}

	private void resetIndex(RevTree tree) throws IOException {
		DirCache dc = repo.lockDirCache();
		try (TreeWalk walk = new TreeWalk(repo)) {
			DirCacheBuilder builder = dc.builder();

			walk.addTree(tree);
			walk.addTree(new DirCacheIterator(dc));
			walk.setRecursive(true);

			while (walk.next()) {
				AbstractTreeIterator cIter = walk.getTree(0
						AbstractTreeIterator.class);
				if (cIter == null) {
					continue;
				}

				final DirCacheEntry entry = new DirCacheEntry(walk.getRawPath());
				entry.setFileMode(cIter.getEntryFileMode());
				entry.setObjectIdFromRaw(cIter.idBuffer()

				DirCacheIterator dcIter = walk.getTree(1
						DirCacheIterator.class);
				if (dcIter != null && dcIter.idEqual(cIter)) {
					DirCacheEntry indexEntry = dcIter.getDirCacheEntry();
					entry.setLastModified(indexEntry.getLastModified());
					entry.setLength(indexEntry.getLength());
				}

				builder.add(entry);
			}

			builder.commit();
		} finally {
			dc.unlock();
		}
	}

	private void resetUntracked(RevTree tree) throws CheckoutConflictException
			IOException {
		Set<String> actuallyModifiedPaths = new HashSet<>();
		try (TreeWalk walk = new TreeWalk(repo)) {
			walk.addTree(tree);
			walk.addTree(new FileTreeIterator(repo));
			walk.setRecursive(true);

			final ObjectReader reader = walk.getObjectReader();

			while (walk.next()) {
				final AbstractTreeIterator cIter = walk.getTree(0
						AbstractTreeIterator.class);
				if (cIter == null)
					continue;

				final EolStreamType eolStreamType = walk
						.getEolStreamType(CHECKOUT_OP);
				final DirCacheEntry entry = new DirCacheEntry(walk.getRawPath());
				entry.setFileMode(cIter.getEntryFileMode());
				entry.setObjectIdFromRaw(cIter.idBuffer()

				FileTreeIterator fIter = walk
						.getTree(1
				if (fIter != null) {
					if (fIter.isModified(entry
						throw new CheckoutConflictException(
								entry.getPathString());
					}
				}

				checkoutPath(entry
						new CheckoutMetadata(eolStreamType
				actuallyModifiedPaths.add(entry.getPathString());
			}
		} finally {
			if (!actuallyModifiedPaths.isEmpty()) {
				repo.fireEvent(new WorkingTreeModifiedEvent(
						actuallyModifiedPaths
			}
		}
	}

	private void checkoutPath(DirCacheEntry entry
			CheckoutMetadata checkoutMetadata) {
		try {
			DirCacheCheckout.checkoutEntry(repo
					checkoutMetadata);
		} catch (IOException e) {
			throw new JGitInternalException(MessageFormat.format(
					JGitText.get().checkoutConflictWithFile
					entry.getPathString())
		}
	}
}
