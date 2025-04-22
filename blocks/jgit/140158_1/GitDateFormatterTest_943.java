package org.eclipse.jgit.util;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.attributes.FilterCommand;
import org.eclipse.jgit.attributes.FilterCommandFactory;
import org.eclipse.jgit.attributes.FilterCommandRegistry;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Test;

public class FilterCommandsTest extends RepositoryTestCase {
	private Git git;

	RevCommit initialCommit;

	RevCommit secondCommit;

	class TestCommandFactory implements FilterCommandFactory {
		private int prefix;

		public TestCommandFactory(int prefix) {
			this.prefix = prefix;
		}

		@Override
		public FilterCommand create(Repository repo
				final OutputStream out) {
			FilterCommand cmd = new FilterCommand(in

				@Override
				public int run() throws IOException {
					int b = in.read();
					if (b == -1) {
						in.close();
						out.close();
						return b;
					}
					out.write(prefix);
					out.write(b);
					return 1;
				}
			};
			return cmd;
		}
	}

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);
		writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		initialCommit = git.commit().setMessage("Initial commit").call();

		git.branchCreate().setName("test").call();
		RefUpdate rup = db.updateRef(Constants.HEAD);
		rup.link("refs/heads/test");

		writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		secondCommit = git.commit().setMessage("Second commit").call();
	}

	@Test
	public void testBuiltinCleanFilter()
			throws IOException
		FilterCommandRegistry.register(builtinCommandName
				new TestCommandFactory('c'));
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter"
		config.save();

		writeTrashFile(".gitattributes"
		git.add().addFilepattern(".gitattributes").call();
		git.commit().setMessage("add filter").call();

		writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		assertEquals(
				"[.gitattributes
				indexState(CONTENT));

		writeTrashFile("Test.bin"
		git.add().addFilepattern("Test.bin").call();
		assertEquals(
				"[.gitattributes
				indexState(CONTENT));

		config.setString("filter"
		config.save();

		git.add().addFilepattern("Test.txt").call();
		assertEquals(
				"[.gitattributes
				indexState(CONTENT));

		config.setString("filter"
		config.save();
	}

	@Test
	public void testBuiltinSmudgeFilter() throws IOException
		FilterCommandRegistry.register(builtinCommandName
				new TestCommandFactory('s'));
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter"
		config.save();

		writeTrashFile(".gitattributes"
		git.add().addFilepattern(".gitattributes").call();
		git.commit().setMessage("add filter").call();

		writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		assertEquals(
				"[.gitattributes
				indexState(CONTENT));
		assertEquals("Hello again"
		deleteTrashFile("Test.txt");
		git.checkout().addPath("Test.txt").call();
		assertEquals("sHseslslsos sasgsasisn"

		writeTrashFile("Test.bin"
		git.add().addFilepattern("Test.bin").call();
		assertEquals(
				"[.gitattributes
				indexState(CONTENT));
		deleteTrashFile("Test.bin");
		git.checkout().addPath("Test.bin").call();
		assertEquals("Hello again"

		config.setString("filter"
		config.save();

		git.add().addFilepattern("Test.txt").call();
		assertEquals(
				"[.gitattributes
				indexState(CONTENT));

		config.setString("filter"
		config.save();
	}

	@Test
	public void testBuiltinCleanAndSmudgeFilter() throws IOException
		FilterCommandRegistry.register(builtinCommandPrefix + "smudge"
				new TestCommandFactory('s'));
		FilterCommandRegistry.register(builtinCommandPrefix + "clean"
				new TestCommandFactory('c'));
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter"
		config.setString("filter"
				builtinCommandPrefix + "clean");
		config.save();

		writeTrashFile(".gitattributes"
		git.add().addFilepattern(".gitattributes").call();
		git.commit().setMessage("add filter").call();

		writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		assertEquals(
				"[.gitattributes
				indexState(CONTENT));
		assertEquals("Hello again"
		deleteTrashFile("Test.txt");
		git.checkout().addPath("Test.txt").call();
		assertEquals("scsHscsescslscslscsoscs scsascsgscsascsiscsn"
				read("Test.txt"));

		writeTrashFile("Test.bin"
		git.add().addFilepattern("Test.bin").call();
		assertEquals(
				"[.gitattributes
				indexState(CONTENT));
		deleteTrashFile("Test.bin");
		git.checkout().addPath("Test.bin").call();
		assertEquals("Hello again"

		config.setString("filter"
		config.save();

		git.add().addFilepattern("Test.txt").call();
		assertEquals(
				"[.gitattributes
				indexState(CONTENT));

		config.setString("filter"
		config.save();
	}

}
