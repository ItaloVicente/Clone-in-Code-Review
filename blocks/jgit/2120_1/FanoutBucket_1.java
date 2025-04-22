package org.eclipse.jgit.notes;

import java.io.IOException;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.io.UnionInputStream;

public class DefaultNoteMerger implements NoteMerger {

	private final Repository db;

	public DefaultNoteMerger(Repository db) {
		this.db = db;
	}

	public Note merge(Note base
			throws MissingObjectException
		if (ours == null)
			return theirs;

		if (theirs == null)
			return ours;

		ObjectReader reader = db.newObjectReader();
		ObjectLoader lo = reader.open(ours);
		ObjectLoader lt = reader.open(theirs);
		UnionInputStream union = new UnionInputStream(lo.openStream()
				lt.openStream());
		ObjectInserter inserter = db.newObjectInserter();
		ObjectId noteData = inserter.insert(Constants.OBJ_BLOB
				lo.getSize() + lt.getSize()
		inserter.flush();
		return new Note(ours
	}
}
