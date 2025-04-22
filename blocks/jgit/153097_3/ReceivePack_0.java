
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;

public class IterativeConnectivityChecker implements ConnectivityChecker {

	private final ConnectivityChecker connectivityChecker;

	private final Set<ObjectId> forcedHaves = new HashSet<>();

	public IterativeConnectivityChecker(
			ConnectivityChecker connectivityChecker) {
		this.connectivityChecker = connectivityChecker;
	}

	@Override
	public void checkConnectivity(ReceivePack receivePack
			Set<ObjectId> advertisedHaves
			throws MissingObjectException
		try {
			Set<ObjectId> expectedParents = new HashSet<>();
			Set<ObjectId> newRefs = new HashSet<>();
			for (ReceiveCommand cmd : receivePack.getAllCommands()) {
				if (cmd.getType() == ReceiveCommand.Type.UPDATE || cmd
						.getType() == ReceiveCommand.Type.UPDATE_NONFASTFORWARD) {
					if (advertisedHaves.contains(cmd.getOldId())) {
						expectedParents.add(cmd.getOldId());
					}
					if (advertisedHaves.contains(cmd.getNewId())) {
						expectedParents.add(cmd.getNewId());
					}
				} else if (cmd.getType() == ReceiveCommand.Type.CREATE) {
					if (advertisedHaves.contains(cmd.getNewId())) {
						expectedParents.add(cmd.getNewId());
					} else {
						newRefs.add(cmd.getNewId());
					}
				}
			}
			if (!newRefs.isEmpty()) {
				expectedParents.addAll(extractAdvertisedParentCommits(
						receivePack
			}

			expectedParents.addAll(forcedHaves);

			if (!expectedParents.isEmpty()) {
				connectivityChecker.checkConnectivity(receivePack
						expectedParents
				return;
			}
		} catch (MissingObjectException e) {
		}
		connectivityChecker.checkConnectivity(receivePack
				advertisedHaves
	}

	public void setForcedHaves(Set<ObjectId> forcedHaves) {
		this.forcedHaves.addAll(forcedHaves);
	}

	private Set<ObjectId> extractAdvertisedParentCommits(
			ReceivePack receivePack
			Set<ObjectId> newRefs)
			throws MissingObjectException
		Set<ObjectId> advertisedParents = new HashSet<>();
		for (ObjectId newRef : newRefs) {
			RevObject object = receivePack.walk.parseAny(newRef);
			if (object instanceof RevCommit) {
				for (RevCommit parentCommit : ((RevCommit) object)
						.getParents()) {
					if (advertisedHaves.contains(parentCommit.getId())) {
						advertisedParents.add(parentCommit.getId());
					}
				}
			}
		}
		return advertisedParents;
	}

}
