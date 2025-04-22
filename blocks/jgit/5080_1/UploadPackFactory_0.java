
package org.eclipse.jgit.transport;

import java.util.Map;

import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.pack.PackWriter;

public interface UploadSession {
	public static enum RequestPolicy {
		ADVERTISED
		REACHABLE_COMMIT
		ANY;
	}

	public Repository getRepository();

	public RevWalk getRevWalk();

	public Map<String

	public int getTimeout();

	public boolean isBiDirectionalPipe();

	public RequestPolicy getRequestPolicy();

	public RefFilter getRefFilter();

	public PreUploadHook getPreUploadHook();

	public UploadPackLogger getLogger();

	public PackWriter.Statistics getPackStatistics();
}
