package org.eclipse.egit.core.internal;

import java.io.File;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;

public class Utils {

	public static String getRepositoryName(Repository repository) {
		String repositoryName;
		File gitDir = repository.getDirectory();
		if (gitDir != null)
			repositoryName = repository.getDirectory().getParentFile()
					.getName();
		else
			repositoryName = ""; //$NON-NLS-1$
		return repositoryName;
	}

	public static String getShortObjectId(ObjectId id) {
		return id.getName().substring(0, 6);
	}

}
