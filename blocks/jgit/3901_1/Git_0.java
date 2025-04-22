package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.junit.Before;
import org.junit.Test;

public class ListTagCommandTest extends RepositoryTestCase {

	private Git git;

	@Before
	public void before() {
		git = new Git(db);
	}

	@Test
	public void shouldNotBlowUpIfThereAreNoTagsInRepository() throws Exception {
		git.add().addFilepattern("*").call();
		git.commit().setMessage("initial commit").call();
		List<Ref> list = git.tagList().call();
		assertEquals(0
	}

	@Test
	public void shouldNotBlowUpIfThereAreNoCommitsInRepository()
			throws Exception {
		List<Ref> list = git.tagList().call();
		assertEquals(0
	}

	@Test
	public void shouldListAllTagsInRepositoryInOrder() throws Exception {
		git.add().addFilepattern("*").call();
		git.commit().setMessage("initial commit").call();

		git.tag().setName("v3").call();
		git.tag().setName("v2").call();
		git.tag().setName("v10").call();

		List<Ref> list = git.tagList().call();

		assertEquals(3
		assertEquals(
				Arrays.asList(db.getRef("refs/tags/v10")
						db.getRef("refs/tags/v2")
				list);
	}
}
