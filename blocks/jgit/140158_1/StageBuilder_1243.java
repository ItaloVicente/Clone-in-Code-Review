
package org.eclipse.jgit.internal.ketch;

import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.transport.ReceiveCommand;

abstract class Round {
	final KetchLeader leader;
	final LogIndex acceptedOldIndex;
	LogIndex acceptedNewIndex;
	List<ReceiveCommand> stageCommands;

	Round(KetchLeader leader
		this.leader = leader;
		this.acceptedOldIndex = head;
	}

	KetchSystem getSystem() {
		return leader.getSystem();
	}

	abstract void start() throws IOException;

	void runAsync(AnyObjectId newId) {
		acceptedNewIndex = acceptedOldIndex.nextIndex(newId);
		leader.runAsync(this);
	}

	abstract void success();
}
