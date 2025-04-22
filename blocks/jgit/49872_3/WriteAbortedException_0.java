
package org.eclipse.jgit.transport;

import org.eclipse.jgit.internal.storage.pack.PackWriter;

public interface ObjectCountCallback {
	void setObjectCount(long objectCount) throws WriteAbortedException;
}
