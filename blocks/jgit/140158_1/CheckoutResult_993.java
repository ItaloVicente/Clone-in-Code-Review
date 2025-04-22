package org.eclipse.jgit.api;

import static org.eclipse.jgit.treewalk.TreeWalk.OperationType.CHECKOUT_OP;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.api.CheckoutResult.Status;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.dircache.DirCacheCheckout.CheckoutMetadata;
import org.eclipse.jgit.dircache.DirCacheEditor;
import org.eclipse.jgit.dircache.DirCacheEditor.PathEdit;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.UnmergedPathException;
import org.eclipse.jgit.events.WorkingTreeModifiedEvent;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.CoreConfig.EolStreamType;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;

public class CheckoutCommand extends GitCommand<Ref> {

	public static enum Stage {
		BASE(DirCacheEntry.STAGE_1)

		OURS(DirCacheEntry.STAGE_2)

		THEIRS(DirCacheEntry.STAGE_3);

		private final int number;

		private Stage(int number) {
			this.number = number;
		}
	}

	private String name;

	private boolean forceRefUpdate = false;

	private boolean forced = false;

	private boolean createBranch = false;

	private boolean orphan = false;

	private CreateBranchCommand.SetupUpstreamMode upstreamMode;

	private String startPoint = null;

	private RevCommit startCommit;

	private Stage checkoutStage = null;

	private CheckoutResult status;

	private List<String> paths;

	private boolean checkoutAllPaths;

	private Set<String> actuallyModifiedPaths;

	private ProgressMonitor monitor = NullProgressMonitor.INSTANCE;

	protected CheckoutCommand(Repository repo) {
		super(repo);
		this.paths = new LinkedList<>();
	}

	@Override
	public Ref call() throws GitAPIException
			RefNotFoundException
			CheckoutConflictException {
		checkCallable();
		try {
			processOptions();
			if (checkoutAllPaths || !paths.isEmpty()) {
				checkoutPaths();
				status = new CheckoutResult(Status.OK
				setCallable(false);
				return null;
			}

			if (createBranch) {
				try (Git git = new Git(repo)) {
					CreateBranchCommand command = git.branchCreate();
					command.setName(name);
					if (startCommit != null)
						command.setStartPoint(startCommit);
					else
						command.setStartPoint(startPoint);
					if (upstreamMode != null)
						command.setUpstreamMode(upstreamMode);
					command.call();
				}
			}

			Ref headRef = repo.exactRef(Constants.HEAD);
			if (headRef == null) {
				throw new UnsupportedOperationException(
						JGitText.get().cannotCheckoutFromUnbornBranch);
			}
			String shortHeadRef = getShortBranchName(headRef);
			ObjectId branch;
			if (orphan) {
				if (startPoint == null && startCommit == null) {
					Result r = repo.updateRef(Constants.HEAD).link(
							getBranchName());
					if (!EnumSet.of(Result.NEW
						throw new JGitInternalException(MessageFormat.format(
								JGitText.get().checkoutUnexpectedResult
								r.name()));
					this.status = CheckoutResult.NOT_TRIED_RESULT;
					return repo.exactRef(Constants.HEAD);
				}
				branch = getStartPointObjectId();
			} else {
				branch = repo.resolve(name);
				if (branch == null)
					throw new RefNotFoundException(MessageFormat.format(
							JGitText.get().refNotResolved
			}

			RevCommit headCommit = null;
			RevCommit newCommit = null;
			try (RevWalk revWalk = new RevWalk(repo)) {
				AnyObjectId headId = headRef.getObjectId();
				headCommit = headId == null ? null
						: revWalk.parseCommit(headId);
				newCommit = revWalk.parseCommit(branch);
			}
			RevTree headTree = headCommit == null ? null : headCommit.getTree();
			DirCacheCheckout dco;
			DirCache dc = repo.lockDirCache();
			try {
				dco = new DirCacheCheckout(repo
						newCommit.getTree());
				dco.setFailOnConflict(true);
				dco.setForce(forced);
				if (forced) {
					dco.setFailOnConflict(false);
				}
				dco.setProgressMonitor(monitor);
				try {
					dco.checkout();
				} catch (org.eclipse.jgit.errors.CheckoutConflictException e) {
					status = new CheckoutResult(Status.CONFLICTS
							dco.getConflicts());
					throw new CheckoutConflictException(dco.getConflicts()
				}
			} finally {
				dc.unlock();
			}
			Ref ref = repo.findRef(name);
			if (ref != null && !ref.getName().startsWith(Constants.R_HEADS))
				ref = null;
			String toName = Repository.shortenRefName(name);
			RefUpdate refUpdate = repo.updateRef(Constants.HEAD
			refUpdate.setForceUpdate(forceRefUpdate);
			refUpdate.setRefLogMessage(refLogMessage + " to " + toName
			Result updateResult;
			if (ref != null)
				updateResult = refUpdate.link(ref.getName());
			else if (orphan) {
				updateResult = refUpdate.link(getBranchName());
				ref = repo.exactRef(Constants.HEAD);
			} else {
				refUpdate.setNewObjectId(newCommit);
				updateResult = refUpdate.forceUpdate();
			}

			setCallable(false);

			boolean ok = false;
			switch (updateResult) {
			case NEW:
				ok = true;
				break;
			case NO_CHANGE:
			case FAST_FORWARD:
			case FORCED:
				ok = true;
				break;
			default:
				break;
			}

			if (!ok)
				throw new JGitInternalException(MessageFormat.format(JGitText
						.get().checkoutUnexpectedResult


			if (!dco.getToBeDeleted().isEmpty()) {
				status = new CheckoutResult(Status.NONDELETED
						dco.getToBeDeleted()
						new ArrayList<>(dco.getUpdated().keySet())
						dco.getRemoved());
			} else
				status = new CheckoutResult(new ArrayList<>(dco
						.getUpdated().keySet())

			return ref;
		} catch (IOException ioe) {
			throw new JGitInternalException(ioe.getMessage()
		} finally {
			if (status == null)
				status = CheckoutResult.ERROR_RESULT;
		}
	}

	private String getShortBranchName(Ref headRef) {
		if (headRef.isSymbolic()) {
			return Repository.shortenRefName(headRef.getTarget().getName());
		}
		ObjectId id = headRef.getObjectId();
		if (id == null) {
			throw new NullPointerException();
		}
		return id.getName();
	}

	public CheckoutCommand setProgressMonitor(ProgressMonitor monitor) {
		if (monitor == null) {
			monitor = NullProgressMonitor.INSTANCE;
		}
		this.monitor = monitor;
		return this;
	}

	public CheckoutCommand addPath(String path) {
		checkCallable();
		this.paths.add(path);
		return this;
	}

	public CheckoutCommand addPaths(List<String> p) {
		checkCallable();
		this.paths.addAll(p);
		return this;
	}

	public CheckoutCommand setAllPaths(boolean all) {
		checkoutAllPaths = all;
		return this;
	}

	protected CheckoutCommand checkoutPaths() throws IOException
			RefNotFoundException {
		actuallyModifiedPaths = new HashSet<>();
		DirCache dc = repo.lockDirCache();
		try (RevWalk revWalk = new RevWalk(repo);
				TreeWalk treeWalk = new TreeWalk(repo
						revWalk.getObjectReader())) {
			treeWalk.setRecursive(true);
			if (!checkoutAllPaths)
				treeWalk.setFilter(PathFilterGroup.createFromStrings(paths));
			if (isCheckoutIndex())
				checkoutPathsFromIndex(treeWalk
			else {
				RevCommit commit = revWalk.parseCommit(getStartPointObjectId());
				checkoutPathsFromCommit(treeWalk
			}
		} finally {
			try {
				dc.unlock();
			} finally {
				WorkingTreeModifiedEvent event = new WorkingTreeModifiedEvent(
						actuallyModifiedPaths
				actuallyModifiedPaths = null;
				if (!event.isEmpty()) {
					repo.fireEvent(event);
				}
			}
		}
		return this;
	}

	private void checkoutPathsFromIndex(TreeWalk treeWalk
			throws IOException {
		DirCacheIterator dci = new DirCacheIterator(dc);
		treeWalk.addTree(dci);

		String previousPath = null;

		final ObjectReader r = treeWalk.getObjectReader();
		DirCacheEditor editor = dc.editor();
		while (treeWalk.next()) {
			String path = treeWalk.getPathString();
			if (path.equals(previousPath))
				continue;

			final EolStreamType eolStreamType = treeWalk
					.getEolStreamType(CHECKOUT_OP);
			final String filterCommand = treeWalk
					.getFilterCommand(Constants.ATTR_FILTER_TYPE_SMUDGE);
			editor.add(new PathEdit(path) {
				@Override
				public void apply(DirCacheEntry ent) {
					int stage = ent.getStage();
					if (stage > DirCacheEntry.STAGE_0) {
						if (checkoutStage != null) {
							if (stage == checkoutStage.number) {
								checkoutPath(ent
										eolStreamType
								actuallyModifiedPaths.add(path);
							}
						} else {
							UnmergedPathException e = new UnmergedPathException(
									ent);
							throw new JGitInternalException(e.getMessage()
						}
					} else {
						checkoutPath(ent
								filterCommand));
						actuallyModifiedPaths.add(path);
					}
				}
			});

			previousPath = path;
		}
		editor.commit();
	}

	private void checkoutPathsFromCommit(TreeWalk treeWalk
			RevCommit commit) throws IOException {
		treeWalk.addTree(commit.getTree());
		final ObjectReader r = treeWalk.getObjectReader();
		DirCacheEditor editor = dc.editor();
		while (treeWalk.next()) {
			final ObjectId blobId = treeWalk.getObjectId(0);
			final FileMode mode = treeWalk.getFileMode(0);
			final EolStreamType eolStreamType = treeWalk
					.getEolStreamType(CHECKOUT_OP);
			final String filterCommand = treeWalk
					.getFilterCommand(Constants.ATTR_FILTER_TYPE_SMUDGE);
			final String path = treeWalk.getPathString();
			editor.add(new PathEdit(path) {
				@Override
				public void apply(DirCacheEntry ent) {
					ent.setObjectId(blobId);
					ent.setFileMode(mode);
					checkoutPath(ent
							new CheckoutMetadata(eolStreamType
					actuallyModifiedPaths.add(path);
				}
			});
		}
		editor.commit();
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

	private boolean isCheckoutIndex() {
		return startCommit == null && startPoint == null;
	}

	private ObjectId getStartPointObjectId() throws AmbiguousObjectException
			RefNotFoundException
		if (startCommit != null)
			return startCommit.getId();

		String startPointOrHead = (startPoint != null) ? startPoint
				: Constants.HEAD;
		ObjectId result = repo.resolve(startPointOrHead);
		if (result == null)
			throw new RefNotFoundException(MessageFormat.format(
					JGitText.get().refNotResolved
		return result;
	}

	private void processOptions() throws InvalidRefNameException
			RefAlreadyExistsException
		if (((!checkoutAllPaths && paths.isEmpty()) || orphan)
				&& (name == null || !Repository
						.isValidRefName(Constants.R_HEADS + name)))
			throw new InvalidRefNameException(MessageFormat.format(JGitText
					.get().branchNameInvalid

		if (orphan) {
			Ref refToCheck = repo.exactRef(getBranchName());
			if (refToCheck != null)
				throw new RefAlreadyExistsException(MessageFormat.format(
						JGitText.get().refAlreadyExists
		}
	}

	private String getBranchName() {
		if (name.startsWith(Constants.R_REFS))
			return name;

		return Constants.R_HEADS + name;
	}

	public CheckoutCommand setName(String name) {
		checkCallable();
		this.name = name;
		return this;
	}

	public CheckoutCommand setCreateBranch(boolean createBranch) {
		checkCallable();
		this.createBranch = createBranch;
		return this;
	}

	public CheckoutCommand setOrphan(boolean orphan) {
		checkCallable();
		this.orphan = orphan;
		return this;
	}

	@Deprecated
	public CheckoutCommand setForce(boolean force) {
		return setForceRefUpdate(force);
	}

	public CheckoutCommand setForceRefUpdate(boolean forceRefUpdate) {
		checkCallable();
		this.forceRefUpdate = forceRefUpdate;
		return this;
	}

	public CheckoutCommand setForced(boolean forced) {
		checkCallable();
		this.forced = forced;
		return this;
	}

	public CheckoutCommand setStartPoint(String startPoint) {
		checkCallable();
		this.startPoint = startPoint;
		this.startCommit = null;
		checkOptions();
		return this;
	}

	public CheckoutCommand setStartPoint(RevCommit startCommit) {
		checkCallable();
		this.startCommit = startCommit;
		this.startPoint = null;
		checkOptions();
		return this;
	}

	public CheckoutCommand setUpstreamMode(
			CreateBranchCommand.SetupUpstreamMode mode) {
		checkCallable();
		this.upstreamMode = mode;
		return this;
	}

	public CheckoutCommand setStage(Stage stage) {
		checkCallable();
		this.checkoutStage = stage;
		checkOptions();
		return this;
	}

	public CheckoutResult getResult() {
		if (status == null)
			return CheckoutResult.NOT_TRIED_RESULT;
		return status;
	}

	private void checkOptions() {
		if (checkoutStage != null && !isCheckoutIndex())
			throw new IllegalStateException(
					JGitText.get().cannotCheckoutOursSwitchBranch);
	}
}
