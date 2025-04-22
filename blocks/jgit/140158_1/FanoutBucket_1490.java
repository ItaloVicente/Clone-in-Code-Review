
package org.eclipse.jgit.notes;

import java.io.IOException;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.util.io.UnionInputStream;

public class DefaultNoteMerger implements NoteMerger {

	@Override
	public Note merge(Note base
			ObjectInserter inserter) throws IOException {
		if (ours == null)
			return theirs;

		if (theirs == null)
			return ours;

		if (ours.getData().equals(theirs.getData()))
			return ours;

		ObjectLoader lo = reader.open(ours.getData());
		ObjectLoader lt = reader.open(theirs.getData());
		try (UnionInputStream union = new UnionInputStream(lo.openStream()
				lt.openStream())) {
			ObjectId noteData = inserter.insert(Constants.OBJ_BLOB
					lo.getSize() + lt.getSize()
			return new Note(ours
		}
	}
}
