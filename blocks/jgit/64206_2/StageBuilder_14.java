
package org.eclipse.jgit.internal.ketch;

import static org.eclipse.jgit.internal.ketch.KetchReplica.State.OFFLINE;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.lib.ObjectId;

public class Snapshot {
	final List<Replica> replicas = new ArrayList<>();
	KetchLeader.State state;
	long term;
	LogId head;
	LogId committed;
	boolean running;

	Snapshot() {
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
		for (Replica r : replicas) {
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
		if (c instanceof LogId) {
			return String.format("%5d/%s"
					Long.valueOf(((LogId) c).index)
		} else if (c != null) {
			return c.abbreviate(8).name();
		}
	}

	public static class Replica {
		String name;
		KetchReplica.Type type;
		ObjectId txnAccepted;
		ObjectId txnCommitted;
		KetchReplica.State state;
		String error;
		long retryAtMillis;

		Replica() {
		}
	}
}
