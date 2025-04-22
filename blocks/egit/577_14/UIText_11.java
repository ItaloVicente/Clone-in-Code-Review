package org.eclipse.egit.core.synchronize.dto;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

public class GitSynchronizeDataSet implements Iterable<GitSynchronizeData> {

	private final Set<GitSynchronizeData> gsd;

	private final Map<IProject, GitSynchronizeData> projectMapping;

	public GitSynchronizeDataSet() {
		gsd = new HashSet<GitSynchronizeData>();
		projectMapping = new HashMap<IProject, GitSynchronizeData>();
	}

	public GitSynchronizeDataSet(GitSynchronizeData data) {
		this();
		add(data);
	}

	public void add(GitSynchronizeData data) {
		gsd.add(data);
		for (IProject proj : data.getProjects()) {
			projectMapping.put(proj, data);
		}
	}

	public boolean contains(IProject project) {
		return projectMapping.containsKey(project);
	}

	public GitSynchronizeData getData(IProject project) {
		return projectMapping.get(project);
	}

	public Iterator<GitSynchronizeData> iterator() {
		return gsd.iterator();
	}

	public IResource[] getAllResources() {
		Set<IResource> resource = new HashSet<IResource>();
		for (GitSynchronizeData data : gsd) {
			resource.addAll(data.getProjects());
		}
		return resource.toArray(new IResource[resource.size()]);
	}

}
