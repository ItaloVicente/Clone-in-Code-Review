package org.eclipse.jgit.transport.internal;

import java.util.List;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.PackParser;
import org.eclipse.jgit.transport.ReceiveCommand;

public class ConnectivityCheckInfo {
	private Repository db;

	private PackParser parser;

	private boolean checkReferencedObjectsAreReachable;

	private List<ReceiveCommand> commands;

	private RevWalk walk;

	public Repository getDb() {
		return db;
	}

	public void setDb(Repository db) {
		this.db = db;
	}

	public PackParser getParser() {
		return parser;
	}

	public void setParser(PackParser parser) {
		this.parser = parser;
	}

	public boolean isCheckReferencedObjectsAreReachable() {
		return checkReferencedObjectsAreReachable;
	}

	public void setCheckReferencedObjectsAreReachable(
			boolean checkReferencedObjectsAreReachable) {
		this.checkReferencedObjectsAreReachable = checkReferencedObjectsAreReachable;
	}

	public List<ReceiveCommand> getCommands() {
		return commands;
	}

	public void setCommands(List<ReceiveCommand> commands) {
		this.commands = commands;
	}

	public void setWalk(RevWalk walk) {
		this.walk = walk;
	}

	public RevWalk getWalk() {
		return walk;
	}
}
