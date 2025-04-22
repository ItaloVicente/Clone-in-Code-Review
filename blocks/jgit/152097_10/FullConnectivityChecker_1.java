
package org.eclipse.jgit.transport.internal;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.PackParser;
import org.eclipse.jgit.transport.ReceiveCommand;

public interface ConnectivityChecker {

	void checkConnectivity(ConnectivityCheckInfo connectivityCheckInfo
			Set<ObjectId> haves
			throws IOException;

	public static class ConnectivityCheckInfo {
		private Repository repository;

		private PackParser parser;

		private boolean checkObjects;

		private List<ReceiveCommand> commands;

		private RevWalk walk;

		public Repository getRepository() {
			return repository;
		}

		public void setRepository(Repository repository) {
			this.repository = repository;
		}

		public PackParser getParser() {
			return parser;
		}

		public void setParser(PackParser parser) {
			this.parser = parser;
		}

		public boolean isCheckObjects() {
			return checkObjects;
		}

		public void setCheckObjects(boolean checkObjects) {
			this.checkObjects = checkObjects;
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
}
