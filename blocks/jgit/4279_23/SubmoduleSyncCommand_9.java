package org.eclipse.jgit.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.submodule.SubmoduleGenerator;
import org.eclipse.jgit.submodule.SubmoduleStatus;
import org.eclipse.jgit.submodule.SubmoduleStatusType;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

public class SubmoduleStatusCommand extends
		GitCommand<Map<String

	private final Collection<String> paths;

	public SubmoduleStatusCommand(Repository repo) {
		super(repo);
		paths = new ArrayList<String>();
	}

	public SubmoduleStatusCommand addPath(String path) {
		paths.add(path);
		return this;
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
				ObjectId id = generator.getObjectId();
				String path = generator.getPath();

				if (generator.getModulesPath() == null) {
					status.put(path
							SubmoduleStatusType.MISSING
					continue;
				}

				if (generator.getConfigUrl() == null) {
					status.put(path
							SubmoduleStatusType.UNINITIALIZED
					continue;
				}

				Repository subRepo = generator.getRepository();
				if (subRepo == null) {
					status.put(path
							SubmoduleStatusType.UNINITIALIZED
					continue;
				}

				ObjectId headId = subRepo.resolve(Constants.HEAD);
				if (headId == null)
					status.put(path
							SubmoduleStatusType.UNINITIALIZED
				else if (!headId.equals(id))
					status.put(path
							SubmoduleStatusType.REV_CHECKED_OUT
				else
					status.put(path
							SubmoduleStatusType.INITIALIZED
			}
			return status;
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		} catch (ConfigInvalidException e) {
			throw new JGitInternalException(e.getMessage()
		}
	}
}
