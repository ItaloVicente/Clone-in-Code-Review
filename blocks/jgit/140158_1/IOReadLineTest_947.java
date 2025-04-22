package org.eclipse.jgit.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.AbortedByHookException;
import org.eclipse.jgit.hooks.CommitMsgHook;
import org.eclipse.jgit.hooks.PostCommitHook;
import org.eclipse.jgit.hooks.PreCommitHook;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Assume;
import org.junit.Test;

public class HookTest extends RepositoryTestCase {

	@Test
	public void testFindHook() throws Exception {
		assumeSupportedPlatform();

		assertNull("no hook should be installed"
				FS.DETECTED.findHook(db
		File hookFile = writeHookFile(PreCommitHook.NAME
				"#!/bin/bash\necho \"test $1 $2\"");
		assertEquals("expected to find pre-commit hook"
				FS.DETECTED.findHook(db
	}

	@Test
	public void testFindPostCommitHook() throws Exception {
		assumeSupportedPlatform();

		assertNull("no hook should be installed"
				FS.DETECTED.findHook(db
		File hookFile = writeHookFile(PostCommitHook.NAME
				"#!/bin/bash\necho \"test $1 $2\"");
		assertEquals("expected to find post-commit hook"
				FS.DETECTED.findHook(db
	}

	@Test
	public void testFailedCommitMsgHookBlocksCommit() throws Exception {
		assumeSupportedPlatform();

		writeHookFile(CommitMsgHook.NAME
				"#!/bin/sh\necho \"test\"\n\necho 1>&2 \"stderr\"\nexit 1");
		Git git = Git.wrap(db);
		String path = "a.txt";
		writeTrashFile(path
		git.add().addFilepattern(path).call();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			git.commit().setMessage("commit")
					.setHookOutputStream(new PrintStream(out)).call();
			fail("expected commit-msg hook to abort commit");
		} catch (AbortedByHookException e) {
			assertEquals("unexpected error message from commit-msg hook"
					"Rejected by \"commit-msg\" hook.\nstderr\n"
					e.getMessage());
			assertEquals("unexpected output from commit-msg hook"
					out.toString());
		}
	}

	@Test
	public void testCommitMsgHookReceivesCorrectParameter() throws Exception {
		assumeSupportedPlatform();

		writeHookFile(CommitMsgHook.NAME
				"#!/bin/sh\necho $1\n\necho 1>&2 \"stderr\"\nexit 0");
		Git git = Git.wrap(db);
		String path = "a.txt";
		writeTrashFile(path
		git.add().addFilepattern(path).call();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		git.commit().setMessage("commit")
				.setHookOutputStream(new PrintStream(out)).call();
		assertEquals(".git/COMMIT_EDITMSG\n"
				out.toString("UTF-8"));
	}

	@Test
	public void testCommitMsgHookCanModifyCommitMessage() throws Exception {
		assumeSupportedPlatform();

		writeHookFile(CommitMsgHook.NAME
				"#!/bin/sh\necho \"new message\" > $1\nexit 0");
		Git git = Git.wrap(db);
		String path = "a.txt";
		writeTrashFile(path
		git.add().addFilepattern(path).call();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		RevCommit revCommit = git.commit().setMessage("commit")
				.setHookOutputStream(new PrintStream(out)).call();
		assertEquals("new message\n"
	}

	@Test
	public void testPostCommitRunHook() throws Exception {
		assumeSupportedPlatform();

		writeHookFile(PostCommitHook.NAME
				"#!/bin/sh\necho \"test $1 $2\"\nread INPUT\necho $INPUT\necho 1>&2 \"stderr\"");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayOutputStream err = new ByteArrayOutputStream();
		ProcessResult res = FS.DETECTED.runHookIfPresent(db
				PostCommitHook.NAME
				new String[] {
				"arg1"
				new PrintStream(out)

		assertEquals("unexpected hook output"
				out.toString("UTF-8"));
		assertEquals("unexpected output on stderr stream"
				err.toString("UTF-8"));
		assertEquals("unexpected exit code"
		assertEquals("unexpected process status"
				res.getStatus());
	}

	@Test
	public void testAllCommitHooks() throws Exception {
		assumeSupportedPlatform();

		writeHookFile(PreCommitHook.NAME
				"#!/bin/sh\necho \"test pre-commit\"\n\necho 1>&2 \"stderr pre-commit\"\nexit 0");
		writeHookFile(CommitMsgHook.NAME
				"#!/bin/sh\necho \"test commit-msg $1\"\n\necho 1>&2 \"stderr commit-msg\"\nexit 0");
		writeHookFile(PostCommitHook.NAME
				"#!/bin/sh\necho \"test post-commit\"\necho 1>&2 \"stderr post-commit\"\nexit 0");
		Git git = Git.wrap(db);
		String path = "a.txt";
		writeTrashFile(path
		git.add().addFilepattern(path).call();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			git.commit().setMessage("commit")
					.setHookOutputStream(new PrintStream(out)).call();
		} catch (AbortedByHookException e) {
			fail("unexpected hook failure");
		}
		assertEquals("unexpected hook output"
				"test pre-commit\ntest commit-msg .git/COMMIT_EDITMSG\ntest post-commit\n"
				out.toString("UTF-8"));
	}

	@Test
	public void testRunHook() throws Exception {
		assumeSupportedPlatform();

		writeHookFile(PreCommitHook.NAME
				"#!/bin/sh\necho \"test $1 $2\"\nread INPUT\necho $INPUT\n"
						+ "echo $GIT_DIR\necho $GIT_WORK_TREE\necho 1>&2 \"stderr\"");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayOutputStream err = new ByteArrayOutputStream();
		ProcessResult res = FS.DETECTED.runHookIfPresent(db
				PreCommitHook.NAME
				new String[] {
				"arg1"
				new PrintStream(out)

		assertEquals("unexpected hook output"
				"test arg1 arg2\nstdin\n" + db.getDirectory().getAbsolutePath()
						+ '\n' + db.getWorkTree().getAbsolutePath() + '\n'
				out.toString("UTF-8"));
		assertEquals("unexpected output on stderr stream"
				err.toString("UTF-8"));
		assertEquals("unexpected exit code"
		assertEquals("unexpected process status"
				res.getStatus());
	}

	@Test
	public void testFailedPreCommitHookBlockCommit() throws Exception {
		assumeSupportedPlatform();

		writeHookFile(PreCommitHook.NAME
				"#!/bin/sh\necho \"test\"\n\necho 1>&2 \"stderr\"\nexit 1");
		Git git = Git.wrap(db);
		String path = "a.txt";
		writeTrashFile(path
		git.add().addFilepattern(path).call();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			git.commit().setMessage("commit")
					.setHookOutputStream(new PrintStream(out)).call();
			fail("expected pre-commit hook to abort commit");
		} catch (AbortedByHookException e) {
			assertEquals("unexpected error message from pre-commit hook"
					"Rejected by \"pre-commit\" hook.\nstderr\n"
					e.getMessage());
			assertEquals("unexpected output from pre-commit hook"
					out.toString());
		}
	}

	private File writeHookFile(String name
			throws IOException {
		File path = new File(db.getWorkTree() + "/.git/hooks/"
		JGitTestUtil.write(path
		FS.DETECTED.setExecute(path
		return path;
	}

	private void assumeSupportedPlatform() {
		Assume.assumeTrue(FS.DETECTED instanceof FS_POSIX
				|| FS.DETECTED instanceof FS_Win32_Cygwin);
	}
}
