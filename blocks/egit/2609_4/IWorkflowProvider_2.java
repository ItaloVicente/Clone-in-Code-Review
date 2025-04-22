package org.eclipse.egit.internal.mylyn.ui.tasks;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.egit.core.ProjectReference;
import org.eclipse.egit.core.op.BranchOperation;
import org.eclipse.egit.core.op.CreateLocalBranchOperation;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.mylyn.tasks.core.ITask;
import org.eclipse.mylyn.tasks.core.ITaskActivationListener;
import org.eclipse.mylyn.versions.core.ProjectSetConverter;
import org.eclipse.team.core.RepositoryProviderType;

public class TaskActivationListener implements ITaskActivationListener {

	Set<Repository> usedRepositories = new HashSet<Repository>();

	public void preTaskDeactivated(ITask task) {

		List<IProject> projectsInActiveContext = ProjectSetConverter
				.getProjectsInActiveContext();

		for (IProject project : projectsInActiveContext) {
			RepositoryMapping repositoryMapping = RepositoryMapping
					.getMapping(project);
			Repository repo = repositoryMapping.getRepository();
			usedRepositories.add(repo);

		}

		ProjectSetConverter.exportProjectSet(task);
	}

	@SuppressWarnings("restriction")
	public void taskActivated(ITask task) {
		usedRepositories = new HashSet<Repository>();

		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects(IContainer.INCLUDE_HIDDEN);

		Set<Repository> allRepositories = new HashSet<Repository>();

		for (IProject project : projects) {
			RepositoryMapping repositoryMapping = RepositoryMapping
					.getMapping(project);
			if (repositoryMapping != null)
				allRepositories.add(repositoryMapping.getRepository());
		}

		Map<String, Repository> remoteMapping = new HashMap<String, Repository>();

		for (Repository repo : allRepositories) {
			StoredConfig config = repo.getConfig();
			Set<String> subsections = config.getSubsections("remote"); //$NON-NLS-1$
			for (String subsec : subsections) {
				String url = config.getString("remote", subsec, "url"); //$NON-NLS-1$//$NON-NLS-2$
				remoteMapping.put(url, repo);
			}
		}

		RepositoryProviderType providerType = RepositoryProviderType
				.getProviderType("org.eclipse.egit.core.GitProvider"); //$NON-NLS-1$
		ArrayList<String> projectReferencesString = ProjectSetConverter
				.getProjectReferences(task, providerType);
		ArrayList<ProjectReference> references = new ArrayList<ProjectReference>();

		for (String ref : projectReferencesString) {
			try {
				references.add(new ProjectReference(ref));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}

		Map<Repository, String> repositoriesInPSF = new HashMap<Repository, String>();

		for (ProjectReference ref : references) {
			if (remoteMapping.containsKey(ref.getRepository().toString())) {
				repositoriesInPSF.put(
						remoteMapping.get(ref.getRepository().toString()),
						ref.getBranch());
			}
		}


		if (repositoriesInPSF.isEmpty()) {
		} else
			performBranchCheckout(repositoriesInPSF);
	}

	private void performBranchCheckout(
			Map<Repository, String> reposBranchMapping) {
		usedRepositories.addAll(reposBranchMapping.keySet());
		try {
			for (Repository repo : reposBranchMapping.keySet()) {
				if (repo.getRefDatabase().getRef(reposBranchMapping.get(repo)) == null) {
					CreateLocalBranchOperation createOperation = new CreateLocalBranchOperation(
							repo, reposBranchMapping.get(repo),
							repo.getRef(Constants.R_REMOTES
									+ Constants.DEFAULT_REMOTE_NAME
									+ "/" + Constants.MASTER), //$NON-NLS-1$
							null);
					createOperation.execute(null);
				}

				BranchOperation operation = new BranchOperation(repo,
						reposBranchMapping.get(repo));
				operation.execute(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void taskDeactivated(ITask task) {
	}

	public void preTaskActivated(ITask task) {

	}
}
