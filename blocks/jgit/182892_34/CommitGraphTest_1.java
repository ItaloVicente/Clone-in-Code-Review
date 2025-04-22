
package org.eclipse.jgit.internal.storage.commitgraph;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.jgit.internal.storage.commitgraph.CommitGraph.CommitData;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.lib.ObjectId;
import org.junit.Test;

public class CommitGraphLoaderTest {

	private CommitGraph commitGraph;

	@Test
	public void readCommitGraphV1() throws Exception {
		commitGraph = CommitGraphLoader
				.open(JGitTestUtil.getTestResourceFile("commit-graph.v1"));
		assertNotNull(commitGraph);
		assertEquals(10
		verifyGraphObjectIndex();

		assertCommitData("85b0176af27fa1640868f061f224d01e0b295f59"
				new int[] { 5
		assertCommitData("d4f7c00aab3f0160168c9e5991abb6194a4e0d9e"
				new int[] {}
		assertCommitData("4d03aaf9c20c97d6ccdc05cb7f146b1deb6c01d5"
				new int[] { 5 }
		assertCommitData("a2f409b753880bf83b18bfb433dd340a6185e8be"
				new int[] { 7 }
		assertCommitData("431343847343979bbe31127ed905a24fed9a636c"
				new int[] { 3
		assertCommitData("c3f745ad8928ef56b5dbf33740fc8ede6b598290"
				new int[] { 1 }
		assertCommitData("95b12422c8ea4371e54cd58925eeed9d960ff1f0"
				new int[] { 1 }
		assertCommitData("de0ea882503cdd9c984c0a43238014569a123cac"
				new int[] { 1 }
		assertCommitData("102c9d6481559b1a113eb66bf55085903de6fb00"
				new int[] { 6 }
		assertCommitData("b5de2a84867f8ffc6321649dabf8c0680661ec03"
				new int[] { 7
	}

	private void verifyGraphObjectIndex() {
		for (int i = 0; i < commitGraph.getCommitCnt(); i++) {
			ObjectId id = commitGraph.getObjectId(i);
			int pos = commitGraph.findGraphPosition(id);
			assertEquals(i
		}
	}

	private void assertCommitData(String expectedTree
			long expectedCommitTime
		CommitData commitData = commitGraph.getCommitData(graphPos);
		assertEquals(ObjectId.fromString(expectedTree)
		assertArrayEquals(expectedParents
		assertEquals(expectedCommitTime
		assertEquals(expectedGeneration
	}
}
