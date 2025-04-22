package org.eclipse.egit.ui.internal.synchronize;

import org.eclipse.core.resources.IResource;
import org.eclipse.egit.ui.UIText;
import org.eclipse.egit.ui.internal.synchronize.dto.GitSynchronizeDataSet;
import org.eclipse.team.core.TeamException;
import org.eclipse.team.core.synchronize.SyncInfo;
import org.eclipse.team.core.variants.IResourceVariant;
import org.eclipse.team.core.variants.IResourceVariantComparator;
import org.eclipse.team.core.variants.IResourceVariantTree;
import org.eclipse.team.core.variants.ResourceVariantByteStore;
import org.eclipse.team.core.variants.ResourceVariantTreeSubscriber;

class GitResourceVariantTreeSubscriber extends
		ResourceVariantTreeSubscriber {

	private final ResourceVariantByteStore store;

	private IResourceVariantTree remoteTree;

	private IResourceVariantTree baseTree;

	private GitSynchronizeDataSet gitSynchronizeDataSet;

	GitResourceVariantTreeSubscriber(GitSynchronizeDataSet data,
			ResourceVariantByteStore store) {
		this.store = store;
		this.gitSynchronizeDataSet = data;
	}

	private IResource[] roots;

	@Override
	public boolean isSupervised(IResource resource) throws TeamException {
		return true; //gitSynchronizeDataSet.contains(resource.getProject());
	}

	@Override
	public IResource[] roots() {
		if (roots != null) {
			return roots;
		}

		roots = gitSynchronizeDataSet.getAllResources();
		return roots;
	}

	void reset(GitSynchronizeDataSet data) {
		gitSynchronizeDataSet = data;

		baseTree = null;
		remoteTree = null;
	}

	@Override
	public IResource[] members(IResource resource) throws TeamException {
		return getBaseTree().members(resource);
	}

	@Override
	public String getName() {
		return UIText.GitBranchResourceVariantTreeSubscriber_gitRepository;
	}

	@Override
	public IResourceVariantComparator getResourceComparator() {
		return new GitResourceVariantComparator(gitSynchronizeDataSet, store);
	}

	@Override
	protected IResourceVariantTree getBaseTree() {
		if (baseTree == null) {
			baseTree = new GitBaseResourceVariantTree(gitSynchronizeDataSet, store);
		}
		return baseTree;
	}

	@Override
	protected IResourceVariantTree getRemoteTree() {
		if (remoteTree == null) {
			remoteTree = new GitRemoteResourceVariantTree(gitSynchronizeDataSet, store);
		}
		return remoteTree;
	}

	@Override
	protected SyncInfo getSyncInfo(IResource local, IResourceVariant base,
			IResourceVariant remote) throws TeamException {
		GitSyncInfo info = new GitSyncInfo(local, base, remote,
				getResourceComparator());
		info.init();
		return info;
	}

}
