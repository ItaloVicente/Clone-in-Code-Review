
package org.eclipse.jgit.transport;

import java.io.OutputStream;

import org.eclipse.jgit.lib.ProgressMonitor;

public interface ObjectCountCallback {
	void setObjectCount(long objectCount) throws WriteAbortedException;
}
