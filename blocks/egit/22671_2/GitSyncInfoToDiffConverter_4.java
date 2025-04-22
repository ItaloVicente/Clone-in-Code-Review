package org.eclipse.egit.core.internal.merge;

import java.util.Arrays;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.egit.core.internal.CoreText;
import org.eclipse.team.core.TeamException;
import org.eclipse.team.core.diff.IDiff;
import org.eclipse.team.core.synchronize.SyncInfo;
import org.eclipse.team.core.variants.IResourceVariant;
import org.eclipse.team.core.variants.IResourceVariantComparator;
import org.eclipse.team.core.variants.IResourceVariantTree;
import org.eclipse.team.core.variants.ResourceVariantTreeSubscriber;
import org.eclipse.team.internal.core.mapping.SyncInfoToDiffConverter;

public class GitResourceVariantTreeSubscriber extends
		ResourceVariantTreeSubscriber {
	private GitResourceVariantTreeProvider variantTreeProvider;

	private final SyncInfoToDiffConverter syncInfoConverter;

	private final IResourceVariantComparator comparator;

	public GitResourceVariantTreeSubscriber(
			GitResourceVariantTreeProvider variantTreeProvider) {
		this.variantTreeProvider = variantTreeProvider;
		syncInfoConverter = new GitSyncInfoToDiffConverter(variantTreeProvider);
		comparator = new GitVariantComparator(
				variantTreeProvider.getSourceTree());
	}

	@Override
	protected IResourceVariantTree getBaseTree() {
		return variantTreeProvider.getBaseTree();
	}

	@Override
	protected IResourceVariantTree getRemoteTree() {
		return variantTreeProvider.getRemoteTree();
	}

	protected IResourceVariantTree getSourceTree() {
		return variantTreeProvider.getSourceTree();
	}

	@Override
	public IDiff getDiff(IResource resource) throws CoreException {
		final SyncInfo info = getSyncInfo(resource);
		if (info == null || info.getKind() == SyncInfo.IN_SYNC)
			return null;
		return syncInfoConverter.getDeltaFor(info);
	}

	@Override
	public SyncInfo getSyncInfo(IResource resource) throws TeamException {
		try {
			return super.getSyncInfo(resource);
		} catch (ForwardedTeamException e) {
			throw (TeamException) e.getCause();
		}
	}

	@Override
	public String getName() {
		return CoreText.GitResourceVariantTreeSubscriber_name;
	}

	@Override
	public boolean isSupervised(IResource resource) throws TeamException {
		return variantTreeProvider.getKnownResources().contains(resource);
	}

	@Override
	public IResource[] roots() {
		final Set<IResource> roots = variantTreeProvider.getRoots();
		return roots.toArray(new IResource[roots.size()]);
	}

	@Override
	public IResourceVariantComparator getResourceComparator() {
		return comparator;
	}

	private static class GitVariantComparator implements
			IResourceVariantComparator {
		private final IResourceVariantTree oursTree;

		public GitVariantComparator(IResourceVariantTree oursTree) {
			this.oursTree = oursTree;
		}

		public boolean compare(IResource local, IResourceVariant remote) {
			try {
				final IResourceVariant oursVariant = oursTree
						.getResourceVariant(local);
				if (oursVariant == null)
					return remote == null;
				return compare(oursVariant, remote);
			} catch (TeamException e) {
				throw new ForwardedTeamException(e);
			}
		}

		public boolean compare(IResourceVariant base, IResourceVariant remote) {
			return Arrays.equals(base.asBytes(), remote.asBytes());
		}

		public boolean isThreeWay() {
			return true;
		}
	}

	private static class ForwardedTeamException extends RuntimeException {
		private static final long serialVersionUID = 4074010396155542178L;

		public ForwardedTeamException(TeamException e) {
			super(e);
		}
	}
}
