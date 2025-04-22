
package org.eclipse.jgit.notes;

import java.io.IOException;
import java.util.Iterator;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;

abstract class NoteBucket {
	abstract Note getNote(AnyObjectId objId
			throws IOException;

	abstract Iterator<Note> iterator(AnyObjectId objId
			throws IOException;

	abstract int estimateSize(AnyObjectId noteOn
			throws IOException;

	abstract InMemoryNoteBucket set(AnyObjectId noteOn
			ObjectReader reader) throws IOException;

	abstract ObjectId writeTree(ObjectInserter inserter) throws IOException;

	abstract ObjectId getTreeId();
}
