package org.eclipse.jgit.api;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.api.errors.DetachedHeadException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefRename;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RefUpdate.Result;

public class RenameBranchCommand extends GitCommand<Ref> {
	private String oldName;

	private String newName;

	protected RenameBranchCommand(Repository repo) {
		super(repo);
	}

	public Ref call() throws RefNotFoundException
			RefAlreadyExistsException
		checkCallable();

		if (newName == null)
			throw new InvalidRefNameException(MessageFormat.format(JGitText
					.get().branchNameInvalid

		try {
			String fullOldName;
			String fullNewName;
			if (repo.getRef(newName) != null)
				throw new RefAlreadyExistsException(MessageFormat.format(
						JGitText.get().refAlreadExists
			if (oldName != null) {
				Ref ref = repo.getRef(oldName);
				if (ref == null)
					throw new RefNotFoundException(MessageFormat.format(
							JGitText.get().refNotResolved
				if (ref.getName().startsWith(Constants.R_TAGS))
					throw new RefNotFoundException(MessageFormat.format(
							JGitText.get().renameBranchFailedBecauseTag
							oldName));
				fullOldName = ref.getName();
			} else {
				fullOldName = repo.getFullBranch();
				if (ObjectId.isId(fullOldName))
					throw new DetachedHeadException();
			}

			if (fullOldName.startsWith(Constants.R_REMOTES))
				fullNewName = Constants.R_REMOTES + newName;
			else {
				fullNewName = Constants.R_HEADS + newName;
			}

			if (!Repository.isValidRefName(fullNewName))
				throw new InvalidRefNameException(MessageFormat.format(JGitText
						.get().branchNameInvalid

			RefRename rename = repo.renameRef(fullOldName
			Result renameResult = rename.rename();

			setCallable(false);

			boolean ok = Result.RENAMED == renameResult;

			if (ok) {
				if (fullNewName.startsWith(Constants.R_HEADS)) {
					String shortOldName = fullOldName
							.substring(Constants.R_HEADS.length());
					String oldRemote = repo.getConfig().getString(
							ConfigConstants.CONFIG_BRANCH_SECTION
							shortOldName
					if (oldRemote != null) {
						repo.getConfig().setString(
								ConfigConstants.CONFIG_BRANCH_SECTION
								ConfigConstants.CONFIG_KEY_REMOTE
					}
					String oldMerge = repo.getConfig().getString(
							ConfigConstants.CONFIG_BRANCH_SECTION
							shortOldName
					if (oldMerge != null) {
						repo.getConfig().setString(
								ConfigConstants.CONFIG_BRANCH_SECTION
								ConfigConstants.CONFIG_KEY_MERGE
					}
					repo.getConfig()
							.unsetSection(
									ConfigConstants.CONFIG_BRANCH_SECTION
									shortOldName);
					repo.getConfig().save();
				}

			} else
				throw new JGitInternalException(MessageFormat.format(JGitText
						.get().renameBranchUnexpectedResult
						.name()));

			Ref resultRef = repo.getRef(newName);
			if (resultRef == null)
				throw new JGitInternalException(
						JGitText.get().renameBranchFailedUnknownReason);
			return resultRef;
		} catch (IOException ioe) {
			throw new JGitInternalException(ioe.getMessage()
		}
	}

	public RenameBranchCommand setNewName(String newName) {
		checkCallable();
		this.newName = newName;
		return this;
	}

	public RenameBranchCommand setOldName(String oldName) {
		checkCallable();
		this.oldName = oldName;
		return this;
	}
}
