package org.eclipse.jgit.api;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.notes.Note;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Test;

public class NotesCommandTest extends RepositoryTestCase {

	private Git git;

	private RevCommit commit1

	private static final String FILE = "test.txt";

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		git = new Git(db);
		writeTrashFile(FILE
		git.add().addFilepattern(FILE).call();
		commit1 = git.commit().setMessage("Initial commit").call();
		git.rm().addFilepattern(FILE).call();
		commit2 = git.commit().setMessage("Removed file").call();
		git.notesAdd().setObjectId(commit1)
				.setMessage("data").call();
	}

	@Test
	public void testListNotes() throws Exception {
		List<Note> notes = git.notesList().call();
		assertEquals(1
	}

	@Test
	public void testAddAndRemoveNote() throws Exception {
		git.notesAdd().setObjectId(commit2).setMessage("data").call();
		Note note = git.notesShow().setObjectId(commit2).call();
		String content = new String(db.open(note.getData()).getCachedBytes()
				UTF_8);
		assertEquals(content

		git.notesRemove().setObjectId(commit2).call();

		List<Note> notes = git.notesList().call();
		assertEquals(1
	}

}
