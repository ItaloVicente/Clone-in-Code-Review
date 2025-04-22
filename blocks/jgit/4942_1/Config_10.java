package org.eclipse.jgit.pgm;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.eclipse.jgit.util.SystemReader;
import org.junit.Before;
import org.junit.Test;

public class ConfigTest extends CLIRepositoryTestCase {
	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		new Git(db).commit().setMessage("initial commit").call();
	}

	@Test
	public void testListConfig() throws Exception {
		boolean isWindows = SystemReader.getInstance().getProperty("os.name")
				.startsWith("Windows");
		boolean isMac = SystemReader.getInstance().getProperty("os.name")
				.equals("Mac OS X");

		String[] output = execute("git config --list");
		List<String> expect = new ArrayList<String>();
		expect.add("core.filemode=" + !isWindows);
		expect.add("core.logallrefupdates=true");
		if (isMac)
			expect.add("core.precomposeunicode=true");
		expect.add("core.repositoryformatversion=0");
		assertArrayEquals("expected default configuration"
				output);
	}
}
