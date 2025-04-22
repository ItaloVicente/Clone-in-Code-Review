
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.util.Set;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;

public interface ConnectivityChecker {

	void checkConnectivity(ReceivePack receivePack
			Set<ObjectId> haves
			throws IOException;

}
