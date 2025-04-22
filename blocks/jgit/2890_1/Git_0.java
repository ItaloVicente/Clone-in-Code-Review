package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.junit.Test;

public class StatusCommandTest extends RepositoryTestCase {

	@Test
	public void testEmptyStatus() throws IOException {
		Git git = new Git(db);

		Status stat = git.status().call();
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
	}

	@Test
	public void testDifferentStates() throws IOException
			NoFilepatternException
		Git git = new Git(db);
		writeTrashFile("a"
		writeTrashFile("b"
		writeTrashFile("c"
		git.add().addFilepattern("a").addFilepattern("b").call();
		Status stat = git.status().call();
		assertEquals(set("a"
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(set("c")
		git.commit().setMessage("initial").call();

		writeTrashFile("a"
		writeTrashFile("b"
		writeTrashFile("d"
		git.add().addFilepattern("a").addFilepattern("d").call();
		writeTrashFile("a"
		stat = git.status().call();
		assertEquals(set("d")
		assertEquals(set("a")
		assertEquals(0
		assertEquals(set("b"
		assertEquals(0
		assertEquals(set("c")
		git.add().addFilepattern(".").call();
		git.commit().setMessage("second").call();

		stat = git.status().call();
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0

		deleteTrashFile("a");
		assertFalse(new File(git.getRepository().getWorkTree()
		git.add().addFilepattern("a").setUpdate(true).call();
		writeTrashFile("a"
		stat = git.status().call();
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(set("a")
		assertEquals(set("a")
		git.commit().setMessage("t").call();

	}

	public static Set<String> set(String... elements) {
		Set<String> ret = new HashSet<String>();
		for (String element : elements)
			ret.add(element);
		return ret;
	}
}
