package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Test;

public class CreateOrphanBranchCommandTest extends RepositoryTestCase {

	Git git;

	List<RevCommit> commits;

	CreateOrphanBranchCommand target;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		this.git = new Git(db);
		this.commits = new ArrayList<RevCommit>();
		this.commits.add(this.newFile("aaa"));
		this.commits.add(this.newFile("bbb"));
		this.commits.add(this.newFile("ccc"));

		this.target = new CreateOrphanBranchCommand(db);
	}

	protected RevCommit newFile(String name) throws Exception {
		return commitFile(name + ".txt"
	}

	@Test
	public void orphan() throws Exception {
		Ref ref = this.target.setName("ppp").call();
		assertNotNull(ref);
		assertEquals("refs/heads/ppp"

		File HEAD = new File(trash
		String headRef = read(HEAD);
		assertEquals("ref: refs/heads/ppp\n"
		assertEquals(4

		File heads = new File(trash
		assertEquals(1

		this.noHead();
		this.assertStatus(3);
		assertEquals(CheckoutResult.NOT_TRIED_RESULT
	}

	protected void noHead() throws GitAPIException {
		try {
			this.git.log().call();
			fail();
		} catch (NoHeadException e) {
		}
	}

	protected void assertStatus(int files) throws GitAPIException {
		Status status = this.git.status().call();
		assertFalse(status.isClean());
		assertEquals(files
	}

	@Test
	public void startCommit() throws Exception {
		this.target.setStartPoint(this.commits.get(1)).setName("qqq").call();
		assertEquals(3
		this.noHead();
		this.assertStatus(2);
	}

	@Test
	public void startPoint() throws Exception {
		this.target.setStartPoint("HEAD^^").setName("zzz").call();
		assertEquals(2
		this.noHead();
		this.assertStatus(1);
	}

	@Test
	public void linkFail() throws Exception {
		File HEAD = new File(trash
		FileInputStream in = new FileInputStream(HEAD);
		try {
			this.target.setName("aaa").call();
			fail("Should have failed");
		} catch (JGitInternalException e) {
		} finally {
			in.close();
		}
	}

	@Test
	public void invalidRefName() throws Exception {
		try {
			this.target.setName("../hoge").call();
			fail("Should have failed");
		} catch (InvalidRefNameException e) {
		}
	}

	@Test
	public void nullRefName() throws Exception {
		try {
			this.target.setName(null).call();
			fail("Should have failed");
		} catch (InvalidRefNameException e) {
		}
	}

	@Test
	public void alreadyExists() throws Exception {
		this.git.checkout().setCreateBranch(true).setName("ppp").call();
		this.git.checkout().setName("master").call();

		try {
			this.target.setName("ppp").call();
			fail("Should have failed");
		} catch (RefAlreadyExistsException e) {
		}
	}

	@Test
	public void refNotFound() throws Exception {
		try {
			this.target.setStartPoint("1234567").setName("ppp").call();
			fail("Should have failed");
		} catch (RefNotFoundException e) {
		}
	}

	@Test
	public void toBeDeleted() throws Exception {
		File ccc = new File(trash
		FileInputStream in = new FileInputStream(ccc);
		try {
			this.target.setName("zzz").setStartPoint(this.commits.get(0))
					.call();
			assertEquals(1
		} finally {
			in.close();
		}
	}

	@Test
	public void conflicts() throws Exception {
		this.git.checkout().setCreateBranch(true).setName("aaa").call();
		File bbb = new File(trash
		write(bbb
		try {
			this.target.setName("zzz").setStartPoint(this.commits.get(0))
					.call();
			fail("Should have failed");
		} catch (CheckoutConflictException e) {
			assertEquals(1
			assertEquals(1
		}
	}
}
