
package org.eclipse.jgit.transport;

import java.io.IOException;

public interface RemoteSession {
	Process exec(String commandName

	default FtpChannel getFtpChannel() {
		return null;
	}

	void disconnect();
}
