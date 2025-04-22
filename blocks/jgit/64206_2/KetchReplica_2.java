
package org.eclipse.jgit.internal.ketch;

import static org.eclipse.jgit.internal.ketch.KetchLeader.State.CANDIDATE;
import static org.eclipse.jgit.internal.ketch.KetchLeader.State.LEADER;
import static org.eclipse.jgit.internal.ketch.KetchLeader.State.SHUTDOWN;
import static org.eclipse.jgit.internal.ketch.KetchReplica.Type.FOLLOWER;
import static org.eclipse.jgit.internal.ketch.Proposal.State.QUEUED;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.jgit.internal.storage.reftree.RefTree;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class KetchLeader {
	private static final Logger log = LoggerFactory.getLogger(KetchLeader.class);

	static enum State {
		CANDIDATE
	}

	private final KetchSystem system;

	private KetchReplica[] voters;
	private KetchReplica[] followers;
	private LocalReplica self;

	final Lock lock;

	private State state = CANDIDATE;

	private long term;

	private final List<Proposal> queued;

	private RefTree refTree;

	volatile boolean copyOnQueue;

	private LogId head;

	private LogId committed;

	private boolean running;
	private Round runningRound;

	protected KetchLeader(KetchSystem system) {
		this.system = system;
		this.queued = new ArrayList<>(4);
	}

	KetchSystem getSystem() {
		return system;
	}

	public void setReplicas(Collection<KetchReplica> replicas) {
		List<KetchReplica> v = new ArrayList<>(5);
		List<KetchReplica> f = new ArrayList<>(5);
		for (KetchReplica r : replicas) {
			switch (r.getType()) {
			case VOTER:
				v.add(r);
				break;

			case FOLLOWER:
				f.add(r);
				break;

			case NONE:
				continue;
			}
		}

		Collection<Integer> validVoters = validVoterCounts();
		if (!validVoters.contains(Integer.valueOf(v.size()))) {
			throw new IllegalArgumentException(MessageFormat.format(
					KetchText.get().unsupportedVoterCount
					Integer.valueOf(v.size())
					validVoters));
		}

		LocalReplica me = findLeader(v);
		if (me == null) {
			throw new IllegalArgumentException(
					KetchText.get().leaderReplicaRequired);
		}

		lock.lock();
		try {
			voters = v.toArray(new KetchReplica[v.size()]);
			followers = f.toArray(new KetchReplica[f.size()]);
			self = me;
		} finally {
			lock.unlock();
		}
	}

	private static Collection<Integer> validVoterCounts() {
		@SuppressWarnings("boxing")
		Integer[] valid = {
				1
		return Arrays.asList(valid);
	}

	private static LocalReplica findLeader(Collection<KetchReplica> voters) {
		for (KetchReplica r : voters) {
			if (r instanceof LocalReplica) {
				return (LocalReplica) r;
			}
		}
		return null;
	}

	protected abstract Repository openRepository() throws IOException;

	public void executeAsync(Proposal proposal)
			throws InterruptedException
		try {
			lock.lockInterruptibly();
		} catch (InterruptedException e) {
			proposal.abort();
			throw e;
		}
		try {
			if (refTree == null) {
				initialize();
				for (Proposal p : queued) {
					refTree.apply(p.getCommands());
				}
			} else if (copyOnQueue) {
				refTree = refTree.copy();
				copyOnQueue = false;
			}

			if (!refTree.apply(proposal.getCommands())) {
				proposal.abort();
				return;
			}

			queued.add(proposal);
			proposal.notifyState(QUEUED);

			if (!running) {
				scheduleLeader();
			}
		} finally {
			lock.unlock();
		}
	}

	private void initialize() throws IOException {
		try (Repository git = openRepository(); RevWalk rw = new RevWalk(git)) {
			self.initialize(git);

			ObjectId accepted = self.getTxnAccepted();
			if (!ObjectId.zeroId().equals(accepted)) {
				RevCommit c = rw.parseCommit(accepted);
				head = new LogId(accepted
				refTree = RefTree.read(rw.getObjectReader()
			} else {
				head = new LogId(ObjectId.zeroId()
				refTree = RefTree.newEmptyTree();
			}
		}
	}

	private void scheduleLeader() {
		running = true;
		system.getExecutor().execute(new Runnable() {
			@Override
			public void run() {
				runLeader();
			}
		});
	}

	private void runLeader() {
		Round r;
		lock.lock();
		try {
			switch (state) {
			case CANDIDATE:
				r = new ElectionRound(this
				break;

			case LEADER:
				r = newProposalRound();
				break;

			case DEPOSED:
			case SHUTDOWN:
			default:
				log.warn("Leader cannot run {}"
				return;
			}
		} finally {
			lock.unlock();
		}

		try {
			r.start();
		} catch (IOException e) {
			log.error(KetchText.get().leaderFailedStore
			lock.lock();
			try {
				nextRound();
			} finally {
				lock.unlock();
			}
		}
	}

	private ProposalRound newProposalRound() {
		List<Proposal> todo = new ArrayList<>(queued);
		queued.clear();

		ProposalRound r = new ProposalRound(this
		if (todo.size() == 1) {
			copyOnQueue = true;
			r.setTree(refTree);
		} else {
			copyOnQueue = false;
		}
		return r;
	}

	long getTerm() {
		return term;
	}

	LogId getHead() {
		return head;
	}

	LogId getCommitted() {
		return committed;
	}

	boolean isIdle() {
		return !running;
	}

	void acceptAsync(Round round) {
		lock.lock();
		try {
			head = round.acceptedNew;
			runningRound = round;

			for (KetchReplica r : voters) {
				r.acceptAsync(round);
			}
			for (KetchReplica r : followers) {
				r.acceptAsync(round);
			}
		} finally {
			lock.unlock();
		}
	}

	void onReplicaUpdate(KetchReplica replica) {
		if (log.isDebugEnabled()) {
			log.debug("Replica {} finished:\n{}"
					replica.describeForLog()
		}

		if (replica.getType() == FOLLOWER) {
			return;
		} else if (runningRound == null) {
			return;
		}

		assert head.equals(runningRound.acceptedNew);
		int matching = 0;
		for (KetchReplica r : voters) {
			if (r.hasAccepted(head)) {
				matching++;
			}
		}

		int quorum = voters.length / 2 + 1;
		boolean success = matching >= quorum;
		if (!success) {
			return;
		}

		switch (state) {
		case CANDIDATE:
			term = ((ElectionRound) runningRound).getTerm();
			state = LEADER;
			if (log.isDebugEnabled()) {
				log.debug("Won election
			}

		case LEADER:
			committed = head;
			if (log.isDebugEnabled()) {
				log.debug("Committed {}/{} in term {}"
						Long.valueOf(committed.index)
						committed.abbreviate(8).name()
						Long.valueOf(term));
			}
			runningRound.success();
			nextRound();
			commitAsync(replica);
			if (log.isDebugEnabled()) {
				log.debug("Leader state:\n{}"
			}
			break;
		}
	}

	private void commitAsync(KetchReplica caller) {
		LogId c = committed;
		boolean idle = isIdle();
		for (KetchReplica r : voters) {
			if (r != caller && r.hasAccepted(c)) {
				r.commitAsync(c
			}
		}
		for (KetchReplica r : followers) {
			if (r != caller && r.hasAccepted(c)) {
				r.commitAsync(c
			}
		}
	}

	void nextRound() {
		runningRound = null;

		if (queued.isEmpty()) {
			running = false;
		} else {
			scheduleLeader();
		}
	}

	public Snapshot snapshot() {
		lock.lock();
		try {
			Snapshot s = new Snapshot();
			s.state = state;
			s.term = term;
			s.head = head;
			s.committed = committed;
			s.running = running;
			for (KetchReplica r : voters) {
				s.replicas.add(r.snapshot());
			}
			for (KetchReplica r : followers) {
				s.replicas.add(r.snapshot());
			}
			return s;
		} finally {
			lock.unlock();
		}
	}

	public void shutdown() {
		lock.lock();
		try {
			state = SHUTDOWN;
			for (KetchReplica r : voters) {
				r.shutdown();
			}
			for (KetchReplica r : followers) {
				r.shutdown();
			}
		} finally {
			lock.unlock();
		}
	}

	@Override
	public String toString() {
		return snapshot().toString();
	}
}
