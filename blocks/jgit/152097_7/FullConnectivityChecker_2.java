
package org.eclipse.jgit.transport.internal;

import java.io.IOException;
import java.util.Set;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;

public interface ConnectivityChecker {

	void checkConnectivity(ConnectivityCheckInfo connectivityCheckInfo
			Set<ObjectId> haves
			throws IOException;

}
