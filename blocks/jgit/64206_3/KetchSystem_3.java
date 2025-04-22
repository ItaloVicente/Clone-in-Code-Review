
package org.eclipse.jgit.internal.ketch;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.eclipse.jgit.internal.ketch.KetchReplica.CommitSpeed.BATCHED;
import static org.eclipse.jgit.internal.ketch.KetchReplica.CommitSpeed.FAST;
import static org.eclipse.jgit.internal.ketch.KetchReplica.State.AHEAD;
import static org.eclipse.jgit.internal.ketch.KetchReplica.State.CURRENT;
import static org.eclipse.jgit.internal.ketch.KetchReplica.State.DIVERGENT;
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
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.storage.reftree.RefTree;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.ReceiveCommand;
import org.eclipse.jgit.treewalk.TreeWalk;
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
	private final String name;
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
		this.name = name;
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
		return name;
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
		ReplicaSnapshot s = new ReplicaSnapshot();
		s.name = name;
		s.type = participation;
		s.txnAccepted = txnAccepted;
		s.txnCommitted = txnCommitted;
		s.state = state;
		s.error = error;
		s.retryAtMillis = waitingForRetry() ? retryAtMillis : 0;
		return s;
	}

	ObjectId getTxnAccepted() {
		return txnAccepted;
	}

	boolean hasAccepted(LogId id) {
		return equals(txnAccepted
	}

	private static boolean equals(@Nullable ObjectId a
		return a != null && b != null && AnyObjectId.equals(a
	}

	void acceptAsync(Round round) {
		List<ReceiveCommand> cmds = new ArrayList<>();
		if (commitSpeed == BATCHED) {
			LogId c = leader.getCommitted();
			if (equals(txnAccepted
				commit(cmds
			}
		}

		if (round.stageCommands != null) {
			for (ReceiveCommand c : round.stageCommands) {
				cmds.add(copy(c));
			}
		}
		cmds.add(new ReceiveCommand(
				round.acceptedOld
				getSystem().getTxnAccepted()));
		pushAsync(new ReplicaPushRequest(this
	}

	private static ReceiveCommand copy(ReceiveCommand c) {
		return new ReceiveCommand(c.getOldId()
	}

	void commitAsync(ObjectId committed
		if (leaderIsIdle || commitSpeed == FAST) {
			List<ReceiveCommand> cmds = new ArrayList<>();
			commit(cmds
			pushAsync(new ReplicaPushRequest(this
		}
	}

	private void commit(List<ReceiveCommand> cmds
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
		if (staged.isEmpty() || !(committed instanceof LogId)) {
			return;
		}

		LogId committedId = (LogId) committed;
		Iterator<Map.Entry<ObjectId

		i = staged.entrySet().iterator();
		while (i.hasNext()) {
			Map.Entry<ObjectId
			if (e.getKey() instanceof LogId) {
				LogId k = (LogId) e.getKey();
				if (k.isBefore(committedId)) {
					delete(cmds
					i.remove();
				}
			}
		}
	}

	private static void delete(List<ReceiveCommand> cmds
			List<ReceiveCommand> createCmds) {
		for (ReceiveCommand c : createCmds) {
			ObjectId id = c.getNewId();
			String n = c.getRefName();
			cmds.add(new ReceiveCommand(id
		}
	}

	private void nextPush() {
		LogId c = leader.getCommitted();
		if (equals(txnAccepted
			commitAsync(c
		}

		if (queued.isEmpty() || !running.isEmpty() || waitingForRetry()) {
			return;
		}

		Map<String
		for (ReplicaPushRequest req : queued) {
			for (ReceiveCommand n : req.getCommands()) {
				String rn = n.getRefName();
				ReceiveCommand o = cmdMap.remove(rn);
				if (o != null) {
					n = new ReceiveCommand(o.getOldId()
				}
				cmdMap.put(rn
			}
		}
		queued.clear();
		waiting.clear();

		List<ReceiveCommand> next = new ArrayList<>(cmdMap.values());
		for (ReceiveCommand r : next) {
			running.put(r.getRefName()
		}
		start(new ReplicaPushRequest(this
	}

	private void pushAsync(ReplicaPushRequest req) {
		if (defer(req)) {
			for (ReceiveCommand c : req.getCommands()) {
				waiting.put(c.getRefName()
			}
			queued.add(req);
		} else {
			for (ReceiveCommand c : req.getCommands()) {
				running.put(c.getRefName()
			}
			start(req);
		}
	}

	private boolean defer(ReplicaPushRequest req) {
		if (waitingForRetry()) {
			return true;
		}

		for (ReceiveCommand n : req.getCommands()) {
			ReceiveCommand o = waiting.get(n.getRefName());
			if (o == null) {
				o = running.get(n.getRefName());
			}
			if (o != null) {
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
		for (ReceiveCommand c : cmds) {
			c.setResult(NOT_ATTEMPTED
			if (!waiting.containsKey(c.getRefName())) {
				waiting.put(c.getRefName()
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
			retryAtMillis = System.currentTimeMillis() + delay;
			retryFuture = getSystem().getExecutor()
					.schedule(new WeakRetry(this)
		}
	}

	static class WeakRetry extends WeakReference<KetchReplica>
			implements Callable<Void> {
		WeakRetry(KetchReplica r) {
			super(r);
		}

		@Override
		public Void call() throws Exception {
			KetchReplica r = get();
			if (r != null) {
				r.doRetry();
			}
			return null;
		}
	}

	private void doRetry() {
		leader.lock.lock();
		try {
			retryFuture = null;
			nextPush();
		} finally {
			leader.lock.unlock();
		}
	}

	protected abstract void start(ReplicaPushRequest req);

	protected void initialize(Map<String
		if (txnAccepted == null) {
			txnAccepted = getId(refs.get(getSystem().getTxnAccepted()));
		}
		if (txnCommitted == null) {
			txnCommitted = getId(refs.get(getSystem().getTxnCommitted()));
		}
	}

	void afterPush(@Nullable Repository repo
		Collection<ReceiveCommand> cmds = req.getCommands();
		ReceiveCommand acceptCmd = null;
		ReceiveCommand commitCmd = null;
		List<ReceiveCommand> stages = null;

		for (ReceiveCommand c : cmds) {
			if (getSystem().getTxnAccepted().equals(c.getRefName())) {
				acceptCmd = c;
			} else if (getSystem().getTxnCommitted().equals(c.getRefName())) {
				commitCmd = c;
			} else if (c.getResult() == OK
					&& c.getType() == CREATE
					&& c.getRefName().startsWith(getSystem().getTxnStage())) {
				if (stages == null) {
					stages = new ArrayList<>();
				}
				stages.add(c);
			}
		}

		if (repo != null && acceptCmd != null && acceptCmd.getResult() != OK) {
			checkLagging(repo
		}

		leader.lock.lock();
		try {
			for (ReceiveCommand c : cmds) {
				running.remove(c.getRefName());
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
			}

			leader.onReplicaUpdate(this);
			nextPush();
		} finally {
			leader.lock.unlock();
		}
	}

	private void checkLagging(Repository repo
			ReceiveCommand acceptCmd
			ReplicaPushRequest req) {
		ObjectId id = readId(req
		if (id == null) {
			return;
		} else if (AnyObjectId.equals(id
			state = LAGGING;
			return;
		}

		try (RevWalk rw = new RevWalk(repo)) {
			rw.setRetainBody(false);
			RevCommit remote;
			try {
				remote = rw.parseCommit(id);
			} catch (MissingObjectException notLocal) {
				state = DIVERGENT;
				return;
			}

			RevCommit head = rw.parseCommit(acceptCmd.getNewId());
			if (rw.isMergedInto(remote
				state = LAGGING;
			} else if (rw.isMergedInto(head
				state = AHEAD;
			} else {
				state = DIVERGENT;
			}
		} catch (IOException err) {
			log.error("Cannot compare " + acceptCmd.getRefName()
		}
	}

	private void updateView(ReplicaPushRequest req
			ReceiveCommand commitCmd) {
		ObjectId accepted = readId(req
		if (accepted != null) {
			txnAccepted = accepted;
		}

		ObjectId committed = readId(req
		if (committed != null) {
			txnCommitted = committed;
		} else if (acceptCmd != null && txnCommitted == null) {
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

	protected Collection<ReceiveCommand> commit(Repository git
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

				String n = RefTree.refName(tw.getPathString());
				Ref oldRef = remote.remove(n);
				ObjectId oldId = getId(oldRef);
				ObjectId newId = tw.getObjectId(0);
				if (!AnyObjectId.equals(oldId
					delta.add(new ReceiveCommand(oldId
				}
			}
		}

		for (Ref r : remote.values()) {
			if (canDelete(r)) {
				delta.add(new ReceiveCommand(
					r.getObjectId()
					ObjectId.zeroId()
					r.getName()));
			}
		}
		return delta;
	}

	boolean canDelete(Ref r) {
		if (HEAD.equals(r.getName())) {
			return false;
		}
		if (r.getName().startsWith(getSystem().getTxnNamespace())) {
			return false;
		}
		return true;
	}

	@NonNull
	static ObjectId getId(@Nullable Ref r) {
		if (r != null) {
			ObjectId id = r.getObjectId();
			if (id != null) {
				return id;
			}
		}
		return ObjectId.zeroId();
	}
}
