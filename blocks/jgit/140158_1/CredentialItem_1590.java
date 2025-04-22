
package org.eclipse.jgit.transport;

import java.util.Collection;
import java.util.Map;

import org.eclipse.jgit.lib.Ref;

public interface Connection extends AutoCloseable {
	Map<String

	Collection<Ref> getRefs();

	Ref getRef(String name);

	@Override
	void close();

	String getMessages();

	String getPeerUserAgent();
}
