
package org.eclipse.jgit.pgm;

import static org.junit.Assert.assertArrayEquals;

import java.io.File;

import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.eclipse.jgit.lib.Constants;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class InitTest extends CLIRepositoryTestCase {

	@Rule
	public final TemporaryFolder tempFolder = new TemporaryFolder();

	@Test
	public void testInitBare() throws Exception {
		File directory = tempFolder.getRoot();

		String[] result = execute(
				"git init '" + directory.getCanonicalPath() + "' --bare");

		String[] expecteds = new String[] {
				"Initialized empty Git repository in "
						+ directory.getCanonicalPath()
				"" };
		assertArrayEquals(expecteds
	}

	@Test
	public void testInitDirectory() throws Exception {
		File workDirectory = tempFolder.getRoot();
		File gitDirectory = new File(workDirectory

		String[] result = execute(
				"git init '" + workDirectory.getCanonicalPath() + "'");

		String[] expecteds = new String[] {
				"Initialized empty Git repository in "
						+ gitDirectory.getCanonicalPath()
				"" };
		assertArrayEquals(expecteds
	}

}
