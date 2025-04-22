package org.eclipse.jgit.api;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collection;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class DescribeCommandTest extends RepositoryTestCase {

	private Git git;

	@Parameter(0)
	public boolean useAnnotatedTags;

	@Parameter(1)
	public boolean describeUseAllTags;

	@Parameters(name = "git tag -a {0}?-a: with git describe {1}?--tags:")
	public static Collection<Boolean[]> getUseAnnotatedTagsValues() {
		return Arrays.asList(new Boolean[][] { { Boolean.TRUE
				{ Boolean.FALSE
				{ Boolean.TRUE
				{ Boolean.FALSE
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);
	}

	@Test(expected = RefNotFoundException.class)
	public void noTargetSet() throws Exception {
		git.describe().call();
	}

	@Test
	public void testDescribe() throws Exception {
		ObjectId c1 = modify("aaa");

		ObjectId c2 = modify("bbb");
		tag("alice-t1");

		ObjectId c3 = modify("ccc");
		tag("bob-t2");

		ObjectId c4 = modify("ddd");
		assertNameStartsWith(c4

		assertNull(describe(c1));
		assertNull(describe(c1
		assertNull(describe(c1
		assertNull(describe(c2
		assertNull(describe(c2

		if (useAnnotatedTags || describeUseAllTags) {
			assertEquals("alice-t1"
			assertEquals("alice-t1"
			assertEquals("alice-t1"

			assertEquals("bob-t2"
			assertEquals("bob-t2-0-g44579eb"
			assertEquals("alice-t1-1-g44579eb"
			assertEquals("alice-t1-1-g44579eb"
			assertEquals("bob-t2"
			assertEquals("bob-t2"
			assertEquals("bob-t2"

			assertEquals("bob-t2-1-g3e563c5"
			assertEquals("bob-t2-1-g3e563c5"
			assertEquals("alice-t1-2-g3e563c5"
			assertEquals("bob-t2-1-g3e563c5"
			assertEquals("bob-t2-1-g3e563c5"
		} else {
			assertEquals(null
			assertEquals(null
			assertEquals(null
		}

		if (useAnnotatedTags) {
			assertEquals("bob-t2-1-g3e563c5"
			assertEquals("bob-t2-1-g3e563c5"
					git.describe().setTags(false).call());
			assertEquals("bob-t2-1-g3e563c5"
					git.describe().setTags(true).call());
		} else {
			assertEquals(null
			assertEquals(null
			assertEquals("bob-t2-1-g3e563c5"
					git.describe().setTags(true).call());
		}
	}

	@Test
	public void testDescribeMultiMatch() throws Exception {
		ObjectId c1 = modify("aaa");
		tag("v1.0.0");
		tick();
		tag("v1.0.1");
		tick();
		tag("v1.1.0");
		tick();
		tag("v1.1.1");
		ObjectId c2 = modify("bbb");

		if (!useAnnotatedTags && !describeUseAllTags) {
			assertEquals(null
			assertEquals(null
			return;
		}

		if (useAnnotatedTags) {
			assertEquals("v1.1.1"
			assertEquals("v1.1.1-1-gb89dead"
			assertEquals("v1.0.1"
			assertEquals("v1.1.1"
			assertEquals("v1.0.1-1-gb89dead"
			assertEquals("v1.1.1-1-gb89dead"

			assertEquals("v1.1.1"
			assertEquals("v1.1.1"
			assertEquals("v1.1.1-1-gb89dead"
			assertEquals("v1.1.1-1-gb89dead"
		} else {
			assertNotNull(describe(c1));
			assertNotNull(describe(c2));

			assertNotNull(describe(c1
			assertNotNull(describe(c1
			assertNotNull(describe(c2
			assertNotNull(describe(c2

			assertNotNull(describe(c1
			assertNotNull(describe(c1
			assertNotNull(describe(c2
			assertNotNull(describe(c2
		}
	}

	@Test
	public void testDescribeBranch() throws Exception {
		ObjectId c1 = modify("aaa");

		ObjectId c2 = modify("bbb");
		tag("t");

		branch("b"

		ObjectId c3 = modify("ccc");

		ObjectId c4 = merge(c2);

		assertNameStartsWith(c4
		if (useAnnotatedTags || describeUseAllTags) {
			assertEquals("2 commits: c4 and c3"
		} else {
			assertEquals(null
		}
		assertNull(describe(c3));
		assertNull(describe(c3
	}

	private void branch(String name
		git.checkout().setCreateBranch(true).setName(name)
				.setStartPoint(base.name()).call();
	}

	@Test
	public void t1DominatesT2() throws Exception {
		ObjectId c1 = modify("aaa");
		tag("t1");

		ObjectId c2 = modify("bbb");
		tag("t2");

		branch("b"

		ObjectId c3 = modify("ccc");
		assertNameStartsWith(c3

		ObjectId c4 = merge(c2);

		assertNameStartsWith(c4

		if (useAnnotatedTags || describeUseAllTags) {
			assertEquals("t2-2-g119892b"
			assertEquals("t1-1-g0244e7f"
		} else {
			assertEquals(null
			assertEquals(null
		}
	}

	@Test
	public void t1AnnotatedDominatesT2lightweight() throws Exception {
		ObjectId c1 = modify("aaa");
		tag("t1"

		ObjectId c2 = modify("bbb");
		tag("t2"

		assertNameStartsWith(c2
		if (useAnnotatedTags && !describeUseAllTags) {
			assertEquals(
					"only annotated tag t1 expected to be used for describe"
					"t1-1-g3747db3"
		} else if (!useAnnotatedTags && !describeUseAllTags) {
			assertEquals("no commits to describe expected"
		} else {
			assertEquals("lightweight tag t2 expected in describe"
					describe(c2));
		}

		branch("b"

		ObjectId c3 = modify("ccc");

		assertNameStartsWith(c3
		if (useAnnotatedTags || describeUseAllTags) {
			assertEquals("t1-1-g0244e7f"
		}

		ObjectId c4 = merge(c2);

		assertNameStartsWith(c4
		if (describeUseAllTags) {
			assertEquals(
					"2 commits for describe commit increment expected since lightweight tag: c4 and c3"
					"t2-2-g119892b"
		} else if (!useAnnotatedTags && !describeUseAllTags) {
			assertEquals("no matching commits expected"
		} else {
			assertEquals(
					"3 commits for describe commit increment expected since annotated tag: c4 and c3 and c2"
					"t1-3-g119892b"
		}
	}

	@Test
	public void t1nearerT2() throws Exception {
		ObjectId c1 = modify("aaa");
		modify("bbb");
		ObjectId t1 = modify("ccc");
		tag("t1");

		branch("b"
		modify("ddd");
		tag("t2");
		modify("eee");
		ObjectId c4 = merge(t1);

		assertNameStartsWith(c4
		if (useAnnotatedTags || describeUseAllTags) {
			assertEquals("t1-3-gbb389a4"
		} else {
			assertEquals(null
		}
	}

	@Test
	public void t1sameDepthT2() throws Exception {
		ObjectId c1 = modify("aaa");
		modify("bbb");
		tag("t1");
		ObjectId c2 = modify("ccc");

		branch("b"
		modify("ddd");
		tag("t2");
		modify("eee");
		ObjectId c4 = merge(c2);

		assertNameStartsWith(c4
		if (useAnnotatedTags || describeUseAllTags) {
			assertEquals("t2-4-gbb389a4"
		} else {
			assertEquals(null
		}
	}

	private ObjectId merge(ObjectId c2) throws GitAPIException {
		return git.merge().include(c2).call().getNewHead();
	}

	private ObjectId modify(String content) throws Exception {
		File a = new File(db.getWorkTree()
		touch(a
		return git.commit().setAll(true).setMessage(content).call().getId();
	}

	private void tag(String tag) throws GitAPIException {
		tag(tag
	}

	private void tag(String tag
		TagCommand tagCommand = git.tag().setName(tag)
				.setAnnotated(annotatedTag);
		if (annotatedTag) {
			tagCommand.setMessage(tag);
		}
		tagCommand.call();
	}

	private static void touch(File f
		try (BufferedWriter w = Files.newBufferedWriter(f.toPath()
			w.write(contents);
		}
	}

	private String describe(ObjectId c1
			throws GitAPIException
		return git.describe().setTarget(c1).setTags(describeUseAllTags)
				.setLong(longDesc).call();
	}

	private String describe(ObjectId c1) throws GitAPIException
		return describe(c1
	}

	private String describe(ObjectId c1
		return git.describe().setTarget(c1).setTags(describeUseAllTags)
				.setMatch(patterns).call();
	}

	private static void assertNameStartsWith(ObjectId c4
		assertTrue(c4.name()
	}
}
