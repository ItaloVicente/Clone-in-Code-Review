package org.eclipse.jgit.pgm;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.eclipse.jgit.pgm.opt.CmdLineParser;
import org.eclipse.jgit.pgm.opt.SubcommandHandler;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.junit.Before;
import org.kohsuke.args4j.Argument;

public abstract class ToolTestCase extends CLIRepositoryTestCase {

	public static class GitCliJGitWrapperParser {
		@Argument(index = 0
		TextBuiltin subcommand;

		@Argument(index = 1
		List<String> arguments = new ArrayList<>();
	}

	protected static final String TOOL_NAME = "some_tool";

	private static final String TEST_BRANCH_NAME = "test_branch";

	private Git git;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);
		git.commit().setMessage("initial commit").call();
		git.branchCreate().setName(TEST_BRANCH_NAME).call();
	}

	protected String[] runAndCaptureUsingInitRaw(String... args)
			throws Exception {
		return runAndCaptureUsingInitRaw(inputStream
	}

	protected String[] runAndCaptureUsingInitRaw(InputStream inputStream
			String... args) throws Exception {
		CLIGitCommand.Result result = new CLIGitCommand.Result();

		GitCliJGitWrapperParser bean = new GitCliJGitWrapperParser();
		CmdLineParser clp = new CmdLineParser(bean);
		clp.parseArgument(args);

		TextBuiltin cmd = bean.subcommand;
		cmd.initRaw(db
		cmd.execute(bean.arguments.toArray(new String[bean.arguments.size()]));
		if (cmd.getOutputWriter() != null) {
			cmd.getOutputWriter().flush();
		}
		if (cmd.getErrorWriter() != null) {
			cmd.getErrorWriter().flush();
		}

		List<String> errLines = result.errLines().stream()
				.collect(Collectors.toList());
		assertEquals("Expected no standard error output from tool"
				Collections.EMPTY_LIST.toString()

		return result.outLines().toArray(new String[0]);
	}

	protected String[] createMergeConflict() throws Exception {
		git.checkout().setName(TEST_BRANCH_NAME).call();
		writeTrashFile("dir1/a"
		writeTrashFile("dir2/b"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("files a & b added").call();
		git.branchCreate().setName("branch_1").call();
		git.checkout().setName("branch_1").call();
		writeTrashFile("dir1/a"
		writeTrashFile("dir2/b"
		git.add().addFilepattern(".").call();
		RevCommit commit1 = git.commit()
				.setMessage("files a & b modified commit 1").call();
		git.checkout().setName(TEST_BRANCH_NAME).call();
		git.branchCreate().setName("branch_2").call();
		git.checkout().setName("branch_2").call();
		writeTrashFile("dir1/a"
		writeTrashFile("dir2/b"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("files a & b modified commit 2").call();
		git.cherryPick().include(commit1).call();
		String[] conflictingFilenames = { "dir1/a"
		return conflictingFilenames;
	}

	protected String[] createDeletedConflict() throws Exception {
		git.checkout().setName(TEST_BRANCH_NAME).call();
		writeTrashFile("dir1/a"
		writeTrashFile("dir2/b"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("files a & b added").call();
		git.branchCreate().setName("branch_1").call();
		git.checkout().setName("branch_1").call();
		writeTrashFile("dir1/a"
		writeTrashFile("dir2/b"
		git.add().addFilepattern(".").call();
		RevCommit commit1 = git.commit()
				.setMessage("files a & b modified commit 1").call();
		git.checkout().setName(TEST_BRANCH_NAME).call();
		git.branchCreate().setName("branch_2").call();
		git.checkout().setName("branch_2").call();
		git.rm().addFilepattern("dir1/a").call();
		git.rm().addFilepattern("dir2/b").call();
		git.commit().setMessage("files a & b deleted commit 2").call();
		git.cherryPick().include(commit1).call();
		String[] conflictingFilenames = { "dir1/a"
		return conflictingFilenames;
	}

	protected String[] createUnstagedChanges() throws Exception {
		writeTrashFile("dir1/a"
		writeTrashFile("dir2/b"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("files a & b").call();
		writeTrashFile("dir1/a"
		writeTrashFile("dir2/b"
		String[] conflictingFilenames = { "dir1/a"
		return conflictingFilenames;
	}

	protected String[] createStagedChanges() throws Exception {
		String[] conflictingFilenames = createUnstagedChanges();
		git.add().addFilepattern(".").call();
		return conflictingFilenames;
	}

	protected List<DiffEntry> getRepositoryChanges(RevCommit commit)
			throws Exception {
		TreeWalk tw = new TreeWalk(db);
		tw.addTree(commit.getTree());
		FileTreeIterator modifiedTree = new FileTreeIterator(db);
		tw.addTree(modifiedTree);
		List<DiffEntry> changes = DiffEntry.scan(tw);
		return changes;
	}

	protected static InputStream createInputStream(String[] inputLines) {
		return createInputStream(Arrays.asList(inputLines));
	}

	protected static InputStream createInputStream(List<String> inputLines) {
		String input = String.join(System.lineSeparator()
		InputStream inputStream = new ByteArrayInputStream(input.getBytes());
		return inputStream;
	}

	protected static void assertArrayOfLinesEquals(String failMessage
			String[] expected
		assertEquals(failMessage
	}

	protected static String getEchoCommand() {
		return "(echo \"$MERGED\")";
	}
}
