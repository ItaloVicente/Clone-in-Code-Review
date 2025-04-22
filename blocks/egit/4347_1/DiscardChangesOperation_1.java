package org.eclipse.egit.core.internal.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.jgit.lib.Repository;

public class ResourceUtil {

	public static Map<Repository, Collection<String>> splitResourcesByRepository(IResource[] resources) {
		Map<Repository, Collection<String>> result = new HashMap<Repository, Collection<String>>();
		for (IResource resource:resources) {
			RepositoryMapping repositoryMapping = RepositoryMapping.getMapping(resource);
			if (repositoryMapping != null) {
				Repository repository = repositoryMapping.getRepository();
				Collection<String> resourcesList = result.get(repository);
				if (resourcesList == null) {
					resourcesList = new ArrayList<String>();
					result.put(repository, resourcesList);
				}
				resourcesList.add(repositoryMapping.getRepoRelativePath(resource));
			}
		}
		return result;
	}
}
