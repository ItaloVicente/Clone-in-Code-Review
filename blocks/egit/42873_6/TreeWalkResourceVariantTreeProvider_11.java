package org.eclipse.egit.core.internal.merge;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.internal.CoreText;
import org.eclipse.egit.core.internal.storage.WorkspaceFileRevision;
import org.eclipse.osgi.util.NLS;
import org.eclipse.team.core.TeamException;
import org.eclipse.team.core.diff.IDiff;
import org.eclipse.team.core.diff.ITwoWayDiff;
import org.eclipse.team.core.diff.provider.ThreeWayDiff;
import org.eclipse.team.core.history.IFileRevision;
import org.eclipse.team.core.mapping.provider.ResourceDiff;
import org.eclipse.team.core.synchronize.SyncInfo;
import org.eclipse.team.core.variants.IResourceVariant;
import org.eclipse.team.internal.core.mapping.ResourceVariantFileRevision;
import org.eclipse.team.internal.core.mapping.SyncInfoToDiffConverter;

public class GitSyncInfoToDiffConverter extends SyncInfoToDiffConverter {
	private GitResourceVariantTreeProvider variantTreeProvider;

	public GitSyncInfoToDiffConverter(
			GitResourceVariantTreeProvider variantTreeProvider) {
		this.variantTreeProvider = variantTreeProvider;
	}

	@Override
	public IDiff getDeltaFor(SyncInfo info) {
		if (info.getComparator().isThreeWay()) {
			ITwoWayDiff local = getLocalDelta(info);
			ITwoWayDiff remote = getRemoteDelta(info);
			return new ThreeWayDiff(local, remote);
		} else {
			if (info.getKind() != SyncInfo.IN_SYNC) {
				IResourceVariant remote = info.getRemote();
				IResource local = info.getLocal();

				return computeResourceDiff(info, remote, local);
			}
			return null;
		}
	}

	private ITwoWayDiff getLocalDelta(SyncInfo info) {
		int direction = SyncInfo.getDirection(info.getKind());
		if (direction == SyncInfo.OUTGOING || direction == SyncInfo.CONFLICTING) {
			IResourceVariant ancestor = info.getBase();
			IResource local = info.getLocal();

			return computeResourceDiff(info, ancestor, local);
		}
		return null;
	}

	private ResourceDiff computeResourceDiff(SyncInfo info,
			IResourceVariant variant, IResource local) {
		int kind;
		if (variant == null)
			kind = IDiff.REMOVE;
		else if (!local.exists())
			kind = IDiff.ADD;
		else
			kind = IDiff.CHANGE;

		if (local.getType() == IResource.FILE) {
			IFileRevision after = asFileState(variant);
			IFileRevision before = getLocalFileRevision((IFile) local);
			return new ResourceDiff(info.getLocal(), kind, 0, before, after);
		}
		return new ResourceDiff(info.getLocal(), kind);
	}

	public IFileRevision getLocalFileRevision(IFile local) {
		try {
			return asFileState(variantTreeProvider.getSourceTree()
					.getResourceVariant(local));
		} catch (TeamException e) {
			String error = NLS
					.bind(CoreText.GitResourceVariantTreeSubscriber_CouldNotFindSourceVariant,
							local.getName());
			Activator.logError(error, e);
			return new WorkspaceFileRevision(local);
		}
	}

	private ITwoWayDiff getRemoteDelta(SyncInfo info) {
		int direction = SyncInfo.getDirection(info.getKind());
		if (direction == SyncInfo.INCOMING || direction == SyncInfo.CONFLICTING) {
			IResourceVariant ancestor = info.getBase();
			IResourceVariant remote = info.getRemote();

			int kind;
			if (ancestor == null)
				kind = IDiff.ADD;
			else if (remote == null)
				kind = IDiff.REMOVE;
			else
				kind = IDiff.CHANGE;

			if (info.getLocal().getType() == IResource.FILE) {
				IFileRevision before = asFileState(ancestor);
				IFileRevision after = asFileState(remote);
				return new ResourceDiff(info.getLocal(), kind, 0, before, after);
			}

			return new ResourceDiff(info.getLocal(), kind);
		}
		return null;
	}

	private IFileRevision asFileState(final IResourceVariant variant) {
		if (variant == null)
			return null;
		return asFileRevision(variant);
	}

	@Override
	protected ResourceVariantFileRevision asFileRevision(
			IResourceVariant variant) {
		return new GitResourceVariantFileRevision(variant);
	}
}
