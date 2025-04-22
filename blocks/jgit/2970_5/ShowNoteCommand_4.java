package org.eclipse.jgit.api;

import java.io.IOException;

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

public class RemoveNoteCommand extends GitCommand<Note> {

	private RevObject id;

	private String notesRef = Constants.R_NOTES_COMMITS;

	protected RemoveNoteCommand(Repository repo) {
		super(repo);
	}

	public Note call() throws JGitInternalException {
		checkCallable();
		RevWalk walk = new RevWalk(repo);
		ObjectInserter inserter = repo.newObjectInserter();
		NoteMap map = NoteMap.newEmptyMap();
		RevCommit notesCommit = null;
		try {
			Ref ref = repo.getRef(notesRef);
			if (ref != null) {
				notesCommit = walk.parseCommit(ref.getObjectId());
				map = NoteMap.read(walk.getObjectReader()
			}
			map.set(id
			commitNoteMap(walk
					"Notes removed by 'git notes remove'");
			return map.getNote(id);
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		} finally {
			inserter.release();
			walk.release();
		}
	}

	public RemoveNoteCommand setObjectId(RevObject id) {
		checkCallable();
		this.id = id;
		return this;
	}

	private void commitNoteMap(RevWalk walk
			RevCommit notesCommit
			ObjectInserter inserter
			String msg)
			throws IOException {
		CommitBuilder builder = new CommitBuilder();
		builder.setTreeId(map.writeTree(inserter));
		builder.setAuthor(new PersonIdent(repo));
		builder.setCommitter(builder.getAuthor());
		builder.setMessage(msg);
		if (notesCommit != null)
			builder.setParentIds(notesCommit);
		ObjectId commit = inserter.insert(builder);
		inserter.flush();
		RefUpdate refUpdate = repo.updateRef(notesRef);
		if (notesCommit != null)
			refUpdate.setExpectedOldObjectId(notesCommit);
		else
			refUpdate.setExpectedOldObjectId(ObjectId.zeroId());
		refUpdate.setNewObjectId(commit);
		refUpdate.update(walk);
	}

	public RemoveNoteCommand setNotesRef(String notesRef) {
		checkCallable();
		this.notesRef = notesRef;
		return this;
	}

}
