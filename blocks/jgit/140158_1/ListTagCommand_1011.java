package org.eclipse.jgit.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.lib.Constants;
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

	@Override
	public List<Note> call() throws GitAPIException {
		checkCallable();
		List<Note> notes = new ArrayList<>();
		NoteMap map = NoteMap.newEmptyMap();
		try (RevWalk walk = new RevWalk(repo)) {
			Ref ref = repo.findRef(notesRef);
			if (ref != null) {
				RevCommit notesCommit = walk.parseCommit(ref.getObjectId());
				map = NoteMap.read(walk.getObjectReader()
			}

			Iterator<Note> i = map.iterator();
			while (i.hasNext())
				notes.add(i.next());
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		}

		return notes;
	}

	public ListNotesCommand setNotesRef(String notesRef) {
		checkCallable();
		this.notesRef = notesRef;
		return this;
	}

}
