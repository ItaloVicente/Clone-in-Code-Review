package org.eclipse.jgit.internal.storage.file;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.transport.PackedObjectInfo;
import org.eclipse.jgit.util.BlockList;
import org.junit.Test;

public class PackObjectSizeIndexV1Test {
	private static final ObjectId OBJ_ID = ObjectId
			.fromString("b8b1d53172fb3fb19647adce4b38fab4371c2454");

	private static final long GB = 1 << 30;

	private static final int MAX_24BITS_UINT = 0xffffff;

	@Test
	public void write_24bPositions_32bSizes() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PackObjectSizeIndexWriter writer = PackObjectSizeIndexWriter
				.createWriter(out
		List<PackedObjectInfo> objs = new ArrayList<>();
		objs.add(blobWithSize(100));
		objs.add(blobWithSize(400));
		objs.add(blobWithSize(200));

		writer.write(objs);
		byte[] expected = new byte[] { -1
				0x01
				0x00
				0x00
				0x18
				0x00
				0x00
				0x00
				0x00
				0x00
				0x00
				0x00
				0x00
				0x00
		};
		byte[] output = out.toByteArray();
		assertArrayEquals(expected
	}

	@Test
	public void write_32bPositions_32bSizes() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PackObjectSizeIndexWriter writer = PackObjectSizeIndexWriter
				.createWriter(out
		List<PackedObjectInfo> objs = new BlockList<>(9_000_000);
		PackedObjectInfo commit = objInfo(Constants.OBJ_COMMIT
		for (int i = 0; i <= MAX_24BITS_UINT; i++) {
			objs.add(commit);
		}
		objs.add(blobWithSize(100));
		objs.add(blobWithSize(400));
		objs.add(blobWithSize(200));

		writer.write(objs);
		byte[] expected = new byte[] { -1
				0x01
				0x00
				0x00
				(byte) 0x20
				0x00
				0x01
				0x01
				0x01
				0x00
				0x00
				0x00
				0x00
				0x00
		};
		byte[] output = out.toByteArray();
		assertArrayEquals(expected
	}

	@Test
	public void write_24b32bPositions_32bSizes() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PackObjectSizeIndexWriter writer = PackObjectSizeIndexWriter
				.createWriter(out
		List<PackedObjectInfo> objs = new BlockList<>(9_000_000);
		PackedObjectInfo commit = objInfo(Constants.OBJ_COMMIT
		for (int i = 0; i < MAX_24BITS_UINT; i++) {
			objs.add(commit);
		}
		objs.add(blobWithSize(100));
		objs.add(blobWithSize(400));
		objs.add(blobWithSize(200));

		writer.write(objs);
		byte[] expected = new byte[] { -1
				0x01
				0x00
				0x00
				0x18
				0x00
				(byte) 0xff
				(byte) 0x20
				0x00
				0x01
				0x01
				0x00
				0x00
				0x00
				0x00
				0x00
		};
		byte[] output = out.toByteArray();
		assertArrayEquals(expected
	}

	@Test
	public void write_64bitsSize() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PackObjectSizeIndexWriter writer = PackObjectSizeIndexWriter
				.createWriter(out
		List<PackedObjectInfo> objs = new ArrayList<>();
		objs.add(blobWithSize(100));
		objs.add(blobWithSize(3 * GB));
		objs.add(blobWithSize(4 * GB));
		objs.add(blobWithSize(400));

		writer.write(objs);
		byte[] expected = new byte[] { -1
				0x01
				0x00
				0x00
				0x18
				0x00
				0x00
				0x00
				0x00
				0x00
				0x00
				0x00
				(byte) 0xff
				(byte) 0xff
				0x00
				0x00
				0x00
				(byte) 0xc0
				0x00
				(byte) 0x00
				0x00
		};
		byte[] output = out.toByteArray();
		assertArrayEquals(expected
	}

	@Test
	public void write_allObjectsTooSmall() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PackObjectSizeIndexWriter writer = PackObjectSizeIndexWriter
				.createWriter(out
		List<PackedObjectInfo> objs = new ArrayList<>();
		objs.add(blobWithSize(100));
		objs.add(blobWithSize(200));
		objs.add(blobWithSize(400));

		writer.write(objs);
		byte[] expected = new byte[] { -1
				0x01
				0x00
				0x00
		};
		byte[] output = out.toByteArray();
		assertArrayEquals(expected
	}

	@Test
	public void write_onlyBlobsIndexed() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PackObjectSizeIndexWriter writer = PackObjectSizeIndexWriter
				.createWriter(out
		List<PackedObjectInfo> objs = new ArrayList<>();
		objs.add(objInfo(Constants.OBJ_COMMIT
		objs.add(blobWithSize(100));
		objs.add(objInfo(Constants.OBJ_TAG
		objs.add(blobWithSize(400));
		objs.add(blobWithSize(200));

		writer.write(objs);
		byte[] expected = new byte[] { -1
				0x01
				0x00
				0x00
				0x18
				0x00
				0x00
				0x00
				0x00
				0x00
				0x00
				0x00
				0x00
				0x00
		};
		byte[] output = out.toByteArray();
		assertArrayEquals(expected
	}

	@Test
	public void write_noObjects() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PackObjectSizeIndexWriter writer = PackObjectSizeIndexWriter
				.createWriter(out
		List<PackedObjectInfo> objs = new ArrayList<>();

		writer.write(objs);
		byte[] expected = new byte[] { -1
				0x01
				0x00
				0x00
		};
		byte[] output = out.toByteArray();
		assertArrayEquals(expected
	}

	@Test
	public void read_empty() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PackObjectSizeIndexWriter writer = PackObjectSizeIndexWriter
				.createWriter(out
		List<PackedObjectInfo> objs = new ArrayList<>();

		writer.write(objs);
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		PackObjectSizeIndex index = PackObjectSizeIndexLoader.load(in);
		assertEquals(-1
		assertEquals(-1
		assertEquals(-1
	}

	@Test
	public void read_only24bitsPositions() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PackObjectSizeIndexWriter writer = PackObjectSizeIndexWriter
				.createWriter(out
		List<PackedObjectInfo> objs = new ArrayList<>();
		objs.add(blobWithSize(100));
		objs.add(blobWithSize(200));
		objs.add(blobWithSize(400));
		objs.add(blobWithSize(1500));

		writer.write(objs);
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		PackObjectSizeIndex index = PackObjectSizeIndexLoader.load(in);
		assertEquals(100
		assertEquals(200
		assertEquals(400
		assertEquals(1500
	}

	@Test
	public void read_only32bitsPositions() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PackObjectSizeIndexWriter writer = PackObjectSizeIndexWriter
				.createWriter(out
		List<PackedObjectInfo> objs = new ArrayList<>();
		PackedObjectInfo smallObj = blobWithSize(100);
		for (int i = 0; i <= MAX_24BITS_UINT; i++) {
			objs.add(smallObj);
		}
		objs.add(blobWithSize(1000));
		objs.add(blobWithSize(3000));
		objs.add(blobWithSize(2500));
		objs.add(blobWithSize(1000));

		writer.write(objs);
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		PackObjectSizeIndex index = PackObjectSizeIndexLoader.load(in);
		assertEquals(-1
		assertEquals(-1
		assertEquals(3000
		assertEquals(2500
		assertEquals(-1
	}

	@Test
	public void read_24and32BitsPositions() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PackObjectSizeIndexWriter writer = PackObjectSizeIndexWriter
				.createWriter(out
		List<PackedObjectInfo> objs = new ArrayList<>();
		PackedObjectInfo smallObj = blobWithSize(100);
		for (int i = 0; i <= MAX_24BITS_UINT; i++) {
			if (i == 500 || i == 1000 || i == 1500) {
				objs.add(blobWithSize(2500));
				continue;
			}
			objs.add(smallObj);
		}
		objs.add(blobWithSize(3000));
		objs.add(blobWithSize(1000));
		objs.add(blobWithSize(2500));
		objs.add(blobWithSize(1000));

		writer.write(objs);
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		PackObjectSizeIndex index = PackObjectSizeIndexLoader.load(in);
		assertEquals(-1
		assertEquals(2500
		assertEquals(3000
		assertEquals(-1
		assertEquals(2500
		assertEquals(-1
	}

	@Test
	public void read_only64bits() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PackObjectSizeIndexWriter writer = PackObjectSizeIndexWriter
				.createWriter(out
		List<PackedObjectInfo> objs = new ArrayList<>();
		objs.add(blobWithSize(3 * GB));
		objs.add(blobWithSize(8 * GB));

		writer.write(objs);
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		PackObjectSizeIndex index = PackObjectSizeIndexLoader.load(in);
		assertEquals(3 * GB
		assertEquals(8 * GB
	}

	@Test
	public void read_withMinSize() throws IOException {
		int minSize = 1000;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PackObjectSizeIndexWriter writer = PackObjectSizeIndexWriter
				.createWriter(out
		List<PackedObjectInfo> objs = new ArrayList<>();
		objs.add(blobWithSize(3 * GB));
		objs.add(blobWithSize(1500));
		objs.add(blobWithSize(500));
		objs.add(blobWithSize(1000));
		objs.add(blobWithSize(2000));

		writer.write(objs);
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		PackObjectSizeIndex index = PackObjectSizeIndexLoader.load(in);
		assertEquals(3 * GB
		assertEquals(1500
		assertEquals(-1
		assertEquals(1000
		assertEquals(2000
	}

	private static PackedObjectInfo blobWithSize(long size) {
		return objInfo(Constants.OBJ_BLOB
	}

	private static PackedObjectInfo objInfo(int type
		PackedObjectInfo objectInfo = new PackedObjectInfo(OBJ_ID);
		objectInfo.setType(type);
		objectInfo.setFullSize(size);
		return objectInfo;
	}
}
