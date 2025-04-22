package org.eclipse.jgit.pgm;

import static org.junit.Assert.assertArrayEquals;

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

		String[] output = execute("git config --list");
		assertArrayEquals("expected default configuration"
				new String[] { "core.filemode=" + !isWindows
						"core.logallrefupdates=true"
						"core.repositoryformatversion=0"
	}
}
