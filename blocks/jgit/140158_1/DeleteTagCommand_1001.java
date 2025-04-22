package org.eclipse.jgit.api;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.api.errors.CannotDeleteCurrentBranchException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NotMergedException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public class DeleteBranchCommand extends GitCommand<List<String>> {
	private final Set<String> branchNames = new HashSet<>();

	private boolean force;

	protected DeleteBranchCommand(Repository repo) {
		super(repo);
	}

	@Override
	public List<String> call() throws GitAPIException
			NotMergedException
		checkCallable();
		List<String> result = new ArrayList<>();
		if (branchNames.isEmpty())
			return result;
		try {
			String currentBranch = repo.getFullBranch();
			if (!force) {
				try (RevWalk walk = new RevWalk(repo)) {
					RevCommit tip = walk
							.parseCommit(repo.resolve(Constants.HEAD));
					for (String branchName : branchNames) {
						if (branchName == null)
							continue;
						Ref currentRef = repo.findRef(branchName);
						if (currentRef == null)
							continue;

						RevCommit base = walk
								.parseCommit(repo.resolve(branchName));
						if (!walk.isMergedInto(base
							throw new NotMergedException();
						}
					}
				}
			}
			setCallable(false);
			for (String branchName : branchNames) {
				if (branchName == null)
					continue;
				Ref currentRef = repo.findRef(branchName);
				if (currentRef == null)
					continue;
				String fullName = currentRef.getName();
				if (fullName.equals(currentBranch))
					throw new CannotDeleteCurrentBranchException(
							MessageFormat
									.format(
											JGitText.get().cannotDeleteCheckedOutBranch
											branchName));
				RefUpdate update = repo.updateRef(fullName);
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
					result.add(fullName);
					if (fullName.startsWith(Constants.R_HEADS)) {
						String shortenedName = fullName
								.substring(Constants.R_HEADS.length());
						final StoredConfig cfg = repo.getConfig();
						cfg.unsetSection(
								ConfigConstants.CONFIG_BRANCH_SECTION
								shortenedName);
						cfg.save();
					}
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
