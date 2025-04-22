package org.eclipse.jgit.pgm;

import static org.eclipse.jgit.junit.JGitTestUtil.check;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.eclipse.jgit.pgm.opt.CmdLineParser;
import org.eclipse.jgit.pgm.opt.SubcommandHandler;
import org.junit.Test;
import org.kohsuke.args4j.Argument;

public class TextBuiltinTest extends CLIRepositoryTestCase {
	public static class GitCliJGitWrapperParser {
		@Argument(index = 0
		TextBuiltin subcommand;

		@Argument(index = 1
		List<String> arguments = new ArrayList<>();
	}

	private String[] runAndCaptureUsingInitRaw(String... args)
			throws Exception {
		CLIGitCommand.Result result = new CLIGitCommand.Result();

		GitCliJGitWrapperParser bean = new GitCliJGitWrapperParser();
		final CmdLineParser clp = new CmdLineParser(bean);
		clp.parseArgument(args);

		final TextBuiltin cmd = bean.subcommand;
		cmd.initRaw(db
		cmd.execute(bean.arguments.toArray(new String[bean.arguments.size()]));
		if (cmd.getOutputWriter() != null) {
			cmd.getOutputWriter().flush();
		}
		if (cmd.getErrorWriter() != null) {
			cmd.getErrorWriter().flush();
		}
		return result.outLines().toArray(new String[0]);
	}

	@Test
	public void testCleanDeleteDirs() throws Exception {
		try (Git git = new Git(db)) {
			git.commit().setMessage("initial commit").call();

			writeTrashFile("dir/file"
			writeTrashFile("a"
			writeTrashFile("b"

			assertTrue(check(db
			assertTrue(check(db
			assertTrue(check(db

			assertArrayOfLinesEquals(new String[] { "Removing a"
					"Removing dir/" }
					runAndCaptureUsingInitRaw("clean"
			assertFalse(check(db
			assertFalse(check(db
			assertFalse(check(db
		}
	}
}
