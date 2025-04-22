
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
			StringBuilder m = new StringBuilder();
			m.append(replica.describeForLog());
			for (ReceiveCommand cmd : commands) {
				m.append(cmd.getOldId().abbreviate(8).name());
				m.append(' ');
				m.append(cmd.getNewId().abbreviate(8).name());
				m.append(' ');
				m.append(cmd.getRefName());
				m.append('\n');
			}
			KetchReplica.log.error(m.toString()
		}

		exception = err;
		replica.afterPush(repo
	}

	public void done(Repository repo) {
		if (KetchReplica.log.isDebugEnabled()) {
			StringBuilder m = new StringBuilder();
			m.append(replica.describeForLog());
			for (ReceiveCommand cmd : commands) {
				m.append(cmd.getOldId().abbreviate(8).name());
				m.append(' ');
				m.append(cmd.getNewId().abbreviate(8).name());
				m.append(' ');
				m.append(cmd.getRefName());
				m.append(' ');
				m.append(cmd.getResult());
				if (cmd.getMessage() != null) {
					m.append(' ').append(cmd.getMessage());
				}
				m.append('\n');
			}
			KetchReplica.log.debug(m.toString());
		}

		replica.afterPush(repo
	}
}
