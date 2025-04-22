
package org.eclipse.jgit.subtree;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;

public class SubtreeAnalyzerTest extends SubtreeTestCase {

	@Test
	public void simpleParent() throws Exception {

		RevCommit A = commit(tree(file("a"
		RevCommit B = commit(tree(file("b"
		RevCommit C = subtreeAdd("suba"

		SubtreeAnalyzer sa = new SubtreeAnalyzer(db);
		Map<String

		assertEquals(1
		assertEquals(B
	}

}
