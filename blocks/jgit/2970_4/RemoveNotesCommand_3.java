package org.eclipse.jgit.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.notes.Note;
import org.eclipse.jgit.notes.NoteMap;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public class ListNotesCommand extends GitCommand<List<Note>> {

	private String notesRef = Constants.R_NOTES_COMMITS;

	protected ListNotesCommand(Repository repo) {
		super(repo);
	}

	public List<Note> call() throws JGitInternalException {
		checkCallable();
		List<Note> notes = new ArrayList<Note>();
		RevWalk walk = new RevWalk(repo);
		ObjectInserter inserter = repo.newObjectInserter();
		NoteMap map = NoteMap.newEmptyMap();
		try {
			Ref ref = repo.getRef(notesRef);
			if (ref != null) {
				RevCommit notesCommit = walk.parseCommit(ref.getObjectId());
				map = NoteMap.read(walk.getObjectReader()
			}

			Iterator<Note> i = map.iterator();
			while (i.hasNext())
				notes.add(i.next());

			inserter.flush();
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		} finally {
			inserter.release();
			walk.release();
		}

		return notes;
	}

	public ListNotesCommand setNotesRef(String notesRef) {
		checkCallable();
		this.notesRef = notesRef;
		return this;
	}

}
