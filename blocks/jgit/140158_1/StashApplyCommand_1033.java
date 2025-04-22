package org.eclipse.jgit.api;

import java.io.IOException;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.notes.Note;
import org.eclipse.jgit.notes.NoteMap;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevWalk;

public class ShowNoteCommand extends GitCommand<Note> {

	private RevObject id;

	private String notesRef = Constants.R_NOTES_COMMITS;

	protected ShowNoteCommand(Repository repo) {
		super(repo);
	}

	@Override
	public Note call() throws GitAPIException {
		checkCallable();
		NoteMap map = NoteMap.newEmptyMap();
		RevCommit notesCommit = null;
		try (RevWalk walk = new RevWalk(repo)) {
			Ref ref = repo.exactRef(notesRef);
			if (ref != null) {
				notesCommit = walk.parseCommit(ref.getObjectId());
				map = NoteMap.read(walk.getObjectReader()
			}
			return map.getNote(id);
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		}
	}

	public ShowNoteCommand setObjectId(RevObject id) {
		checkCallable();
		this.id = id;
		return this;
	}

	public ShowNoteCommand setNotesRef(String notesRef) {
		checkCallable();
		this.notesRef = notesRef;
		return this;
	}

}
