package org.eclipse.jgit.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.submodule.SubmoduleWalk;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;

public class SubmoduleSyncCommand extends GitCommand<Map<String

	private final Collection<String> paths;

	public SubmoduleSyncCommand(Repository repo) {
		super(repo);
		paths = new ArrayList<>();
	}

	public SubmoduleSyncCommand addPath(String path) {
		paths.add(path);
		return this;
	}

	protected String getHeadBranch(Repository subRepo) throws IOException {
		Ref head = subRepo.exactRef(Constants.HEAD);
		if (head != null && head.isSymbolic())
			return Repository.shortenRefName(head.getLeaf().getName());
		else
			return null;
	}

	@Override
	public Map<String
		checkCallable();

		try (SubmoduleWalk generator = SubmoduleWalk.forIndex(repo)) {
			if (!paths.isEmpty())
				generator.setFilter(PathFilterGroup.createFromStrings(paths));
			Map<String
			StoredConfig config = repo.getConfig();
			while (generator.next()) {
				String remoteUrl = generator.getRemoteUrl();
				if (remoteUrl == null)
					continue;

				String path = generator.getPath();
				config.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
						path
				synced.put(path

				try (Repository subRepo = generator.getRepository()) {
					if (subRepo == null) {
						continue;
					}

					StoredConfig subConfig;
					String branch;

					subConfig = subRepo.getConfig();
					branch = getHeadBranch(subRepo);
					String remote = null;
					if (branch != null) {
						remote = subConfig.getString(
								ConfigConstants.CONFIG_BRANCH_SECTION
								ConfigConstants.CONFIG_KEY_REMOTE);
					}
					if (remote == null) {
						remote = Constants.DEFAULT_REMOTE_NAME;
					}

					subConfig.setString(ConfigConstants.CONFIG_REMOTE_SECTION
							remote
					subConfig.save();
				}
			}
			if (!synced.isEmpty())
				config.save();
			return synced;
		} catch (IOException | ConfigInvalidException e) {
			throw new JGitInternalException(e.getMessage()
		}
	}
}
