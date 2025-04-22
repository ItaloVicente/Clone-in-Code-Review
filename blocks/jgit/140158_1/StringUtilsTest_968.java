
package org.eclipse.jgit.util;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.util.FS.ExecutionResult;
import org.junit.Before;
import org.junit.Test;

public class RunExternalScriptTest {
	private static final String LF = "\n";

	private ByteArrayOutputStream out;

	private ByteArrayOutputStream err;

	@Before
	public void setUp() throws Exception {
		out = new ByteArrayOutputStream();
		err = new ByteArrayOutputStream();
	}

	@Test
	public void testCopyStdIn() throws IOException
		String inputStr = "a\nb\rc\r\nd";
		File script = writeTempFile("cat -");
		int rc = FS.DETECTED.runProcess(
				new ProcessBuilder("sh"
				new ByteArrayInputStream(inputStr.getBytes(UTF_8)));
		assertEquals(0
		assertEquals(inputStr
		assertEquals(""
	}

	@Test
	public void testCopyNullStdIn() throws IOException
		File script = writeTempFile("cat -");
		int rc = FS.DETECTED.runProcess(
				new ProcessBuilder("sh"
				(InputStream) null);
		assertEquals(0
		assertEquals(""
		assertEquals(""
	}

	@Test
	public void testArguments() throws IOException
		File script = writeTempFile("echo $#
		int rc = FS.DETECTED.runProcess(
				new ProcessBuilder("sh"
				script.getPath()
		assertEquals(0
		assertEquals("3
		assertEquals(""
	}

	@Test
	public void testRc() throws IOException
		File script = writeTempFile("exit 3");
		int rc = FS.DETECTED.runProcess(
				new ProcessBuilder("sh"
				out
		assertEquals(3
		assertEquals(""
		assertEquals(""
	}

	@Test
	public void testNullStdout() throws IOException
		File script = writeTempFile("echo hi");
		int rc = FS.DETECTED.runProcess(
				new ProcessBuilder("sh"
				(InputStream) null);
		assertEquals(0
		assertEquals(""
		assertEquals(""
	}

	@Test
	public void testStdErr() throws IOException
		File script = writeTempFile("echo hi >&2");
		int rc = FS.DETECTED.runProcess(
				new ProcessBuilder("sh"
				(InputStream) null);
		assertEquals(0
		assertEquals(""
		assertEquals("hi" + LF
	}

	@Test
	public void testAllTogetherBin() throws IOException
		String inputStr = "a\nb\rc\r\nd";
		File script = writeTempFile("echo $#
		int rc = FS.DETECTED.runProcess(
				new ProcessBuilder("sh"
				out
		assertEquals(5
		assertEquals(inputStr
		assertEquals("3
		FS.DETECTED.runProcess(
				new ProcessBuilder("/bin/sh-foo"
						"c")
	}

	@Test
	public void testWrongScript() throws IOException
		File script = writeTempFile("cat-foo -");
		int rc = FS.DETECTED.runProcess(
				new ProcessBuilder("sh"
				out
		assertEquals(127
	}

	@Test
	public void testCopyStdInExecute()
			throws IOException
		String inputStr = "a\nb\rc\r\nd";
		File script = writeTempFile("cat -");
		ProcessBuilder pb = new ProcessBuilder("sh"
		ExecutionResult res = FS.DETECTED.execute(pb
				new ByteArrayInputStream(inputStr.getBytes(UTF_8)));
		assertEquals(0
		assertEquals(inputStr
				new String(res.getStdout().toByteArray()
		assertEquals(""
	}

	@Test
	public void testStdErrExecute() throws IOException
		File script = writeTempFile("echo hi >&2");
		ProcessBuilder pb = new ProcessBuilder("sh"
		ExecutionResult res = FS.DETECTED.execute(pb
		assertEquals(0
		assertEquals(""
		assertEquals("hi" + LF
				new String(res.getStderr().toByteArray()
	}

	@Test
	public void testAllTogetherBinExecute()
			throws IOException
		String inputStr = "a\nb\rc\r\nd";
		File script = writeTempFile(
				"echo $#
		ProcessBuilder pb = new ProcessBuilder("sh"
				"b"
		ExecutionResult res = FS.DETECTED.execute(pb
				new ByteArrayInputStream(inputStr.getBytes(UTF_8)));
		assertEquals(5
		assertEquals(inputStr
				new String(res.getStdout().toByteArray()
		assertEquals("3
		JGitTestUtil.write(f
		return f;
	}
}
