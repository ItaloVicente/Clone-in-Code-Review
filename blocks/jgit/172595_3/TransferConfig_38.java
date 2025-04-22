
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.util.Map;

public interface RemoteSession2 extends RemoteSession {

	Process exec(String commandName
			int timeout) throws IOException;
}
