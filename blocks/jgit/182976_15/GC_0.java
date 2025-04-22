
package org.eclipse.jgit.internal.storage.file;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;

public class GcCommitGraphTest extends GcTestCase {

	@Test
	public void testWriteEmptyRepo() throws Exception {
		StoredConfig config = repo.getConfig();
		config.setBoolean(ConfigConstants.CONFIG_GC_SECTION
				ConfigConstants.CONFIG_KEY_WRITE_COMMIT_GRAPH

		assertTrue(gc.willWriteCommitGraph());
		gc.writeCommitGraph();
		File graphFile = new File(repo.getObjectsDirectory()
				Constants.INFO_COMMIT_GRAPH);
		assertFalse(graphFile.exists());
	}

	@Test
	public void testWriteWhenGc() throws Exception {
		StoredConfig config = repo.getConfig();
		config.setBoolean(ConfigConstants.CONFIG_GC_SECTION
				ConfigConstants.CONFIG_KEY_WRITE_COMMIT_GRAPH

		RevCommit tip = commitChain(10);
		TestRepository.BranchBuilder bb = tr.branch("refs/heads/master");
		bb.update(tip);

		assertTrue(gc.willWriteCommitGraph());
		gc.gc();
		File graphFile = new File(repo.getObjectsDirectory()
				Constants.INFO_COMMIT_GRAPH);
		assertTrue(graphFile.exists());
	}

	@Test
	public void testDefaultWriteWhenGc() throws Exception {
		RevCommit tip = commitChain(10);
		TestRepository.BranchBuilder bb = tr.branch("refs/heads/master");
		bb.update(tip);

		assertFalse(gc.willWriteCommitGraph());
		gc.gc();
		File graphFile = new File(repo.getObjectsDirectory()
				Constants.INFO_COMMIT_GRAPH);
		assertFalse(graphFile.exists());
	}
}
