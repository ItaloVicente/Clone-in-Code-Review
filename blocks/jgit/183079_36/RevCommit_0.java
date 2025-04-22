
package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.eclipse.jgit.internal.storage.file.GC;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.junit.Test;

public class RevWalkCommitGraphTest extends RevWalkTestCase {

	@Test
	public void testParseHeadersInGraph() throws Exception {
		RevCommit c1 = commitFile("file1"

		enableAndWriteCommitGraph();

		rw.setRetainBody(true);
		rw.dispose();
		RevCommit notParseInGraph = rw.lookupCommit(c1);
		rw.parseHeaders(notParseInGraph);
		assertNotNull(notParseInGraph.getRawBuffer());
		assertEquals(Constants.COMMIT_GENERATION_UNKNOWN
				notParseInGraph.getGeneration());

		rw.dispose();
		RevCommit parseInGraph = rw.lookupCommit(c1);
		rw.parseHeadersInGraph(parseInGraph);
		assertNull(parseInGraph.getRawBuffer());
		assertEquals(1

		assertEquals(notParseInGraph.getId()
		assertEquals(notParseInGraph.getTree()
		assertEquals(notParseInGraph.getCommitTime()
		assertArrayEquals(notParseInGraph.getParents()
	}

	void enableAndWriteCommitGraph() throws Exception {
		db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_COMMIT_GRAPH_SECTION
		db.getConfig().setBoolean(ConfigConstants.CONFIG_GC_SECTION
				ConfigConstants.CONFIG_KEY_WRITE_COMMIT_GRAPH
		GC gc = new GC(db);
		gc.gc();
		assertNotNull(rw.getCommitGraph());
	}
}
