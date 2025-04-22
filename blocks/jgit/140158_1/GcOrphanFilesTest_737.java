
package org.eclipse.jgit.internal.storage.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Iterator;

import org.eclipse.jgit.internal.storage.file.PackIndex.MutableEntry;
import org.eclipse.jgit.junit.TestRepository.BranchBuilder;
import org.junit.Test;

public class GcKeepFilesTest extends GcTestCase {
	@Test
	public void testKeepFiles() throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/master");
		bb.commit().add("A"
		stats = gc.getStatistics();
		assertEquals(4
		assertEquals(0
		assertEquals(0
		gc.gc();
		stats = gc.getStatistics();
		assertEquals(0
		assertEquals(4
		assertEquals(1

		Iterator<PackFile> packIt = repo.getObjectDatabase().getPacks()
				.iterator();
		PackFile singlePack = packIt.next();
		assertFalse(packIt.hasNext());
		String packFileName = singlePack.getPackFile().getPath();
		String keepFileName = packFileName.substring(0
				packFileName.lastIndexOf('.')) + ".keep";
		File keepFile = new File(keepFileName);
		assertFalse(keepFile.exists());
		assertTrue(keepFile.createNewFile());
		bb.commit().add("A"
		stats = gc.getStatistics();
		assertEquals(4
		assertEquals(4
		assertEquals(1
		gc.gc();
		stats = gc.getStatistics();
		assertEquals(0
		assertEquals(8
		assertEquals(2

		Iterator<PackFile> packs = repo.getObjectDatabase().getPacks()
				.iterator();
		PackIndex ind1 = packs.next().getIndex();
		assertEquals(4
		PackIndex ind2 = packs.next().getIndex();
		assertEquals(4
		for (MutableEntry e: ind1)
			if (ind2.hasObject(e.toObjectId()))
				assertFalse(
						"the following object is in both packfiles: "
								+ e.toObjectId()
						ind2.hasObject(e.toObjectId()));
	}
}
