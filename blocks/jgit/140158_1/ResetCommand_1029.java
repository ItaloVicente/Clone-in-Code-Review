package org.eclipse.jgit.api;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;

import org.eclipse.jgit.api.errors.DetachedHeadException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefRename;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;

public class RenameBranchCommand extends GitCommand<Ref> {
	private String oldName;

	private String newName;

	protected RenameBranchCommand(Repository repo) {
		super(repo);
	}

	@Override
	public Ref call() throws GitAPIException
			RefAlreadyExistsException
		checkCallable();

		if (newName == null) {
			throw new InvalidRefNameException(MessageFormat.format(JGitText
					.get().branchNameInvalid
		}
		try {
			String fullOldName;
			String fullNewName;
			if (oldName != null) {
				Ref ref = repo.exactRef(oldName);
				if (ref == null) {
					ref = repo.exactRef(Constants.R_HEADS + oldName);
					Ref ref2 = repo.exactRef(Constants.R_REMOTES + oldName);
					if (ref != null && ref2 != null) {
						throw new RefNotFoundException(MessageFormat.format(
								JGitText.get().renameBranchFailedAmbiguous
								oldName
					} else if (ref == null) {
						if (ref2 != null) {
							ref = ref2;
						} else {
							throw new RefNotFoundException(MessageFormat.format(
									JGitText.get().refNotResolved
						}
					}
				}
				fullOldName = ref.getName();
			} else {
				fullOldName = repo.getFullBranch();
				if (fullOldName == null) {
					throw new NoHeadException(
							JGitText.get().invalidRepositoryStateNoHead);
				}
				if (ObjectId.isId(fullOldName))
					throw new DetachedHeadException();
			}

			if (fullOldName.startsWith(Constants.R_REMOTES)) {
				fullNewName = Constants.R_REMOTES + newName;
			} else if (fullOldName.startsWith(Constants.R_HEADS)) {
				fullNewName = Constants.R_HEADS + newName;
			} else {
				throw new RefNotFoundException(MessageFormat.format(
						JGitText.get().renameBranchFailedNotABranch
						fullOldName));
			}

			if (!Repository.isValidRefName(fullNewName)) {
				throw new InvalidRefNameException(MessageFormat.format(JGitText
						.get().branchNameInvalid
			}
			if (repo.exactRef(fullNewName) != null) {
				throw new RefAlreadyExistsException(MessageFormat
						.format(JGitText.get().refAlreadyExists1
			}
			RefRename rename = repo.renameRef(fullOldName
			Result renameResult = rename.rename();

			setCallable(false);

			if (Result.RENAMED != renameResult) {
				throw new JGitInternalException(MessageFormat.format(JGitText
						.get().renameBranchUnexpectedResult
						.name()));
			}
			if (fullNewName.startsWith(Constants.R_HEADS)) {
				String shortOldName = fullOldName.substring(Constants.R_HEADS
						.length());
				final StoredConfig repoConfig = repo.getConfig();
				for (String name : repoConfig.getNames(
						ConfigConstants.CONFIG_BRANCH_SECTION
					String[] values = repoConfig.getStringList(
							ConfigConstants.CONFIG_BRANCH_SECTION
							shortOldName
					if (values.length == 0) {
						continue;
					}
					String[] existing = repoConfig.getStringList(
							ConfigConstants.CONFIG_BRANCH_SECTION
							name);
					if (existing.length > 0) {
						String[] newValues = new String[values.length
								+ existing.length];
						System.arraycopy(existing
								existing.length);
						System.arraycopy(values
								values.length);
						values = newValues;
					}

					repoConfig.setStringList(
							ConfigConstants.CONFIG_BRANCH_SECTION
							name
				}
				repoConfig.unsetSection(ConfigConstants.CONFIG_BRANCH_SECTION
						shortOldName);
				repoConfig.save();
			}

			Ref resultRef = repo.exactRef(fullNewName);
			if (resultRef == null) {
				throw new JGitInternalException(
						JGitText.get().renameBranchFailedUnknownReason);
			}
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
