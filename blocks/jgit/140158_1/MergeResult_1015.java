package org.eclipse.jgit.api;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.api.MergeResult.MergeStatus;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidMergeHeadsException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.events.WorkingTreeModifiedEvent;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Config.ConfigEnum;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Ref.Storage;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.merge.MergeConfig;
import org.eclipse.jgit.merge.MergeMessageFormatter;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.merge.Merger;
import org.eclipse.jgit.merge.ResolveMerger;
import org.eclipse.jgit.merge.ResolveMerger.MergeFailureReason;
import org.eclipse.jgit.merge.SquashMessageFormatter;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.RevWalkUtils;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.util.StringUtils;

public class MergeCommand extends GitCommand<MergeResult> {

	private MergeStrategy mergeStrategy = MergeStrategy.RECURSIVE;

	private List<Ref> commits = new LinkedList<>();

	private Boolean squash;

	private FastForwardMode fastForwardMode;

	private String message;

	private boolean insertChangeId;

	private ProgressMonitor monitor = NullProgressMonitor.INSTANCE;

	public enum FastForwardMode implements ConfigEnum {
		FF
		NO_FF
		FF_ONLY;

		@Override
		public String toConfigValue() {
			return "--" + name().toLowerCase(Locale.ROOT).replace('_'
		}

		@Override
		public boolean matchConfigValue(String in) {
			if (StringUtils.isEmptyOrNull(in))
				return false;
				return false;
			return name().equalsIgnoreCase(in.substring(2).replace('-'
		}

		public enum Merge {
			TRUE
			FALSE
			ONLY;

			public static Merge valueOf(FastForwardMode ffMode) {
				switch (ffMode) {
				case NO_FF:
					return FALSE;
				case FF_ONLY:
					return ONLY;
				default:
					return TRUE;
				}
			}
		}

		public static FastForwardMode valueOf(FastForwardMode.Merge ffMode) {
			switch (ffMode) {
			case FALSE:
				return NO_FF;
			case ONLY:
				return FF_ONLY;
			default:
				return FF;
			}
		}
	}

	private Boolean commit;

	protected MergeCommand(Repository repo) {
		super(repo);
	}

	@Override
	@SuppressWarnings("boxing")
	public MergeResult call() throws GitAPIException
			ConcurrentRefUpdateException
			InvalidMergeHeadsException
		checkCallable();
		fallBackToConfiguration();
		checkParameters();

		DirCacheCheckout dco = null;
		try (RevWalk revWalk = new RevWalk(repo)) {
			Ref head = repo.exactRef(Constants.HEAD);
			if (head == null)
				throw new NoHeadException(
						JGitText.get().commitOnRepoWithoutHEADCurrentlyNotSupported);


			Ref ref = commits.get(0);

			refLogMessage.append(ref.getName());

			ref = repo.getRefDatabase().peel(ref);
			ObjectId objectId = ref.getPeeledObjectId();
			if (objectId == null)
				objectId = ref.getObjectId();

			RevCommit srcCommit = revWalk.lookupCommit(objectId);

			ObjectId headId = head.getObjectId();
			if (headId == null) {
				revWalk.parseHeaders(srcCommit);
				dco = new DirCacheCheckout(repo
						repo.lockDirCache()
				dco.setFailOnConflict(true);
				dco.setProgressMonitor(monitor);
				dco.checkout();
				RefUpdate refUpdate = repo
						.updateRef(head.getTarget().getName());
				refUpdate.setNewObjectId(objectId);
				refUpdate.setExpectedOldObjectId(null);
				refUpdate.setRefLogMessage("initial pull"
				if (refUpdate.update() != Result.NEW)
					throw new NoHeadException(
							JGitText.get().commitOnRepoWithoutHEADCurrentlyNotSupported);
				setCallable(false);
				return new MergeResult(srcCommit
						null
						mergeStrategy
			}

			RevCommit headCommit = revWalk.lookupCommit(headId);

			if (revWalk.isMergedInto(srcCommit
				setCallable(false);
				return new MergeResult(headCommit
						headCommit
						MergeStatus.ALREADY_UP_TO_DATE
			} else if (revWalk.isMergedInto(headCommit
					&& fastForwardMode != FastForwardMode.NO_FF) {
				dco = new DirCacheCheckout(repo
						headCommit.getTree()
						srcCommit.getTree());
				dco.setProgressMonitor(monitor);
				dco.setFailOnConflict(true);
				dco.checkout();
				String msg = null;
				ObjectId newHead
				MergeStatus mergeStatus = null;
				if (!squash) {
					updateHead(refLogMessage
					newHead = base = srcCommit;
					mergeStatus = MergeStatus.FAST_FORWARD;
				} else {
					msg = JGitText.get().squashCommitNotUpdatingHEAD;
					newHead = base = headId;
					mergeStatus = MergeStatus.FAST_FORWARD_SQUASHED;
					List<RevCommit> squashedCommits = RevWalkUtils.find(
							revWalk
					String squashMessage = new SquashMessageFormatter().format(
							squashedCommits
					repo.writeSquashCommitMsg(squashMessage);
				}
				setCallable(false);
				return new MergeResult(newHead
						headCommit
						null
			} else {
				if (fastForwardMode == FastForwardMode.FF_ONLY) {
					return new MergeResult(headCommit
							new ObjectId[] { headCommit
							MergeStatus.ABORTED
				}
				if (!squash) {
					if (message != null)
						mergeMessage = message;
					else
						mergeMessage = new MergeMessageFormatter().format(
							commits
					repo.writeMergeCommitMsg(mergeMessage);
					repo.writeMergeHeads(Arrays.asList(ref.getObjectId()));
				} else {
					List<RevCommit> squashedCommits = RevWalkUtils.find(
							revWalk
					String squashMessage = new SquashMessageFormatter().format(
							squashedCommits
					repo.writeSquashCommitMsg(squashMessage);
				}
				Merger merger = mergeStrategy.newMerger(repo);
				merger.setProgressMonitor(monitor);
				boolean noProblems;
				Map<String
				Map<String
				List<String> unmergedPaths = null;
				if (merger instanceof ResolveMerger) {
					ResolveMerger resolveMerger = (ResolveMerger) merger;
					resolveMerger.setCommitNames(new String[] {
							"BASE"
					resolveMerger.setWorkingTreeIterator(new FileTreeIterator(repo));
					noProblems = merger.merge(headCommit
					lowLevelResults = resolveMerger
							.getMergeResults();
					failingPaths = resolveMerger.getFailingPaths();
					unmergedPaths = resolveMerger.getUnmergedPaths();
					if (!resolveMerger.getModifiedFiles().isEmpty()) {
						repo.fireEvent(new WorkingTreeModifiedEvent(
								resolveMerger.getModifiedFiles()
					}
				} else
					noProblems = merger.merge(headCommit
				if (!revWalk.isMergedInto(headCommit
					refLogMessage.append(mergeStrategy.getName());
				else
				refLogMessage.append('.');
				if (noProblems) {
					dco = new DirCacheCheckout(repo
							headCommit.getTree()
							merger.getResultTreeId());
					dco.setFailOnConflict(true);
					dco.setProgressMonitor(monitor);
					dco.checkout();

					String msg = null;
					ObjectId newHeadId = null;
					MergeStatus mergeStatus = null;
					if (!commit && squash) {
						mergeStatus = MergeStatus.MERGED_SQUASHED_NOT_COMMITTED;
					}
					if (!commit && !squash) {
						mergeStatus = MergeStatus.MERGED_NOT_COMMITTED;
					}
					if (commit && !squash) {
						try (Git git = new Git(getRepository())) {
							newHeadId = git.commit()
									.setReflogComment(refLogMessage.toString())
									.setInsertChangeId(insertChangeId)
									.call().getId();
						}
						mergeStatus = MergeStatus.MERGED;
						getRepository().autoGC(monitor);
					}
					if (commit && squash) {
						msg = JGitText.get().squashCommitNotUpdatingHEAD;
						newHeadId = headCommit.getId();
						mergeStatus = MergeStatus.MERGED_SQUASHED;
					}
					return new MergeResult(newHeadId
							new ObjectId[] { headCommit.getId()
									srcCommit.getId() }
							mergeStrategy
				} else {
					if (failingPaths != null) {
						repo.writeMergeCommitMsg(null);
						repo.writeMergeHeads(null);
						return new MergeResult(null
								new ObjectId[] {
										headCommit.getId()
								MergeStatus.FAILED
								lowLevelResults
					} else {
						String mergeMessageWithConflicts = new MergeMessageFormatter()
								.formatWithConflicts(mergeMessage
										unmergedPaths);
						repo.writeMergeCommitMsg(mergeMessageWithConflicts);
						return new MergeResult(null
								new ObjectId[] { headCommit.getId()
										srcCommit.getId() }
								MergeStatus.CONFLICTING
								lowLevelResults
					}
				}
			}
		} catch (org.eclipse.jgit.errors.CheckoutConflictException e) {
			List<String> conflicts = (dco == null) ? Collections
					.<String> emptyList() : dco.getConflicts();
			throw new CheckoutConflictException(conflicts
		} catch (IOException e) {
			throw new JGitInternalException(
					MessageFormat.format(
							JGitText.get().exceptionCaughtDuringExecutionOfMergeCommand
							e)
		}
	}

	private void checkParameters() throws InvalidMergeHeadsException {
		if (squash.booleanValue() && fastForwardMode == FastForwardMode.NO_FF) {
			throw new JGitInternalException(
					JGitText.get().cannotCombineSquashWithNoff);
		}

		if (commits.size() != 1)
			throw new InvalidMergeHeadsException(
					commits.isEmpty() ? JGitText.get().noMergeHeadSpecified
							: MessageFormat.format(
									JGitText.get().mergeStrategyDoesNotSupportHeads
									mergeStrategy.getName()
									Integer.valueOf(commits.size())));
	}

	private void fallBackToConfiguration() {
		MergeConfig config = MergeConfig.getConfigForCurrentBranch(repo);
		if (squash == null)
			squash = Boolean.valueOf(config.isSquash());
		if (commit == null)
			commit = Boolean.valueOf(config.isCommit());
		if (fastForwardMode == null)
			fastForwardMode = config.getFastForwardMode();
	}

	private void updateHead(StringBuilder refLogMessage
			ObjectId oldHeadID) throws IOException
			ConcurrentRefUpdateException {
		RefUpdate refUpdate = repo.updateRef(Constants.HEAD);
		refUpdate.setNewObjectId(newHeadId);
		refUpdate.setRefLogMessage(refLogMessage.toString()
		refUpdate.setExpectedOldObjectId(oldHeadID);
		Result rc = refUpdate.update();
		switch (rc) {
		case NEW:
		case FAST_FORWARD:
			return;
		case REJECTED:
		case LOCK_FAILURE:
			throw new ConcurrentRefUpdateException(
					JGitText.get().couldNotLockHEAD
		default:
			throw new JGitInternalException(MessageFormat.format(
					JGitText.get().updatingRefFailed
					newHeadId.toString()
		}
	}

	public MergeCommand setStrategy(MergeStrategy mergeStrategy) {
		checkCallable();
		this.mergeStrategy = mergeStrategy;
		return this;
	}

	public MergeCommand include(Ref aCommit) {
		checkCallable();
		commits.add(aCommit);
		return this;
	}

	public MergeCommand include(AnyObjectId aCommit) {
		return include(aCommit.getName()
	}

	public MergeCommand include(String name
		return include(new ObjectIdRef.Unpeeled(Storage.LOOSE
				aCommit.copy()));
	}

	public MergeCommand setSquash(boolean squash) {
		checkCallable();
		this.squash = Boolean.valueOf(squash);
		return this;
	}

	public MergeCommand setFastForward(
			@Nullable FastForwardMode fastForwardMode) {
		checkCallable();
		this.fastForwardMode = fastForwardMode;
		return this;
	}

	public MergeCommand setCommit(boolean commit) {
		this.commit = Boolean.valueOf(commit);
		return this;
	}

	public MergeCommand setMessage(String message) {
		this.message = message;
		return this;
	}

	public MergeCommand setInsertChangeId(boolean insertChangeId) {
		checkCallable();
		this.insertChangeId = insertChangeId;
		return this;
	}

	public MergeCommand setProgressMonitor(ProgressMonitor monitor) {
		if (monitor == null) {
			monitor = NullProgressMonitor.INSTANCE;
		}
		this.monitor = monitor;
		return this;
	}
}
