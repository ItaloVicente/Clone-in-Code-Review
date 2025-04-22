
package org.eclipse.jgit.internal.ketch;

import java.util.Collection;
import java.util.Map;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.ReceiveCommand;

public class ReplicaPushRequest {
	private final KetchReplica replica;
	private final Collection<ReceiveCommand> commands;
	private Map<String
	private Throwable exception;
	private boolean notified;

	public ReplicaPushRequest(KetchReplica replica
			Collection<ReceiveCommand> commands) {
		this.replica = replica;
		this.commands = commands;
	}

	public Collection<ReceiveCommand> getCommands() {
		return commands;
	}

	@Nullable
	public Map<String
		return refs;
	}

	public void setRefs(Map<String
		this.refs = refs;
	}

	@Nullable
	public Throwable getException() {
		return exception;
	}

	public void setException(@Nullable Repository repo
		if (KetchReplica.log.isErrorEnabled()) {
			KetchReplica.log.error(describe("failed")
		}
		if (!notified) {
			notified = true;
			exception = err;
			replica.afterPush(repo
		}
	}

	public void done(Repository repo) {
		if (KetchReplica.log.isDebugEnabled()) {
		}
		if (!notified) {
			notified = true;
			replica.afterPush(repo
		}
	}

	private String describe(String heading) {
		StringBuilder b = new StringBuilder();
		b.append(replica.describeForLog());
		for (ReceiveCommand cmd : commands) {
			b.append(String.format(
					"  %-12s %-12s %s %s"
					LeaderSnapshot.str(cmd.getOldId())
					LeaderSnapshot.str(cmd.getNewId())
					cmd.getRefName()
					cmd.getResult()));
			if (cmd.getMessage() != null) {
				b.append(' ').append(cmd.getMessage());
			}
			b.append('\n');
		}
		return b.toString();
	}
}
