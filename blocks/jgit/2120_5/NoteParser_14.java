
package org.eclipse.jgit.notes;

import java.io.IOException;

import org.eclipse.jgit.errors.MissingObjectException;

public interface NoteMerger {

	Note merge(Note base
			throws NotesMergeConflictException
			IOException;
}

