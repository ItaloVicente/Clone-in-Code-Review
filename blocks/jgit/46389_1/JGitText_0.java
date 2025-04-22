package org.eclipse.jgit.api;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;

public class RenameRemoteCommand extends GitCommand<RemoteConfig> {
	private String oldName;

	private String newName;

	protected RenameRemoteCommand(Repository repo) {
		super(repo);
	}

	public RemoteConfig call() throws GitAPIException
			InvalidRefNameException
		checkCallable();

		if (newName == null)
			throw new InvalidRefNameException(MessageFormat.format(
					JGitText.get().remoteNameInvalid

		try {
			final StoredConfig config = repo.getConfig();

			if (!Repository.isValidRefName(Constants.R_REMOTES + newName))
				throw new InvalidRefNameException(MessageFormat.format(
						JGitText.get().remoteNameInvalid

			if (config.getNames(ConfigConstants.CONFIG_REMOTE_SECTION
					.size() > 0) {
				throw new RefAlreadyExistsException(MessageFormat.format(
						JGitText.get().refAlreadyExists1
			}
			if (config.getNames(ConfigConstants.CONFIG_REMOTE_SECTION
					.size() == 0) {
				throw new RefNotFoundException(MessageFormat.format(
						JGitText.get().remoteNotFound
			}

			final RemoteConfig newRemote = new RemoteConfig(config
			newRemote.setName(newName);

			int fetchUpdateCount = 0;

			final String oldFetchName = Constants.R_REMOTES + oldName
					+ RefSpec.WILDCARD_SUFFIX;
			final List<RefSpec> fetches = newRemote.getFetchRefSpecs();

			for (RefSpec fetch : fetches) {
				if (fetch.matchDestination(oldFetchName)) {
					newRemote.addFetchRefSpec(fetch
							.setDestination(Constants.R_REMOTES + newName
									+ RefSpec.WILDCARD_SUFFIX));
					newRemote.removeFetchRefSpec(fetch);
					fetchUpdateCount++;
				}
			}

			newRemote.update(config);

			if (config.getNames(ConfigConstants.CONFIG_REMOTE_SECTION
					.size() == 0) {
				throw new JGitInternalException(
						JGitText.get().renameRemoteFailedUnknownReason);
			}

			config.unsetSection(ConfigConstants.CONFIG_REMOTE_SECTION

			if (fetchUpdateCount > 0) {
				final Collection<Ref> refs = new ArrayList<Ref>();

				refs.addAll(getRefs(Constants.R_REMOTES));

				final String oldRemoteBranchName = Constants.R_REMOTES
				final String newRemoteBranchName = Constants.R_REMOTES

				for (Ref branch : refs) {
					final String remoteBranchName = branch.getName();
					final String branchName = remoteBranchName.split("/"

					if (remoteBranchName.startsWith(oldRemoteBranchName)) {
						final Result renameResult = repo.renameRef(
								remoteBranchName
								newRemoteBranchName + branchName).rename();

						if (renameResult != Result.RENAMED) {
							throw new JGitInternalException(
									MessageFormat.format(
											JGitText.get().renameBranchUnexpectedResult
											renameResult.name()));
						}
					}

					final String remote = config.getString(
							ConfigConstants.CONFIG_BRANCH_SECTION
							ConfigConstants.CONFIG_KEY_REMOTE);

					if (oldName.equals(remote)) {
						config.setString(ConfigConstants.CONFIG_BRANCH_SECTION
								branchName
								newName);
					}
				}
			}

			return newRemote;
		} catch (URISyntaxException e) {
			throw new JGitInternalException(e.getMessage()
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		}
	}

	public RenameRemoteCommand setNewName(String newName) {
		checkCallable();
		this.newName = newName;
		return this;
	}

	public RenameRemoteCommand setOldName(String oldName) {
		checkCallable();
		this.oldName = oldName;
		return this;
	}

	private Collection<Ref> getRefs(String prefix) throws IOException {
		return repo.getRefDatabase().getRefs(prefix).values();
	}
}
