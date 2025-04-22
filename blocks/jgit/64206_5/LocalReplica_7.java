
package org.eclipse.jgit.internal.ketch;

import static org.eclipse.jgit.internal.ketch.KetchReplica.State.OFFLINE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.lib.ObjectId;

public class LeaderSnapshot {
	final List<ReplicaSnapshot> replicas = new ArrayList<>();
	KetchLeader.State state;
	long term;
	LogIndex headIndex;
	LogIndex committedIndex;
	boolean idle;

	LeaderSnapshot() {
	}

	public Collection<ReplicaSnapshot> getReplicas() {
		return Collections.unmodifiableList(replicas);
	}

	public KetchLeader.State getState() {
		return state;
	}

	public boolean isIdle() {
		return idle;
	}

	public long getTerm() {
		return term;
	}

	@Nullable
	public LogIndex getHead() {
		return headIndex;
	}

	@Nullable
	public LogIndex getCommitted() {
		return committedIndex;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		if (getTerm() > 0) {
		}
		s.append('\n');
		s.append(String.format(
				"%-10s %12s %12s\n"
				"Replica"
		debug(s
		s.append('\n');
		for (ReplicaSnapshot r : getReplicas()) {
			debug(s
			s.append('\n');
		}
		s.append('\n');
		return s.toString();
	}

	private static void debug(StringBuilder b
		KetchReplica replica = s.getReplica();
		debug(b
		b.append(String.format(" %-8s %s"
				replica.getParticipation()
		if (s.getState() == OFFLINE) {
			String err = s.getErrorMessage();
			if (err != null) {
			}
		}
	}

	private static void debug(StringBuilder s
			ObjectId accepted
		s.append(String.format(
				"%-10s %-12s %-12s"
				name
	}

	static String str(ObjectId c) {
		if (c instanceof LogIndex) {
			return ((LogIndex) c).describeForLog();
		} else if (c != null) {
			return c.abbreviate(8).name();
		}
	}
}
