
package org.eclipse.jgit.notes;

abstract class InMemoryNoteBucket extends NoteBucket {
	final int prefixLen;

	NonNoteEntry nonNotes;

	InMemoryNoteBucket(int prefixLen) {
		this.prefixLen = prefixLen;
	}

	abstract InMemoryNoteBucket append(Note note);
}
