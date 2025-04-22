package org.eclipse.jgit.pgm;

import static org.junit.Assert.assertArrayEquals;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.CLIRepositoryTestCase;
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
		String[] output = execute("git config --list");
		assertArrayEquals("expected default configuration"
				new String[] { "core.autocrlf=false"
						"core.filemode=true"
						"core.logallrefupdates=true"
						"core.repositoryformatversion=0"
	}
}
