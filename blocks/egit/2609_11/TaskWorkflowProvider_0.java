package org.eclipse.egit.internal.mylyn.ui.commit;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.egit.core.ProjectReference;
import org.eclipse.egit.core.RepositoryCache;
import org.eclipse.egit.core.op.BranchOperation;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.mylyn.commons.core.storage.ICommonStorable;
import org.eclipse.mylyn.context.core.AbstractContextStructureBridge;
import org.eclipse.mylyn.context.core.ContextCore;
import org.eclipse.mylyn.internal.context.tasks.ui.TaskContextStore;
import org.eclipse.mylyn.internal.resources.ui.ResourceStructureBridge;
import org.eclipse.mylyn.internal.tasks.ui.TasksUiPlugin;
import org.eclipse.mylyn.tasks.core.ITask;
import org.eclipse.mylyn.tasks.core.ITaskActivationListener;
import org.eclipse.mylyn.versions.core.ProjectSetConverter;
import org.eclipse.team.core.RepositoryProviderType;

public class TaskActivationListener implements ITaskActivationListener {

	private static final String PROJECTS_PSF = "projects.psf"; //$NON-NLS-1$

	private Set<Repository> managedRepositories = new HashSet<Repository>();

	private Map<Repository, String> noTaskActiveRepositoryToBranch = new HashMap<Repository, String>();

	public void preTaskActivated(ITask task) {
		noTaskActiveRepositoryToBranch.clear();
		for (Repository repo : getRepositoryCache().getAllRepositories())
			try {
				noTaskActiveRepositoryToBranch.put(repo, repo.getBranch());
			} catch (IOException e) {
			}
	}

	public void preTaskDeactivated(ITask task) {
		List<IProject> projectsInActiveContext = new ArrayList<IProject>(
				getProjectsInActiveContext());
		for (IProject project : projectsInActiveContext) {
			RepositoryMapping repositoryMapping = RepositoryMapping
					.getMapping(project);
			if (repositoryMapping != null)
				managedRepositories.add(repositoryMapping.getRepository());
		}

		ICommonStorable storable = ((TaskContextStore) TasksUiPlugin
				.getContextStore()).getStorable(task);
		try {
			OutputStream out = storable.write(PROJECTS_PSF, null);
			try {
				ByteArrayOutputStream data = ProjectSetConverter
						.exportProjectSet(projectsInActiveContext);
				out.write(data.toByteArray());
			} finally {
				out.close();
			}
		} catch (Exception e) {
		} finally {
			storable.release();
		}
	}

	private Set<IProject> getProjectsInActiveContext() {
		AbstractContextStructureBridge bridge = ContextCore.getStructureBridge(ContextCore.CONTENT_TYPE_RESOURCE);
		return ((ResourceStructureBridge)bridge).getProjectsInActiveContext();
	}

	@SuppressWarnings("restriction")
	public void taskActivated(ITask task) {
		managedRepositories.clear();
		ICommonStorable storable = ((TaskContextStore) TasksUiPlugin
				.getContextStore()).getStorable(task);
		try {
			InputStream in = storable.read(PROJECTS_PSF, null);
			try {
				RepositoryProviderType gitProvider = RepositoryProviderType
						.getProviderType("org.eclipse.egit.core.GitProvider"); //$NON-NLS-1$
				List<String> projectReferencesString = ProjectSetConverter
						.readProjectReferences(in, gitProvider);
				Set<Repository> repositories = checkoutProjectReferences(projectReferencesString);
				managedRepositories.addAll(repositories);
			} finally {
				in.close();
			}
		} catch (Exception e) {
		} finally {
			storable.release();
		}
	}

	public void taskDeactivated(ITask task) {
		for (Iterator<Repository> it = noTaskActiveRepositoryToBranch.keySet()
				.iterator(); it.hasNext();)
			if (!managedRepositories.contains(it.next()))
				it.remove();

		performBranchCheckout(noTaskActiveRepositoryToBranch);

		managedRepositories.clear();
		noTaskActiveRepositoryToBranch.clear();
	}

	private Set<Repository> checkoutProjectReferences(
			List<String> projectReferencesString) {
		List<ProjectReference> references = new ArrayList<ProjectReference>();
		for (String ref : projectReferencesString)
			try {
				references.add(new ProjectReference(ref));
			} catch (Exception e) {
			}

		Map<Repository, String> repositoryToBranch = new HashMap<Repository, String>();
		Map<String, Repository> remoteUrlToRepository = getRemoteUrlToRepository();
		for (ProjectReference ref : references) {
			String remoteUrl = ref.getRepository().toString();
			Repository repository = remoteUrlToRepository.get(remoteUrl);
			if (repository != null)
				repositoryToBranch.put(repository, ref.getBranch());
		}

		performBranchCheckout(repositoryToBranch);

		return repositoryToBranch.keySet();
	}

	private Map<String, Repository> getRemoteUrlToRepository() {
		Map<String, Repository> remoteUrlToRepository = new HashMap<String, Repository>();
		for (Repository repo : getRepositoryCache().getAllRepositories()) {
			StoredConfig config = repo.getConfig();
			Set<String> subsections = config.getSubsections("remote"); //$NON-NLS-1$
			for (String subsec : subsections) {
				String url = config.getString("remote", subsec, "url"); //$NON-NLS-1$//$NON-NLS-2$
				remoteUrlToRepository.put(url, repo);
			}
		}
		return remoteUrlToRepository;
	}

	private void performBranchCheckout(
			Map<Repository, String> repositoryToBranch) {
		for (Repository repository : repositoryToBranch.keySet())
			try {
				BranchOperation operation = new BranchOperation(repository,
						repositoryToBranch.get(repository));
				operation.execute(null);
			} catch (Exception e) {
			}
	}

	RepositoryCache getRepositoryCache() {
		org.eclipse.egit.core.Activator egit = org.eclipse.egit.core.Activator
				.getDefault();
		return egit.getRepositoryCache();
	}

}
