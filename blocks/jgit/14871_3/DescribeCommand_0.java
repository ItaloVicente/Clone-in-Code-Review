package org.eclipse.jgit.api;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.ObjectId;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.*;

public class DescribeCommandTest extends RepositoryTestCase {

	private Git git;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);
	}

	@Test(expected=IllegalArgumentException.class)
	public void noTargetSet() throws Exception {
		git.describe().call();
	}

	@Test
	public void testDescribe() throws Exception {
		ObjectId c1 = modify("aaa");

		ObjectId c2 = modify("bbb");
		tag("t1");

		ObjectId c3 = modify("ccc");
		tag("t2");

		ObjectId c4 = modify("ddd");


		assertNull(describe(c1));
		assertEquals("t1"
		assertEquals("t2"

		assertNameStartsWith(c4
		assertEquals("t2-1-g3e563c5"
	}

	@Test
	public void testDescribeBranch() throws Exception {
		ObjectId c1 = modify("aaa");

		ObjectId c2 = modify("bbb");
		tag("t");

		git.checkout().setCreateBranch(true).setName("b").setStartPoint(c1.name()).call();

		ObjectId c3 = modify("ccc");

		git.merge().include(c2).call();
		ObjectId c4 = head();

		assertNameStartsWith(c4
		assertEquals("t-2-g119892b"
		assertNull(describe(c3));
	}

	@Test
	public void t1DominatesT2() throws Exception {
		ObjectId c1 = modify("aaa");
		tag("t1");

		ObjectId c2 = modify("bbb");
		tag("t2");

		git.checkout().setCreateBranch(true).setName("b").setStartPoint(c1.name()).call();

		ObjectId c3 = modify("ccc");

		git.merge().include(c2).call();
		ObjectId c4 = head();

		assertNameStartsWith(c4
		assertEquals("t2-2-g119892b"

		assertNameStartsWith(c3
		assertEquals("t1-1-g0244e7f"
	}

	private ObjectId modify(String content) throws Exception {
		File a = new File(db.getWorkTree()
		touch(a
		git.commit().setAll(true).setMessage(content).call();
		return head();
	}

	private ObjectId head() throws IOException {
		return db.resolve("HEAD");
	}

	private void tag(String tag) throws GitAPIException {
		git.tag().setName(tag).setMessage(tag).call();
	}

	private void touch(File f
		FileWriter w = new FileWriter(f);
		w.write(contents);
		w.close();
	}

	private String describe(ObjectId c1) throws GitAPIException
		return git.describe().setTarget(c1).call();
	}

	private void assertNameStartsWith(ObjectId c4
		assertTrue(c4.name()
	}
}
