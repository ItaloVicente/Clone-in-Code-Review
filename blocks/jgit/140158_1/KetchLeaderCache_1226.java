
package org.eclipse.jgit.internal.ketch;

import static org.eclipse.jgit.internal.ketch.KetchLeader.State.CANDIDATE;
import static org.eclipse.jgit.internal.ketch.KetchLeader.State.LEADER;
import static org.eclipse.jgit.internal.ketch.KetchLeader.State.SHUTDOWN;
import static org.eclipse.jgit.internal.ketch.KetchReplica.Participation.FOLLOWER_ONLY;
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

	public static enum State {
		CANDIDATE

		LEADER

		DEPOSED

		SHUTDOWN;
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

	volatile boolean roundHoldsReferenceToRefTree;

	private LogIndex headIndex;

	private LogIndex committedIndex;

	private boolean idle;

	private Round runningRound;

	protected KetchLeader(KetchSystem system) {
		this.system = system;
		this.queued = new ArrayList<>(4);
		this.idle = true;
	}

	KetchSystem getSystem() {
		return system;
	}

	public void setReplicas(Collection<KetchReplica> replicas) {
		List<KetchReplica> v = new ArrayList<>(5);
		List<KetchReplica> f = new ArrayList<>(5);
		for (KetchReplica r : replicas) {
			switch (r.getParticipation()) {
			case FULL:
				v.add(r);
				break;

			case FOLLOWER_ONLY:
				f.add(r);
				break;
			}
		}

		Collection<Integer> validVoters = validVoterCounts();
		if (!validVoters.contains(Integer.valueOf(v.size()))) {
			throw new IllegalArgumentException(MessageFormat.format(
					KetchText.get().unsupportedVoterCount
					Integer.valueOf(v.size())
					validVoters));
		}

		LocalReplica me = findLocal(v);
		if (me == null) {
			throw new IllegalArgumentException(
					KetchText.get().localReplicaRequired);
		}

		lock.lock();
		try {
			voters = v.toArray(new KetchReplica[0]);
			followers = f.toArray(new KetchReplica[0]);
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

	private static LocalReplica findLocal(Collection<KetchReplica> voters) {
		for (KetchReplica r : voters) {
			if (r instanceof LocalReplica) {
				return (LocalReplica) r;
			}
		}
		return null;
	}

	protected abstract Repository openRepository() throws IOException;

	public void queueProposal(Proposal proposal)
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
			} else if (roundHoldsReferenceToRefTree) {
				refTree = refTree.copy();
				roundHoldsReferenceToRefTree = false;
			}

			if (!refTree.apply(proposal.getCommands())) {
				proposal.abort();
				return;
			}

			queued.add(proposal);
			proposal.notifyState(QUEUED);

			if (idle) {
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
				headIndex = LogIndex.unknown(accepted);
				refTree = RefTree.read(rw.getObjectReader()
			} else {
				headIndex = LogIndex.unknown(ObjectId.zeroId());
				refTree = RefTree.newEmptyTree();
			}
		}
	}

	private void scheduleLeader() {
		idle = false;
		system.getExecutor().execute(new Runnable() {
			@Override
			public void run() {
				runLeader();
			}
		});
	}

	private void runLeader() {
		Round round;
		lock.lock();
		try {
			switch (state) {
			case CANDIDATE:
				round = new ElectionRound(this
				break;

			case LEADER:
				round = newProposalRound();
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
			round.start();
		} catch (IOException e) {
			log.error(KetchText.get().leaderFailedToStore
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
		roundHoldsReferenceToRefTree = true;
		return new ProposalRound(this
	}

	long getTerm() {
		return term;
	}

	LogIndex getHead() {
		return headIndex;
	}

	LogIndex getCommitted() {
		return committedIndex;
	}

	boolean isIdle() {
		return idle;
	}

	void runAsync(Round round) {
		lock.lock();
		try {
			headIndex = round.acceptedNewIndex;
			runningRound = round;

			for (KetchReplica replica : voters) {
				replica.pushTxnAcceptedAsync(round);
			}
			for (KetchReplica replica : followers) {
				replica.pushTxnAcceptedAsync(round);
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

		if (replica.getParticipation() == FOLLOWER_ONLY) {
			return;
		} else if (runningRound == null) {
			return;
		}

		assert headIndex.equals(runningRound.acceptedNewIndex);
		int matching = 0;
		for (KetchReplica r : voters) {
			if (r.hasAccepted(headIndex)) {
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
			committedIndex = headIndex;
			if (log.isDebugEnabled()) {
				log.debug("Committed {} in term {}"
						committedIndex.describeForLog()
						Long.valueOf(term));
			}
			nextRound();
			commitAsync(replica);
			notifySuccess(runningRound);
			if (log.isDebugEnabled()) {
				log.debug("Leader state:\n{}"
			}
			break;

		default:
			log.debug("Leader ignoring replica while in {}"
			break;
		}
	}

	private void notifySuccess(Round round) {
		lock.unlock();
		try {
			round.success();
		} finally {
			lock.lock();
		}
	}

	private void commitAsync(KetchReplica caller) {
		for (KetchReplica r : voters) {
			if (r == caller) {
				continue;
			}
			if (r.shouldPushUnbatchedCommit(committedIndex
				r.pushCommitAsync(committedIndex);
			}
		}
		for (KetchReplica r : followers) {
			if (r == caller) {
				continue;
			}
			if (r.shouldPushUnbatchedCommit(committedIndex
				r.pushCommitAsync(committedIndex);
			}
		}
	}

	void nextRound() {
		runningRound = null;

		if (queued.isEmpty()) {
			idle = true;
		} else {
			scheduleLeader();
		}
	}

	public LeaderSnapshot snapshot() {
		lock.lock();
		try {
			LeaderSnapshot s = new LeaderSnapshot();
			s.state = state;
			s.term = term;
			s.headIndex = headIndex;
			s.committedIndex = committedIndex;
			s.idle = isIdle();
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
			if (state != SHUTDOWN) {
				state = SHUTDOWN;
				for (KetchReplica r : voters) {
					r.shutdown();
				}
				for (KetchReplica r : followers) {
					r.shutdown();
				}
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
