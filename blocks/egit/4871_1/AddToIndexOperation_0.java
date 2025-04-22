package org.eclipse.egit.core.internal.job;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.MultiRule;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.jgit.lib.Repository;

public class RuleUtil {

	public static ISchedulingRule getRule(Repository repository) {
		IProject[] projects = getProjects(repository);
		if (projects.length == 0)
			return null;
		return new MultiRule(projects);
	}

	public static ISchedulingRule getRuleForRepositories(IResource[] resources) {
		ISchedulingRule result = null;
		Set<Repository> repositories = new HashSet<Repository>();
		for (IResource resource : resources) {
			RepositoryMapping mapping = RepositoryMapping.getMapping(resource);
			if (mapping != null)
				repositories.add(mapping.getRepository());
		}
		for (Repository repository : repositories) {
			ISchedulingRule rule = getRule(repository);
			result = MultiRule.combine(result, rule);
		}
		return result;
	}

	private static IProject[] getProjects(Repository repository) {
		final IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects();
		List<IProject> result = new ArrayList<IProject>();
		final File parentFile = repository.getWorkTree();
		for (IProject p : projects) {
			IPath projectLocation = p.getLocation();
			if (projectLocation == null)
				continue;
			if (projectLocation.toFile().getAbsolutePath()
					.startsWith(parentFile.getAbsolutePath()))
				result.add(p);
		}
		return result.toArray(new IProject[result.size()]);
	}

}
