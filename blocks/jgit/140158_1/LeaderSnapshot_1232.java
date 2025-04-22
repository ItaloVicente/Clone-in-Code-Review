
package org.eclipse.jgit.internal.ketch;

import static org.eclipse.jgit.internal.ketch.KetchReplica.State.AHEAD;
import static org.eclipse.jgit.internal.ketch.KetchReplica.State.DIVERGENT;
import static org.eclipse.jgit.internal.ketch.KetchReplica.State.LAGGING;
import static org.eclipse.jgit.internal.ketch.KetchReplica.State.UNKNOWN;
import static org.eclipse.jgit.lib.Constants.OBJ_COMMIT;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.ReceiveCommand;

class LagCheck implements AutoCloseable {
	private final KetchReplica replica;
	private final Repository repo;
	private RevWalk rw;
	private ObjectId remoteId;

	LagCheck(KetchReplica replica
		this.replica = replica;
		this.repo = repo;
		initRevWalk();
	}

	private void initRevWalk() {
		if (rw != null) {
			rw.close();
		}

		rw = new RevWalk(repo);
		rw.setRetainBody(false);
	}

	@Override
	public void close() {
		if (rw != null) {
			rw.close();
			rw = null;
		}
	}

	ObjectId getRemoteId() {
		return remoteId;
	}

	KetchReplica.State check(ObjectId acceptId
		remoteId = acceptId;
		if (remoteId == null) {
			return UNKNOWN;
		}

		if (AnyObjectId.equals(remoteId
			return LAGGING;
		}

		try {
			RevCommit remote;
			try {
				remote = parseRemoteCommit(acceptCmd.getRefName());
			} catch (RefGoneException gone) {
				return LAGGING;
			} catch (MissingObjectException notFound) {
				return DIVERGENT;
			}

			RevCommit head = rw.parseCommit(acceptCmd.getNewId());
			if (rw.isMergedInto(remote
				return LAGGING;
			}

			if (rw.isMergedInto(head
				return AHEAD;
			} else {
				return DIVERGENT;
			}
		} catch (IOException err) {
			KetchReplica.log.error(String.format(
					"Cannot compare %s"
					acceptCmd.getRefName())
			return UNKNOWN;
		}
	}

	private RevCommit parseRemoteCommit(String refName)
			throws IOException
		try {
			return rw.parseCommit(remoteId);
		} catch (MissingObjectException notLocal) {
		}

		ReplicaFetchRequest fetch = new ReplicaFetchRequest(
				Collections.singleton(refName)
				Collections.<ObjectId> emptySet());
		try {
			replica.blockingFetch(repo
		} catch (IOException fetchErr) {
			KetchReplica.log.error(String.format(
					"Cannot fetch %s (%s) from %s"
					remoteId.abbreviate(8).name()
					replica.describeForLog())
			throw new MissingObjectException(remoteId
		}

		Map<String
		if (adv == null) {
			throw new MissingObjectException(remoteId
		}

		Ref ref = adv.get(refName);
		if (ref == null || ref.getObjectId() == null) {
			throw new RefGoneException();
		}

		initRevWalk();
		remoteId = ref.getObjectId();
		return rw.parseCommit(remoteId);
	}

	private static class RefGoneException extends Exception {
		private static final long serialVersionUID = 1L;
	}
}
