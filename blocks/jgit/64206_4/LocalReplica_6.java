
package org.eclipse.jgit.internal.ketch;

import static org.eclipse.jgit.internal.ketch.KetchReplica.State.OFFLINE;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.lib.ObjectId;

public class LeaderSnapshot {
	final List<ReplicaSnapshot> replicas = new ArrayList<>();
	KetchLeader.State state;
	long term;
	LogIndex head;
	LogIndex committed;
	boolean running;

	LeaderSnapshot() {
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		if (term > 0) {
		}
		s.append('\n');
		s.append(String.format(
				"%-10s %12s %12s\n"
				"Replica"
		debug(s
		s.append('\n');
		for (ReplicaSnapshot r : replicas) {
			debug(s
			s.append('\n');
		}
		s.append('\n');
		return s.toString();
	}

	private static void debug(StringBuilder s
		debug(s
		s.append(String.format(" %-8s %s"
		if (r.state == OFFLINE) {
			String e = r.error;
			if (e != null) {
			}
		}
	}

	private static void debug(StringBuilder s
			ObjectId accepted
		s.append(String.format(
				"%-10s %-12s %-12s"
				name
	}

	private static String str(ObjectId c) {
		if (c instanceof LogIndex) {
			return ((LogIndex) c).describeForLog();
		} else if (c != null) {
			return c.abbreviate(8).name();
		}
	}
}
