
package org.eclipse.jgit.notes;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;

public class NotesMergeConflictException extends IOException {
	private static final long serialVersionUID = 1L;

	public NotesMergeConflictException(Note base
		super(MessageFormat.format(JGitText.get().mergeConflictOnNotes
				noteOn(base
				noteData(theirs)));
	}

	public NotesMergeConflictException(NonNoteEntry base
			NonNoteEntry theirs) {
		super(MessageFormat.format(
				JGitText.get().mergeConflictOnNonNoteEntries
				name(ours)
	}

	private static String noteOn(Note base
		if (base != null)
			return base.name();
		if (ours != null)
			return ours.name();
		return theirs.name();
	}

	private static String noteData(Note n) {
		if (n != null)
			return n.getData().name();
	}

	private static String name(NonNoteEntry e) {
	}
}
