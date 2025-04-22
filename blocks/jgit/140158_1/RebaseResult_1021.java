package org.eclipse.jgit.api;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jgit.api.RebaseResult.Status;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRebaseStepException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.StashApplyFailureException;
import org.eclipse.jgit.api.errors.UnmergedPathsException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.RebaseTodoLine;
import org.eclipse.jgit.lib.RebaseTodoLine.Action;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.submodule.SubmoduleWalk.IgnoreSubmoduleMode;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.RawParseUtils;

public class RebaseCommand extends GitCommand<RebaseResult> {


























	public enum Operation {
		BEGIN
		CONTINUE
		SKIP
		ABORT
		PROCESS_STEPS;
	}

	private Operation operation = Operation.BEGIN;

	private RevCommit upstreamCommit;

	private String upstreamCommitName;

	private ProgressMonitor monitor = NullProgressMonitor.INSTANCE;

	private final RevWalk walk;

	private final RebaseState rebaseState;

	private InteractiveHandler interactiveHandler;

	private boolean stopAfterInitialization = false;

	private RevCommit newHead;

	private boolean lastStepWasForward;

	private MergeStrategy strategy = MergeStrategy.RECURSIVE;

	private boolean preserveMerges = false;

	protected RebaseCommand(Repository repo) {
		super(repo);
		walk = new RevWalk(repo);
		rebaseState = new RebaseState(repo.getDirectory());
	}

	@Override
	public RebaseResult call() throws GitAPIException
			RefNotFoundException
		newHead = null;
		lastStepWasForward = false;
		checkCallable();
		checkParameters();
		try {
			switch (operation) {
			case ABORT:
				try {
					return abort(RebaseResult.ABORTED_RESULT);
				} catch (IOException ioe) {
					throw new JGitInternalException(ioe.getMessage()
				}
			case PROCESS_STEPS:
			case SKIP:
			case CONTINUE:
				String upstreamCommitId = rebaseState.readFile(ONTO);
				try {
					upstreamCommitName = rebaseState.readFile(ONTO_NAME);
				} catch (FileNotFoundException e) {
					upstreamCommitName = upstreamCommitId;
				}
				this.upstreamCommit = walk.parseCommit(repo
						.resolve(upstreamCommitId));
				preserveMerges = rebaseState.getRewrittenDir().isDirectory();
				break;
			case BEGIN:
				autoStash();
				if (stopAfterInitialization
						|| !walk.isMergedInto(
								walk.parseCommit(repo.resolve(Constants.HEAD))
								upstreamCommit)) {
					org.eclipse.jgit.api.Status status = Git.wrap(repo)
							.status().setIgnoreSubmodules(IgnoreSubmoduleMode.ALL).call();
					if (status.hasUncommittedChanges()) {
						List<String> list = new ArrayList<>();
						list.addAll(status.getUncommittedChanges());
						return RebaseResult.uncommittedChanges(list);
					}
				}
				RebaseResult res = initFilesAndRewind();
				if (stopAfterInitialization)
					return RebaseResult.INTERACTIVE_PREPARED_RESULT;
				if (res != null) {
					autoStashApply();
					if (rebaseState.getDir().exists())
						FileUtils.delete(rebaseState.getDir()
								FileUtils.RECURSIVE);
					return res;
				}
			}

			if (monitor.isCancelled())
				return abort(RebaseResult.ABORTED_RESULT);

			if (operation == Operation.CONTINUE) {
				newHead = continueRebase();
				List<RebaseTodoLine> doneLines = repo.readRebaseTodo(
						rebaseState.getPath(DONE)
				RebaseTodoLine step = doneLines.get(doneLines.size() - 1);
				if (newHead != null
						&& step.getAction() != Action.PICK) {
					RebaseTodoLine newStep = new RebaseTodoLine(
							step.getAction()
							AbbreviatedObjectId.fromObjectId(newHead)
							step.getShortMessage());
					RebaseResult result = processStep(newStep
					if (result != null)
						return result;
				}
				File amendFile = rebaseState.getFile(AMEND);
				boolean amendExists = amendFile.exists();
				if (amendExists) {
					FileUtils.delete(amendFile);
				}
				if (newHead == null && !amendExists) {
					return RebaseResult.NOTHING_TO_COMMIT_RESULT;
				}
			}

			if (operation == Operation.SKIP)
				newHead = checkoutCurrentHead();

			List<RebaseTodoLine> steps = repo.readRebaseTodo(
					rebaseState.getPath(GIT_REBASE_TODO)
			if (steps.isEmpty()) {
				return finishRebase(walk.parseCommit(repo.resolve(Constants.HEAD))
			}
			if (isInteractive()) {
				interactiveHandler.prepareSteps(steps);
				repo.writeRebaseTodoFile(rebaseState.getPath(GIT_REBASE_TODO)
						steps
			}
			checkSteps(steps);
			for (int i = 0; i < steps.size(); i++) {
				RebaseTodoLine step = steps.get(i);
				popSteps(1);
				RebaseResult result = processStep(step
				if (result != null) {
					return result;
				}
			}
			return finishRebase(newHead
		} catch (CheckoutConflictException cce) {
			return RebaseResult.conflicts(cce.getConflictingPaths());
		} catch (IOException ioe) {
			throw new JGitInternalException(ioe.getMessage()
		}
	}

	private void autoStash() throws GitAPIException
		if (repo.getConfig().getBoolean(ConfigConstants.CONFIG_REBASE_SECTION
				ConfigConstants.CONFIG_KEY_AUTOSTASH
			String message = MessageFormat.format(
							AUTOSTASH_MSG
							Repository
									.shortenRefName(getHeadName(getHead())));
			RevCommit stashCommit = Git.wrap(repo).stashCreate().setRef(null)
					.setWorkingDirectoryMessage(
							message)
					.call();
			if (stashCommit != null) {
				FileUtils.mkdir(rebaseState.getDir());
				rebaseState.createFile(AUTOSTASH
			}
		}
	}

	private boolean autoStashApply() throws IOException
		boolean conflicts = false;
		if (rebaseState.getFile(AUTOSTASH).exists()) {
			String stash = rebaseState.readFile(AUTOSTASH);
			try (Git git = Git.wrap(repo)) {
				git.stashApply().setStashRef(stash)
						.ignoreRepositoryState(true).setStrategy(strategy)
						.call();
			} catch (StashApplyFailureException e) {
				conflicts = true;
				try (RevWalk rw = new RevWalk(repo)) {
					ObjectId stashId = repo.resolve(stash);
					RevCommit commit = rw.parseCommit(stashId);
					updateStashRef(commit
							commit.getShortMessage());
				}
			}
		}
		return conflicts;
	}

	private void updateStashRef(ObjectId commitId
			String refLogMessage) throws IOException {
		Ref currentRef = repo.exactRef(Constants.R_STASH);
		RefUpdate refUpdate = repo.updateRef(Constants.R_STASH);
		refUpdate.setNewObjectId(commitId);
		refUpdate.setRefLogIdent(refLogIdent);
		refUpdate.setRefLogMessage(refLogMessage
		refUpdate.setForceRefLog(true);
		if (currentRef != null)
			refUpdate.setExpectedOldObjectId(currentRef.getObjectId());
		else
			refUpdate.setExpectedOldObjectId(ObjectId.zeroId());
		refUpdate.forceUpdate();
	}

	private RebaseResult processStep(RebaseTodoLine step
			throws IOException
		if (Action.COMMENT.equals(step.getAction()))
			return null;
		if (preserveMerges
				&& shouldPick
				&& (Action.EDIT.equals(step.getAction()) || Action.PICK
						.equals(step.getAction()))) {
			writeRewrittenHashes();
		}
		ObjectReader or = repo.newObjectReader();

		Collection<ObjectId> ids = or.resolve(step.getCommit());
		if (ids.size() != 1)
			throw new JGitInternalException(
					JGitText.get().cannotResolveUniquelyAbbrevObjectId);
		RevCommit commitToPick = walk.parseCommit(ids.iterator().next());
		if (shouldPick) {
			if (monitor.isCancelled())
				return RebaseResult.result(Status.STOPPED
			RebaseResult result = cherryPickCommit(commitToPick);
			if (result != null)
				return result;
		}
		boolean isSquash = false;
		switch (step.getAction()) {
		case PICK:
		case REWORD:
			String oldMessage = commitToPick.getFullMessage();
			String newMessage = interactiveHandler
					.modifyCommitMessage(oldMessage);
			try (Git git = new Git(repo)) {
				newHead = git.commit().setMessage(newMessage).setAmend(true)
						.setNoVerify(true).call();
			}
			return null;
		case EDIT:
			rebaseState.createFile(AMEND
			return stop(commitToPick
		case COMMENT:
			break;
		case SQUASH:
			isSquash = true;
		case FIXUP:
			resetSoftToParent();
			List<RebaseTodoLine> steps = repo.readRebaseTodo(
					rebaseState.getPath(GIT_REBASE_TODO)
			RebaseTodoLine nextStep = steps.size() > 0 ? steps.get(0) : null;
			File messageFixupFile = rebaseState.getFile(MESSAGE_FIXUP);
			File messageSquashFile = rebaseState.getFile(MESSAGE_SQUASH);
			if (isSquash && messageFixupFile.exists())
				messageFixupFile.delete();
			newHead = doSquashFixup(isSquash
					messageFixupFile
		}
		return null;
	}

	private RebaseResult cherryPickCommit(RevCommit commitToPick)
			throws IOException
			UnmergedPathsException
			WrongRepositoryStateException
		try {
			monitor.beginTask(MessageFormat.format(
					JGitText.get().applyingCommit
					commitToPick.getShortMessage())
			if (preserveMerges)
				return cherryPickCommitPreservingMerges(commitToPick);
			else
				return cherryPickCommitFlattening(commitToPick);
		} finally {
			monitor.endTask();
		}
	}

	private RebaseResult cherryPickCommitFlattening(RevCommit commitToPick)
			throws IOException
			UnmergedPathsException
			WrongRepositoryStateException
		newHead = tryFastForward(commitToPick);
		lastStepWasForward = newHead != null;
		if (!lastStepWasForward) {
			String ourCommitName = getOurCommitName();
			try (Git git = new Git(repo)) {
				CherryPickResult cherryPickResult = git.cherryPick()
					.include(commitToPick).setOurCommitName(ourCommitName)
					.setReflogPrefix(REFLOG_PREFIX).setStrategy(strategy)
					.call();
				switch (cherryPickResult.getStatus()) {
				case FAILED:
					if (operation == Operation.BEGIN)
						return abort(RebaseResult
								.failed(cherryPickResult.getFailingPaths()));
					else
						return stop(commitToPick
				case CONFLICTING:
					return stop(commitToPick
				case OK:
					newHead = cherryPickResult.getNewHead();
				}
			}
		}
		return null;
	}

	private RebaseResult cherryPickCommitPreservingMerges(RevCommit commitToPick)
			throws IOException
			UnmergedPathsException
			WrongRepositoryStateException

		writeCurrentCommit(commitToPick);

		List<RevCommit> newParents = getNewParents(commitToPick);
		boolean otherParentsUnchanged = true;
		for (int i = 1; i < commitToPick.getParentCount(); i++)
			otherParentsUnchanged &= newParents.get(i).equals(
					commitToPick.getParent(i));
		newHead = otherParentsUnchanged ? tryFastForward(commitToPick) : null;
		lastStepWasForward = newHead != null;
		if (!lastStepWasForward) {
			ObjectId headId = getHead().getObjectId();
			assert headId != null;
			if (!AnyObjectId.equals(headId
				checkoutCommit(headId.getName()

			try (Git git = new Git(repo)) {
				if (otherParentsUnchanged) {
					boolean isMerge = commitToPick.getParentCount() > 1;
					String ourCommitName = getOurCommitName();
					CherryPickCommand pickCommand = git.cherryPick()
							.include(commitToPick)
							.setOurCommitName(ourCommitName)
							.setReflogPrefix(REFLOG_PREFIX)
							.setStrategy(strategy);
					if (isMerge) {
						pickCommand.setMainlineParentNumber(1);
						pickCommand.setNoCommit(true);
						writeMergeInfo(commitToPick
					}
					CherryPickResult cherryPickResult = pickCommand.call();
					switch (cherryPickResult.getStatus()) {
					case FAILED:
						if (operation == Operation.BEGIN)
							return abort(RebaseResult.failed(
									cherryPickResult.getFailingPaths()));
						else
							return stop(commitToPick
					case CONFLICTING:
						return stop(commitToPick
					case OK:
						if (isMerge) {
							CommitCommand commit = git.commit();
							commit.setAuthor(commitToPick.getAuthorIdent());
									+ commitToPick.getShortMessage());
							newHead = commit.call();
						} else
							newHead = cherryPickResult.getNewHead();
						break;
					}
				} else {
					MergeCommand merge = git.merge()
							.setFastForward(MergeCommand.FastForwardMode.NO_FF)
							.setProgressMonitor(monitor)
							.setCommit(false);
					for (int i = 1; i < commitToPick.getParentCount(); i++)
						merge.include(newParents.get(i));
					MergeResult mergeResult = merge.call();
					if (mergeResult.getMergeStatus().isSuccessful()) {
						CommitCommand commit = git.commit();
						commit.setAuthor(commitToPick.getAuthorIdent());
						commit.setMessage(commitToPick.getFullMessage());
								+ commitToPick.getShortMessage());
						newHead = commit.call();
					} else {
						if (operation == Operation.BEGIN && mergeResult
								.getMergeStatus() == MergeResult.MergeStatus.FAILED)
							return abort(RebaseResult
									.failed(mergeResult.getFailingPaths()));
						return stop(commitToPick
					}
				}
			}
		}
		return null;
	}

	private void writeMergeInfo(RevCommit commitToPick
			List<RevCommit> newParents) throws IOException {
		repo.writeMergeHeads(newParents.subList(1
		repo.writeMergeCommitMsg(commitToPick.getFullMessage());
	}

	private List<RevCommit> getNewParents(RevCommit commitToPick)
			throws IOException {
		List<RevCommit> newParents = new ArrayList<>();
		for (int p = 0; p < commitToPick.getParentCount(); p++) {
			String parentHash = commitToPick.getParent(p).getName();
			if (!new File(rebaseState.getRewrittenDir()
				newParents.add(commitToPick.getParent(p));
			else {
				String newParent = RebaseState.readFile(
						rebaseState.getRewrittenDir()
				if (newParent.length() == 0)
					newParents.add(walk.parseCommit(repo
							.resolve(Constants.HEAD)));
				else
					newParents.add(walk.parseCommit(ObjectId
							.fromString(newParent)));
			}
		}
		return newParents;
	}

	private void writeCurrentCommit(RevCommit commit) throws IOException {
		RebaseState.appendToFile(rebaseState.getFile(CURRENT_COMMIT)
				commit.name());
	}

	private void writeRewrittenHashes() throws RevisionSyntaxException
			IOException
		File currentCommitFile = rebaseState.getFile(CURRENT_COMMIT);
		if (!currentCommitFile.exists())
			return;

		ObjectId headId = getHead().getObjectId();
		assert headId != null;
		String head = headId.getName();
		String currentCommits = rebaseState.readFile(CURRENT_COMMIT);
			RebaseState
					.createFile(rebaseState.getRewrittenDir()
		FileUtils.delete(currentCommitFile);
	}

	private RebaseResult finishRebase(RevCommit finalHead
			boolean lastStepIsForward) throws IOException
		String headName = rebaseState.readFile(HEAD_NAME);
		updateHead(headName
		boolean stashConflicts = autoStashApply();
		getRepository().autoGC(monitor);
		FileUtils.delete(rebaseState.getDir()
		if (stashConflicts)
			return RebaseResult.STASH_APPLY_CONFLICTS_RESULT;
		if (lastStepIsForward || finalHead == null)
			return RebaseResult.FAST_FORWARD_RESULT;
		return RebaseResult.OK_RESULT;
	}

	private void checkSteps(List<RebaseTodoLine> steps)
			throws InvalidRebaseStepException
		if (steps.isEmpty())
			return;
		if (RebaseTodoLine.Action.SQUASH.equals(steps.get(0).getAction())
				|| RebaseTodoLine.Action.FIXUP.equals(steps.get(0).getAction())) {
			if (!rebaseState.getFile(DONE).exists()
					|| rebaseState.readFile(DONE).trim().length() == 0) {
				throw new InvalidRebaseStepException(MessageFormat.format(
						JGitText.get().cannotSquashFixupWithoutPreviousCommit
						steps.get(0).getAction().name()));
			}
		}

	}

	private RevCommit doSquashFixup(boolean isSquash
			RebaseTodoLine nextStep
			throws IOException

		if (!messageSquash.exists()) {
			ObjectId headId = repo.resolve(Constants.HEAD);
			RevCommit previousCommit = walk.parseCommit(headId);

			initializeSquashFixupFile(MESSAGE_SQUASH
					previousCommit.getFullMessage());
			if (!isSquash)
				initializeSquashFixupFile(MESSAGE_FIXUP
					previousCommit.getFullMessage());
		}
		String currSquashMessage = rebaseState
				.readFile(MESSAGE_SQUASH);

		int count = parseSquashFixupSequenceCount(currSquashMessage) + 1;

		String content = composeSquashMessage(isSquash
				commitToPick
		rebaseState.createFile(MESSAGE_SQUASH
		if (messageFixup.exists())
			rebaseState.createFile(MESSAGE_FIXUP

		return squashIntoPrevious(
				!messageFixup.exists()
				nextStep);
	}

	private void resetSoftToParent() throws IOException
			GitAPIException
		Ref ref = repo.exactRef(Constants.ORIG_HEAD);
		ObjectId orig_head = ref == null ? null : ref.getObjectId();
		try (Git git = Git.wrap(repo)) {
			git.reset().setMode(ResetType.SOFT)
		} finally {
			repo.writeOrigHead(orig_head);
		}
	}

	private RevCommit squashIntoPrevious(boolean sequenceContainsSquash
			RebaseTodoLine nextStep)
			throws IOException
		RevCommit retNewHead;
		String commitMessage = rebaseState
				.readFile(MESSAGE_SQUASH);

		try (Git git = new Git(repo)) {
			if (nextStep == null || ((nextStep.getAction() != Action.FIXUP)
					&& (nextStep.getAction() != Action.SQUASH))) {
				if (sequenceContainsSquash) {
					commitMessage = interactiveHandler
							.modifyCommitMessage(commitMessage);
				}
				retNewHead = git.commit()
						.setMessage(stripCommentLines(commitMessage))
						.setAmend(true).setNoVerify(true).call();
				rebaseState.getFile(MESSAGE_SQUASH).delete();
				rebaseState.getFile(MESSAGE_FIXUP).delete();

			} else {
				retNewHead = git.commit().setMessage(commitMessage)
						.setAmend(true).setNoVerify(true).call();
			}
		}
		return retNewHead;
	}

	private static String stripCommentLines(String commitMessage) {
		StringBuilder result = new StringBuilder();
		}
			int bufferSize = result.length();
			if (bufferSize > 0 && result.charAt(bufferSize - 1) == '\n') {
				result.deleteCharAt(bufferSize - 1);
			}
		}
		return result.toString();
	}

	@SuppressWarnings("nls")
	private static String composeSquashMessage(boolean isSquash
			RevCommit commitToPick
		StringBuilder sb = new StringBuilder();
		String ordinal = getOrdinal(count);
		sb.setLength(0);
		sb.append("# This is a combination of ").append(count)
				.append(" commits.\n");
		sb.append(currSquashMessage.substring(currSquashMessage.indexOf("\n") + 1));
		sb.append("\n");
		if (isSquash) {
			sb.append("# This is the ").append(count).append(ordinal)
					.append(" commit message:\n");
			sb.append(commitToPick.getFullMessage());
		} else {
			sb.append("# The ").append(count).append(ordinal)
					.append(" commit message will be skipped:\n# ");
			sb.append(commitToPick.getFullMessage().replaceAll("([\n\r])"
					"$1# "));
		}
		return sb.toString();
	}

	private static String getOrdinal(int count) {
		switch (count % 10) {
		case 1:
		case 2:
		case 3:
		default:
		}
	}

	static int parseSquashFixupSequenceCount(String currSquashMessage) {
		String firstLine = currSquashMessage.substring(0
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(firstLine);
		if (!matcher.find())
			throw new IllegalArgumentException();
		return Integer.parseInt(matcher.group(1));
	}

	private void initializeSquashFixupFile(String messageFile
			String fullMessage) throws IOException {
		rebaseState
				.createFile(
						messageFile
	}

	private String getOurCommitName() {
		String ourCommitName = "Upstream
				+ Repository.shortenRefName(upstreamCommitName);
		return ourCommitName;
	}

	private void updateHead(String headName
			throws IOException {

		if (headName.startsWith(Constants.R_REFS)) {
			RefUpdate rup = repo.updateRef(headName);
			rup.setNewObjectId(aNewHead);
					+ onto.getName()
			Result res = rup.forceUpdate();
			switch (res) {
			case FAST_FORWARD:
			case FORCED:
			case NO_CHANGE:
				break;
			default:
				throw new JGitInternalException(
						JGitText.get().updatingHeadFailed);
			}
			rup = repo.updateRef(Constants.HEAD);
			rup.setRefLogMessage("rebase finished: returning to " + headName
					false);
			res = rup.link(headName);
			switch (res) {
			case FAST_FORWARD:
			case FORCED:
			case NO_CHANGE:
				break;
			default:
				throw new JGitInternalException(
						JGitText.get().updatingHeadFailed);
			}
		}
	}

	private RevCommit checkoutCurrentHead() throws IOException
		if (headTree == null)
			throw new NoHeadException(
					JGitText.get().cannotRebaseWithoutCurrentHead);
		DirCache dc = repo.lockDirCache();
		try {
			DirCacheCheckout dco = new DirCacheCheckout(repo
			dco.setFailOnConflict(false);
			dco.setProgressMonitor(monitor);
			boolean needsDeleteFiles = dco.checkout();
			if (needsDeleteFiles) {
				List<String> fileList = dco.getToBeDeleted();
				for (String filePath : fileList) {
					File fileToDelete = new File(repo.getWorkTree()
					if (repo.getFS().exists(fileToDelete))
						FileUtils.delete(fileToDelete
								| FileUtils.RETRY);
				}
			}
		} finally {
			dc.unlock();
		}
		try (RevWalk rw = new RevWalk(repo)) {
			RevCommit commit = rw.parseCommit(repo.resolve(Constants.HEAD));
			return commit;
		}
	}

	private RevCommit continueRebase() throws GitAPIException
		DirCache dc = repo.readDirCache();
		boolean hasUnmergedPaths = dc.hasUnmergedPaths();
		if (hasUnmergedPaths)
			throw new UnmergedPathsException();

		boolean needsCommit;
		try (TreeWalk treeWalk = new TreeWalk(repo)) {
			treeWalk.reset();
			treeWalk.setRecursive(true);
			treeWalk.addTree(new DirCacheIterator(dc));
			if (id == null)
				throw new NoHeadException(
						JGitText.get().cannotRebaseWithoutCurrentHead);

			treeWalk.addTree(id);

			treeWalk.setFilter(TreeFilter.ANY_DIFF);

			needsCommit = treeWalk.next();
		}
		if (needsCommit) {
			try (Git git = new Git(repo)) {
				CommitCommand commit = git.commit();
				commit.setMessage(rebaseState.readFile(MESSAGE));
				commit.setAuthor(parseAuthor());
				return commit.call();
			}
		}
		return null;
	}

	private PersonIdent parseAuthor() throws IOException {
		File authorScriptFile = rebaseState.getFile(AUTHOR_SCRIPT);
		byte[] raw;
		try {
			raw = IO.readFully(authorScriptFile);
		} catch (FileNotFoundException notFound) {
			if (authorScriptFile.exists()) {
				throw notFound;
			}
			return null;
		}
		return parseAuthor(raw);
	}

	private RebaseResult stop(RevCommit commitToPick
			throws IOException {
		PersonIdent author = commitToPick.getAuthorIdent();
		String authorScript = toAuthorScript(author);
		rebaseState.createFile(AUTHOR_SCRIPT
		rebaseState.createFile(MESSAGE
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (DiffFormatter df = new DiffFormatter(bos)) {
			df.setRepository(repo);
			df.format(commitToPick.getParent(0)
		}
		rebaseState.createFile(PATCH
		rebaseState.createFile(STOPPED_SHA
				repo.newObjectReader()
				.abbreviate(
				commitToPick).name());
		repo.writeCherryPickHead(null);
		return RebaseResult.result(status
	}

	String toAuthorScript(PersonIdent author) {
		StringBuilder sb = new StringBuilder(100);
		sb.append(GIT_AUTHOR_NAME);
		sb.append(author.getName());
		sb.append(GIT_AUTHOR_EMAIL);
		sb.append(author.getEmailAddress());
		sb.append(GIT_AUTHOR_DATE);
		String externalString = author.toExternalString();
		sb
				.append(externalString.substring(externalString
						.lastIndexOf('>') + 2));
		return sb.toString();
	}

	private void popSteps(int numSteps) throws IOException {
		if (numSteps == 0)
			return;
		List<RebaseTodoLine> todoLines = new LinkedList<>();
		List<RebaseTodoLine> poppedLines = new LinkedList<>();

		for (RebaseTodoLine line : repo.readRebaseTodo(
				rebaseState.getPath(GIT_REBASE_TODO)
			if (poppedLines.size() >= numSteps
					|| RebaseTodoLine.Action.COMMENT.equals(line.getAction()))
				todoLines.add(line);
			else
				poppedLines.add(line);
		}

		repo.writeRebaseTodoFile(rebaseState.getPath(GIT_REBASE_TODO)
				todoLines
		if (poppedLines.size() > 0) {
			repo.writeRebaseTodoFile(rebaseState.getPath(DONE)
					true);
		}
	}

	private RebaseResult initFilesAndRewind() throws IOException
			GitAPIException {

		Ref head = getHead();

		ObjectId headId = head.getObjectId();
		if (headId == null) {
			throw new RefNotFoundException(MessageFormat.format(
					JGitText.get().refNotResolved
		}
		String headName = getHeadName(head);
		RevCommit headCommit = walk.lookupCommit(headId);
		RevCommit upstream = walk.lookupCommit(upstreamCommit.getId());

		if (!isInteractive() && walk.isMergedInto(upstream
			return RebaseResult.UP_TO_DATE_RESULT;
		else if (!isInteractive() && walk.isMergedInto(headCommit
			monitor.beginTask(MessageFormat.format(
					JGitText.get().resettingHead
					upstreamCommit.getShortMessage())
			checkoutCommit(headName
			monitor.endTask();

			updateHead(headName
			return RebaseResult.FAST_FORWARD_RESULT;
		}

		monitor.beginTask(JGitText.get().obtainingCommitsForCherryPick
				ProgressMonitor.UNKNOWN);

		FileUtils.mkdir(rebaseState.getDir()

		repo.writeOrigHead(headId);
		rebaseState.createFile(REBASE_HEAD
		rebaseState.createFile(REBASE_HEAD_LEGACY
		rebaseState.createFile(HEAD_NAME
		rebaseState.createFile(ONTO
		rebaseState.createFile(ONTO_NAME
		if (isInteractive() || preserveMerges) {
			rebaseState.createFile(INTERACTIVE
		}
		rebaseState.createFile(QUIET

		ArrayList<RebaseTodoLine> toDoSteps = new ArrayList<>();
		List<RevCommit> cherryPickList = calculatePickList(headCommit);
		ObjectReader reader = walk.getObjectReader();
		for (RevCommit commit : cherryPickList)
			toDoSteps.add(new RebaseTodoLine(Action.PICK
					.abbreviate(commit)
		repo.writeRebaseTodoFile(rebaseState.getPath(GIT_REBASE_TODO)
				toDoSteps

		monitor.endTask();

		monitor.beginTask(MessageFormat.format(JGitText.get().rewinding
				upstreamCommit.getShortMessage())
		boolean checkoutOk = false;
		try {
			checkoutOk = checkoutCommit(headName
		} finally {
			if (!checkoutOk)
				FileUtils.delete(rebaseState.getDir()
		}
		monitor.endTask();

		return null;
	}

	private List<RevCommit> calculatePickList(RevCommit headCommit)
			throws GitAPIException
		Iterable<RevCommit> commitsToUse;
		try (Git git = new Git(repo)) {
			LogCommand cmd = git.log().addRange(upstreamCommit
			commitsToUse = cmd.call();
		}
		List<RevCommit> cherryPickList = new ArrayList<>();
		for (RevCommit commit : commitsToUse) {
			if (preserveMerges || commit.getParentCount() == 1)
				cherryPickList.add(commit);
		}
		Collections.reverse(cherryPickList);

		if (preserveMerges) {
			File rewrittenDir = rebaseState.getRewrittenDir();
			FileUtils.mkdir(rewrittenDir
			walk.reset();
			walk.setRevFilter(RevFilter.MERGE_BASE);
			walk.markStart(upstreamCommit);
			walk.markStart(headCommit);
			RevCommit base;
			while ((base = walk.next()) != null)
				RebaseState.createFile(rewrittenDir
						upstreamCommit.getName());

			Iterator<RevCommit> iterator = cherryPickList.iterator();
			pickLoop: while(iterator.hasNext()){
				RevCommit commit = iterator.next();
				for (int i = 0; i < commit.getParentCount(); i++) {
					boolean parentRewritten = new File(rewrittenDir
							.getParent(i).getName()).exists();
					if (parentRewritten) {
						new File(rewrittenDir
						continue pickLoop;
					}
				}
				iterator.remove();
			}
		}
		return cherryPickList;
	}

	private static String getHeadName(Ref head) {
		String headName;
		if (head.isSymbolic()) {
			headName = head.getTarget().getName();
		} else {
			ObjectId headId = head.getObjectId();
			assert headId != null;
			headName = headId.getName();
		}
		return headName;
	}

	private Ref getHead() throws IOException
		Ref head = repo.exactRef(Constants.HEAD);
		if (head == null || head.getObjectId() == null)
			throw new RefNotFoundException(MessageFormat.format(
					JGitText.get().refNotResolved
		return head;
	}

	private boolean isInteractive() {
		return interactiveHandler != null;
	}

	public RevCommit tryFastForward(RevCommit newCommit) throws IOException
			GitAPIException {
		Ref head = getHead();

		ObjectId headId = head.getObjectId();
		if (headId == null)
			throw new RefNotFoundException(MessageFormat.format(
					JGitText.get().refNotResolved
		RevCommit headCommit = walk.lookupCommit(headId);
		if (walk.isMergedInto(newCommit
			return newCommit;

		String headName = getHeadName(head);
		return tryFastForward(headName
	}

	private RevCommit tryFastForward(String headName
			RevCommit newCommit) throws IOException
		boolean tryRebase = false;
		for (RevCommit parentCommit : newCommit.getParents())
			if (parentCommit.equals(oldCommit))
				tryRebase = true;
		if (!tryRebase)
			return null;

		CheckoutCommand co = new CheckoutCommand(repo);
		try {
			co.setProgressMonitor(monitor);
			co.setName(newCommit.name()).call();
			if (headName.startsWith(Constants.R_HEADS)) {
				RefUpdate rup = repo.updateRef(headName);
				rup.setExpectedOldObjectId(oldCommit);
				rup.setNewObjectId(newCommit);
						+ " to " + newCommit.name()
				Result res = rup.update(walk);
				switch (res) {
				case FAST_FORWARD:
				case NO_CHANGE:
				case FORCED:
					break;
				default:
				}
			}
			return newCommit;
		} catch (RefAlreadyExistsException | RefNotFoundException | InvalidRefNameException | CheckoutConflictException e) {
			throw new JGitInternalException(e.getMessage()
		}
	}

	private void checkParameters() throws WrongRepositoryStateException {
		if (this.operation == Operation.PROCESS_STEPS) {
			if (rebaseState.getFile(DONE).exists())
				throw new WrongRepositoryStateException(MessageFormat.format(
						JGitText.get().wrongRepositoryState
								.getRepositoryState().name()));
		}
		if (this.operation != Operation.BEGIN) {
			switch (repo.getRepositoryState()) {
			case REBASING_INTERACTIVE:
			case REBASING:
			case REBASING_REBASING:
			case REBASING_MERGE:
				break;
			default:
				throw new WrongRepositoryStateException(MessageFormat.format(
						JGitText.get().wrongRepositoryState
								.getRepositoryState().name()));
			}
		} else
			switch (repo.getRepositoryState()) {
			case SAFE:
				if (this.upstreamCommit == null)
					throw new JGitInternalException(MessageFormat
							.format(JGitText.get().missingRequiredParameter
				return;
			default:
				throw new WrongRepositoryStateException(MessageFormat.format(
						JGitText.get().wrongRepositoryState
								.getRepositoryState().name()));

			}
	}

	private RebaseResult abort(RebaseResult result) throws IOException
			GitAPIException {
		ObjectId origHead = getOriginalHead();
		try {
			String commitId = origHead != null ? origHead.name() : null;
			monitor.beginTask(MessageFormat.format(
					JGitText.get().abortingRebase
					ProgressMonitor.UNKNOWN);

			DirCacheCheckout dco;
			if (commitId == null)
				throw new JGitInternalException(
						JGitText.get().abortingRebaseFailedNoOrigHead);
			ObjectId id = repo.resolve(commitId);
			RevCommit commit = walk.parseCommit(id);
			if (result.getStatus().equals(Status.FAILED)) {
				RevCommit head = walk.parseCommit(repo.resolve(Constants.HEAD));
				dco = new DirCacheCheckout(repo
						repo.lockDirCache()
			} else {
				dco = new DirCacheCheckout(repo
						commit.getTree());
			}
			dco.setFailOnConflict(false);
			dco.checkout();
			walk.close();
		} finally {
			monitor.endTask();
		}
		try {
			String headName = rebaseState.readFile(HEAD_NAME);
				monitor.beginTask(MessageFormat.format(
						JGitText.get().resettingHead
						ProgressMonitor.UNKNOWN);

			Result res = null;
			RefUpdate refUpdate = repo.updateRef(Constants.HEAD
			refUpdate.setRefLogMessage("rebase: aborting"
			if (headName.startsWith(Constants.R_REFS)) {
				res = refUpdate.link(headName);
			} else {
				refUpdate.setNewObjectId(origHead);
				res = refUpdate.forceUpdate();

			}
			switch (res) {
			case FAST_FORWARD:
			case FORCED:
			case NO_CHANGE:
				break;
			default:
				throw new JGitInternalException(
						JGitText.get().abortingRebaseFailed);
			}
			boolean stashConflicts = autoStashApply();
			FileUtils.delete(rebaseState.getDir()
			repo.writeCherryPickHead(null);
			repo.writeMergeHeads(null);
			if (stashConflicts)
				return RebaseResult.STASH_APPLY_CONFLICTS_RESULT;
			return result;

		} finally {
			monitor.endTask();
		}
	}

	private ObjectId getOriginalHead() throws IOException {
		try {
			return ObjectId.fromString(rebaseState.readFile(REBASE_HEAD));
		} catch (FileNotFoundException e) {
			try {
				return ObjectId
						.fromString(rebaseState.readFile(REBASE_HEAD_LEGACY));
			} catch (FileNotFoundException ex) {
				return repo.readOrigHead();
			}
		}
	}

	private boolean checkoutCommit(String headName
			throws IOException
			CheckoutConflictException {
		try {
			RevCommit head = walk.parseCommit(repo.resolve(Constants.HEAD));
			DirCacheCheckout dco = new DirCacheCheckout(repo
					repo.lockDirCache()
			dco.setFailOnConflict(true);
			dco.setProgressMonitor(monitor);
			try {
				dco.checkout();
			} catch (org.eclipse.jgit.errors.CheckoutConflictException cce) {
				throw new CheckoutConflictException(dco.getConflicts()
			}
			RefUpdate refUpdate = repo.updateRef(Constants.HEAD
			refUpdate.setExpectedOldObjectId(head);
			refUpdate.setNewObjectId(commit);
			refUpdate.setRefLogMessage(
							+ Repository.shortenRefName(headName)
							+ " to " + commit.getName()
			Result res = refUpdate.forceUpdate();
			switch (res) {
			case FAST_FORWARD:
			case NO_CHANGE:
			case FORCED:
				break;
			default:
				throw new IOException(
						JGitText.get().couldNotRewindToUpstreamCommit);
			}
		} finally {
			walk.close();
			monitor.endTask();
		}
		return true;
	}


	public RebaseCommand setUpstream(RevCommit upstream) {
		this.upstreamCommit = upstream;
		this.upstreamCommitName = upstream.name();
		return this;
	}

	public RebaseCommand setUpstream(AnyObjectId upstream) {
		try {
			this.upstreamCommit = walk.parseCommit(upstream);
			this.upstreamCommitName = upstream.name();
		} catch (IOException e) {
			throw new JGitInternalException(MessageFormat.format(
					JGitText.get().couldNotReadObjectWhileParsingCommit
					upstream.name())
		}
		return this;
	}

	public RebaseCommand setUpstream(String upstream)
			throws RefNotFoundException {
		try {
			ObjectId upstreamId = repo.resolve(upstream);
			if (upstreamId == null)
				throw new RefNotFoundException(MessageFormat.format(JGitText
						.get().refNotResolved
			upstreamCommit = walk.parseCommit(repo.resolve(upstream));
			upstreamCommitName = upstream;
			return this;
		} catch (IOException ioe) {
			throw new JGitInternalException(ioe.getMessage()
		}
	}

	public RebaseCommand setUpstreamName(String upstreamName) {
		if (upstreamCommit == null) {
			throw new IllegalStateException(
		}
		this.upstreamCommitName = upstreamName;
		return this;
	}

	public RebaseCommand setOperation(Operation operation) {
		this.operation = operation;
		return this;
	}

	public RebaseCommand setProgressMonitor(ProgressMonitor monitor) {
		if (monitor == null) {
			monitor = NullProgressMonitor.INSTANCE;
		}
		this.monitor = monitor;
		return this;
	}

	public RebaseCommand runInteractively(InteractiveHandler handler) {
		return runInteractively(handler
	}

	public RebaseCommand runInteractively(InteractiveHandler handler
			final boolean stopAfterRebaseInteractiveInitialization) {
		this.stopAfterInitialization = stopAfterRebaseInteractiveInitialization;
		this.interactiveHandler = handler;
		return this;
	}

	public RebaseCommand setStrategy(MergeStrategy strategy) {
		this.strategy = strategy;
		return this;
	}

	public RebaseCommand setPreserveMerges(boolean preserve) {
		this.preserveMerges = preserve;
		return this;
	}

	public interface InteractiveHandler {
		void prepareSteps(List<RebaseTodoLine> steps);

		String modifyCommitMessage(String commit);
	}


	PersonIdent parseAuthor(byte[] raw) {
		if (raw.length == 0)
			return null;

		Map<String
		for (int p = 0; p < raw.length;) {
			int end = RawParseUtils.nextLF(raw
			if (end == p)
				break;
			int equalsIndex = RawParseUtils.next(raw
			if (equalsIndex == end)
				break;
			String key = RawParseUtils.decode(raw
			String value = RawParseUtils.decode(raw
			p = end;
			keyValueMap.put(key
		}

		String name = keyValueMap.get(GIT_AUTHOR_NAME);
		String email = keyValueMap.get(GIT_AUTHOR_EMAIL);
		String time = keyValueMap.get(GIT_AUTHOR_DATE);

		int timeStart = 0;
			timeStart = 1;
		else
			timeStart = 0;
		long when = Long
				.parseLong(time.substring(timeStart
		String tzOffsetString = time.substring(time.indexOf(' ') + 1);
		int multiplier = -1;
		if (tzOffsetString.charAt(0) == '+')
			multiplier = 1;
		int hours = Integer.parseInt(tzOffsetString.substring(1
		int minutes = Integer.parseInt(tzOffsetString.substring(3
		int tz = (hours * 60 + minutes) * multiplier;
		if (name != null && email != null)
			return new PersonIdent(name
		return null;
	}

	private static class RebaseState {

		private final File repoDirectory;
		private File dir;

		public RebaseState(File repoDirectory) {
			this.repoDirectory = repoDirectory;
		}

		public File getDir() {
			if (dir == null) {
				File rebaseApply = new File(repoDirectory
				if (rebaseApply.exists()) {
					dir = rebaseApply;
				} else {
					File rebaseMerge = new File(repoDirectory
					dir = rebaseMerge;
				}
			}
			return dir;
		}

		public File getRewrittenDir() {
			return new File(getDir()
		}

		public String readFile(String name) throws IOException {
			try {
				return readFile(getDir()
			} catch (FileNotFoundException e) {
				if (ONTO_NAME.equals(name)) {
					File oldFile = getFile(ONTO_NAME.replace('_'
					if (oldFile.exists()) {
						return readFile(oldFile);
					}
				}
				throw e;
			}
		}

		public void createFile(String name
			createFile(getDir()
		}

		public File getFile(String name) {
			return new File(getDir()
		}

		public String getPath(String name) {
		}

		private static String readFile(File file) throws IOException {
			byte[] content = IO.readFully(file);
			int end = RawParseUtils.prevLF(content
			return RawParseUtils.decode(content
		}

		private static String readFile(File directory
				throws IOException {
			return readFile(new File(directory
		}

		private static void createFile(File parentDir
				String content)
				throws IOException {
			File file = new File(parentDir
			try (FileOutputStream fos = new FileOutputStream(file)) {
				fos.write(content.getBytes(UTF_8));
				fos.write('\n');
			}
		}

		private static void appendToFile(File file
				throws IOException {
			try (FileOutputStream fos = new FileOutputStream(file
				fos.write(content.getBytes(UTF_8));
				fos.write('\n');
			}
		}
	}
}
