
package org.eclipse.jgit.transport;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.NOT_ATTEMPTED;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.OK;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.REJECTED_NONFASTFORWARD;
import static org.eclipse.jgit.transport.ReceiveCommand.Type.UPDATE_NONFASTFORWARD;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.NotSupportedException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.file.LockFile;
import org.eclipse.jgit.internal.storage.file.PackLock;
import org.eclipse.jgit.lib.BatchRefUpdate;
import org.eclipse.jgit.lib.BatchingProgressMonitor;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.revwalk.ObjectWalk;
import org.eclipse.jgit.revwalk.RevWalk;

class FetchProcess {
	private final Transport transport;

	private final Collection<RefSpec> toFetch;

	private final HashMap<ObjectId

	private final HashSet<ObjectId> have = new HashSet<>();

	private final ArrayList<TrackingRefUpdate> localUpdates = new ArrayList<>();

	private final ArrayList<FetchHeadRecord> fetchHeadUpdates = new ArrayList<>();

	private final ArrayList<PackLock> packLocks = new ArrayList<>();

	private FetchConnection conn;

	private Map<String

	FetchProcess(Transport t
		transport = t;
		toFetch = f;
	}

	void execute(ProgressMonitor monitor
			throws NotSupportedException
		askFor.clear();
		localUpdates.clear();
		fetchHeadUpdates.clear();
		packLocks.clear();
		localRefs = null;

		try {
			executeImp(monitor
		} finally {
			try {
			for (PackLock lock : packLocks)
				lock.unlock();
			} catch (IOException e) {
				throw new TransportException(e.getMessage()
			}
		}
	}

	private void executeImp(final ProgressMonitor monitor
			final FetchResult result) throws NotSupportedException
			TransportException {
		conn = transport.openFetch();
		try {
			result.setAdvertisedRefs(transport.getURI()
			result.peerUserAgent = conn.getPeerUserAgent();
			final Set<Ref> matched = new HashSet<>();
			for (RefSpec spec : toFetch) {
				if (spec.getSource() == null)
					throw new TransportException(MessageFormat.format(
							JGitText.get().sourceRefNotSpecifiedForRefspec

				if (spec.isWildcard())
					expandWildcard(spec
				else
					expandSingle(spec
			}

			Collection<Ref> additionalTags = Collections.<Ref> emptyList();
			final TagOpt tagopt = transport.getTagOpt();
			if (tagopt == TagOpt.AUTO_FOLLOW)
				additionalTags = expandAutoFollowTags();
			else if (tagopt == TagOpt.FETCH_TAGS)
				expandFetchTags();

			final boolean includedTags;
			if (!askFor.isEmpty() && !askForIsComplete()) {
				fetchObjects(monitor);
				includedTags = conn.didFetchIncludeTags();

				closeConnection(result);
			} else {
				includedTags = false;
			}

			if (tagopt == TagOpt.AUTO_FOLLOW && !additionalTags.isEmpty()) {
				have.addAll(askFor.keySet());
				askFor.clear();
				for (Ref r : additionalTags) {
					ObjectId id = r.getPeeledObjectId();
					if (id == null)
						id = r.getObjectId();
					if (localHasObject(id))
						wantTag(r);
				}

				if (!askFor.isEmpty() && (!includedTags || !askForIsComplete())) {
					reopenConnection();
					if (!askFor.isEmpty())
						fetchObjects(monitor);
				}
			}
		} finally {
			closeConnection(result);
		}

		BatchRefUpdate batch = transport.local.getRefDatabase()
				.newBatchUpdate()
				.setAllowNonFastForwards(true)
				.setRefLogMessage("fetch"
		try (RevWalk walk = new RevWalk(transport.local)) {
			walk.setRetainBody(false);
			if (monitor instanceof BatchingProgressMonitor) {
				((BatchingProgressMonitor) monitor).setDelayStart(
						250
			}
			if (transport.isRemoveDeletedRefs()) {
				deleteStaleTrackingRefs(result
			}
			addUpdateBatchCommands(result
			for (ReceiveCommand cmd : batch.getCommands()) {
				cmd.updateType(walk);
				if (cmd.getType() == UPDATE_NONFASTFORWARD
						&& cmd instanceof TrackingRefUpdate.Command
						&& !((TrackingRefUpdate.Command) cmd).canForceUpdate())
					cmd.setResult(REJECTED_NONFASTFORWARD);
			}
			if (transport.isDryRun()) {
				for (ReceiveCommand cmd : batch.getCommands()) {
					if (cmd.getResult() == NOT_ATTEMPTED)
						cmd.setResult(OK);
				}
			} else {
				batch.execute(walk
			}
		} catch (TransportException e) {
			throw e;
		} catch (IOException err) {
			throw new TransportException(MessageFormat.format(
					JGitText.get().failureUpdatingTrackingRef
					getFirstFailedRefName(batch)
		}

		if (!fetchHeadUpdates.isEmpty()) {
			try {
				updateFETCH_HEAD(result);
			} catch (IOException err) {
				throw new TransportException(MessageFormat.format(
						JGitText.get().failureUpdatingFETCH_HEAD
			}
		}
	}

	private void addUpdateBatchCommands(FetchResult result
			BatchRefUpdate batch) throws TransportException {
		Map<String
		for (TrackingRefUpdate u : localUpdates) {
			ObjectId existing = refs.get(u.getLocalName());
			if (existing == null) {
				refs.put(u.getLocalName()
				result.add(u);
				batch.addCommand(u.asReceiveCommand());
			} else if (!existing.equals(u.getNewObjectId())) {
				throw new TransportException(MessageFormat
						.format(JGitText.get().duplicateRef
			}
		}
	}

	private void fetchObjects(ProgressMonitor monitor)
			throws TransportException {
		try {
			conn.fetch(monitor
		} finally {
			packLocks.addAll(conn.getPackLocks());
		}
		if (transport.isCheckFetchedObjects()
				&& !conn.didFetchTestConnectivity() && !askForIsComplete())
			throw new TransportException(transport.getURI()
					JGitText.get().peerDidNotSupplyACompleteObjectGraph);
	}

	private void closeConnection(FetchResult result) {
		if (conn != null) {
			conn.close();
			result.addMessages(conn.getMessages());
			conn = null;
		}
	}

	private void reopenConnection() throws NotSupportedException
			TransportException {
		if (conn != null)
			return;

		conn = transport.openFetch();

		final HashMap<ObjectId
		for (Ref r : conn.getRefs())
			avail.put(r.getObjectId()

		final Collection<Ref> wants = new ArrayList<>(askFor.values());
		askFor.clear();
		for (Ref want : wants) {
			final Ref newRef = avail.get(want.getObjectId());
			if (newRef != null) {
				askFor.put(newRef.getObjectId()
			} else {
				removeFetchHeadRecord(want.getObjectId());
				removeTrackingRefUpdate(want.getObjectId());
			}
		}
	}

	private void removeTrackingRefUpdate(ObjectId want) {
		final Iterator<TrackingRefUpdate> i = localUpdates.iterator();
		while (i.hasNext()) {
			final TrackingRefUpdate u = i.next();
			if (u.getNewObjectId().equals(want))
				i.remove();
		}
	}

	private void removeFetchHeadRecord(ObjectId want) {
		final Iterator<FetchHeadRecord> i = fetchHeadUpdates.iterator();
		while (i.hasNext()) {
			final FetchHeadRecord fh = i.next();
			if (fh.newValue.equals(want))
				i.remove();
		}
	}

	private void updateFETCH_HEAD(FetchResult result) throws IOException {
		File meta = transport.local.getDirectory();
		if (meta == null)
			return;
		final LockFile lock = new LockFile(new File(meta
		try {
			if (lock.lock()) {
				try (Writer w = new OutputStreamWriter(
						lock.getOutputStream()
					for (FetchHeadRecord h : fetchHeadUpdates) {
						h.write(w);
						result.add(h);
					}
				}
				lock.commit();
			}
		} finally {
			lock.unlock();
		}
	}

	private boolean askForIsComplete() throws TransportException {
		try {
			try (ObjectWalk ow = new ObjectWalk(transport.local)) {
				for (ObjectId want : askFor.keySet())
					ow.markStart(ow.parseAny(want));
				for (Ref ref : localRefs().values())
					ow.markUninteresting(ow.parseAny(ref.getObjectId()));
				ow.checkConnectivity();
			}
			return true;
		} catch (MissingObjectException e) {
			return false;
		} catch (IOException e) {
			throw new TransportException(JGitText.get().unableToCheckConnectivity
		}
	}

	private void expandWildcard(RefSpec spec
			throws TransportException {
		for (Ref src : conn.getRefs()) {
			if (spec.matchSource(src) && matched.add(src))
				want(src
		}
	}

	private void expandSingle(RefSpec spec
			throws TransportException {
		String want = spec.getSource();
		if (ObjectId.isId(want)) {
			want(ObjectId.fromString(want));
			return;
		}

		Ref src = conn.getRef(want);
		if (src == null) {
			throw new TransportException(MessageFormat.format(JGitText.get().remoteDoesNotHaveSpec
		}
		if (matched.add(src)) {
			want(src
		}
	}

	private boolean localHasObject(ObjectId id) throws TransportException {
		try {
			return transport.local.getObjectDatabase().has(id);
		} catch (IOException err) {
			throw new TransportException(
					MessageFormat.format(
							JGitText.get().readingObjectsFromLocalRepositoryFailed
							err.getMessage())
					err);
		}
	}

	private Collection<Ref> expandAutoFollowTags() throws TransportException {
		final Collection<Ref> additionalTags = new ArrayList<>();
		final Map<String
		for (Ref r : conn.getRefs()) {
			if (!isTag(r))
				continue;

			Ref local = haveRefs.get(r.getName());
			if (local != null)
				continue;

			ObjectId obj = r.getPeeledObjectId();
			if (obj == null)
				obj = r.getObjectId();

			if (askFor.containsKey(obj) || localHasObject(obj))
				wantTag(r);
			else
				additionalTags.add(r);
		}
		return additionalTags;
	}

	private void expandFetchTags() throws TransportException {
		final Map<String
		for (Ref r : conn.getRefs()) {
			if (!isTag(r)) {
				continue;
			}
			ObjectId id = r.getObjectId();
			if (id == null) {
				continue;
			}
			final Ref local = haveRefs.get(r.getName());
			if (local == null || !id.equals(local.getObjectId())) {
				wantTag(r);
			}
		}
	}

	private void wantTag(Ref r) throws TransportException {
		want(r
				.setDestination(r.getName()).setForceUpdate(true));
	}

	private void want(Ref src
			throws TransportException {
		final ObjectId newId = src.getObjectId();
		if (newId == null) {
			throw new NullPointerException(MessageFormat.format(
					JGitText.get().transportProvidedRefWithNoObjectId
					src.getName()));
		}
		if (spec.getDestination() != null) {
			final TrackingRefUpdate tru = createUpdate(spec
			if (newId.equals(tru.getOldObjectId()))
				return;
			localUpdates.add(tru);
		}

		askFor.put(newId

		final FetchHeadRecord fhr = new FetchHeadRecord();
		fhr.newValue = newId;
		fhr.notForMerge = spec.getDestination() != null;
		fhr.sourceName = src.getName();
		fhr.sourceURI = transport.getURI();
		fetchHeadUpdates.add(fhr);
	}

	private void want(ObjectId id) {
		askFor.put(id
				new ObjectIdRef.Unpeeled(Ref.Storage.NETWORK
	}

	private TrackingRefUpdate createUpdate(RefSpec spec
			throws TransportException {
		Ref ref = localRefs().get(spec.getDestination());
		ObjectId oldId = ref != null && ref.getObjectId() != null
				? ref.getObjectId()
				: ObjectId.zeroId();
		return new TrackingRefUpdate(
				spec.isForceUpdate()
				spec.getSource()
				spec.getDestination()
				oldId
				newId);
	}

	private Map<String
		if (localRefs == null) {
			try {
				localRefs = transport.local.getRefDatabase()
						.getRefs(RefDatabase.ALL);
			} catch (IOException err) {
				throw new TransportException(JGitText.get().cannotListRefs
			}
		}
		return localRefs;
	}

	private void deleteStaleTrackingRefs(FetchResult result
			BatchRefUpdate batch) throws IOException {
		Set<Ref> processed = new HashSet<>();
		for (Ref ref : localRefs().values()) {
			if (ref.isSymbolic()) {
				continue;
			}
			String refname = ref.getName();
			for (RefSpec spec : toFetch) {
				if (spec.matchDestination(refname)) {
					RefSpec s = spec.expandFromDestination(refname);
					if (result.getAdvertisedRef(s.getSource()) == null
							&& processed.add(ref)) {
						deleteTrackingRef(result
					}
				}
			}
		}
	}

	private void deleteTrackingRef(final FetchResult result
			final BatchRefUpdate batch
		if (localRef.getObjectId() == null)
			return;
		TrackingRefUpdate update = new TrackingRefUpdate(
				true
				spec.getSource()
				localRef.getName()
				localRef.getObjectId()
				ObjectId.zeroId());
		result.add(update);
		batch.addCommand(update.asReceiveCommand());
	}

	private static boolean isTag(Ref r) {
		return isTag(r.getName());
	}

	private static boolean isTag(String name) {
		return name.startsWith(Constants.R_TAGS);
	}

	private static String getFirstFailedRefName(BatchRefUpdate batch) {
		for (ReceiveCommand cmd : batch.getCommands()) {
			if (cmd.getResult() != ReceiveCommand.Result.OK)
				return cmd.getRefName();
		}
	}
}
