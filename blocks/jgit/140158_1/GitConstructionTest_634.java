package org.eclipse.jgit.api;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Properties;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.util.GitDateParser;
import org.eclipse.jgit.util.SystemReader;
import org.junit.Before;
import org.junit.Test;

public class GarbageCollectCommandTest extends RepositoryTestCase {
	private Git git;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);
		String path = "a.txt";
		writeTrashFile(path
		git.add().addFilepattern(path).call();
		git.commit().setMessage("commit").call();
	}

	@Test
	public void testGConeCommit() throws Exception {
		Date expire = GitDateParser.parse("now"
				.getInstance().getLocale());
		Properties res = git.gc().setExpire(expire).call();
		assertTrue(res.size() == 7);
	}

	@Test
	public void testGCmoreCommits() throws Exception {
		writeTrashFile("a.txt"
		writeTrashFile("b.txt"
		writeTrashFile("c.txt"
		git.commit().setAll(true).setMessage("commit2").call();
		writeTrashFile("a.txt"
		writeTrashFile("b.txt"
		writeTrashFile("c.txt"
		git.commit().setAll(true).setMessage("commit3").call();
		Properties res = git
				.gc()
				.setExpire(
						GitDateParser.parse("now"
								.getInstance().getLocale())).call();
		assertTrue(res.size() == 7);
	}
}
