
package org.eclipse.jgit.lib;

import java.io.IOException;

import org.eclipse.jgit.errors.MissingObjectException;

public interface AsyncObjectSizeQueue<T extends ObjectId> extends
		AsyncOperation {

	public boolean next() throws MissingObjectException

	public T getCurrent();

	public ObjectId getObjectId();

	public long getSize();
}
