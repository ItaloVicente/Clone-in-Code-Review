
package org.eclipse.jgit.internal.storage.file;

import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.transport.ReceiveCommand;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class RefDirectoryWritableSnapshotTest extends RefDirectoryTest {
	private RefDirectory originalRefDirectory;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		originalRefDirectory = refdir;
		refdir = refdir.createWritableSnapshot();
	}

	@Test
	public void testSnapshot_IncludeExternalPackedRefsUpdatesWithWrites()
			throws IOException {
		String refA = "refs/heads/refA";
		String refB = "refs/heads/refB";
				A.name() + " " + refB + "\n");
		assertEquals(A
		assertEquals(A

				A.name() + " " + refB + "\n");
		assertEquals(B
		assertEquals(A

		PackedBatchRefUpdate update = refdir.newBatchUpdate();
		update.addCommand(new ReceiveCommand(A
		update.execute(repo.getRevWalk()

		assertEquals(B
		assertEquals(B
		assertEquals(B
		assertEquals(B
	}
}
