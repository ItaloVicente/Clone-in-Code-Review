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

public class SubmoduleStatusCommand extends
		GitCommand<Map<String

	private final Collection<String> paths;

	public SubmoduleStatusCommand(final Repository repo) {
		super(repo);
		paths = new ArrayList<String>();
	}

	public SubmoduleStatusCommand addPath(final String path) {
		paths.add(path);
		return this;
	}

	public Map<String
		checkCallable();

		try {
			SubmoduleGenerator generator = SubmoduleGenerator.forIndex(repo);
			if (!paths.isEmpty())
				generator.setFilter(PathFilterGroup.createFromStrings(paths));
			Map<String
			while (generator.next()) {
				SubmoduleStatus status = getStatus(generator);
				statuses.put(status.getPath()
			}
			return statuses;
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		} catch (ConfigInvalidException e) {
			throw new JGitInternalException(e.getMessage()
		}
	}

	private SubmoduleStatus getStatus(SubmoduleGenerator generator)
			throws IOException
		ObjectId id = generator.getObjectId();
		String path = generator.getPath();

		if (generator.getModulesPath() == null)
			return new SubmoduleStatus(SubmoduleStatusType.MISSING

		if (generator.getConfigUrl() == null)
			return new SubmoduleStatus(SubmoduleStatusType.UNINITIALIZED
					id);

		Repository subRepo = generator.getRepository();
		if (subRepo == null)
			return new SubmoduleStatus(SubmoduleStatusType.UNINITIALIZED
					id);

		ObjectId headId = subRepo.resolve(Constants.HEAD);

		if (headId == null)
			return new SubmoduleStatus(SubmoduleStatusType.UNINITIALIZED
					id

		if (!headId.equals(id))
			return new SubmoduleStatus(SubmoduleStatusType.REV_CHECKED_OUT
					path

		return new SubmoduleStatus(SubmoduleStatusType.INITIALIZED
				headId);
	}
}
