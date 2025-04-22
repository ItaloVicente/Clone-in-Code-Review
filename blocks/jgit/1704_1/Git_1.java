package org.eclipse.jgit.api;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NotMergedException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefRename;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public class BranchCommand extends GitCommand<List<Ref>> {
	public enum ListMode {
		ALL
		REMOTE;
	}

	public enum SetupUpstreamMode {
		TRACK
		NOTRACK
		SETUPSTREAM;
	}

	private enum Mode {
		CREATE
		private final Map<String

		public Map<String
			return myOptions;
		}
	}

	private Mode mode = Mode.LIST;

	protected BranchCommand(Repository repo) {
		super(repo);
	}

	public List<Ref> call() throws JGitInternalException
			RefAlreadyExistsException
			IllegalArgumentException {
		checkCallable();
		switch (mode) {
		case LIST:
			return list();
		case RENAME:
			return rename();
		case DELETE:
			return delete();
		case CREATE:
			return create();
		}
		throw new JGitInternalException(
	}

	private List<Ref> create() throws RefAlreadyExistsException {
		try {
			List<Ref> result = new ArrayList<Ref>(1);
			Map<String
			boolean force = options.containsKey("f");
			String name = (String) options.get("branchname");
			if (name == null)
				throw new IllegalArgumentException(
						JGitText.get().createBranchMissingName);
			boolean exists = repo.getRef(name) != null;
			if (!force && exists)
				throw new RefAlreadyExistsException(MessageFormat.format(
						JGitText.get().refAlreadExists

			String startPoint = (String) options.get("start-point");
			if (startPoint == null)
				startPoint = repo.getFullBranch();

			ObjectId startAt;
			String baseBranch = "";
			String baseTag = "";
			String baseCommit = "";
			if (ObjectId.isId(startPoint)) {
				RevCommit commit = new RevWalk(repo).parseCommit(ObjectId
						.fromString(startPoint));
				startAt = commit.getId();
				baseCommit = commit.getShortMessage();
			} else {
				Ref ref = repo.getRef(startPoint);
				if (ref.getName().startsWith(Constants.R_TAGS)) {
					baseTag = ref.getName();
					RevCommit commit = new RevWalk(repo).parseCommit(ref
							.getLeaf().getObjectId());
					startAt = commit.getId();
				} else if (ref.getName().startsWith(Constants.R_REFS)) {
					baseBranch = ref.getName();
					startAt = new RevWalk(repo).parseCommit(ref.getObjectId());
				} else {
					throw new IllegalArgumentException();
				}
			}

			String commitMessage;
			if (exists) {
				if (baseBranch.length() > 0)
					commitMessage = "branch: Reset start-point to branch "
							+ baseBranch;
				else if (baseTag.length() > 0)
					commitMessage = "branch: Reset start-point to tag "
							+ baseTag;
				else
					commitMessage = "branch: Reset start-point to commit "
							+ baseCommit;
			} else {
				if (baseBranch.length() > 0)
					commitMessage = "branch: Created from branch " + baseBranch;
				else if (baseTag.length() > 0)
					commitMessage = "branch: Created from tag " + baseTag;
				else
					commitMessage = "branch: Created from commit " + baseCommit;
			}

			RefUpdate updateRef = repo.updateRef(Constants.R_HEADS + name);
			updateRef.setNewObjectId(startAt);
			updateRef.setRefLogMessage(commitMessage
			Result updateResult;
			if (exists && force)
				updateResult = updateRef.forceUpdate();
			else
				updateResult = updateRef.update();

			if ((!exists && updateResult != Result.NEW)
					|| (exists && updateResult != Result.FORCED
							&& updateResult != Result.NO_CHANGE && updateResult != Result.FAST_FORWARD))
				throw new JGitInternalException(MessageFormat.format(JGitText
						.get().createBranchUnexpectedResult
						.name()));

			if (repo.getRef(name) == null)
				throw new JGitInternalException(
						JGitText.get().createBranchFailedUnknownReason);

			setCallable(false);
			result.add(repo.getRef(name));

			if (baseBranch.length() == 0) {
				return result;
			}

			SetupUpstreamMode explicitMode = (SetupUpstreamMode) options
					.get("upstreamMode");
			boolean doConfigure;
			if (explicitMode == SetupUpstreamMode.SETUPSTREAM
					|| explicitMode == SetupUpstreamMode.TRACK)
				doConfigure = true;
			else if (explicitMode == SetupUpstreamMode.NOTRACK)
				doConfigure = false;
			else {
				String autosetupflag = repo.getConfig().getString("branch"
						null
				if ("false".equals(autosetupflag)) {
					doConfigure = false;
				} else if ("always".equals(autosetupflag)) {
					doConfigure = true;
				} else {
					doConfigure = baseBranch.startsWith(Constants.R_REMOTES);
				}
			}

			if (doConfigure) {
				StoredConfig config = repo.getConfig();
				String[] tokens = baseBranch.split("/"
				boolean isRemote = tokens[1].equals("remotes");
				if (isRemote) {
					String remoteName = tokens[2];
					String branchName = tokens[3];
					config.setString("branch"
					config.setString("branch"
							+ branchName);
				} else {
					config.setString("branch"
					config.setString("branch"
				}
				config.save();
			}
			return result;
		} catch (IOException ioe) {
			throw new JGitInternalException(ioe.getMessage()
		}
	}

	private List<Ref> delete() throws NotMergedException {
		Map<String
		String[] branchnames = (String[]) options.get("branchname");
		if (branchnames == null)
			return new ArrayList<Ref>(0);
		boolean force = options.containsKey("D");
		try {
			if (!force) {
				RevWalk walk = null;
				RevCommit tip = null;
				walk = new RevWalk(repo);
				tip = walk.parseCommit(repo.resolve(Constants.HEAD));
				for (String branchName : branchnames) {
					if (branchName == null)
						continue;
					Ref currentRef = repo.getRef(branchName);
					if (currentRef == null)
						continue;
					if (walk != null) {
						RevCommit base = walk.parseCommit(repo
								.resolve(branchName));
						if (!walk.isMergedInto(base
							throw new NotMergedException();
						}
					}
				}
			}
			setCallable(false);
			for (String branchName : branchnames) {
				if (branchName == null)
					continue;
				Ref currentRef = repo.getRef(branchName);
				if (currentRef == null)
					continue;
				RefUpdate update = repo.updateRef(currentRef.getName());
				update.setRefLogMessage("branch deleted"
				update.setForceUpdate(true);
				Result deleteRes = update.delete();
				if (deleteRes == Result.REJECTED) {
					throw new JGitInternalException(deleteRes.name());
				}
				repo.getConfig().unsetSection("branch"
				repo.getConfig().save();
			}
			return new ArrayList<Ref>(0);
		} catch (IOException ioe) {
			throw new JGitInternalException(ioe.getMessage()
		}
	}

	private List<Ref> rename() throws RefAlreadyExistsException
			NoHeadException {
		Map<String
		boolean force = options.containsKey("M");
		String newName = (String) options.get("newbranch");
		String oldName = (String) options.get("oldbranch");
		try {
			boolean exists = repo.resolve(newName) != null;
			if (!force && exists)
				throw new RefAlreadyExistsException(MessageFormat.format(
						JGitText.get().refAlreadExists
			if (oldName != null) {
				Ref ref = repo.getRef(oldName);
				if (ref == null)
					throw new IllegalArgumentException(MessageFormat.format(
							JGitText.get().refNotResolved
				if (ref.getName().startsWith(Constants.R_TAGS))
					throw new IllegalArgumentException(MessageFormat.format(
							JGitText.get().renameBranchFailedBecauseTag
							oldName));
				else
					oldName = ref.getName();
			} else {
				oldName = repo.getFullBranch();
				if (ObjectId.isId(oldName))
					throw new NoHeadException(
							"Can not rename currently checked out branch
			}

			setCallable(false);
			RefRename rename = repo.renameRef(oldName
			Result renameResult = rename.rename();
			if (Result.RENAMED != renameResult)
				throw new JGitInternalException(MessageFormat.format(JGitText
						.get().renameBranchUnexpectedResult
						.name()));
			ArrayList<Ref> resultList = new ArrayList<Ref>();
			Ref resultRef = repo.getRef(newName);
			if (resultRef == null)
				throw new JGitInternalException(
						JGitText.get().renameBranchFailedUnknownReason);
			resultList.add(resultRef);
			return resultList;
		} catch (IOException ioe) {
			throw new JGitInternalException(ioe.getMessage()
		}
	}

	private List<Ref> list() {
		Map<String
		ListMode listMode = (ListMode) options.get("listmode");
		Map<String
		try {
			if (listMode == null) {
				refList = repo.getRefDatabase().getRefs(Constants.R_HEADS);
			} else if (listMode == ListMode.REMOTE) {
				refList = repo.getRefDatabase().getRefs(Constants.R_REMOTES);
			} else {
				refList = repo.getRefDatabase().getRefs(Constants.R_HEADS);
				refList.putAll(repo.getRefDatabase().getRefs(
						Constants.R_REMOTES));
			}
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		}
		List<Ref> resultRefs = new ArrayList<Ref>();
		resultRefs.addAll(refList.values());
		Collections.sort(resultRefs
			public int compare(Ref o1
				return o1.getName().compareTo(o2.getName());
			}
		});
		setCallable(false);
		return resultRefs;
	}

	public BranchCommand setDelete(String[] branchnames
		checkCallable();
		mode = Mode.DELETE;
		mode.getOptions().clear();
		mode.getOptions().put("branchname"
		if (force)
			mode.getOptions().put("D"
		return this;
	}

	public BranchCommand setDelete(String branchname
		return setDelete(new String[] { branchname }
	}

	public BranchCommand setRename(String oldbranch
			boolean force) {
		checkCallable();
		mode = Mode.RENAME;
		mode.getOptions().clear();
		mode.getOptions().put("newbranch"
		if (force)
			mode.getOptions().put("M"
		if (oldbranch != null)
			mode.getOptions().put("oldbranch"
		return this;
	}

	public BranchCommand setList(ListMode listMode) {
		checkCallable();
		mode = Mode.LIST;
		mode.getOptions().clear();
		mode.getOptions().put("listmode"
		return this;
	}

	public BranchCommand setCreate(String name
			String startpoint
		checkCallable();
		mode = Mode.CREATE;
		mode.getOptions().clear();
		mode.getOptions().put("branchname"
		if (force)
			mode.getOptions().put("f"
		mode.getOptions().put("upstreamMode"
		mode.getOptions().put("start-point"
		return this;
	}
}
