
package org.eclipse.jgit.transport;

import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevWalk;

public abstract class AbstractAdvertiseRefsHook implements AdvertiseRefsHook {
	public void advertiseRefs(UploadSession uploadSession)
			throws ServiceMayNotContinueException {
		uploadSession.setAdvertisedRefs(getAdvertisedRefs(
				uploadSession.getRepository()
	}

	public void advertiseRefs(ReceiveSession receiveSession)
			throws ServiceMayNotContinueException {
		Map<String
				receiveSession.getRevWalk());
		Set<ObjectId> haves = getAdvertisedHaves(receiveSession.getRepository()
				receiveSession.getRevWalk());
		receiveSession.setAdvertisedRefs(refs
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
