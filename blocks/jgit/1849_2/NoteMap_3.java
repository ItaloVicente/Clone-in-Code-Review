
package org.eclipse.jgit.notes;

import java.io.IOException;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;

abstract class NoteBucket {
	abstract ObjectId get(AnyObjectId objId
			throws IOException;
}
