
package org.eclipse.jgit.internal.storage.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.File;
import org.eclipse.jgit.internal.storage.pack.PackExt;
import org.junit.Test;

public class PackFileTest {
	private static final String TEST_ID = "0123456789012345678901234567890123456789";

	private static final String PREFIX = "pack-";

	private static final String OLD_PREFIX = "old-";

	private static final String OLD_PACK = PREFIX + TEST_ID + "." + OLD_PREFIX
			+ PackExt.PACK.getExtension();

	private static final File TEST_PACK_DIR = new File(
			"/path/to/repo.git/objects/pack");

	private static final File TEST_PRESERVED_DIR = new File(TEST_PACK_DIR
			"preserved");

	private static final PackFile TEST_PACKFILE_NO_EXT = new PackFile(
			new File(TEST_PACK_DIR

	@Test
	public void idIsSameFromFileOrDirAndName() throws Exception {
		File pack = new File(TEST_PACK_DIR
		PackFile pf = new PackFile(pack);
		PackFile pfFromDirAndName = new PackFile(TEST_PACK_DIR
				PREFIX + TEST_ID);
		assertEquals(pf.getId()
	}

	@Test
	public void idIsSameFromFileWithOrWithoutExt() throws Exception {
		PackFile packWithExt = new PackFile(new File(TEST_PACK_DIR
				PREFIX + TEST_ID + "." + PackExt.PACK.getExtension()));
		assertEquals(packWithExt.getId()
	}

	@Test
	public void idIsSameFromFileWithOrWithoutPrefix() throws Exception {
		PackFile packWithoutPrefix = new PackFile(
				new File(TEST_PACK_DIR
		assertEquals(packWithoutPrefix.getId()
				TEST_PACKFILE_NO_EXT.getId());
	}

	@Test
	public void canCreatePreservedFromFile() throws Exception {
		PackFile preserved = new PackFile(
				new File(TEST_PRESERVED_DIR
		assertTrue(preserved.getName().contains(OLD_PACK));
		assertEquals(preserved.getId()
		assertEquals(preserved.getPackExt()
	}

	@Test
	public void canCreatePreservedFromDirAndName() throws Exception {
		PackFile preserved = new PackFile(TEST_PRESERVED_DIR
		assertTrue(preserved.getName().contains(OLD_PACK));
		assertEquals(preserved.getId()
		assertEquals(preserved.getPackExt()
	}

	@Test
	public void cannotCreatePreservedNoExtFromNonPreservedNoExt()
			throws Exception {
		assertThrows(IllegalArgumentException.class
				() -> TEST_PACKFILE_NO_EXT
						.createPreservedForDirectory(TEST_PRESERVED_DIR));
	}

	@Test
	public void canCreateAnyExtFromAnyExt() throws Exception {
		for (PackExt from : PackExt.values()) {
			PackFile dotFrom = TEST_PACKFILE_NO_EXT.create(from);
			for (PackExt to : PackExt.values()) {
				PackFile dotTo = dotFrom.create(to);
				File expected = new File(TEST_PACK_DIR
						PREFIX + TEST_ID + "." + to.getExtension());
				assertEquals(dotTo.getPackExt()
				assertEquals(dotFrom.getId()
				assertEquals(expected.getName()
			}
		}
	}

	@Test
	public void canCreatePreservedFromAnyExt() throws Exception {
		for (PackExt ext : PackExt.values()) {
			PackFile nonPreserved = TEST_PACKFILE_NO_EXT.create(ext);
			PackFile preserved = nonPreserved
					.createPreservedForDirectory(TEST_PRESERVED_DIR);
			File expected = new File(TEST_PRESERVED_DIR
					PREFIX + TEST_ID + "." + OLD_PREFIX + ext.getExtension());
			assertEquals(preserved.getName()
			assertEquals(preserved.getId()
			assertEquals(preserved.getPackExt()
		}
	}

	@Test
	public void canCreateAnyPreservedExtFromAnyPreservedExt() throws Exception {
		PackFile preserved = new PackFile(TEST_PRESERVED_DIR
		for (PackExt from : PackExt.values()) {
			PackFile preservedWithExt = preserved.create(from);
			for (PackExt to : PackExt.values()) {
				PackFile preservedNewExt = preservedWithExt.create(to);
				File expected = new File(TEST_PRESERVED_DIR
						+ "." + OLD_PREFIX + to.getExtension());
				assertEquals(preservedNewExt.getPackExt()
				assertEquals(preservedWithExt.getId()
				assertEquals(preservedNewExt.getName()
			}
		}
	}

	@Test
	public void canCreateNonPreservedFromAnyPreservedExt() throws Exception {
		PackFile preserved = new PackFile(TEST_PRESERVED_DIR
		for (PackExt ext : PackExt.values()) {
			PackFile preservedWithExt = preserved.create(ext);
			PackFile nonPreserved = preservedWithExt
					.createForDirectory(TEST_PACK_DIR);
			File expected = new File(TEST_PACK_DIR
					PREFIX + TEST_ID + "." + ext.getExtension());
			assertEquals(nonPreserved.getName()
			assertEquals(nonPreserved.getId()
			assertEquals(nonPreserved.getPackExt()
					preservedWithExt.getPackExt());
		}
	}
}
