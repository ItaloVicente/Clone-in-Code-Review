package org.eclipse.jgit.pgm;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.eclipse.jgit.pgm.opt.CmdLineParser;
import org.eclipse.jgit.pgm.opt.SubcommandHandler;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.junit.Before;
import org.junit.Test;
import org.kohsuke.args4j.Argument;

public class DiffToolTest extends CLIRepositoryTestCase {
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

	private Git git;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);
		git.commit().setMessage("initial commit").call();
	}

	@Test
	public void testTool() throws Exception {
		RevCommit commit = createUnstagedChanges();
		List<DiffEntry> changes = getRepositoryChanges(commit);
		String[] expectedOutput = getExpectedDiffToolOutput(changes);

		String[] options = {
				"--tool"
				"-t"
		};

		for (String option : options) {
			assertArrayOfLinesEquals("Incorrect output for option: " + option
					expectedOutput
					runAndCaptureUsingInitRaw("difftool"
							"some_tool"));
		}
	}

	@Test
	public void testToolCached() throws Exception {
		RevCommit commit = createStagedChanges();
		List<DiffEntry> changes = getRepositoryChanges(commit);
		String[] expectedOutput = getExpectedDiffToolOutput(changes);

		String[] options = { "--cached"

		for (String option : options) {
			assertArrayOfLinesEquals("Incorrect output for option: " + option
					expectedOutput
							option
		}
	}

	@Test
	public void testToolHelp() throws Exception {
		String[] expectedOutput = {
				"git difftool --tool=<tool> may be set to one of the following:"
				"user-defined:"
				"The following tools are valid
				"Some of the tools listed above only work in a windowed"
				"environment. If run in a terminal-only session

		String option = "--tool-help";
		assertArrayOfLinesEquals("Incorrect output for option: " + option
				expectedOutput
	}

	private RevCommit createUnstagedChanges() throws Exception {
		writeTrashFile("a"
		writeTrashFile("b"
		git.add().addFilepattern(".").call();
		RevCommit commit = git.commit().setMessage("files a & b").call();
		writeTrashFile("a"
		writeTrashFile("b"
		return commit;
	}

	private RevCommit createStagedChanges() throws Exception {
		RevCommit commit = createUnstagedChanges();
		git.add().addFilepattern(".").call();
		return commit;
	}

	private List<DiffEntry> getRepositoryChanges(RevCommit commit)
			throws Exception {
		TreeWalk tw = new TreeWalk(db);
		tw.addTree(commit.getTree());
		FileTreeIterator modifiedTree = new FileTreeIterator(db);
		tw.addTree(modifiedTree);
		List<DiffEntry> changes = DiffEntry.scan(tw);
		return changes;
	}

	private String[] getExpectedDiffToolOutput(List<DiffEntry> changes) {
		String[] expectedToolOutput = new String[changes.size()];
		for (int i = 0; i < changes.size(); ++i) {
			DiffEntry change = changes.get(i);
			String newPath = change.getNewPath();
			String oldPath = change.getOldPath();
			String newIdName = change.getNewId().name();
			String oldIdName = change.getOldId().name();
			String expectedLine = "M\t" + newPath + " (" + newIdName + ")"
					+ "\t" + oldPath + " (" + oldIdName + ")";
			expectedToolOutput[i] = expectedLine;
		}
		return expectedToolOutput;
	}

	private static void assertArrayOfLinesEquals(String failMessage
			String[] expected
		assertEquals(failMessage
	}
}
