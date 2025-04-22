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
import org.eclipse.jgit.submodule.SubmoduleGenerator;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

public class SubmoduleInitCommand extends GitCommand<Collection<String>> {

	private final Collection<String> paths;

	public SubmoduleInitCommand(Repository repo) {
		super(repo);
		paths = new ArrayList<String>();
	}

	public SubmoduleInitCommand addPath(String path) {
		checkCallable();
		paths.add(path);
		return this;
	}

	public Collection<String> call() throws JGitInternalException {
		checkCallable();

		TreeFilter filter = null;
		if (!paths.isEmpty())
			filter = PathFilterGroup.createFromStrings(paths);
		try {
			SubmoduleGenerator generator = new SubmoduleGenerator(repo
			StoredConfig config = repo.getConfig();
			List<String> initialized = new ArrayList<String>();
			while (generator.next()) {
				String path = generator.getPath();
				if (config.getString(ConfigConstants.CONFIG_SUBMODULE_SECTION
						path
					continue;

				String configUrl = generator.getConfigUrl();
				String configUpdate = generator.getConfigUpdate();
				if (configUrl != null)
					config.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
							path
				if (configUpdate != null)
					config.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
							path
							configUpdate);
				if (configUrl != null || configUpdate != null)
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
