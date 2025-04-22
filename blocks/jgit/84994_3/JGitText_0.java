
package org.eclipse.jgit.internal.storage.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.lib.Repository;
import org.junit.Test;

public class DescriptionTest extends LocalDiskRepositoryTestCase {
	private String unconfigured = "Unnamed repository; edit this file to name it for gitweb.";

	@Test
	public void description() throws IOException {
		Repository git = createBareRepository();
		File path = new File(git.getDirectory()
		assertNull("description"

		String desc = "a test repo\nfor jgit";
		git.setGitwebDescription(desc);
		assertEquals(desc + '\n'
		assertEquals(desc

		git.setGitwebDescription(null);
		assertEquals(""

		desc = "foo";
		git.setGitwebDescription(desc);
		assertEquals(desc + '\n'
		assertEquals(desc

		git.setGitwebDescription("");
		assertEquals(""

		git.setGitwebDescription(unconfigured);
		assertEquals(unconfigured + '\n'
		assertNull("description"
	}
}
