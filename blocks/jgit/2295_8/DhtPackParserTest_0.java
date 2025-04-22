
package org.eclipse.jgit.storage.dht;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.transport.PackedObjectInfo;
import org.eclipse.jgit.util.NB;
import org.junit.Test;

public class ChunkIndexTest {
	@Test
	public void testSingleObject_NotFound() throws DhtException {
		List<PackedObjectInfo> objs = list(object(1
		ChunkIndex idx = index(objs);
		assertEquals(-1
	}

	@Test
	public void testSingleObject_Offset1() throws DhtException {
		assertEquals(header(0

		List<PackedObjectInfo> objs = list(object(0x1200
		ChunkIndex idx = index(objs);

		assertEquals(header(0
		assertEquals(1
		assertEquals(2 + 20 + 1

		assertEquals(objs.get(0)
		assertEquals(objs.get(0).getOffset()
		assertEquals(objs.get(0).getOffset()
	}

	@Test
	public void testSingleObject_Offset2() throws DhtException {
		assertEquals(header(0

		List<PackedObjectInfo> objs = list(object(0x1200
		ChunkIndex idx = index(objs);

		assertEquals(header(0
		assertEquals(1
		assertEquals(2 + 20 + 2

		assertEquals(objs.get(0)
		assertEquals(objs.get(0).getOffset()
		assertEquals(objs.get(0).getOffset()
	}

	@Test
	public void testSingleObject_Offset3() throws DhtException {
		assertEquals(header(0

		List<PackedObjectInfo> objs = list(object(0x1200
		ChunkIndex idx = index(objs);

		assertEquals(header(0
		assertEquals(1
		assertEquals(2 + 20 + 3

		assertEquals(objs.get(0)
		assertEquals(objs.get(0).getOffset()
		assertEquals(objs.get(0).getOffset()
	}

	@Test
	public void testSingleObject_Offset4() throws DhtException {
		assertEquals(header(0

		List<PackedObjectInfo> objs = list(object(0x1200
		ChunkIndex idx = index(objs);

		assertEquals(header(0
		assertEquals(1
		assertEquals(objs.get(0)

		assertEquals(2 + 20 + 4
		assertEquals(objs.get(0).getOffset()
		assertEquals(objs.get(0).getOffset()
	}

	@Test
	public void testObjects3() throws DhtException {
		List<PackedObjectInfo> objs = objects(2
		ChunkIndex idx = index(objs);

		assertEquals(header(0
		assertEquals(3
		assertEquals(2 + 3 * 20 + 3 * 1
		assertTrue(isSorted(objs));

		for (int i = 0; i < objs.size(); i++) {
			assertEquals(objs.get(i)
			assertEquals(objs.get(i).getOffset()
			assertEquals(objs.get(i).getOffset()
		}
	}

	@Test
	public void testObjects255_SameBucket() throws DhtException {
		int[] ints = new int[255];
		for (int i = 0; i < 255; i++)
			ints[i] = i;
		List<PackedObjectInfo> objs = objects(ints);
		ChunkIndex idx = index(objs);

		assertEquals(header(1
		assertEquals(255
				+ 12 + 4 * 256
		assertTrue(isSorted(objs));

		for (int i = 0; i < objs.size(); i++) {
			assertEquals(objs.get(i)
			assertEquals(objs.get(i).getOffset()
			assertEquals(objs.get(i).getOffset()
		}
	}

	@Test
	public void testObjects512_ManyBuckets() throws DhtException {
		int[] ints = new int[512];
		for (int i = 0; i < 256; i++) {
			ints[i] = (i << 8) | 0;
			ints[i + 256] = (i << 8) | 1;
		}
		List<PackedObjectInfo> objs = objects(ints);
		ChunkIndex idx = index(objs);

		assertEquals(header(1
		assertEquals(512
				+ 12 + 4 * 256
		assertTrue(isSorted(objs));

		for (int i = 0; i < objs.size(); i++) {
			assertEquals(objs.get(i)
			assertEquals(objs.get(i).getOffset()
			assertEquals(objs.get(i).getOffset()
		}
	}

	@Test
	public void testFanout2() throws DhtException {
		List<PackedObjectInfo> objs = new ArrayList<PackedObjectInfo>(65280);
		MutableObjectId idBuf = new MutableObjectId();
		for (int i = 0; i < 256; i++) {
			idBuf.setByte(2
			for (int j = 0; j < 255; j++) {
				idBuf.setByte(3
				PackedObjectInfo oe = new PackedObjectInfo(idBuf);
				oe.setOffset((i << 8) | j);
				objs.add(oe);
			}
		}
		ChunkIndex idx = index(objs);

		assertEquals(header(2
		assertEquals(256 * 255
		assertTrue(isSorted(objs));

		for (int i = 0; i < objs.size(); i++) {
			assertEquals(objs.get(i)
			assertEquals(objs.get(i).getOffset()
			assertEquals(objs.get(i).getOffset()
		}
	}

	@Test
	public void testFanout3() throws DhtException {
		List<PackedObjectInfo> objs = new ArrayList<PackedObjectInfo>(1 << 16);
		MutableObjectId idBuf = new MutableObjectId();
		for (int i = 0; i < 256; i++) {
			idBuf.setByte(2
			for (int j = 0; j < 256; j++) {
				idBuf.setByte(3
				PackedObjectInfo oe = new PackedObjectInfo(idBuf);
				oe.setOffset((i << 8) | j);
				objs.add(oe);
			}
		}
		ChunkIndex idx = index(objs);

		assertEquals(header(3
		assertEquals(256 * 256
		assertTrue(isSorted(objs));

		for (int i = 0; i < objs.size(); i++) {
			assertEquals(objs.get(i)
			assertEquals(objs.get(i).getOffset()
			assertEquals(objs.get(i).getOffset()
		}
	}

	@Test
	public void testObjects65280_ManyBuckets() throws DhtException {
		List<PackedObjectInfo> objs = new ArrayList<PackedObjectInfo>(65280);
		MutableObjectId idBuf = new MutableObjectId();
		for (int i = 0; i < 256; i++) {
			idBuf.setByte(0
			for (int j = 0; j < 255; j++) {
				idBuf.setByte(3
				PackedObjectInfo oe = new PackedObjectInfo(idBuf);
				oe.setOffset((i << 8) | j);
				objs.add(oe);
			}
		}
		ChunkIndex idx = index(objs);

		assertEquals(header(1
		assertEquals(65280
		assertTrue(isSorted(objs));

		for (int i = 0; i < objs.size(); i++) {
			assertEquals(objs.get(i)
			assertEquals(objs.get(i).getOffset()
			assertEquals(objs.get(i).getOffset()
		}
	}

	private boolean isSorted(List<PackedObjectInfo> objs) {
		PackedObjectInfo last = objs.get(0);
		for (int i = 1; i < objs.size(); i++) {
			PackedObjectInfo oe = objs.get(i);
			if (oe.compareTo(last) <= 0)
				return false;
		}
		return true;
	}

	private List<PackedObjectInfo> list(PackedObjectInfo... all) {
		List<PackedObjectInfo> objs = new ArrayList<PackedObjectInfo>();
		for (PackedObjectInfo o : all)
			objs.add(o);
		return objs;
	}

	private int header(int fanoutTable
		return (0x01 << 8) | (fanoutTable << 3) | offsetTable;
	}

	private int header(List<PackedObjectInfo> objs) {
		byte[] index = ChunkIndex.create(objs);
		return NB.decodeUInt16(index
	}

	private ChunkIndex index(List<PackedObjectInfo> objs) throws DhtException {
		ChunkKey key = null;
		byte[] index = ChunkIndex.create(objs);
		return ChunkIndex.fromBytes(key
	}

	private List<PackedObjectInfo> objects(int... values) {
		List<PackedObjectInfo> objs = new ArrayList<PackedObjectInfo>();
		for (int i = 0; i < values.length; i++)
			objs.add(object(values[i]
		return objs;
	}

	private PackedObjectInfo object(int id
		MutableObjectId idBuf = new MutableObjectId();
		idBuf.setByte(0
		idBuf.setByte(1

		PackedObjectInfo obj = new PackedObjectInfo(idBuf);
		obj.setOffset(off);
		return obj;
	}
}
