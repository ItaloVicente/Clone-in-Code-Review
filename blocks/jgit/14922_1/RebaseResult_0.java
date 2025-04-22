package org.eclipse.jgit.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.RebaseResult.Status;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.UnmergedPathsException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryState;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.RawParseUtils;

public class RebaseCommand extends GitCommand<RebaseResult> {




















	private static final String COMMIT_MESSAGE_REPLACE_SOURCE = "\n[^\\"
			+ COMMIT_MESSAGE_COMMENT_PREFIX + "]";

	private static final String COMMIT_MESSAGE_REPLACE_WITH = "\n"
			+ COMMIT_MESSAGE_COMMENT_PREFIX;

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

	protected RebaseCommand(Repository repo) {
		super(repo);
		walk = new RevWalk(repo);
		rebaseState = new RebaseState(repo.getDirectory());
	}

	public RebaseResult call() throws GitAPIException
			RefNotFoundException
		RevCommit newHead = null;
		boolean lastStepWasForward = false;
		int squashCount = 0;
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
				break;
			case BEGIN:
				RebaseResult res = initFilesAndRewind();
				if (stopAfterInitialization)
					return RebaseResult.INTERACTIVE_PREPARED_RESULT;
				if (res != null)
					return res;
			}

			if (monitor.isCancelled())
				return abort(RebaseResult.ABORTED_RESULT);

			if (operation == Operation.PROCESS_STEPS)
				newHead = continueRebase();

			if (operation == Operation.CONTINUE) {
				newHead = continueRebase();

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

			ObjectReader or = repo.newObjectReader();

			LinkedList<Step> steps = loadSteps();
			if (steps.isEmpty())
				return abort(RebaseResult.NOTHING_TO_DO_RESULT);
			if (isInteractive()) {
				interactiveHandler.prepareSteps(steps);
				writeSteps(steps);
			}
			if (steps.isEmpty() && !rebaseState.getFile(DONE).exists())
				return abort(RebaseResult.NOTHING_TO_DO_RESULT);
			StringBuilder sb = new StringBuilder();
			for (; !steps.isEmpty(); steps = loadSteps()) {
				Step step = steps.poll();
				if (step == null) {
					continue;
				}
				popSteps(1);

				Collection<ObjectId> ids = or.resolve(step.commit);
				if (ids.size() != 1)
					throw new JGitInternalException(
							"Could not resolve uniquely the abbreviated object ID");
				RevCommit commitToPick = walk
						.parseCommit(ids.iterator().next());
				if (monitor.isCancelled())
					return new RebaseResult(commitToPick);
				try {
					monitor.beginTask(MessageFormat.format(
							JGitText.get().applyingCommit
							commitToPick.getShortMessage())
							ProgressMonitor.UNKNOWN);
					newHead = tryFastForward(commitToPick);
					lastStepWasForward = newHead != null;
					if (!lastStepWasForward) {
						String ourCommitName = getOurCommitName();
						CherryPickResult cherryPickResult = new Git(repo)
								.cherryPick().include(commitToPick)
								.setOurCommitName(ourCommitName).call();
						switch (cherryPickResult.getStatus()) {
						case FAILED:
							if (operation == Operation.BEGIN)
								return abort(new RebaseResult(
										cherryPickResult.getFailingPaths()));
							else
								return stop(commitToPick);
						case CONFLICTING:
							return stop(commitToPick);
						case OK:
							newHead = cherryPickResult.getNewHead();
						}
					}
					switch (step.action) {
					case PICK:
					case SQUASH:
					case FIXUP:
						Ref orig_head = repo.getRef(Constants.ORIG_HEAD);
						ObjectId orig_headId = orig_head.getObjectId();

						Git.wrap(repo).reset().setMode(ResetType.SOFT)
								.setRef("HEAD~1").call();
						repo.writeOrigHead(orig_headId);

						File messageFixup = rebaseState.getFile(MESSAGE_FIXUP);
						if (step.action == Action.SQUASH) {
							if (messageFixup.exists()) {
								messageFixup.delete();
							}
						}
						if (!rebaseState.getFile(DONE).exists()
								|| rebaseState.readFile(DONE).isEmpty()) {
							throw new JGitInternalException(
						}
						File messageSquash = rebaseState
								.getFile(MESSAGE_SQUASH);
						try {
							String currSquashMessage = null;
							if (!messageSquash.exists()) {
								squashCount = 1;

								RevWalk rw = new RevWalk(repo);
								RevCommit squashInto = rw.parseCommit(repo
										.resolve(Constants.HEAD));
								rw.release();
								String contendPre = COMMIT_MESSAGE_COMMENT_PREFIX

								currSquashMessage = contendPre
										+ "The first commit's message is:\n"
										+ squashInto.getFullMessage();
								rebaseState.createFile(MESSAGE_SQUASH
										contendPre);
								if (step.action == Action.FIXUP) {
									rebaseState.createFile(MESSAGE_FIXUP
								}
							}
							if (currSquashMessage == null)
								currSquashMessage = rebaseState
									.readFile(MESSAGE_SQUASH);
							sb.setLength(0);
							squashCount++;
							sb.append("This is a combination of " + squashCount
									+ " commits\n");

							String ordinal;
							switch (squashCount % 10) {
							case 1:
								break;
							case 2:
								break;
							case 3:
								break;
							default:
								break;
							}

							if (step.action == Action.SQUASH) {
								sb.append("#\tThe ").append(squashCount)
										.append(ordinal)
										.append(" commit's message is:\n");
								sb.append(commitToPick.getFullMessage());
							} else {
								sb.append("#\tThe ")
										.append(squashCount)
										.append(ordinal)
										.append(" commit message will be skipped:\n");
								sb.append(commitToPick.getFullMessage()
										.replace(COMMIT_MESSAGE_REPLACE_SOURCE
												COMMIT_MESSAGE_REPLACE_WITH));
							}
							sb.append(currSquashMessage
									.substring(currSquashMessage.indexOf("\n") + 1));

							String contend = sb.toString();
							rebaseState.createFile(MESSAGE_SQUASH
							if (messageFixup.exists()) {
								rebaseState.createFile(MESSAGE_FIXUP
							}

						} finally {
							Step nextStep = steps.peek();
							String commitMessage = rebaseState
									.readFile(messageFixup.exists() ? MESSAGE_FIXUP
											: MESSAGE_SQUASH);
							if (nextStep != null
									&& ((nextStep.getAction() == Action.FIXUP) || (nextStep
											.getAction() == Action.SQUASH))) {
								newHead = new Git(repo).commit()
										.setMessage(commitMessage)
										.setAmend(true).call();
							} else {
								if (messageFixup.exists()) {
									newHead = new Git(repo).commit()
											.setMessage(commitMessage).setAmend(true)
											.call();
								} else {
									String newMessage = commitMessage;
									if (interactiveHandler != null) {
										newMessage = interactiveHandler
												.modifyCommitMessage(commitMessage);
									}
									if (newMessage == null)
										return abort(RebaseResult.ABORTED_RESULT);

									newHead = new Git(repo).commit()
											.setMessage(newMessage).setAmend(true)
											.call();
									}
							}
						}
						break;
					case REWORD:
						String newMessage = commitToPick.getFullMessage();
						if (isInteractive()) {
							newMessage = interactiveHandler
									.modifyCommitMessage(newMessage);
							if (newMessage == null)
								return abort(RebaseResult.ABORTED_RESULT);
						}
						newHead = new Git(repo).commit().setMessage(newMessage)
								.setAmend(true).call();
						continue;
					case EDIT:
						rebaseState.createFile(AMEND
						return stop(commitToPick);
					}
				} finally {
					monitor.endTask();
				}
			}
			if (newHead != null) {
				String headName = rebaseState.readFile(HEAD_NAME);
				updateHead(headName
				FileUtils.delete(rebaseState.getDir()
				if (lastStepWasForward)
					return RebaseResult.FAST_FORWARD_RESULT;
				return RebaseResult.OK_RESULT;
			}
			return RebaseResult.FAST_FORWARD_RESULT;
		} catch (CheckoutConflictException cce) {
			return new RebaseResult(cce.getConflictingPaths());
		} catch (IOException ioe) {
			throw new JGitInternalException(ioe.getMessage()
		}
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
			rup.setNewObjectId(newHead);
			Result res = rup.forceUpdate();
			switch (res) {
			case FAST_FORWARD:
			case FORCED:
			case NO_CHANGE:
				break;
			default:
				throw new JGitInternalException("Updating HEAD failed");
			}
			rup = repo.updateRef(Constants.HEAD);
			res = rup.link(headName);
			switch (res) {
			case FAST_FORWARD:
			case FORCED:
			case NO_CHANGE:
				break;
			default:
				throw new JGitInternalException("Updating HEAD failed");
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
			boolean needsDeleteFiles = dco.checkout();
			if (needsDeleteFiles) {
				List<String> fileList = dco.getToBeDeleted();
				for (String filePath : fileList) {
					File fileToDelete = new File(repo.getWorkTree()
					if (fileToDelete.exists())
						FileUtils.delete(fileToDelete
								| FileUtils.RETRY);
				}
			}
		} finally {
			dc.unlock();
		}
		RevWalk rw = new RevWalk(repo);
		RevCommit commit = rw.parseCommit(repo.resolve(Constants.HEAD));
		rw.release();
		return commit;
	}

	private RevCommit continueRebase() throws GitAPIException
		DirCache dc = repo.readDirCache();
		boolean hasUnmergedPaths = dc.hasUnmergedPaths();
		if (hasUnmergedPaths)
			throw new UnmergedPathsException();

		TreeWalk treeWalk = new TreeWalk(repo);
		treeWalk.reset();
		treeWalk.setRecursive(true);
		treeWalk.addTree(new DirCacheIterator(dc));
		if (id == null)
			throw new NoHeadException(
					JGitText.get().cannotRebaseWithoutCurrentHead);

		treeWalk.addTree(id);

		treeWalk.setFilter(TreeFilter.ANY_DIFF);

		boolean needsCommit = treeWalk.next();
		treeWalk.release();

		if (needsCommit) {
			CommitCommand commit = new Git(repo).commit();
			commit.setMessage(rebaseState.readFile(MESSAGE));
			commit.setAuthor(parseAuthor());
			return commit.call();
		}
		return null;
	}

	private PersonIdent parseAuthor() throws IOException {
		File authorScriptFile = rebaseState.getFile(AUTHOR_SCRIPT);
		byte[] raw;
		try {
			raw = IO.readFully(authorScriptFile);
		} catch (FileNotFoundException notFound) {
			return null;
		}
		return parseAuthor(raw);
	}

	private RebaseResult stop(RevCommit commitToPick) throws IOException {
		PersonIdent author = commitToPick.getAuthorIdent();
		String authorScript = toAuthorScript(author);
		rebaseState.createFile(AUTHOR_SCRIPT
		rebaseState.createFile(MESSAGE
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DiffFormatter df = new DiffFormatter(bos);
		df.setRepository(repo);
		df.format(commitToPick.getParent(0)
		rebaseState.createFile(PATCH
				Constants.CHARACTER_ENCODING));
		rebaseState.createFile(STOPPED_SHA
				repo.newObjectReader()
				.abbreviate(
				commitToPick).name());
		repo.writeCherryPickHead(null);
		return new RebaseResult(commitToPick);
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
		List<String> todoLines = new ArrayList<String>();
		List<String> poppedLines = new ArrayList<String>();
		File todoFile = rebaseState.getFile(GIT_REBASE_TODO);
		File doneFile = rebaseState.getFile(DONE);
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(todoFile)
		try {
			while (poppedLines.size() < numSteps) {
				String popCandidate = br.readLine();
				if (popCandidate == null)
					break;
				if (popCandidate.length() == 0)
					continue;
				if (popCandidate.charAt(0) == '#')
					continue;
				int spaceIndex = popCandidate.indexOf(' ');
				boolean pop = false;
				if (spaceIndex >= 0) {
					String actionToken = popCandidate.substring(0
					pop = Action.parse(actionToken) != null;
				}
				if (pop)
					poppedLines.add(popCandidate);
				else
					todoLines.add(popCandidate);
			}
			String readLine = br.readLine();
			while (readLine != null) {
				todoLines.add(readLine);
				readLine = br.readLine();
			}
		} finally {
			br.close();
		}

		BufferedWriter todoWriter = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(todoFile)
		try {
			for (String writeLine : todoLines) {
				todoWriter.write(writeLine);
				todoWriter.newLine();
			}
		} finally {
			todoWriter.close();
		}

		if (poppedLines.size() > 0) {
			BufferedWriter doneWriter = new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(doneFile
							Constants.CHARACTER_ENCODING));
			try {
				for (String writeLine : poppedLines) {
					doneWriter.write(writeLine);
					doneWriter.newLine();
				}
			} finally {
				doneWriter.close();
			}
		}
	}

	private RebaseResult initFilesAndRewind() throws IOException
			GitAPIException {

		Ref head = repo.getRef(Constants.HEAD);
		if (head == null || head.getObjectId() == null)
			throw new RefNotFoundException(MessageFormat.format(
					JGitText.get().refNotResolved

		String headName;
		if (head.isSymbolic())
			headName = head.getTarget().getName();
		else
			headName = "detached HEAD";
		ObjectId headId = head.getObjectId();
		if (headId == null)
			throw new RefNotFoundException(MessageFormat.format(
					JGitText.get().refNotResolved
		RevCommit headCommit = walk.lookupCommit(headId);
		RevCommit upstream = walk.lookupCommit(upstreamCommit.getId());

		if (!isInteractive() && walk.isMergedInto(upstream
			return RebaseResult.UP_TO_DATE_RESULT;
		else if (!isInteractive() && walk.isMergedInto(headCommit
			monitor.beginTask(MessageFormat.format(
					JGitText.get().resettingHead
					upstreamCommit.getShortMessage())
			checkoutCommit(upstreamCommit);
			monitor.endTask();

			updateHead(headName
			return RebaseResult.FAST_FORWARD_RESULT;
		}

		monitor.beginTask(JGitText.get().obtainingCommitsForCherryPick
				ProgressMonitor.UNKNOWN);

		LogCommand cmd = new Git(repo).log().addRange(upstreamCommit
				headCommit);
		Iterable<RevCommit> commitsToUse = cmd.call();

		List<RevCommit> cherryPickList = new ArrayList<RevCommit>();
		for (RevCommit commit : commitsToUse) {
			if (commit.getParentCount() != 1)
				continue;
			cherryPickList.add(commit);
		}

		Collections.reverse(cherryPickList);
		FileUtils.mkdir(rebaseState.getDir());

		repo.writeOrigHead(headId);
		rebaseState.createFile(REBASE_HEAD
		rebaseState.createFile(HEAD_NAME
		rebaseState.createFile(ONTO
		rebaseState.createFile(ONTO_NAME
		rebaseState.createFile(INTERACTIVE

		writeSteps("# Created by EGit: rebasing " + upstreamCommit.name()
				+ " onto " + headId.name()
				cherryPickList
					private ObjectReader reader = walk.getObjectReader();

					public String getToken(RevCommit obj) {
						return Action.PICK.toToken();
					}

					public String getName(RevCommit obj) throws IOException {
						return reader.abbreviate(obj).name();
					}

					public String getShortMessage(RevCommit obj) {
						return obj.getShortMessage();
					}

				});

		monitor.endTask();

		monitor.beginTask(MessageFormat.format(JGitText.get().rewinding
				upstreamCommit.getShortMessage())
		boolean checkoutOk = false;
		try {
			checkoutOk = checkoutCommit(upstreamCommit);
		} finally {
			if (!checkoutOk)
				FileUtils.delete(rebaseState.getDir()
		}
		monitor.endTask();

		return null;
	}

	private boolean isInteractive() {
		return interactiveHandler != null;
	}

	public RevCommit tryFastForward(RevCommit newCommit) throws IOException
			GitAPIException {
		Ref head = repo.getRef(Constants.HEAD);
		if (head == null || head.getObjectId() == null)
			throw new RefNotFoundException(MessageFormat.format(
					JGitText.get().refNotResolved

		ObjectId headId = head.getObjectId();
		if (headId == null)
			throw new RefNotFoundException(MessageFormat.format(
					JGitText.get().refNotResolved
		RevCommit headCommit = walk.lookupCommit(headId);
		if (walk.isMergedInto(newCommit
			return newCommit;

		String headName;
		if (head.isSymbolic())
			headName = head.getTarget().getName();
		else
			headName = "detached HEAD";
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
		} catch (RefAlreadyExistsException e) {
			throw new JGitInternalException(e.getMessage()
		} catch (RefNotFoundException e) {
			throw new JGitInternalException(e.getMessage()
		} catch (InvalidRefNameException e) {
			throw new JGitInternalException(e.getMessage()
		} catch (CheckoutConflictException e) {
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
									"upstream"));
				return;
			default:
				throw new WrongRepositoryStateException(MessageFormat.format(
						JGitText.get().wrongRepositoryState
								.getRepositoryState().name()));

			}
	}

	private RebaseResult abort(RebaseResult result) throws IOException {
		try {
			ObjectId origHead = repo.readOrigHead();
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
			walk.release();
		} finally {
			monitor.endTask();
		}
		try {
			String headName = rebaseState.readFile(HEAD_NAME);
			if (headName.startsWith(Constants.R_REFS)) {
				monitor.beginTask(MessageFormat.format(
						JGitText.get().resettingHead
						ProgressMonitor.UNKNOWN);

				RefUpdate refUpdate = repo.updateRef(Constants.HEAD
				Result res = refUpdate.link(headName);
				switch (res) {
				case FAST_FORWARD:
				case FORCED:
				case NO_CHANGE:
					break;
				default:
					throw new JGitInternalException(
							JGitText.get().abortingRebaseFailed);
				}
			}
			FileUtils.delete(rebaseState.getDir()
			repo.writeCherryPickHead(null);
			return result;

		} finally {
			monitor.endTask();
		}
	}

	private boolean checkoutCommit(RevCommit commit) throws IOException
			CheckoutConflictException {
		try {
			RevCommit head = walk.parseCommit(repo.resolve(Constants.HEAD));
			DirCacheCheckout dco = new DirCacheCheckout(repo
					repo.lockDirCache()
			dco.setFailOnConflict(true);
			try {
				dco.checkout();
			} catch (org.eclipse.jgit.errors.CheckoutConflictException cce) {
				throw new CheckoutConflictException(dco.getConflicts()
			}
			RefUpdate refUpdate = repo.updateRef(Constants.HEAD
			refUpdate.setExpectedOldObjectId(head);
			refUpdate.setNewObjectId(commit);
			Result res = refUpdate.forceUpdate();
			switch (res) {
			case FAST_FORWARD:
			case NO_CHANGE:
			case FORCED:
				break;
			default:
				throw new IOException("Could not rewind to upstream commit");
			}
		} finally {
			walk.release();
			monitor.endTask();
		}
		return true;
	}

	private interface StepFormatter<T> {
		public static final StepFormatter<Step> STEP_FORMATTER = new StepFormatter<Step>() {

			public String getToken(Step obj) {
				return obj.action.token;
			}

			public String getName(Step obj) {
				return obj.commit.name();
			}

			public String getShortMessage(Step obj) {
				return RawParseUtils.decode(obj.shortMessage);
			}
		};

		String getToken(T obj) throws IOException;

		String getName(T obj) throws IOException;

		String getShortMessage(T obj) throws IOException;
	}

	public void writeSteps(List<Step> steps) throws IOException
			WrongRepositoryStateException {
		if (repo.getRepositoryState() != RepositoryState.REBASING_INTERACTIVE)
		writeSteps(null
	}

	private <T> void writeSteps(final String headComment
			StepFormatter<T> formatter) throws
			FileNotFoundException
		if (formatter == null)
		BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(rebaseState.getFile(GIT_REBASE_TODO))
				Constants.CHARACTER_ENCODING));
		try {
			if (headComment != null) {
				fw.write(headComment);
				fw.newLine();
			}
			StringBuilder sb = new StringBuilder();
			for (T step : steps) {
				if (step == null)
					continue;
				sb.setLength(0);
				sb.append(formatter.getToken(step));
				sb.append(formatter.getName(step));
				sb.append(formatter.getShortMessage(step));
				fw.write(sb.toString());
				fw.newLine();
			}
		} finally {
			fw.close();
		}
	}

	public List<Step> readSteps() throws IOException
			WrongRepositoryStateException {
		if (repo.getRepositoryState() == RepositoryState.REBASING_INTERACTIVE)
			return loadSteps();
		throw new WrongRepositoryStateException(
				"Not in rebase interactive state");
	}

	LinkedList<Step> loadSteps() throws IOException {
		byte[] buf = IO.readFully(rebaseState.getFile(GIT_REBASE_TODO));
		int ptr = 0;
		int tokenBegin = 0;
		LinkedList<Step> r = new LinkedList<Step>();
		while (ptr < buf.length) {
			tokenBegin = ptr;
			ptr = RawParseUtils.nextLF(buf
			int nextSpace = RawParseUtils.next(buf
			int tokenCount = 0;
			Step current = null;
			while (tokenCount < 3 && nextSpace < ptr) {
				switch (tokenCount) {
				case 0:
					String actionToken = new String(buf
							- tokenBegin - 1);
					tokenBegin = nextSpace;
					if (actionToken.charAt(0) == '#') {
						tokenCount = 3;
						break;
					}
					Action action = Action.parse(actionToken);
					if (action != null)
						current = new Step(Action.parse(actionToken));
					break;
				case 1:
					if (current == null)
						break;
					nextSpace = RawParseUtils.next(buf
					String commitToken = new String(buf
							- tokenBegin - 1);
					tokenBegin = nextSpace;
					current.commit = AbbreviatedObjectId
							.fromString(commitToken);
					break;
				case 2:
					if (current == null)
						break;
					nextSpace = ptr;
					int length = ptr - tokenBegin;
					current.shortMessage = new byte[length];
					System.arraycopy(buf
							length);
					r.add(current);
					break;
				}
				tokenCount++;
			}
		}
		return r;
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
					"setUpstreamName must be called after setUpstream.");
		}
		this.upstreamCommitName = upstreamName;
		return this;
	}

	public RebaseCommand setOperation(Operation operation) {
		this.operation = operation;
		return this;
	}

	public RebaseCommand setProgressMonitor(ProgressMonitor monitor) {
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

	public interface InteractiveHandler {
		void prepareSteps(List<Step> steps);

		String modifyCommitMessage(String commit);
	}

	public static enum Action {
		PICK("pick"
		REWORD("reword"
		EDIT("edit"
		SQUASH("squash"
		FIXUP("fixup"
		private final String token;

		private final String shortToken;

		private Action(String token
			this.token = token;
			this.shortToken = shortToken;
		}

		public String toToken() {
			return this.token;
		}

		@SuppressWarnings("nls")
		@Override
		public String toString() {
			return "Action[" + token + "]";
		}

		static Action parse(String token) {
			for (Action action : Action.values()) {
				if (action.token.equals(token)
						|| action.shortToken.equals(token))
					return action;
			}
			throw new JGitInternalException(MessageFormat.format(
					JGitText.get().unknownOrUnsupportedCommand
					Action.values()));
		}
	}

	public static class Step {
		Action action;

		AbbreviatedObjectId commit;

		byte[] shortMessage;

		Step(Action action) {
			this.action = action;
		}

		public Action getAction() {
			return action;
		}

		public void setAction(Action action) {
			this.action = action;
		}

		public AbbreviatedObjectId getCommit() {
			return commit;
		}

		public byte[] getShortMessage() {
			return shortMessage;
		}

		@SuppressWarnings("nls")
		@Override
		public String toString() {
			return "Step["
					+ action
					+ "
					+ ((commit == null) ? "null" : commit)
					+ "
					+ ((shortMessage == null) ? "null" : new String(
							shortMessage)) + "]";
		}

		public static Step create(AbbreviatedObjectId commitId
				byte[] shortMessage
			Step theStep = new Step(action);
			theStep.commit = commitId;
			theStep.shortMessage = shortMessage;
			return theStep;
		}
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

		public String readFile(String name) throws IOException {
			return readFile(getDir()
		}

		public void createFile(String name
			createFile(getDir()
		}

		public File getFile(String name) {
			return new File(getDir()
		}

		private static String readFile(File directory
				throws IOException {
			byte[] content = IO.readFully(new File(directory
			int end = RawParseUtils.prevLF(content
			return RawParseUtils.decode(content
		}

		private static void createFile(File parentDir
				String content)
				throws IOException {
			File file = new File(parentDir
			FileOutputStream fos = new FileOutputStream(file);
			try {
				fos.write(content.getBytes(Constants.CHARACTER_ENCODING));
				fos.write('\n');
			} finally {
				fos.close();
			}
		}
	}
}
