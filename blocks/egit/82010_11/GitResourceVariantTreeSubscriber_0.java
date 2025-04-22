package org.eclipse.egit.core.synchronize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.mapping.RemoteResourceMappingContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.internal.CoreText;
import org.eclipse.egit.core.internal.util.ProjectUtil;
import org.eclipse.egit.core.internal.util.ResourceUtil;
import org.eclipse.egit.core.synchronize.dto.GitSynchronizeData;
import org.eclipse.egit.core.synchronize.dto.GitSynchronizeDataSet;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.team.core.TeamException;
import org.eclipse.team.core.synchronize.SyncInfo;
import org.eclipse.team.core.variants.IResourceVariant;

public class GitLazyResourceVariantTreeSubscriber
		extends GitResourceVariantTreeSubscriber {

	public GitLazyResourceVariantTreeSubscriber(GitSynchronizeDataSet gsds) {
		super(gsds);
	}

	@Override
	public boolean isSupervised(IResource res) throws TeamException {
		return true;
	}

	@Override
	protected SyncInfo getSyncInfo(IResource local, IResourceVariant base,
			IResourceVariant remote) throws TeamException {
		if (getDataSet().shouldBeIncluded(local)) {
			return super.getSyncInfo(local, base, remote);
		}

		IProject project = local.getProject();
		Repository repo = ResourceUtil.getRepository(local);
		if (repo == null) {
			return null;
		}
		GitSynchronizeData data = getDataSet().getData(project);
		if (data == null) {
			for (GitSynchronizeData sd : getDataSet()) {
				if (repo.equals(sd.getRepository())) {
					data = sd;
					break;
				}
			}
		}
		if (data == null) {
			return null;
		}
		return getSyncInfo(local, base, remote, data);
	}

	private SyncInfo getSyncInfo(IResource local, IResourceVariant base,
			IResourceVariant remote, @NonNull GitSynchronizeData gsd) {
		GitSyncObjectCache repoCache = getCache().get(gsd.getRepository());
		if (repoCache == null) {
			return null;
		}
		if (GitSyncCache.loadDataFromGit(gsd, null, repoCache)) {
			try {
				return getSyncInfo(local, base, remote, gsd.getRepository());
			} catch (TeamException e) {
				Activator.logError(e.getMessage(), e);
			}
		}
		return null;
	}

	@Override
	public String getName() {
		return CoreText.GitLazyResourceVariantTreeSubscriber_name;
	}

	@Override
	public IResource[] roots() {
		List<IResource> projects = new ArrayList<>();
		try {
			for (GitSynchronizeData data : getDataSet()) {
				projects.addAll(Arrays.asList(ProjectUtil
						.getValidOpenProjects(data.getRepository())));
			}
		} catch (CoreException e) {
			Activator.logError(e.getMessage(), e);
		}
		return projects.toArray(new IResource[projects.size()]);
	}

}
