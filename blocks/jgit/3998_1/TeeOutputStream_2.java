
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Set;

import org.eclipse.jgit.lib.ObjectId;

public interface UploadPackCache {
	public boolean sendFromCache(OutputStream out
			Set<String> capabilities
			Collection<? extends ObjectId> wants
			Collection<? extends ObjectId> haves) throws IOException;

	public OutputStream newEntry(
			Set<String> capabilities
			Collection<? extends ObjectId> wants
			Collection<? extends ObjectId> haves);

	public void finishEntry(OutputStream cacheOut
}
