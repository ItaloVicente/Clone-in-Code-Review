package org.eclipse.egit.ui.internal.synchronize;

import org.eclipse.core.resources.IResource;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevCommitList;
import org.eclipse.team.core.TeamException;
import org.eclipse.team.core.synchronize.SyncInfo;
import org.eclipse.team.core.variants.IResourceVariant;
import org.eclipse.team.core.variants.IResourceVariantComparator;

class GitSyncInfo extends SyncInfo {

	GitSyncInfo(IResource local, IResourceVariant base,
			IResourceVariant remote, IResourceVariantComparator comparator) {
		super(local, base, remote, comparator);
	}

	@Override
	protected int calculateKind() throws TeamException {
		IResource local = getLocal();
		GitResourceVariant base = (GitResourceVariant) getBase();
		GitResourceVariant remote = (GitResourceVariant) getRemote();

		if (base instanceof GitBlobResourceVariant) {
			if (remote instanceof GitBlobResourceVariant) {
				if (local.exists()) {
					if (getComparator().compare(local, base)) {
						GitBlobResourceVariant baseBlob = (GitBlobResourceVariant) base;
						GitBlobResourceVariant remoteBlob = (GitBlobResourceVariant) remote;

						if (baseBlob.getId().equals(remoteBlob.getId())) {
							return IN_SYNC;
						}

						RevCommitList<RevCommit> baseList = baseBlob
								.getCommitList();
						RevCommitList<RevCommit> remoteList = remoteBlob
								.getCommitList();

						RevCommit recentRemoteCommit = remoteList.get(0);
						RevCommit recentBaseCommit = baseList.get(0);

						for (RevCommit baseCommit : baseList) {
							if (recentRemoteCommit.name().equals(
									baseCommit.name())) {
								return OUTGOING | CHANGE;
							}
						}

						for (RevCommit remoteCommit : remoteList) {
							if (recentBaseCommit.name().equals(
									remoteCommit.name())) {
								return INCOMING | CHANGE;
							}
						}

						return CONFLICTING | CHANGE;
					} else {
						return OUTGOING | CHANGE;
					}
				}
			} else if (remote == null) {
				if (getComparator().compare(local, base)) {
					return OUTGOING | ADDITION;
				} else {
					return OUTGOING | CHANGE;
				}
			} else {
				return CONFLICTING | CHANGE;
			}
		} else if (base instanceof GitFolderResourceVariant) {
			if (remote instanceof GitFolderResourceVariant) {
				if (!base.getResource().exists()) {
					return INCOMING | ADDITION;
				}
			} else if (remote == null) {
				return OUTGOING | ADDITION;
			} else {
				return CONFLICTING | CHANGE;
			}
		}

		return super.calculateKind();
	}
}
