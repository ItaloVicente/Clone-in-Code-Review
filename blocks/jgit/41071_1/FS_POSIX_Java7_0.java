package org.eclipse.jgit.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.RejectCommitException;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.junit.Assume;
import org.junit.Test;

public class HookTest extends RepositoryTestCase {

	@Test
	public void testFindHook() throws Exception {
		assumeSupportedPlatform();

		Hook h = Hook.PRE_COMMIT;
		assertNull("no hook should be installed"
		File hookFile = writeHookFile(h.getName()
				"#!/bin/bash\necho \"test $1 $2\"");
		assertEquals("exected to find pre-commit hook"
				FS.DETECTED.findHook(db
	}

	@Test
	public void testRunHook() throws Exception {
		assumeSupportedPlatform();

		Hook h = Hook.PRE_COMMIT;
		writeHookFile(
				h.getName()
				"#!/bin/sh\necho \"test $1 $2\"\nread INPUT\necho $INPUT\necho 1>&2 \"stderr\"");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayOutputStream err = new ByteArrayOutputStream();
		ProcessResult res = FS.DETECTED.runIfPresent(db
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
	public void testPreCommitHook() throws Exception {
		assumeSupportedPlatform();

		Hook h = Hook.PRE_COMMIT;
		writeHookFile(h.getName()
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
		} catch (RejectCommitException e) {
			assertEquals("unexpected error message from pre-commit hook"
					"Commit rejected by \"pre-commit\" hook.\nstderr\n"
					e.getMessage());
			assertEquals("unexpected output from pre-commit hook"
					out.toString());
		} catch (Throwable e) {
			fail("unexpected exception thrown by pre-commit hook: " + e);
		}
	}

	private File writeHookFile(final String name
			throws IOException {
		File path = new File(db.getWorkTree() + "/.git/hooks/"
		JGitTestUtil.write(path
		FS.DETECTED.setExecute(path
		return path;
	}

	private void assumeSupportedPlatform() {
		Assume.assumeTrue(FS.DETECTED instanceof FS_POSIX
				|| FS.DETECTED instanceof FS_Win32_Java7Cygwin);
	}
}
