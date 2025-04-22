
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.NotSupportedException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.RemoteRefUpdate.Status;

class PushProcess {
	static final String PROGRESS_OPENING_CONNECTION = JGitText.get().openingConnection;

	private final Transport transport;

	private PushConnection connection;

	private final Map<String

	private final RevWalk walker;

	private final OutputStream out;

	private List<String> pushOptions;

	PushProcess(final Transport transport
			final Collection<RemoteRefUpdate> toPush) throws TransportException {
		this(transport
	}

	PushProcess(final Transport transport
			final Collection<RemoteRefUpdate> toPush
			throws TransportException {
		this.walker = new RevWalk(transport.local);
		this.transport = transport;
		this.toPush = new LinkedHashMap<>();
		this.out = out;
		this.pushOptions = transport.getPushOptions();
		for (RemoteRefUpdate rru : toPush) {
			if (this.toPush.put(rru.getRemoteName()
				throw new TransportException(MessageFormat.format(
						JGitText.get().duplicateRemoteRefUpdateIsIllegal
		}
	}

	PushResult execute(ProgressMonitor monitor)
			throws NotSupportedException
		try {
			monitor.beginTask(PROGRESS_OPENING_CONNECTION
					ProgressMonitor.UNKNOWN);

			final PushResult res = new PushResult();
			connection = transport.openPush();
			try {
				res.setAdvertisedRefs(transport.getURI()
						.getRefsMap());
				res.peerUserAgent = connection.getPeerUserAgent();
				res.setRemoteUpdates(toPush);
				monitor.endTask();

				final Map<String
				if (transport.isDryRun())
					modifyUpdatesForDryRun();
				else if (!preprocessed.isEmpty())
					connection.push(monitor
			} finally {
				connection.close();
				res.addMessages(connection.getMessages());
			}
			if (!transport.isDryRun())
				updateTrackingRefs();
			for (RemoteRefUpdate rru : toPush.values()) {
				final TrackingRefUpdate tru = rru.getTrackingRefUpdate();
				if (tru != null)
					res.add(tru);
			}
			return res;
		} finally {
			walker.close();
		}
	}

	private Map<String
			throws TransportException {
		boolean atomic = transport.isPushAtomic();
		final Map<String
		for (RemoteRefUpdate rru : toPush.values()) {
			final Ref advertisedRef = connection.getRef(rru.getRemoteName());
			ObjectId advertisedOld = null;
			if (advertisedRef != null) {
				advertisedOld = advertisedRef.getObjectId();
			}
			if (advertisedOld == null) {
				advertisedOld = ObjectId.zeroId();
			}

			if (rru.getNewObjectId().equals(advertisedOld)) {
				if (rru.isDelete()) {
					rru.setStatus(Status.NON_EXISTING);
				} else {
					rru.setStatus(Status.UP_TO_DATE);
				}
				continue;
			}

			if (rru.isExpectingOldObjectId()
					&& !rru.getExpectedOldObjectId().equals(advertisedOld)) {
				rru.setStatus(Status.REJECTED_REMOTE_CHANGED);
				if (atomic) {
					return rejectAll();
				}
				continue;
			}
			if (!rru.isExpectingOldObjectId()) {
				rru.setExpectedOldObjectId(advertisedOld);
			}

			if (advertisedOld.equals(ObjectId.zeroId()) || rru.isDelete()) {
				rru.setFastForward(true);
				result.put(rru.getRemoteName()
				continue;
			}

			boolean fastForward = true;
			try {
				RevObject oldRev = walker.parseAny(advertisedOld);
				final RevObject newRev = walker.parseAny(rru.getNewObjectId());
				if (!(oldRev instanceof RevCommit)
						|| !(newRev instanceof RevCommit)
						|| !walker.isMergedInto((RevCommit) oldRev
								(RevCommit) newRev))
					fastForward = false;
			} catch (MissingObjectException x) {
				fastForward = false;
			} catch (Exception x) {
				throw new TransportException(transport.getURI()
						JGitText.get().readingObjectsFromLocalRepositoryFailed
			}
			rru.setFastForward(fastForward);
			if (!fastForward && !rru.isForceUpdate()) {
				rru.setStatus(Status.REJECTED_NONFASTFORWARD);
				if (atomic) {
					return rejectAll();
				}
			} else {
				result.put(rru.getRemoteName()
			}
		}
		return result;
	}

	private Map<String
		for (RemoteRefUpdate rru : toPush.values()) {
			if (rru.getStatus() == Status.NOT_ATTEMPTED) {
				rru.setStatus(RemoteRefUpdate.Status.REJECTED_OTHER_REASON);
				rru.setMessage(JGitText.get().transactionAborted);
			}
		}
		return Collections.emptyMap();
	}

	private void modifyUpdatesForDryRun() {
		for (RemoteRefUpdate rru : toPush.values())
			if (rru.getStatus() == Status.NOT_ATTEMPTED)
				rru.setStatus(Status.OK);
	}

	private void updateTrackingRefs() {
		for (RemoteRefUpdate rru : toPush.values()) {
			final Status status = rru.getStatus();
			if (rru.hasTrackingRefUpdate()
					&& (status == Status.UP_TO_DATE || status == Status.OK)) {
				try {
					rru.updateTrackingRef(walker);
				} catch (IOException e) {
				}
			}
		}
	}

	public List<String> getPushOptions() {
		return pushOptions;
	}
}
