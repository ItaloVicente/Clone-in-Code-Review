
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.util.Set;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.revwalk.ObjectWalk;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevFlag;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevSort;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.transport.ReceiveCommand.Result;

public class BaseConnectivityChecker implements ConnectivityChecker {
	@Override
	public void checkConnectivity(BaseReceivePack baseReceivePack
			Set<ObjectId> haves
			throws MissingObjectException
		monitor.beginTask(JGitText.get().countingObjects
				ProgressMonitor.UNKNOWN);
		try (ObjectWalk ow = new ObjectWalk(baseReceivePack.db)) {
			if (!markStartAndKnownNodes(baseReceivePack
					monitor)) {
				return;
			}
			checkCommitTree(baseReceivePack
			checkObjects(baseReceivePack
		} finally {
			monitor.endTask();
		}
	}

	private boolean markStartAndKnownNodes(BaseReceivePack baseReceivePack
			ObjectWalk ow
			Set<ObjectId> haves
			throws IOException {
		boolean markTrees = baseReceivePack
				.isCheckReferencedObjectsAreReachable()
				&& !baseReceivePack.parser.getBaseObjectIds().isEmpty();
		if (baseReceivePack.isCheckReferencedObjectsAreReachable()) {
			ow.sort(RevSort.TOPO);
			if (!baseReceivePack.parser.getBaseObjectIds().isEmpty()) {
				ow.sort(RevSort.BOUNDARY
			}
		}
		boolean hasInteresting = false;

		for (ReceiveCommand cmd : baseReceivePack.getAllCommands()) {
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
			monitor.update(1);
			hasInteresting = true;
		}
		if (!hasInteresting) {
			return false;
		}
		for (ObjectId have : haves) {
			RevObject o = ow.parseAny(have);
			ow.markUninteresting(o);
			monitor.update(1);

			if (markTrees) {
				o = ow.peel(o);
				if (o instanceof RevCommit) {
					o = ((RevCommit) o).getTree();
				}
				if (o instanceof RevTree) {
					ow.markUninteresting(o);
				}
				monitor.update(1);
			}
		}
		return true;
	}

	private void checkObjects(BaseReceivePack baseReceivePack
			ProgressMonitor monitor) throws IOException {
		RevObject o;

		while ((o = ow.nextObject()) != null) {
			monitor.update(1);
			if (o.has(RevFlag.UNINTERESTING)) {
				continue;
			}

			if (baseReceivePack.isCheckReferencedObjectsAreReachable()) {
				if (baseReceivePack.parser.getNewObjectIds().contains(o)) {
					continue;
				}
				throw new MissingObjectException(o

			}

			if (o instanceof RevBlob
					&& !baseReceivePack.db.getObjectDatabase().has(o)) {
				throw new MissingObjectException(o
			}
		}

		if (baseReceivePack.isCheckReferencedObjectsAreReachable()) {
			for (ObjectId id : baseReceivePack.parser.getBaseObjectIds()) {
				o = ow.parseAny(id);
				if (!o.has(RevFlag.UNINTERESTING)) {
					throw new MissingObjectException(o
				}
			}
		}
	}

	private void checkCommitTree(BaseReceivePack baseReceivePack
			ProgressMonitor monitor) throws IOException {
		RevCommit c;
		while ((c = ow.next()) != null) {
			monitor.update(1);
			if (baseReceivePack.isCheckReferencedObjectsAreReachable()
					&& !baseReceivePack.parser.getNewObjectIds().contains(c)) {
				throw new MissingObjectException(c
			}
		}
	}
}
