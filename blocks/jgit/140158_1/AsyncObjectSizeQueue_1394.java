
package org.eclipse.jgit.lib;

import java.io.IOException;

import org.eclipse.jgit.errors.MissingObjectException;

public interface AsyncObjectLoaderQueue<T extends ObjectId> extends
		AsyncOperation {

	boolean next() throws MissingObjectException

	T getCurrent();

	ObjectId getObjectId();

	ObjectLoader open() throws IOException;
}
