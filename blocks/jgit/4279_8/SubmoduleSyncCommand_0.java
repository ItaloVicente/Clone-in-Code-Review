package org.eclipse.jgit.api;

import java.io.IOException;
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

public class SubmoduleStatusCommand extends
		GitCommand<Map<String

	public SubmoduleStatusCommand(Repository repo) {
		super(repo);
	}

	public Map<String
		checkCallable();
		try {
			SubmoduleGenerator generator = new SubmoduleGenerator(repo);
			Map<String
			while (generator.next()) {
				ObjectId id = generator.getObjectId();
				String path = generator.getPath();
				String configPath = generator.getConfigPath();
				if (configPath != null) {
					if (generator.hasGitDirectory()) {
						ObjectId headId = generator.getRepository().resolve(
								Constants.HEAD);
						if (headId == null)
							status.put(path
									SubmoduleStatusType.NOT_INITIALIZED
									id));
						else if (!headId.equals(id))
							status.put(path
									SubmoduleStatusType.REV_CHECKED_OUT
									headId));
						else
							status.put(path
									SubmoduleStatusType.INITIALIZED
					} else
						status.put(path
								SubmoduleStatusType.NOT_INITIALIZED
				} else
					status.put(path
							SubmoduleStatusType.MISSING
			}
			return status;
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		} catch (ConfigInvalidException e) {
			throw new JGitInternalException(e.getMessage()
		}
	}
}
