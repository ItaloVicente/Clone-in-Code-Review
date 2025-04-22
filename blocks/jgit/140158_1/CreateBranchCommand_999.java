package org.eclipse.jgit.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jgit.api.errors.AbortedByHookException;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.EmptyCommitException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.UnmergedPathsException;
import org.eclipse.jgit.api.errors.UnsupportedSigningFormatException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuildIterator;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.UnmergedPathException;
import org.eclipse.jgit.hooks.CommitMsgHook;
import org.eclipse.jgit.hooks.Hooks;
import org.eclipse.jgit.hooks.PostCommitHook;
import org.eclipse.jgit.hooks.PreCommitHook;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.GpgConfig;
import org.eclipse.jgit.lib.GpgConfig.GpgFormat;
import org.eclipse.jgit.lib.GpgSigner;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryState;
import org.eclipse.jgit.lib.internal.BouncyCastleGpgSigner;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.TreeWalk.OperationType;
import org.eclipse.jgit.util.ChangeIdUtil;

public class CommitCommand extends GitCommand<RevCommit> {
	private PersonIdent author;

	private PersonIdent committer;

	private String message;

	private boolean all;

	private List<String> only = new ArrayList<>();

	private boolean[] onlyProcessed;

	private boolean amend;

	private boolean insertChangeId;

	private List<ObjectId> parents = new LinkedList<>();

	private String reflogComment;

	private boolean useDefaultReflogMessage = true;

	private boolean noVerify;

	private HashMap<String

	private Boolean allowEmpty;

	private Boolean signCommit;

	private String signingKey;

	private GpgSigner gpgSigner;

	private CredentialsProvider credentialsProvider;

	protected CommitCommand(Repository repo) {
		super(repo);
		this.credentialsProvider = CredentialsProvider.getDefault();
	}

	@Override
	public RevCommit call() throws GitAPIException
			NoMessageException
			ConcurrentRefUpdateException
			AbortedByHookException {
		checkCallable();
		Collections.sort(only);

		try (RevWalk rw = new RevWalk(repo)) {
			RepositoryState state = repo.getRepositoryState();
			if (!state.canCommit())
				throw new WrongRepositoryStateException(MessageFormat.format(
						JGitText.get().cannotCommitOnARepoWithState
						state.name()));

			if (!noVerify) {
				Hooks.preCommit(repo
						.call();
			}

			processOptions(state

			if (all && !repo.isBare()) {
				try (Git git = new Git(repo)) {
					git.add()
							.setUpdate(true).call();
				} catch (NoFilepatternException e) {
					throw new JGitInternalException(e.getMessage()
				}
			}

			Ref head = repo.exactRef(Constants.HEAD);
			if (head == null)
				throw new NoHeadException(
						JGitText.get().commitOnRepoWithoutHEADCurrentlyNotSupported);

			if (headId == null && amend)
				throw new WrongRepositoryStateException(
						JGitText.get().commitAmendOnInitialNotPossible);

			if (headId != null)
				if (amend) {
					RevCommit previousCommit = rw.parseCommit(headId);
					for (RevCommit p : previousCommit.getParents())
						parents.add(p.getId());
					if (author == null)
						author = previousCommit.getAuthorIdent();
				} else {
					parents.add(0
				}

			if (!noVerify) {
				message = Hooks
						.commitMsg(repo
								hookOutRedirect.get(CommitMsgHook.NAME))
						.setCommitMessage(message).call();
			}

			DirCache index = repo.lockDirCache();
			try (ObjectInserter odi = repo.newObjectInserter()) {
				if (!only.isEmpty())
					index = createTemporaryIndex(headId

				ObjectId indexTreeId = index.writeTree(odi);

				if (insertChangeId)
					insertChangeId(indexTreeId);

				if (headId != null && !allowEmpty.booleanValue()) {
					RevCommit headCommit = rw.parseCommit(headId);
					headCommit.getTree();
					if (indexTreeId.equals(headCommit.getTree())) {
						throw new EmptyCommitException(
								JGitText.get().emptyCommit);
					}
				}

				CommitBuilder commit = new CommitBuilder();
				commit.setCommitter(committer);
				commit.setAuthor(author);
				commit.setMessage(message);

				commit.setParentIds(parents);
				commit.setTreeId(indexTreeId);

				if (signCommit.booleanValue()) {
					gpgSigner.sign(commit
							credentialsProvider);
				}

				ObjectId commitId = odi.insert(commit);
				odi.flush();

				RevCommit revCommit = rw.parseCommit(commitId);
				RefUpdate ru = repo.updateRef(Constants.HEAD);
				ru.setNewObjectId(commitId);
				if (!useDefaultReflogMessage) {
					ru.setRefLogMessage(reflogComment
				} else {
					ru.setRefLogMessage(prefix + revCommit.getShortMessage()
							false);
				}
				if (headId != null)
					ru.setExpectedOldObjectId(headId);
				else
					ru.setExpectedOldObjectId(ObjectId.zeroId());
				Result rc = ru.forceUpdate();
				switch (rc) {
				case NEW:
				case FORCED:
				case FAST_FORWARD: {
					setCallable(false);
					if (state == RepositoryState.MERGING_RESOLVED
							|| isMergeDuringRebase(state)) {
						repo.writeMergeCommitMsg(null);
						repo.writeMergeHeads(null);
					} else if (state == RepositoryState.CHERRY_PICKING_RESOLVED) {
						repo.writeMergeCommitMsg(null);
						repo.writeCherryPickHead(null);
					} else if (state == RepositoryState.REVERTING_RESOLVED) {
						repo.writeMergeCommitMsg(null);
						repo.writeRevertHead(null);
					}
					Hooks.postCommit(repo
							hookOutRedirect.get(PostCommitHook.NAME)).call();

					return revCommit;
				}
				case REJECTED:
				case LOCK_FAILURE:
					throw new ConcurrentRefUpdateException(
							JGitText.get().couldNotLockHEAD
				default:
					throw new JGitInternalException(MessageFormat.format(
							JGitText.get().updatingRefFailed
							commitId.toString()
				}
			} finally {
				index.unlock();
			}
		} catch (UnmergedPathException e) {
			throw new UnmergedPathsException(e);
		} catch (IOException e) {
			throw new JGitInternalException(
					JGitText.get().exceptionCaughtDuringExecutionOfCommitCommand
		}
	}

	private void insertChangeId(ObjectId treeId) {
		ObjectId firstParentId = null;
		if (!parents.isEmpty())
			firstParentId = parents.get(0);
		ObjectId changeId = ChangeIdUtil.computeChangeId(treeId
				author
		message = ChangeIdUtil.insertId(message
		if (changeId != null)
					+ ObjectId.zeroId().getName() + "\n"
	}

	private DirCache createTemporaryIndex(ObjectId headId
			RevWalk rw)
			throws IOException {
		ObjectInserter inserter = null;

		DirCacheBuilder existingBuilder = index.builder();

		DirCache inCoreIndex = DirCache.newInCore();
		DirCacheBuilder tempBuilder = inCoreIndex.builder();

		onlyProcessed = new boolean[only.size()];
		boolean emptyCommit = true;

		try (TreeWalk treeWalk = new TreeWalk(repo)) {
			treeWalk.setOperationType(OperationType.CHECKIN_OP);
			int dcIdx = treeWalk
					.addTree(new DirCacheBuildIterator(existingBuilder));
			FileTreeIterator fti = new FileTreeIterator(repo);
			fti.setDirCacheIterator(treeWalk
			int fIdx = treeWalk.addTree(fti);
			int hIdx = -1;
			if (headId != null)
				hIdx = treeWalk.addTree(rw.parseTree(headId));
			treeWalk.setRecursive(true);

			String lastAddedFile = null;
			while (treeWalk.next()) {
				String path = treeWalk.getPathString();
				int pos = lookupOnly(path);

				CanonicalTreeParser hTree = null;
				if (hIdx != -1)
					hTree = treeWalk.getTree(hIdx

				DirCacheIterator dcTree = treeWalk.getTree(dcIdx
						DirCacheIterator.class);

				if (pos >= 0) {

					FileTreeIterator fTree = treeWalk.getTree(fIdx
							FileTreeIterator.class);

					boolean tracked = dcTree != null || hTree != null;
					if (!tracked)
						continue;

					if (path.equals(lastAddedFile))
						continue;

					lastAddedFile = path;

					if (fTree != null) {
						final DirCacheEntry dcEntry = new DirCacheEntry(path);
						long entryLength = fTree.getEntryLength();
						dcEntry.setLength(entryLength);
						dcEntry.setLastModified(fTree.getEntryLastModified());
						dcEntry.setFileMode(fTree.getIndexFileMode(dcTree));

						boolean objectExists = (dcTree != null
								&& fTree.idEqual(dcTree))
								|| (hTree != null && fTree.idEqual(hTree));
						if (objectExists) {
							dcEntry.setObjectId(fTree.getEntryObjectId());
						} else {
							if (FileMode.GITLINK.equals(dcEntry.getFileMode()))
								dcEntry.setObjectId(fTree.getEntryObjectId());
							else {
								if (inserter == null)
									inserter = repo.newObjectInserter();
								long contentLength = fTree
										.getEntryContentLength();
								try (InputStream inputStream = fTree
										.openEntryStream()) {
									dcEntry.setObjectId(inserter.insert(
											Constants.OBJ_BLOB
											inputStream));
								}
							}
						}

						existingBuilder.add(dcEntry);
						tempBuilder.add(dcEntry);

						if (emptyCommit
								&& (hTree == null || !hTree.idEqual(fTree)
										|| hTree.getEntryRawMode() != fTree
												.getEntryRawMode()))
							emptyCommit = false;
					} else {

						if (emptyCommit && hTree != null)
							emptyCommit = false;
					}

					onlyProcessed[pos] = true;
				} else {
					if (hTree != null) {
						final DirCacheEntry dcEntry = new DirCacheEntry(path);
						dcEntry.setObjectId(hTree.getEntryObjectId());
						dcEntry.setFileMode(hTree.getEntryFileMode());

						tempBuilder.add(dcEntry);
					}

					if (dcTree != null)
						existingBuilder.add(dcTree.getDirCacheEntry());
				}
			}
		}

		for (int i = 0; i < onlyProcessed.length; i++)
			if (!onlyProcessed[i])
				throw new JGitInternalException(MessageFormat.format(
						JGitText.get().entryNotFoundByPath

		if (emptyCommit && !allowEmpty.booleanValue())
			throw new JGitInternalException(JGitText.get().emptyCommit);

		existingBuilder.commit();
		tempBuilder.finish();
		return inCoreIndex;
	}

	private int lookupOnly(String pathString) {
		String p = pathString;
		while (true) {
			int position = Collections.binarySearch(only
			if (position >= 0)
				return position;
			if (l < 1)
				break;
			p = p.substring(0
		}
		return -1;
	}

	private void processOptions(RepositoryState state
			throws NoMessageException
		if (committer == null)
			committer = new PersonIdent(repo);
		if (author == null && !amend)
			author = committer;
		if (allowEmpty == null)
			allowEmpty = (only.isEmpty()) ? Boolean.TRUE : Boolean.FALSE;

		if (state == RepositoryState.MERGING_RESOLVED
				|| isMergeDuringRebase(state)) {
			try {
				parents = repo.readMergeHeads();
				if (parents != null)
					for (int i = 0; i < parents.size(); i++) {
						RevObject ro = rw.parseAny(parents.get(i));
						if (ro instanceof RevTag)
							parents.set(i
					}
			} catch (IOException e) {
				throw new JGitInternalException(MessageFormat.format(
						JGitText.get().exceptionOccurredDuringReadingOfGIT_DIR
						Constants.MERGE_HEAD
			}
			if (message == null) {
				try {
					message = repo.readMergeCommitMsg();
				} catch (IOException e) {
					throw new JGitInternalException(MessageFormat.format(
							JGitText.get().exceptionOccurredDuringReadingOfGIT_DIR
							Constants.MERGE_MSG
				}
			}
		} else if (state == RepositoryState.SAFE && message == null) {
			try {
				message = repo.readSquashCommitMsg();
				if (message != null)
			} catch (IOException e) {
				throw new JGitInternalException(MessageFormat.format(
						JGitText.get().exceptionOccurredDuringReadingOfGIT_DIR
						Constants.MERGE_MSG
			}

		}
		if (message == null)
			throw new NoMessageException(JGitText.get().commitMessageNotSpecified);

		GpgConfig gpgConfig = new GpgConfig(repo.getConfig());
		if (signCommit == null) {
			signCommit = gpgConfig.isSignCommits() ? Boolean.TRUE
					: Boolean.FALSE;
		}
		if (signingKey == null) {
			signingKey = gpgConfig.getSigningKey();
		}
		if (gpgSigner == null) {
			if (gpgConfig.getKeyFormat() != GpgFormat.OPENPGP) {
				throw new UnsupportedSigningFormatException(
						JGitText.get().onlyOpenPgpSupportedForSigning);
			}
			gpgSigner = GpgSigner.getDefault();
			if (gpgSigner == null) {
				gpgSigner = new BouncyCastleGpgSigner();
			}
		}
	}

	private boolean isMergeDuringRebase(RepositoryState state) {
		if (state != RepositoryState.REBASING_INTERACTIVE
				&& state != RepositoryState.REBASING_MERGE)
			return false;
		try {
			return repo.readMergeHeads() != null;
		} catch (IOException e) {
			throw new JGitInternalException(MessageFormat.format(
					JGitText.get().exceptionOccurredDuringReadingOfGIT_DIR
					Constants.MERGE_HEAD
		}
	}

	public CommitCommand setMessage(String message) {
		checkCallable();
		this.message = message;
		return this;
	}

	public CommitCommand setAllowEmpty(boolean allowEmpty) {
		this.allowEmpty = Boolean.valueOf(allowEmpty);
		return this;
	}

	public String getMessage() {
		return message;
	}

	public CommitCommand setCommitter(PersonIdent committer) {
		checkCallable();
		this.committer = committer;
		return this;
	}

	public CommitCommand setCommitter(String name
		checkCallable();
		return setCommitter(new PersonIdent(name
	}

	public PersonIdent getCommitter() {
		return committer;
	}

	public CommitCommand setAuthor(PersonIdent author) {
		checkCallable();
		this.author = author;
		return this;
	}

	public CommitCommand setAuthor(String name
		checkCallable();
		return setAuthor(new PersonIdent(name
	}

	public PersonIdent getAuthor() {
		return author;
	}

	public CommitCommand setAll(boolean all) {
		checkCallable();
		if (all && !only.isEmpty())
			throw new JGitInternalException(MessageFormat.format(
					JGitText.get().illegalCombinationOfArguments
		this.all = all;
		return this;
	}

	public CommitCommand setAmend(boolean amend) {
		checkCallable();
		this.amend = amend;
		return this;
	}

	public CommitCommand setOnly(String only) {
		checkCallable();
		if (all)
			throw new JGitInternalException(MessageFormat.format(
					JGitText.get().illegalCombinationOfArguments
		String o = only.endsWith("/") ? only.substring(0
				: only;
		if (!this.only.contains(o))
			this.only.add(o);
		return this;
	}

	public CommitCommand setInsertChangeId(boolean insertChangeId) {
		checkCallable();
		this.insertChangeId = insertChangeId;
		return this;
	}

	public CommitCommand setReflogComment(String reflogComment) {
		this.reflogComment = reflogComment;
		useDefaultReflogMessage = false;
		return this;
	}

	public CommitCommand setNoVerify(boolean noVerify) {
		this.noVerify = noVerify;
		return this;
	}

	public CommitCommand setHookOutputStream(PrintStream hookStdOut) {
		setHookOutputStream(PreCommitHook.NAME
		setHookOutputStream(CommitMsgHook.NAME
		setHookOutputStream(PostCommitHook.NAME
		return this;
	}

	public CommitCommand setHookOutputStream(String hookName
			PrintStream hookStdOut) {
		if (!(PreCommitHook.NAME.equals(hookName)
				|| CommitMsgHook.NAME.equals(hookName)
				|| PostCommitHook.NAME.equals(hookName))) {
			throw new IllegalArgumentException(
					MessageFormat.format(JGitText.get().illegalHookName
							hookName));
		}
		hookOutRedirect.put(hookName
		return this;
	}

	public CommitCommand setSigningKey(String signingKey) {
		checkCallable();
		this.signingKey = signingKey;
		return this;
	}

	public CommitCommand setSign(Boolean sign) {
		checkCallable();
		this.signCommit = sign;
		return this;
	}

	public void setCredentialsProvider(
			CredentialsProvider credentialsProvider) {
		this.credentialsProvider = credentialsProvider;
	}
}
