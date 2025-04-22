
package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.util.FileUtils;
import org.junit.Before;
import org.junit.Test;

public class NetRCTest extends RepositoryTestCase {
	private File home;

	private NetRC netrc;

	private File configFile;

	@Before
	public void setUp() throws Exception {
		super.setUp();

		home = new File(trash
		FileUtils.mkdir(home);

		configFile = new File(home
	}

	private void config(final String data) throws IOException {
		final OutputStreamWriter fw = new OutputStreamWriter(
				new FileOutputStream(configFile)
		fw.write(data);
		fw.close();
	}

	@Test
	public void testSeparatorParsing() throws Exception {
		config("machine github.com login axet password 2222"
				+ "\n"

				+ "#machine code.google.com login kuznetsov.alexey password 5555"
				+ "\n"

				+ "machine code.google2.com #login kuznetsov.alexey password 5555"
				+ "\n"

				+ "login kuznetsov.alexey password 6666" + "\n"

				+ "machine bitbucket.org login axet password 8888");

		netrc = new NetRC(configFile);

		assertEquals("axet"
		assertEquals("2222"
		assertNull(netrc.entry("code.google.com"));
		assertEquals("kuznetsov.alexey"
		assertEquals("6666"
				netrc.entry("code.google2.com").password));
		assertEquals("axet"
		assertEquals("8888"
	}
}
