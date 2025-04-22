
package org.eclipse.jgit.internal.storage.file;

import static org.junit.Assert.assertEquals;

import org.eclipse.jgit.junit.TestRepository.BranchBuilder;
import org.junit.Test;

public class GcDirCacheSavesObjectsTest extends GcTestCase {
	@Test
	public void testDirCacheSavesObjects() throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/master");
		bb.commit().add("A"
		bb.commit().add("A"
		bb.commit().add("A"
		stats = gc.getStatistics();
		assertEquals(9
		assertEquals(0
		gc.gc();
		stats = gc.getStatistics();
		assertEquals(1
		assertEquals(8
		assertEquals(1
	}

	@Test
	public void testDirCacheSavesObjectsWithPruneNow() throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/master");
		bb.commit().add("A"
		bb.commit().add("A"
		bb.commit().add("A"
		stats = gc.getStatistics();
		assertEquals(9
		assertEquals(0
		gc.setExpireAgeMillis(0);
		fsTick();
		gc.gc();
		stats = gc.getStatistics();
		assertEquals(0
		assertEquals(8
		assertEquals(1
	}
}
