package org.eclipse.jgit.transport;

import java.io.IOException;

public interface PackLock {

	void unlock() throws IOException;
}
