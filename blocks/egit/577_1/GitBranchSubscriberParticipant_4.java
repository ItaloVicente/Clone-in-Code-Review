package org.eclipse.egit.ui.internal.synchronize;

import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.team.core.TeamException;
import org.eclipse.team.core.synchronize.SyncInfo;
import org.eclipse.team.core.variants.IResourceVariant;
import org.eclipse.team.core.variants.IResourceVariantTree;

class GitBranchResourceVariantTreeSubscriber extends
		GitResourceVariantTreeSubscriber {

	private IResourceVariantTree remoteTree;

	private IResourceVariantTree baseTree;

	private Map<Repository, String> branches;

	GitBranchResourceVariantTreeSubscriber(Map<Repository, String> branches,
			IResource[] roots) {
		this.branches = branches;
		setRoots(roots);
	}

	void reset(Map<Repository, String> branches, IResource[] roots) {
		this.branches = branches;
		setRoots(roots);

		baseTree = null;
		remoteTree = null;
	}

	@Override
	protected IResourceVariantTree getBaseTree() {
		if (baseTree == null) {
			baseTree = new GitHeadResourceVariantTree(roots());
		}
		return baseTree;
	}

	@Override
	protected IResourceVariantTree getRemoteTree() {
		if (remoteTree == null) {
			remoteTree = new GitBranchResourceVariantTree(branches, roots());
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

	@Override
	public String getName() {
		return "Git Branches";
	}

}
