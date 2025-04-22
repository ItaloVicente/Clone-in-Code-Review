package org.eclipse.jgit.pgm;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.SystemReader;
import org.junit.Before;
import org.junit.Test;

public class ConfigTest extends CLIRepositoryTestCase {
	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		try (Git git = new Git(db)) {
			git.commit().setMessage("initial commit").call();
		}
	}

	@Test
	public void testListConfig() throws Exception {
		boolean isWindows = SystemReader.getInstance().getProperty("os.name")
				.startsWith("Windows");
		boolean isMac = SystemReader.getInstance().getProperty("os.name")
				.equals("Mac OS X");

		String[] output = execute("git config --list");
		List<String> expect = new ArrayList<>();
		expect.add("gc.autoDetach=false");
		expect.add("core.filemode=" + !isWindows);
		expect.add("core.logallrefupdates=true");
		if (isMac)
			expect.add("core.precomposeunicode=true");
		expect.add("core.repositoryformatversion=0");
		if (!FS.DETECTED.supportsSymlinks())
			expect.add("core.symlinks=false");
		assertEquals("expected default configuration"
				Arrays.asList(expect.toArray()).toString()
				Arrays.asList(output).toString());
	}

}
