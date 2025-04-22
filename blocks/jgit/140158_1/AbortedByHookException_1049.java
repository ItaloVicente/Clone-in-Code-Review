
package org.eclipse.jgit.api;

import org.eclipse.jgit.transport.Transport;

public interface TransportConfigCallback {

	void configure(Transport transport);
}
