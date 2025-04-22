package org.eclipse.jgit.pgm;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class EmbeddedCommandRunnerTest {

	@Test
	public void testHelp() throws Exception {
		ByteArrayInputStream in = new ByteArrayInputStream(new byte[0]);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayOutputStream err = new ByteArrayOutputStream();
		try {
			execute(in
		} catch (Die die) {
		}
		System.err.println(err.toString());
		assertTrue(err.toString().contains("jgit checkout"));
	}

	@Test
	public void testInit() throws Exception {
		ByteArrayInputStream in = new ByteArrayInputStream(new byte[0]);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayOutputStream err = new ByteArrayOutputStream();
		try {
			execute(in
			execute(in
		} finally {
			System.out.println(out.toString());
			System.err.println(err.toString());
		}
	}

	private void execute(InputStream in
		new EmbeddedCommandRunner().execute(args
	}
}
