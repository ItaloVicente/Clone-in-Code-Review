package org.eclipse.jgit.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.submodule.SubmoduleWalk;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;

public class SubmoduleInitCommand extends GitCommand<Collection<String>> {

	private final Collection<String> paths;

	public SubmoduleInitCommand(final Repository repo) {
		super(repo);
		paths = new ArrayList<String>();
	}

	public SubmoduleInitCommand addPath(final String path) {
		paths.add(path);
		return this;
	}

	public Collection<String> call() throws JGitInternalException {
		checkCallable();

		try {
			SubmoduleWalk generator = SubmoduleWalk.forIndex(repo);
			if (!paths.isEmpty())
				generator.setFilter(PathFilterGroup.createFromStrings(paths));
			StoredConfig config = repo.getConfig();
			List<String> initialized = new ArrayList<String>();
			while (generator.next()) {
				if (generator.getConfigUrl() != null)
					continue;

				String path = generator.getPath();
				String url = generator.getModulesUrl();
				String update = generator.getModulesUpdate();
				if (url != null)
					config.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
							path
				if (update != null)
					config.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
							path
				if (url != null || update != null)
					initialized.add(path);
			}
			if (!initialized.isEmpty())
				config.save();
			return initialized;
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		} catch (ConfigInvalidException e) {
			throw new JGitInternalException(e.getMessage()
		}
	}
}
