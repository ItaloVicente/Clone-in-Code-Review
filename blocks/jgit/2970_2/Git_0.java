package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.jgit.api.NotesCommand.SubCommand;
import org.eclipse.jgit.lib.RepositoryTestCase;
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
		git.notes().setSubCommand(SubCommand.ADD).setObjectId(commit1)
				.setMessage("data").call();
	}

	@Test
	public void testListNotes() throws Exception {
		NotesCommand command = git.notes();
		List<Note> notes = command.call();
		assertTrue(notes.size() == 1);
	}

	@Test
	public void testAddAndRemoveNote() throws Exception {
		NotesCommand command = git.notes();
		command.setSubCommand(SubCommand.ADD);
		command.setObjectId(commit2);
		command.setMessage("data");
		List<Note> notes = command.call();

		command.setSubCommand(SubCommand.SHOW);
		command.setObjectId(commit2);
		notes = command.call();
		Note n = notes.get(0);
		String content = new String(db.open(n.getData()).getCachedBytes()
				"UTF-8");
		assertEquals(content

		command.setSubCommand(SubCommand.REMOVE);
		command.setMessage(null);
		command.call();

		command.setSubCommand(SubCommand.SHOW);
		notes = command.call();
		assertTrue(notes.size() == 0);
	}

}
