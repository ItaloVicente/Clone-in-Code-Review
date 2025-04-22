package org.eclipse.jgit.api;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.api.errors.CannotDeleteCurrentBranchException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NotMergedException;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public class DeleteBranchCommand extends GitCommand<List<String>> {
	private final Set<String> branchNames = new HashSet<String>();

	private boolean force;

	protected DeleteBranchCommand(Repository repo) {
		super(repo);
	}

	public List<String> call() throws JGitInternalException
			NotMergedException
		checkCallable();
		List<String> result = new ArrayList<String>();
		if (branchNames.isEmpty())
			return result;
		try {
			String currentBranch = repo.getFullBranch();
			if (!force) {
				RevWalk walk = new RevWalk(repo);
				RevCommit tip = walk.parseCommit(repo.resolve(Constants.HEAD));
				for (String branchName : branchNames) {
					if (branchName == null)
						continue;
					Ref currentRef = repo.getRef(branchName);
					if (currentRef == null)
						continue;

					RevCommit base = walk.parseCommit(repo.resolve(branchName));
					if (!walk.isMergedInto(base
						throw new NotMergedException();
					}
				}
			}
			setCallable(false);
			for (String branchName : branchNames) {
				if (branchName == null)
					continue;
				Ref currentRef = repo.getRef(branchName);
				if (currentRef == null)
					continue;
				if (currentRef.getName().equals(currentBranch))
					throw new CannotDeleteCurrentBranchException(
							MessageFormat
									.format(
											JGitText.get().cannotDeleteCheckedOutBranch
											branchName));
				RefUpdate update = repo.updateRef(currentRef.getName());
				update.setRefLogMessage("branch deleted"
				update.setForceUpdate(true);
				Result deleteResult = update.delete();

				boolean ok = true;
				switch (deleteResult) {
				case IO_FAILURE:
				case LOCK_FAILURE:
				case REJECTED:
					ok = false;
					break;
				default:
					break;
				}

				if (ok) {
					result.add(currentRef.getName());
					repo.getConfig().unsetSection(
							ConfigConstants.CONFIG_BRANCH_SECTION
					repo.getConfig().save();
				} else
					throw new JGitInternalException(MessageFormat.format(
							JGitText.get().deleteBranchUnexpectedResult
							deleteResult.name()));
			}
			return result;
		} catch (IOException ioe) {
			throw new JGitInternalException(ioe.getMessage()
		}
	}

	public DeleteBranchCommand setBranchNames(String... branchnames) {
		checkCallable();
		this.branchNames.clear();
		for (String branch : branchnames)
			this.branchNames.add(branch);
		return this;
	}

	public DeleteBranchCommand setForce(boolean force) {
		checkCallable();
		this.force = force;
		return this;
	}
}
