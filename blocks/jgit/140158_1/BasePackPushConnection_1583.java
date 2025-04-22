
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

import org.eclipse.jgit.errors.PackProtocolException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.file.PackLock;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevCommitList;
import org.eclipse.jgit.revwalk.RevFlag;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevSort;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.CommitTimeRevFilter;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.transport.GitProtocolConstants.MultiAck;
import org.eclipse.jgit.transport.PacketLineIn.AckNackResult;
import org.eclipse.jgit.util.TemporaryBuffer;

public abstract class BasePackFetchConnection extends BasePackConnection
		implements FetchConnection {
	private static final int MAX_HAVES = 256;

	protected static final int MIN_CLIENT_BUFFER = 2 * 32 * 46 + 8;

	public static final String OPTION_INCLUDE_TAG = GitProtocolConstants.OPTION_INCLUDE_TAG;

	public static final String OPTION_MULTI_ACK = GitProtocolConstants.OPTION_MULTI_ACK;

	public static final String OPTION_MULTI_ACK_DETAILED = GitProtocolConstants.OPTION_MULTI_ACK_DETAILED;

	public static final String OPTION_THIN_PACK = GitProtocolConstants.OPTION_THIN_PACK;

	public static final String OPTION_SIDE_BAND = GitProtocolConstants.OPTION_SIDE_BAND;

	public static final String OPTION_SIDE_BAND_64K = GitProtocolConstants.OPTION_SIDE_BAND_64K;

	public static final String OPTION_OFS_DELTA = GitProtocolConstants.OPTION_OFS_DELTA;

	public static final String OPTION_SHALLOW = GitProtocolConstants.OPTION_SHALLOW;

	public static final String OPTION_NO_PROGRESS = GitProtocolConstants.OPTION_NO_PROGRESS;

	public static final String OPTION_NO_DONE = GitProtocolConstants.OPTION_NO_DONE;

	public static final String OPTION_ALLOW_TIP_SHA1_IN_WANT = GitProtocolConstants.OPTION_ALLOW_TIP_SHA1_IN_WANT;

	public static final String OPTION_ALLOW_REACHABLE_SHA1_IN_WANT = GitProtocolConstants.OPTION_ALLOW_REACHABLE_SHA1_IN_WANT;

	public static final String OPTION_FILTER = GitProtocolConstants.OPTION_FILTER;

	private final RevWalk walk;

	private RevCommitList<RevCommit> reachableCommits;

	final RevFlag REACHABLE;

	final RevFlag COMMON;

	private final RevFlag STATE;

	final RevFlag ADVERTISED;

	private MultiAck multiAck = MultiAck.OFF;

	private boolean thinPack;

	private boolean sideband;

	private boolean includeTags;

	private boolean allowOfsDelta;

	private boolean noDone;

	private boolean noProgress;

	private String lockMessage;

	private PackLock packLock;

	private int maxHaves;

	private TemporaryBuffer.Heap state;

	private PacketLineOut pckState;

	private final FilterSpec filterSpec;

	public BasePackFetchConnection(PackTransport packTransport) {
		super(packTransport);

		if (local != null) {
			final FetchConfig cfg = getFetchConfig();
			allowOfsDelta = cfg.allowOfsDelta;
			maxHaves = cfg.maxHaves;
		} else {
			allowOfsDelta = true;
			maxHaves = Integer.MAX_VALUE;
		}

		includeTags = transport.getTagOpt() != TagOpt.NO_TAGS;
		thinPack = transport.isFetchThin();
		filterSpec = transport.getFilterSpec();

		if (local != null) {
			walk = new RevWalk(local);
			walk.setRetainBody(false);
			reachableCommits = new RevCommitList<>();

			walk.carry(COMMON);
			walk.carry(REACHABLE);
			walk.carry(ADVERTISED);
		} else {
			walk = null;
			REACHABLE = null;
			COMMON = null;
			STATE = null;
			ADVERTISED = null;
		}
	}

	static class FetchConfig {
		final boolean allowOfsDelta;

		final int maxHaves;

		FetchConfig(Config c) {
			allowOfsDelta = c.getBoolean("repack"
			maxHaves = c.getInt("fetch"
		}

		FetchConfig(boolean allowOfsDelta
			this.allowOfsDelta = allowOfsDelta;
			this.maxHaves = maxHaves;
		}
	}

	@Override
	public final void fetch(final ProgressMonitor monitor
			final Collection<Ref> want
			throws TransportException {
		fetch(monitor
	}

	@Override
	public final void fetch(final ProgressMonitor monitor
			final Collection<Ref> want
			OutputStream outputStream) throws TransportException {
		markStartedOperation();
		doFetch(monitor
	}

	@Override
	public boolean didFetchIncludeTags() {
		return false;
	}

	@Override
	public boolean didFetchTestConnectivity() {
		return false;
	}

	@Override
	public void setPackLockMessage(String message) {
		lockMessage = message;
	}

	@Override
	public Collection<PackLock> getPackLocks() {
		if (packLock != null)
			return Collections.singleton(packLock);
		return Collections.<PackLock> emptyList();
	}

	protected void doFetch(final ProgressMonitor monitor
			final Collection<Ref> want
			OutputStream outputStream) throws TransportException {
		try {
			noProgress = monitor == NullProgressMonitor.INSTANCE;

			markRefsAdvertised();
			markReachable(have

			if (statelessRPC) {
				state = new TemporaryBuffer.Heap(Integer.MAX_VALUE);
				pckState = new PacketLineOut(state);
			}

			if (sendWants(want)) {
				negotiate(monitor);

				walk.dispose();
				reachableCommits = null;
				state = null;
				pckState = null;

				receivePack(monitor
			}
		} catch (CancelledException ce) {
			close();
		} catch (IOException | RuntimeException err) {
			close();
			throw new TransportException(err.getMessage()
		}
	}

	@Override
	public void close() {
		if (walk != null)
			walk.close();
		super.close();
	}

	FetchConfig getFetchConfig() {
		return local.getConfig().get(FetchConfig::new);
	}

	private int maxTimeWanted(Collection<Ref> wants) {
		int maxTime = 0;
		for (Ref r : wants) {
			try {
				final RevObject obj = walk.parseAny(r.getObjectId());
				if (obj instanceof RevCommit) {
					final int cTime = ((RevCommit) obj).getCommitTime();
					if (maxTime < cTime)
						maxTime = cTime;
				}
			} catch (IOException error) {
			}
		}
		return maxTime;
	}

	private void markReachable(Set<ObjectId> have
			throws IOException {
		for (Ref r : local.getRefDatabase().getRefs()) {
			ObjectId id = r.getPeeledObjectId();
			if (id == null)
				id = r.getObjectId();
			if (id == null)
				continue;
			parseReachable(id);
		}

		for (ObjectId id : local.getAdditionalHaves())
			parseReachable(id);

		for (ObjectId id : have)
			parseReachable(id);

		if (maxTime > 0) {
			final Date maxWhen = new Date(maxTime * 1000L);
			walk.sort(RevSort.COMMIT_TIME_DESC);
			walk.markStart(reachableCommits);
			walk.setRevFilter(CommitTimeRevFilter.after(maxWhen));
			for (;;) {
				final RevCommit c = walk.next();
				if (c == null)
					break;
				if (c.has(ADVERTISED) && !c.has(COMMON)) {
					c.add(COMMON);
					c.carry(COMMON);
					reachableCommits.add(c);
				}
			}
		}
	}

	private void parseReachable(ObjectId id) {
		try {
			RevCommit o = walk.parseCommit(id);
			if (!o.has(REACHABLE)) {
				o.add(REACHABLE);
				reachableCommits.add(o);
			}
		} catch (IOException readError) {
		}
	}

	private boolean sendWants(Collection<Ref> want) throws IOException {
		final PacketLineOut p = statelessRPC ? pckState : pckOut;
		boolean first = true;
		for (Ref r : want) {
			ObjectId objectId = r.getObjectId();
			if (objectId == null) {
				continue;
			}
			try {
				if (walk.parseAny(objectId).has(REACHABLE)) {
					continue;
				}
			} catch (IOException err) {
			}

			final StringBuilder line = new StringBuilder(46);
			line.append(objectId.name());
			if (first) {
				line.append(enableCapabilities());
				first = false;
			}
			line.append('\n');
			p.writeString(line.toString());
		}
		if (first) {
			return false;
		}
		if (!filterSpec.isNoOp()) {
			p.writeString(filterSpec.filterLine());
		}
		p.end();
		outNeedsEnd = false;
		return true;
	}

	private String enableCapabilities() throws TransportException {
		final StringBuilder line = new StringBuilder();
		if (noProgress)
			wantCapability(line
		if (includeTags)
			includeTags = wantCapability(line
		if (allowOfsDelta)
			wantCapability(line

		if (wantCapability(line
			multiAck = MultiAck.DETAILED;
			if (statelessRPC)
				noDone = wantCapability(line
		} else if (wantCapability(line
			multiAck = MultiAck.CONTINUE;
		else
			multiAck = MultiAck.OFF;

		if (thinPack)
			thinPack = wantCapability(line
		if (wantCapability(line
			sideband = true;
		else if (wantCapability(line
			sideband = true;

		if (statelessRPC && multiAck != MultiAck.DETAILED) {
			throw new PackProtocolException(uri
					JGitText.get().statelessRPCRequiresOptionToBeEnabled
					OPTION_MULTI_ACK_DETAILED));
		}

		if (!filterSpec.isNoOp() && !wantCapability(line
			throw new PackProtocolException(uri
					JGitText.get().filterRequiresCapability);
		}

		addUserAgentCapability(line);
		return line.toString();
	}

	private void negotiate(ProgressMonitor monitor) throws IOException
			CancelledException {
		final MutableObjectId ackId = new MutableObjectId();
		int resultsPending = 0;
		int havesSent = 0;
		int havesSinceLastContinue = 0;
		boolean receivedContinue = false;
		boolean receivedAck = false;
		boolean receivedReady = false;

		if (statelessRPC) {
			state.writeTo(out
		}

		negotiateBegin();
		SEND_HAVES: for (;;) {
			final RevCommit c = walk.next();
			if (c == null) {
				break SEND_HAVES;
			}

			ObjectId o = c.getId();
			havesSent++;
			havesSinceLastContinue++;

			if ((31 & havesSent) != 0) {
				continue;
			}

			if (monitor.isCancelled()) {
				throw new CancelledException();
			}

			pckOut.end();

			if (havesSent == 32 && !statelessRPC) {
				continue;
			}

			READ_RESULT: for (;;) {
				final AckNackResult anr = pckIn.readACK(ackId);
				switch (anr) {
				case NAK:
					resultsPending--;
					break READ_RESULT;

				case ACK:
					multiAck = MultiAck.OFF;
					resultsPending = 0;
					receivedAck = true;
					if (statelessRPC) {
						state.writeTo(out
					}
					break SEND_HAVES;

				case ACK_CONTINUE:
				case ACK_COMMON:
				case ACK_READY:
					markCommon(walk.parseAny(ackId)
					receivedAck = true;
					receivedContinue = true;
					havesSinceLastContinue = 0;
					if (anr == AckNackResult.ACK_READY) {
						receivedReady = true;
					}
					break;
				}

				if (monitor.isCancelled()) {
					throw new CancelledException();
				}
			}

			if (noDone & receivedReady) {
				break SEND_HAVES;
			}
			if (statelessRPC) {
				state.writeTo(out
			}

			if (receivedContinue && havesSinceLastContinue > MAX_HAVES
					|| havesSent >= maxHaves) {
				break SEND_HAVES;
			}
		}

		if (monitor.isCancelled()) {
			throw new CancelledException();
		}

		if (!receivedReady || !noDone) {
			pckOut.flush();
		}

		if (!receivedAck) {
			multiAck = MultiAck.OFF;
			resultsPending++;
		}

		READ_RESULT: while (resultsPending > 0 || multiAck != MultiAck.OFF) {
			final AckNackResult anr = pckIn.readACK(ackId);
			resultsPending--;
			switch (anr) {
			case NAK:
				break;

			case ACK:
				break READ_RESULT;

			case ACK_CONTINUE:
			case ACK_COMMON:
			case ACK_READY:
				multiAck = MultiAck.CONTINUE;
				break;
			}

			if (monitor.isCancelled()) {
				throw new CancelledException();
			}
		}
	}

	private void negotiateBegin() throws IOException {
		walk.resetRetain(REACHABLE
		walk.markStart(reachableCommits);
		walk.sort(RevSort.COMMIT_TIME_DESC);
		walk.setRevFilter(new RevFilter() {
			@Override
			public RevFilter clone() {
				return this;
			}

			@Override
			public boolean include(RevWalk walker
				final boolean remoteKnowsIsCommon = c.has(COMMON);
				if (c.has(ADVERTISED)) {
					c.add(COMMON);
				}
				return !remoteKnowsIsCommon;
			}

			@Override
			public boolean requiresCommitBody() {
				return false;
			}
		});
	}

	private void markRefsAdvertised() {
		for (Ref r : getRefs()) {
			markAdvertised(r.getObjectId());
			if (r.getPeeledObjectId() != null)
				markAdvertised(r.getPeeledObjectId());
		}
	}

	private void markAdvertised(AnyObjectId id) {
		try {
			walk.parseAny(id).add(ADVERTISED);
		} catch (IOException readError) {
		}
	}

	private void markCommon(RevObject obj
			throws IOException {
		if (statelessRPC && anr == AckNackResult.ACK_COMMON && !obj.has(STATE)) {
			StringBuilder s;

			s = new StringBuilder(6 + Constants.OBJECT_ID_STRING_LENGTH);
			s.append(obj.name());
			s.append('\n');
			pckState.writeString(s.toString());
			obj.add(STATE);
		}
		obj.add(COMMON);
		if (obj instanceof RevCommit)
			((RevCommit) obj).carry(COMMON);
	}

	private void receivePack(final ProgressMonitor monitor
			OutputStream outputStream) throws IOException {
		onReceivePack();
		InputStream input = in;
		if (sideband)
			input = new SideBandInputStream(input
					outputStream);

		try (ObjectInserter ins = local.newObjectInserter()) {
			PackParser parser = ins.newPackParser(input);
			parser.setAllowThin(thinPack);
			parser.setObjectChecker(transport.getObjectChecker());
			parser.setLockMessage(lockMessage);
			packLock = parser.parse(monitor);
			ins.flush();
		}
	}

	protected void onReceivePack() {
	}

	private static class CancelledException extends Exception {
		private static final long serialVersionUID = 1L;
	}
}
