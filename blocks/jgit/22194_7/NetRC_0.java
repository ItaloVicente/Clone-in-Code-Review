
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
	public void testNetRCFile() throws Exception {
		config("machine my.host login test password 2222"
				+ "\n"

				+ "#machine ignore.host.example login ignoreuser password 5555"
				+ "\n"

				+ "machine twolinehost.example #login kuznetsov.alexey password 5555"
				+ "\n"

				+ "login twologin password 6666"
				+ "\n"

				+ "machine afterlinehost.example login test1 password 8888"
				+ "\n"

				+ "machine macdef.example login test7 password 7777 macdef mac1"
				+ "\n"

				+ "init +apc 1" + "\n"

				+ "init2 +apc 2" + "\n");

		netrc = new NetRC(configFile);

		assertNull(netrc.getEntry("ignore.host.example"));

		assertNull(netrc.getEntry("cignore.host.example"));

		assertEquals("twologin"
		assertEquals("6666"
				netrc.getEntry("twolinehost.example").password));

		assertEquals("test1"
		assertEquals("8888"
				netrc.getEntry("afterlinehost.example").password));

		assertEquals("test7"
		assertEquals("7777"
				netrc.getEntry("macdef.example").password));
		assertEquals("init +apc 1" + "\n" + "init2 +apc 2" + "\n"
				netrc.getEntry("macdef.example").macbody);
	}

	@Test
	public void testNetRCDefault() throws Exception {
		config("machine my.host login test password 2222"
				+ "\n"

				+ "#machine ignore.host.example login ignoreuser password 5555"
				+ "\n"

				+ "machine twolinehost.example #login kuznetsov.alexey password 5555"
				+ "\n"

				+ "login twologin password 6666"
				+ "\n"

				+ "machine afterlinehost.example login test1 password 8888"
				+ "\n"

				+ "machine macdef.example login test7 password 7777 macdef mac1"
				+ "\n"

				+ "init +apc 1" + "\n"

				+ "init2 +apc 2" + "\n"

				+ "\n"

				+ "default login test5 password 3333" + "\n"

		);

		netrc = new NetRC(configFile);

		assertEquals("test5"
		assertEquals("3333"
				netrc.getEntry("ignore.host.example").password));

		assertEquals("test5"
				netrc.getEntry("ignore.host.example").login));
		assertEquals("3333"
				netrc.getEntry("ignore.host.example").password));

		assertEquals("test5"
		assertEquals("3333"
				netrc.getEntry("cignore.host.example").password));

		assertEquals("twologin"
		assertEquals("6666"
				netrc.getEntry("twolinehost.example").password));

		assertEquals("test1"
		assertEquals("8888"
				netrc.getEntry("afterlinehost.example").password));

		assertEquals("test7"
		assertEquals("7777"
				netrc.getEntry("macdef.example").password));
		assertEquals("init +apc 1" + "\n" + "init2 +apc 2" + "\n"
				netrc.getEntry("macdef.example").macbody);
	}
}
