
package org.eclipse.jgit.lib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.junit.Test;

public class RebaseTodoFileTest extends RepositoryTestCase {

	private static final String TEST_TODO = "test.todo";

	private void createTodoList(String... lines) throws IOException {
		Path p = Paths.get(db.getDirectory().getAbsolutePath()
		Files.write(p
	}

	@Test
	public void testReadTodoFile() throws Exception {
		String[] expected = { "reword " + ObjectId.zeroId().name() + " Foo"
				"# A comment in the the todo list"
				"pick " + ObjectId.zeroId().name() + " Foo fie"
				"squash " + ObjectId.zeroId().name() + " F"
				"fixup " + ObjectId.zeroId().name()
				"edit " + ObjectId.zeroId().name() + " f"
				"edit " + ObjectId.zeroId().name() + ' ' };
		createTodoList(expected);
		RebaseTodoFile todo = new RebaseTodoFile(db);
		List<RebaseTodoLine> lines = todo.readRebaseTodo(TEST_TODO
		assertEquals("Expected 7 lines"
		int i = 0;
		for (RebaseTodoLine line : lines) {
			switch (i) {
			case 0:
				assertEquals("Expected REWORD"
						line.getAction());
				assertEquals("Unexpected ID"
						line.getCommit());
				assertEquals("Unexpected Message"
						line.getShortMessage());
				break;
			case 1:
				assertEquals("Expected COMMENT"
						line.getAction());
				assertEquals("Unexpected Message"
						"# A comment in the the todo list"
						line.getComment());
				break;
			case 2:
				assertEquals("Expected PICK"
						line.getAction());
				assertEquals("Unexpected ID"
						line.getCommit());
				assertEquals("Unexpected Message"
						line.getShortMessage());
				break;
			case 3:
				assertEquals("Expected SQUASH"
						line.getAction());
				assertEquals("Unexpected ID"
						line.getCommit());
				assertEquals("Unexpected Message"
				break;
			case 4:
				assertEquals("Expected FIXUP"
						line.getAction());
				assertEquals("Unexpected ID"
						line.getCommit());
				assertEquals("Unexpected Message"
				break;
			case 5:
				assertEquals("Expected EDIT"
						line.getAction());
				assertEquals("Unexpected ID"
						line.getCommit());
				assertEquals("Unexpected Message"
				break;
			case 6:
				assertEquals("Expected EDIT"
						line.getAction());
				assertEquals("Unexpected ID"
						line.getCommit());
				assertEquals("Unexpected Message"
				break;
			default:
				fail("Too many lines");
				return;
			}
			i++;
		}
	}
}
