package org.eclipse.jgit.api;

import java.io.IOException;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.notes.Note;
import org.eclipse.jgit.notes.NoteMap;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevWalk;

public class AddNoteCommand extends GitCommand<Note> {

	private RevObject id;

	private String message;

	private String notesRef = Constants.R_NOTES_COMMITS;

	protected AddNoteCommand(Repository repo) {
		super(repo);
	}

	@Override
	public Note call() throws GitAPIException {
		checkCallable();
		NoteMap map = NoteMap.newEmptyMap();
		RevCommit notesCommit = null;
		try (RevWalk walk = new RevWalk(repo);
				ObjectInserter inserter = repo.newObjectInserter()) {
			Ref ref = repo.findRef(notesRef);
			if (ref != null) {
				notesCommit = walk.parseCommit(ref.getObjectId());
				map = NoteMap.read(walk.getObjectReader()
			}
			map.set(id
			commitNoteMap(repo
			return map.getNote(id);
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		}
	}

	public AddNoteCommand setObjectId(RevObject id) {
		checkCallable();
		this.id = id;
		return this;
	}

	public AddNoteCommand setMessage(String message) {
		checkCallable();
		this.message = message;
		return this;
	}

	static void commitNoteMap(Repository r
			NoteMap map
			RevCommit notesCommit
			ObjectInserter inserter
			String msg)
			throws IOException {
		CommitBuilder builder = new CommitBuilder();
		builder.setTreeId(map.writeTree(inserter));
		builder.setAuthor(new PersonIdent(r));
		builder.setCommitter(builder.getAuthor());
		builder.setMessage(msg);
		if (notesCommit != null)
			builder.setParentIds(notesCommit);
		ObjectId commit = inserter.insert(builder);
		inserter.flush();
		RefUpdate refUpdate = r.updateRef(ref);
		if (notesCommit != null)
			refUpdate.setExpectedOldObjectId(notesCommit);
		else
			refUpdate.setExpectedOldObjectId(ObjectId.zeroId());
		refUpdate.setNewObjectId(commit);
		refUpdate.update(walk);
	}

	public AddNoteCommand setNotesRef(String notesRef) {
		checkCallable();
		this.notesRef = notesRef;
		return this;
	}

}
