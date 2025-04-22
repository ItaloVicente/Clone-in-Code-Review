
package org.eclipse.jgit.notes;

import java.io.IOException;

import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;

public interface NoteMerger {

	Note merge(Note base
			ObjectInserter inserter) throws NotesMergeConflictException
			IOException;
}

