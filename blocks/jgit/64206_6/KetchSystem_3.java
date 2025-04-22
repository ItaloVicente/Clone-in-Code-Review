
package org.eclipse.jgit.internal.ketch;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.eclipse.jgit.internal.ketch.KetchReplica.CommitSpeed.BATCHED;
import static org.eclipse.jgit.internal.ketch.KetchReplica.CommitSpeed.FAST;
import static org.eclipse.jgit.internal.ketch.KetchReplica.State.CURRENT;
import static org.eclipse.jgit.internal.ketch.KetchReplica.State.LAGGING;
import static org.eclipse.jgit.internal.ketch.KetchReplica.State.OFFLINE;
import static org.eclipse.jgit.internal.ketch.KetchReplica.State.UNKNOWN;
import static org.eclipse.jgit.lib.Constants.HEAD;
import static org.eclipse.jgit.lib.FileMode.TYPE_GITLINK;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.NOT_ATTEMPTED;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.OK;
import static org.eclipse.jgit.transport.ReceiveCommand.Type.CREATE;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.internal.storage.reftree.RefTree;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.ReceiveCommand;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.SystemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class KetchReplica {
	static final Logger log = LoggerFactory.getLogger(KetchReplica.class);
	private static final byte[] PEEL = { ' '

	public enum Participation {
		FULL

		FOLLOWER_ONLY;
	}

	public enum CommitMethod {
		ALL_REFS

		TXN_COMMITTED;
	}

	public enum CommitSpeed {
		FAST

		BATCHED;
	}

	public enum State {
		UNKNOWN

		LAGGING

		CURRENT

		DIVERGENT

		AHEAD

		OFFLINE;
	}

	private final KetchLeader leader;
	private final String replicaName;
	private final Participation participation;
	private final CommitMethod commitMethod;
	private final CommitSpeed commitSpeed;
	private final long minRetryMillis;
	private final long maxRetryMillis;
	private final Map<ObjectId
	private final Map<String
	private final Map<String
	private final List<ReplicaPushRequest> queued;

	private ObjectId txnAccepted;

	private ObjectId txnCommitted;

	private State state = UNKNOWN;
	private String error;

	private Future<?> retryFuture;
	private long lastRetryMillis;
	private long retryAtMillis;

	protected KetchReplica(KetchLeader leader
		this.leader = leader;
		this.replicaName = name;
		this.participation = cfg.getParticipation();
		this.commitMethod = cfg.getCommitMethod();
		this.commitSpeed = cfg.getCommitSpeed();
		this.minRetryMillis = cfg.getMinRetry(MILLISECONDS);
		this.maxRetryMillis = cfg.getMaxRetry(MILLISECONDS);
		this.staged = new HashMap<>();
		this.running = new HashMap<>();
		this.waiting = new HashMap<>();
		this.queued = new ArrayList<>(4);
	}

	public KetchSystem getSystem() {
		return getLeader().getSystem();
	}

	public KetchLeader getLeader() {
		return leader;
	}

	public String getName() {
		return replicaName;
	}

	protected String describeForLog() {
		return getName();
	}

	public Participation getParticipation() {
		return participation;
	}

	public CommitMethod getCommitMethod() {
		return commitMethod;
	}

	public CommitSpeed getCommitSpeed() {
		return commitSpeed;
	}

	protected void shutdown() {
		Future<?> f = retryFuture;
		if (f != null) {
			retryFuture = null;
			f.cancel(true);
		}
	}

	ReplicaSnapshot snapshot() {
		ReplicaSnapshot s = new ReplicaSnapshot(this);
		s.accepted = txnAccepted;
		s.committed = txnCommitted;
		s.state = state;
		s.error = error;
		s.retryAtMillis = waitingForRetry() ? retryAtMillis : 0;
		return s;
	}

	void initialize(Map<String
		if (txnAccepted == null) {
			txnAccepted = getId(refs.get(getSystem().getTxnAccepted()));
		}
		if (txnCommitted == null) {
			txnCommitted = getId(refs.get(getSystem().getTxnCommitted()));
		}
	}

	ObjectId getTxnAccepted() {
		return txnAccepted;
	}

	boolean hasAccepted(LogIndex id) {
		return equals(txnAccepted
	}

	private static boolean equals(@Nullable ObjectId a
		return a != null && b != null && AnyObjectId.equals(a
	}

	void pushTxnAcceptedAsync(Round round) {
		List<ReceiveCommand> cmds = new ArrayList<>();
		if (commitSpeed == BATCHED) {
			LogIndex committedIndex = leader.getCommitted();
			if (equals(txnAccepted
					&& !equals(txnCommitted
				prepareTxnCommitted(cmds
			}
		}

		if (round.stageCommands != null) {
			for (ReceiveCommand cmd : round.stageCommands) {
				cmds.add(copy(cmd));
			}
		}
		cmds.add(new ReceiveCommand(
				round.acceptedOldIndex
				getSystem().getTxnAccepted()));
		pushAsync(new ReplicaPushRequest(this
	}

	private static ReceiveCommand copy(ReceiveCommand c) {
		return new ReceiveCommand(c.getOldId()
	}

	boolean shouldPushUnbatchedCommit(LogIndex committed
		return (leaderIdle || commitSpeed == FAST) && hasAccepted(committed);
	}

	void pushCommitAsync(LogIndex committed) {
		List<ReceiveCommand> cmds = new ArrayList<>();
		prepareTxnCommitted(cmds
		pushAsync(new ReplicaPushRequest(this
	}

	private void prepareTxnCommitted(List<ReceiveCommand> cmds
			ObjectId committed) {
		removeStaged(cmds
		cmds.add(new ReceiveCommand(
				txnCommitted
				getSystem().getTxnCommitted()));
	}

	private void removeStaged(List<ReceiveCommand> cmds
		List<ReceiveCommand> a = staged.remove(committed);
		if (a != null) {
			delete(cmds
		}
		if (staged.isEmpty() || !(committed instanceof LogIndex)) {
			return;
		}

		LogIndex committedIndex = (LogIndex) committed;
		Iterator<Map.Entry<ObjectId
				.entrySet().iterator();
		while (itr.hasNext()) {
			Map.Entry<ObjectId
			if (e.getKey() instanceof LogIndex) {
				LogIndex stagedIndex = (LogIndex) e.getKey();
				if (stagedIndex.isBefore(committedIndex)) {
					delete(cmds
					itr.remove();
				}
			}
		}
	}

	private static void delete(List<ReceiveCommand> cmds
			List<ReceiveCommand> createCmds) {
		for (ReceiveCommand cmd : createCmds) {
			ObjectId id = cmd.getNewId();
			String name = cmd.getRefName();
			cmds.add(new ReceiveCommand(id
		}
	}

	private void runNextPushRequest() {
		LogIndex committed = leader.getCommitted();
		if (!equals(txnCommitted
				&& shouldPushUnbatchedCommit(committed
			pushCommitAsync(committed);
		}

		if (queued.isEmpty() || !running.isEmpty() || waitingForRetry()) {
			return;
		}

		Map<String
		for (ReplicaPushRequest req : queued) {
			for (ReceiveCommand cmd : req.getCommands()) {
				String name = cmd.getRefName();
				ReceiveCommand old = cmdMap.remove(name);
				if (old != null) {
					cmd = new ReceiveCommand(
							old.getOldId()
							name);
				}
				cmdMap.put(name
			}
		}
		queued.clear();
		waiting.clear();

		List<ReceiveCommand> next = new ArrayList<>(cmdMap.values());
		for (ReceiveCommand cmd : next) {
			running.put(cmd.getRefName()
		}
		startPush(new ReplicaPushRequest(this
	}

	private void pushAsync(ReplicaPushRequest req) {
		if (defer(req)) {
			for (ReceiveCommand cmd : req.getCommands()) {
				waiting.put(cmd.getRefName()
			}
			queued.add(req);
		} else {
			for (ReceiveCommand cmd : req.getCommands()) {
				running.put(cmd.getRefName()
			}
			startPush(req);
		}
	}

	private boolean defer(ReplicaPushRequest req) {
		if (waitingForRetry()) {
			return true;
		}

		for (ReceiveCommand nextCmd : req.getCommands()) {
			ReceiveCommand priorCmd = waiting.get(nextCmd.getRefName());
			if (priorCmd == null) {
				priorCmd = running.get(nextCmd.getRefName());
			}
			if (priorCmd != null) {
				return true;
			}
		}
		return false;
	}

	private boolean waitingForRetry() {
		Future<?> f = retryFuture;
		return f != null && !f.isDone();
	}

	private void retryLater(ReplicaPushRequest req) {
		Collection<ReceiveCommand> cmds = req.getCommands();
		for (ReceiveCommand cmd : cmds) {
			cmd.setResult(NOT_ATTEMPTED
			if (!waiting.containsKey(cmd.getRefName())) {
				waiting.put(cmd.getRefName()
			}
		}
		queued.add(0

		if (!waitingForRetry()) {
			long delay = KetchSystem.delay(
					lastRetryMillis
					minRetryMillis
			if (log.isDebugEnabled()) {
				log.debug("Retrying {} after {} ms"
						describeForLog()
			}
			lastRetryMillis = delay;
			retryAtMillis = SystemReader.getInstance().getCurrentTime() + delay;
			retryFuture = getSystem().getExecutor()
					.schedule(new WeakRetryPush(this)
		}
	}

	static class WeakRetryPush extends WeakReference<KetchReplica>
			implements Callable<Void> {
		WeakRetryPush(KetchReplica r) {
			super(r);
		}

		@Override
		public Void call() throws Exception {
			KetchReplica r = get();
			if (r != null) {
				r.doRetryPush();
			}
			return null;
		}
	}

	private void doRetryPush() {
		leader.lock.lock();
		try {
			retryFuture = null;
			runNextPushRequest();
		} finally {
			leader.lock.unlock();
		}
	}

	protected abstract void startPush(ReplicaPushRequest req);

	void afterPush(@Nullable Repository repo
		ReceiveCommand acceptCmd = null;
		ReceiveCommand commitCmd = null;
		List<ReceiveCommand> stages = null;

		for (ReceiveCommand cmd : req.getCommands()) {
			String name = cmd.getRefName();
			if (name.equals(getSystem().getTxnAccepted())) {
				acceptCmd = cmd;
			} else if (name.equals(getSystem().getTxnCommitted())) {
				commitCmd = cmd;
			} else if (cmd.getResult() == OK && cmd.getType() == CREATE
					&& name.startsWith(getSystem().getTxnStage())) {
				if (stages == null) {
					stages = new ArrayList<>();
				}
				stages.add(cmd);
			}
		}

		State newState = null;
		ObjectId acceptId = readId(req
		if (repo != null && acceptCmd != null && acceptCmd.getResult() != OK
				&& req.getException() == null) {
			try (LagCheck lag = new LagCheck(this
				newState = lag.check(acceptId
				acceptId = lag.getRemoteId();
			}
		}

		leader.lock.lock();
		try {
			for (ReceiveCommand cmd : req.getCommands()) {
				running.remove(cmd.getRefName());
			}

			Throwable err = req.getException();
			if (err != null) {
				state = OFFLINE;
				error = err.toString();
				retryLater(req);
				leader.onReplicaUpdate(this);
				return;
			}

			lastRetryMillis = 0;
			error = null;
			updateView(req

			if (acceptCmd != null && acceptCmd.getResult() == OK) {
				state = hasAccepted(leader.getHead()) ? CURRENT : LAGGING;
				if (stages != null) {
					staged.put(acceptCmd.getNewId()
				}
			} else if (newState != null) {
				state = newState;
			}

			leader.onReplicaUpdate(this);
			runNextPushRequest();
		} finally {
			leader.lock.unlock();
		}
	}

	private void updateView(ReplicaPushRequest req
			ReceiveCommand commitCmd) {
		if (acceptId != null) {
			txnAccepted = acceptId;
		}

		ObjectId committed = readId(req
		if (committed != null) {
			txnCommitted = committed;
		} else if (acceptId != null && txnCommitted == null) {
			Map<String
			if (adv != null) {
				Ref refs = adv.get(getSystem().getTxnCommitted());
				txnCommitted = getId(refs);
			}
		}
	}

	@Nullable
	private static ObjectId readId(ReplicaPushRequest req
			@Nullable ReceiveCommand cmd) {
		if (cmd == null) {
			return null;

		} else if (cmd.getResult() == OK) {
			return cmd.getNewId();
		}

		Map<String
		return refs != null ? getId(refs.get(cmd.getRefName())) : null;
	}

	protected abstract void blockingFetch(Repository repo
			ReplicaFetchRequest req) throws IOException;

	protected Collection<ReceiveCommand> prepareCommit(Repository git
			Map<String
		List<ReceiveCommand> delta = new ArrayList<>();
		Map<String
		try (RevWalk rw = new RevWalk(git);
				TreeWalk tw = new TreeWalk(rw.getObjectReader())) {
			tw.setRecursive(true);
			tw.addTree(rw.parseCommit(committed).getTree());
			while (tw.next()) {
				if (tw.getRawMode(0) != TYPE_GITLINK
						|| tw.isPathSuffix(PEEL
					continue;
				}

				String name = RefTree.refName(tw.getPathString());
				Ref oldRef = remote.remove(name);
				ObjectId oldId = getId(oldRef);
				ObjectId newId = tw.getObjectId(0);
				if (!AnyObjectId.equals(oldId
					delta.add(new ReceiveCommand(oldId
				}
			}
		}

		for (Ref ref : remote.values()) {
			if (canDelete(ref)) {
				delta.add(new ReceiveCommand(
					ref.getObjectId()
					ref.getName()));
			}
		}
		return delta;
	}

	boolean canDelete(Ref ref) {
		String name = ref.getName();
		if (HEAD.equals(name)) {
			return false;
		}
		if (name.startsWith(getSystem().getTxnNamespace())) {
			return false;
		}
		return true;
	}

	@NonNull
	static ObjectId getId(@Nullable Ref ref) {
		if (ref != null) {
			ObjectId id = ref.getObjectId();
			if (id != null) {
				return id;
			}
		}
		return ObjectId.zeroId();
	}
}
