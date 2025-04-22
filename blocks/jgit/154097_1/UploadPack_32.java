package org.eclipse.jgit.transport;

import java.io.IOException;

public interface UnpackErrorHandler {
	void handleUnpackException(Throwable t) throws IOException;
}
