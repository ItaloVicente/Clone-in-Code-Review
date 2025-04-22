
package org.eclipse.jgit.storage.dht;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.zip.Deflater;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ObjectStream;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.dht.spi.memory.MemoryDatabase;
import org.eclipse.jgit.util.IO;
import org.junit.Before;
import org.junit.Test;

public class LargeNonDeltaObjectTest {
	private MemoryDatabase db;

	@Before
	public void setUpDatabase() {
		db = new MemoryDatabase();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testInsertRead() throws IOException {
		DhtInserterOptions insopt = new DhtInserterOptions();
		insopt.setChunkSize(128);
		insopt.setCompression(Deflater.NO_COMPRESSION);

				.build();
		repo.create(true);

		byte[] data = new byte[insopt.getChunkSize() * 3];
		Arrays.fill(data

		ObjectInserter ins = repo.newObjectInserter();
		ObjectId id = ins.insert(Constants.OBJ_BLOB
		ins.flush();
		ins.release();

		ObjectReader reader = repo.newObjectReader();
		ObjectLoader ldr = reader.open(id);
		assertEquals(Constants.OBJ_BLOB
		assertEquals(data.length
		assertTrue(ldr.isLarge());

		byte[] dst = new byte[data.length];
		ObjectStream in = ldr.openStream();
		IO.readFully(in
		assertTrue(Arrays.equals(data
		in.close();

		dst = new byte[data.length];
		in = ldr.openStream();
		IO.readFully(in
		assertTrue(Arrays.equals(data
		in.close();

		reader.release();
	}
}
