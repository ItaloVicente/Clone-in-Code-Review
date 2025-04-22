
package org.eclipse.jgit.internal.storage.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.test.resources.SampleDataRepositoryTestCase;
import org.junit.Test;

public class T0004_PackReaderTest extends SampleDataRepositoryTestCase {
	private static final String PACK_NAME = "34be9032ac282b11fa9babdc2b2a93ca996c9c2f";

	@Test
	public void test003_lookupCompressedObject() throws IOException {
		final ObjectId id;
		final ObjectLoader or;

		PackFile pr = null;
		for (PackFile p : db.getObjectDatabase().getPacks()) {
			if (PACK_NAME.equals(p.getPackName())) {
				pr = p;
				break;
			}
		}
		assertNotNull("have pack-" + PACK_NAME

		id = ObjectId.fromString("902d5476fa249b7abc9d84c611577a81381f0327");
		or = pr.get(new WindowCursor(null)
		assertNotNull(or);
		assertEquals(Constants.OBJ_TREE
		assertEquals(35
		pr.close();
	}

	@Test
	public void test004_lookupDeltifiedObject() throws IOException {
		final ObjectId id;
		final ObjectLoader or;

		id = ObjectId.fromString("5b6e7c66c276e7610d4a73c70ec1a1f7c1003259");
		or = db.open(id);
		assertNotNull(or);
		assertEquals(Constants.OBJ_BLOB
		assertEquals(18009
	}
}
