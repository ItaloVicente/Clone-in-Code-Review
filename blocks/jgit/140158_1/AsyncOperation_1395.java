
package org.eclipse.jgit.lib;

import java.io.IOException;

import org.eclipse.jgit.errors.MissingObjectException;

public interface AsyncObjectSizeQueue<T extends ObjectId> extends
		AsyncOperation {

	boolean next() throws MissingObjectException

	T getCurrent();

	ObjectId getObjectId();

	long getSize();
}
