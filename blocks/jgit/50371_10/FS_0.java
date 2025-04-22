
package org.eclipse.jgit.util;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jgit.junit.JGitTestUtil;
import org.junit.Before;
import org.junit.Test;

public class RunExternalScriptTest {
	private ByteArrayOutputStream out;

	private ByteArrayOutputStream err;

	private String sep = System.getProperty("line.separator");

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
				new ProcessBuilder("/bin/sh"
				new ByteArrayInputStream(inputStr.getBytes()));
		assertEquals(0
		assertEquals(inputStr
		assertEquals(""
	}

	@Test
	public void testCopyNullStdIn() throws IOException
		File script = writeTempFile("cat -");
		int rc = FS.DETECTED.runProcess(
				new ProcessBuilder("/bin/sh"
				(InputStream) null);
		assertEquals(0
		assertEquals(""
		assertEquals(""
	}

	@Test
	public void testArguments() throws IOException
		File script = writeTempFile("echo $#
		int rc = FS.DETECTED.runProcess(new ProcessBuilder("/bin/bash"
				script.getPath()
		assertEquals(0
		assertEquals("3
		assertEquals(""
	}

	@Test
	public void testRc() throws IOException
		File script = writeTempFile("exit 3");
		int rc = FS.DETECTED.runProcess(
				new ProcessBuilder("/bin/sh"
				out
		assertEquals(3
		assertEquals(""
		assertEquals(""
	}

	@Test
	public void testNullStdout() throws IOException
		File script = writeTempFile("echo hi");
		int rc = FS.DETECTED.runProcess(
				new ProcessBuilder("/bin/sh"
				(InputStream) null);
		assertEquals(0
		assertEquals(""
		assertEquals(""
	}

	@Test
	public void testStdErr() throws IOException
		File script = writeTempFile("echo hi >&2");
		int rc = FS.DETECTED.runProcess(
				new ProcessBuilder("/bin/sh"
				(InputStream) null);
		assertEquals(0
		assertEquals(""
		assertEquals("hi" + sep
	}

	@Test
	public void testAllTogetherBin() throws IOException
		String inputStr = "a\nb\rc\r\nd";
		File script = writeTempFile("echo $#
		int rc = FS.DETECTED.runProcess(
				new ProcessBuilder("/bin/sh"
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
				new ProcessBuilder("/bin/sh"
				out
		assertEquals(127
	}

	private File writeTempFile(String body) throws IOException {
		File f = File.createTempFile("RunProcessTestScript_"
		JGitTestUtil.write(f
		return f;
	}
}
