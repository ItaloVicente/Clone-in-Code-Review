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
import org.eclipse.jgit.treewalk.filter.TreeFilter;

public class SubmoduleSyncCommand extends GitCommand<Map<String

	private final Collection<String> paths;

	public SubmoduleSyncCommand(Repository repo) {
		super(repo);
		paths = new ArrayList<String>();
	}

	public SubmoduleSyncCommand addPath(String path) {
		checkCallable();
		paths.add(path);
		return this;
	}

	protected String getHeadBranch(Repository subRepo) throws IOException {
		Ref head = subRepo.getRef(Constants.HEAD);
		if (head != null && head.isSymbolic())
			return Repository.shortenRefName(head.getLeaf().getName());
		else
			return null;
	}

	public Map<String
		checkCallable();

		TreeFilter filter = null;
		if (!paths.isEmpty())
			filter = PathFilterGroup.createFromStrings(paths);
		try {
			SubmoduleGenerator generator = new SubmoduleGenerator(repo
			Map<String
			while (generator.next()) {
				if (!generator.hasGitDirectory())
					continue;

				String path = generator.getPath();
				String remoteUrl = generator.getConfigUrl();
				if (remoteUrl == null)
					continue;

				Repository subRepo = generator.getRepository();
				if (subRepo == null)
					continue;
				String branch = getHeadBranch(subRepo);
				if (branch == null)
					continue;

				StoredConfig subConfig = subRepo.getConfig();
				String remote = subConfig.getString(
						ConfigConstants.CONFIG_BRANCH_SECTION
						ConfigConstants.CONFIG_KEY_REMOTE);
				if (remote == null)
					continue;

				subConfig.setString(ConfigConstants.CONFIG_REMOTE_SECTION
						remote
				subConfig.save();
				updated.put(path
			}
			return updated;
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		} catch (ConfigInvalidException e) {
			throw new JGitInternalException(e.getMessage()
		}
	}
}
