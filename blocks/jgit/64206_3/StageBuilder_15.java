
package org.eclipse.jgit.internal.ketch;

import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.transport.ReceiveCommand;

abstract class Round {
	final KetchLeader leader;
	final LogId acceptedOld;
	LogId acceptedNew;
	List<ReceiveCommand> stageCommands;

	Round(KetchLeader leader
		this.leader = leader;
		this.acceptedOld = head;
	}

	abstract void start() throws IOException;

	void acceptAsync(AnyObjectId id) {
		acceptedNew = acceptedOld.nextId(id);
		leader.acceptAsync(this);
	}

	abstract void success();
}
