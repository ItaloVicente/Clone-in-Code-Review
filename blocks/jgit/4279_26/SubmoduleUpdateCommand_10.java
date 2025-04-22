package org.eclipse.jgit.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.submodule.SubmoduleGenerator;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;

public class SubmoduleSyncCommand extends GitCommand<Map<String

	private final Collection<String> paths;

	public SubmoduleSyncCommand(final Repository repo) {
		super(repo);
		paths = new ArrayList<String>();
	}

	public SubmoduleSyncCommand addPath(final String path) {
		paths.add(path);
		return this;
	}

	protected String getHeadBranch(final Repository subRepo) throws IOException {
		Ref head = subRepo.getRef(Constants.HEAD);
		if (head != null && head.isSymbolic())
			return Repository.shortenRefName(head.getLeaf().getName());
		else
			return null;
	}

	public Map<String
		checkCallable();

		try {
			SubmoduleGenerator generator = SubmoduleGenerator.forIndex(repo);
			if (!paths.isEmpty())
				generator.setFilter(PathFilterGroup.createFromStrings(paths));
			Map<String
			StoredConfig config = repo.getConfig();
			while (generator.next()) {
				String remoteUrl = generator.getModulesUrl();
				if (remoteUrl == null)
					continue;

				String path = generator.getPath();
				config.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
						path
				synced.put(path

				Repository subRepo = generator.getRepository();
				if (subRepo == null)
					continue;

				StoredConfig subConfig = subRepo.getConfig();
				String branch = getHeadBranch(subRepo);
				String remote = null;
				if (branch != null)
					remote = subConfig.getString(
							ConfigConstants.CONFIG_BRANCH_SECTION
							ConfigConstants.CONFIG_KEY_REMOTE);
				if (remote == null)
					remote = Constants.DEFAULT_REMOTE_NAME;

				subConfig.setString(ConfigConstants.CONFIG_REMOTE_SECTION
						remote
				subConfig.save();
			}
			if (!synced.isEmpty())
				config.save();
			return synced;
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		} catch (ConfigInvalidException e) {
			throw new JGitInternalException(e.getMessage()
		}
	}
}
