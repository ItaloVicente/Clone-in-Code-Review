
package org.eclipse.jgit.notes;

import java.io.IOException;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.util.io.UnionInputStream;

class DefaultNoteMerger implements NoteMerger {

	private ObjectReader reader;

	private ObjectInserter inserter;

	DefaultNoteMerger(ObjectReader reader
		this.reader = reader;
		this.inserter = inserter;
	}

	public Note merge(Note base
			throws MissingObjectException
		if (ours == null)
			return theirs;

		if (theirs == null)
			return ours;

		if (ours.getData().equals(theirs.getData())) {
			return ours;
		}

		ObjectLoader lo = reader.open(ours.getData());
		ObjectLoader lt = reader.open(theirs.getData());
		UnionInputStream union = new UnionInputStream(lo.openStream()
				lt.openStream());
		ObjectId noteData = inserter.insert(Constants.OBJ_BLOB
				lo.getSize() + lt.getSize()
		inserter.flush();
		return new Note(ours
	}
}
