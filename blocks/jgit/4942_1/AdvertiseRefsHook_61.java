
package org.eclipse.jgit.transport;

import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevWalk;

public abstract class AbstractAdvertiseRefsHook implements AdvertiseRefsHook {
	public void advertiseRefs(UploadPack uploadPack)
			throws ServiceMayNotContinueException {
		uploadPack.setAdvertisedRefs(getAdvertisedRefs(
				uploadPack.getRepository()
	}

	public void advertiseRefs(BaseReceivePack receivePack)
			throws ServiceMayNotContinueException {
		Map<String
				receivePack.getRevWalk());
		Set<ObjectId> haves = getAdvertisedHaves(receivePack.getRepository()
				receivePack.getRevWalk());
		receivePack.setAdvertisedRefs(refs
	}

	protected abstract Map<String
			Repository repository
			throws ServiceMayNotContinueException;

	protected Set<ObjectId> getAdvertisedHaves(
			Repository repository
			throws ServiceMayNotContinueException {
		return null;
	}
}
