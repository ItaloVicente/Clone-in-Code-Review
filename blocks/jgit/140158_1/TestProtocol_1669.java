
package org.eclipse.jgit.transport;

import org.eclipse.jgit.lib.Repository;

public abstract class TcpTransport extends Transport {
	protected TcpTransport(Repository local
		super(local
	}

	protected TcpTransport(URIish uri) {
		super(uri);
	}
}
