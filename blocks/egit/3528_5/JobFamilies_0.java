package org.eclipse.egit.core.op;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceRuleFactory;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.MultiRule;
import org.eclipse.egit.core.CoreText;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.jgit.api.CleanCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;

public class CleanOperation implements IEGitOperation {

	private IResource[] resources;

	private ISchedulingRule schedulingRule;

	private boolean cleanDirs;

	private Set<String> result = new HashSet<String>();

	private boolean dryRun;

	public CleanOperation(IResource[] resources, boolean cleanDirs, boolean dryRun) {
		this.resources = new IResource[resources.length];
		System.arraycopy(resources, 0, this.resources, 0, resources.length);
		schedulingRule = calcSchedulingRule();
		this.cleanDirs = cleanDirs;
		this.dryRun = dryRun;
	}

	public void execute(IProgressMonitor monitor) throws CoreException {
		IProgressMonitor m = monitor;
		if(m == null)
			m = new NullProgressMonitor();

		if(resources == null || resources.length == 0)
			return;

		try {
			Map<Repository, Set<String>> pathsInRepos = calculatePathsInRepos();
			m.beginTask(CoreText.CleanOperation_title, resources.length + pathsInRepos.size());
			for(Map.Entry<Repository, Set<String>> entry : pathsInRepos.entrySet()) {
				Repository repo = entry.getKey();
				Set<String> pathsToClean = entry.getValue();

				Git git = Git.wrap(repo);
				CleanCommand clean = git.clean();

				clean.setCleanDirectories(cleanDirs);
				clean.setDryRun(dryRun);
				clean.setPaths(findPathsToCleanFromOffsets(pathsToClean, git.status().call(), cleanDirs));
				this.result.addAll(clean.call());
				m.worked(1);
			}

			for(IResource res : resources) {
				res.refreshLocal(IResource.DEPTH_INFINITE, new SubProgressMonitor(m, 1));
			}
		} catch(final Exception e) {
			if(m.isCanceled())
				throw new CoreException(Status.CANCEL_STATUS);
		} finally {
			m.done();
		}
	}

	public ISchedulingRule getSchedulingRule() {
		return schedulingRule;
	}

	public Set<String> getResult() {
		return result;
	}

	private Set<String> findPathsToCleanFromOffsets(Set<String> pathsToClean, org.eclipse.jgit.api.Status status, boolean dirs) {
		Set<String> paths = new HashSet<String>();
		Set<String> folders = new HashSet<String>();
		Set<String> untracked = status.getUntracked();
		Set<String> untrackedFolders = status.getUntrackedFolders();

		for(String offset : pathsToClean) {
			for(String candidate : untracked) {
				if(candidate.startsWith(offset + "/")) //$NON-NLS-1$
					paths.add(candidate);
			}
			if(dirs) {
				for(String candidate : untrackedFolders) {
					if(candidate.startsWith(offset + "/")) //$NON-NLS-1$
						folders.add(candidate);
				}
			}
		}

		if(dirs) {
			Set<String> consolidated = new HashSet<String>();
			for(String file : paths) {
				boolean contained = false;
				for(String folder : folders) {
					if(file.startsWith(folder + "/")) { //$NON-NLS-1$
						contained = true;
						break;
					}
				}
				if(!contained)
					consolidated.add(file);
			}

			consolidated.addAll(folders);
			return consolidated;
		} else {
			return paths;
		}
	}

	private Map<Repository, Set<String>> calculatePathsInRepos() {
		Map<Repository, Set<String>> repoPaths = new HashMap<Repository, Set<String>>();

		for(IResource res : resources) {
			RepositoryMapping mapping = RepositoryMapping.getMapping(res);
			if(mapping != null) {
				if(!repoPaths.containsKey(mapping.getRepository()))
					repoPaths.put(mapping.getRepository(), new HashSet<String>());

				repoPaths.get(mapping.getRepository()).add(mapping.getRepoRelativePath(res));
			}
		}

		return repoPaths;
	}

	private ISchedulingRule calcSchedulingRule() {
		List<ISchedulingRule> rules = new ArrayList<ISchedulingRule>();
		IResourceRuleFactory ruleFactory = ResourcesPlugin.getWorkspace()
				.getRuleFactory();
		for (IResource resource : resources) {
			IContainer container = resource.getParent();
			if (!(container instanceof IWorkspaceRoot)) {
				ISchedulingRule rule = ruleFactory.modifyRule(container);
				if (rule != null)
					rules.add(rule);
			}
		}
		if (rules.size() == 0)
			return null;
		else
			return new MultiRule(rules.toArray(new IResource[rules.size()]));
	}

	public boolean isCleaningDirs() {
		return cleanDirs;
	}

	public boolean isDryRun() {
		return dryRun;
	}
}
