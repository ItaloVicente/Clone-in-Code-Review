
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.util.Set;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdSubclassMap;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.revwalk.ObjectWalk;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevFlag;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevSort;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.transport.ReceiveCommand.Result;

final class FullConnectivityChecker implements ConnectivityChecker {
	@Override
	public void checkConnectivity(ReceivePack receivePack
			Set<ObjectId> haves
			throws MissingObjectException
		pm.beginTask(JGitText.get().countingObjects
				ProgressMonitor.UNKNOWN);
		try (ObjectWalk ow = new ObjectWalk(receivePack.db)) {
			if (!markStartAndKnownNodes(receivePack
					pm)) {
				return;
			}
			checkCommitTree(receivePack
			checkObjects(receivePack
		} finally {
			pm.endTask();
		}
	}

	private boolean markStartAndKnownNodes(ReceivePack receivePack
			ObjectWalk ow
			Set<ObjectId> haves
			throws IOException {
		boolean markTrees = receivePack
				.isCheckReferencedObjectsAreReachable()
				&& !receivePack.parser.getBaseObjectIds().isEmpty();
		if (receivePack.isCheckReferencedObjectsAreReachable()) {
			ow.sort(RevSort.TOPO);
			if (!receivePack.parser.getBaseObjectIds().isEmpty()) {
				ow.sort(RevSort.BOUNDARY
			}
		}
		boolean hasInteresting = false;

		for (ReceiveCommand cmd : receivePack.getAllCommands()) {
			if (cmd.getResult() != Result.NOT_ATTEMPTED) {
				continue;
			}
			if (cmd.getType() == ReceiveCommand.Type.DELETE) {
				continue;
			}
			if (haves.contains(cmd.getNewId())) {
				continue;
			}
			ow.markStart(ow.parseAny(cmd.getNewId()));
			pm.update(1);
			hasInteresting = true;
		}
		if (!hasInteresting) {
			return false;
		}
		for (ObjectId have : haves) {
			RevObject o = ow.parseAny(have);
			ow.markUninteresting(o);
			pm.update(1);

			if (markTrees) {
				o = ow.peel(o);
				if (o instanceof RevCommit) {
					o = ((RevCommit) o).getTree();
				}
				if (o instanceof RevTree) {
					ow.markUninteresting(o);
				}
				pm.update(1);
			}
		}
		return true;
	}

	private void checkCommitTree(ReceivePack receivePack
			ProgressMonitor pm) throws IOException {
		RevCommit c;
		ObjectIdSubclassMap<ObjectId> newObjectIds = receivePack.parser
				.getNewObjectIds();
		while ((c = ow.next()) != null) {
			pm.update(1);
			if (receivePack.isCheckReferencedObjectsAreReachable()
					&& !newObjectIds.contains(c)) {
				throw new MissingObjectException(c
			}
		}
	}

	private void checkObjects(ReceivePack receivePack
			ProgressMonitor pm) throws IOException {
		RevObject o;
		ObjectIdSubclassMap<ObjectId> newObjectIds = receivePack.parser
				.getNewObjectIds();

		while ((o = ow.nextObject()) != null) {
			pm.update(1);
			if (o.has(RevFlag.UNINTERESTING)) {
				continue;
			}

			if (receivePack.isCheckReferencedObjectsAreReachable()) {
				if (newObjectIds.contains(o)) {
					continue;
				}
				throw new MissingObjectException(o

			}

			if (o instanceof RevBlob
					&& !receivePack.db.getObjectDatabase().has(o)) {
				throw new MissingObjectException(o
			}
		}

		if (receivePack.isCheckReferencedObjectsAreReachable()) {
			for (ObjectId id : receivePack.parser.getBaseObjectIds()) {
				o = ow.parseAny(id);
				if (!o.has(RevFlag.UNINTERESTING)) {
					throw new MissingObjectException(o
				}
			}
		}
	}
}
