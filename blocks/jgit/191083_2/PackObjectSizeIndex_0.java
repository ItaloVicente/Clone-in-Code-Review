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
import org.junit.Test;

public class PackObjectSizeIndexV1Test {
	private final static ObjectId OBJ_ID = ObjectId
			.fromString("b8b1d53172fb3fb19647adce4b38fab4371c2454");

	private final static long GB = 1 << 30;

	@Test
	public void write_only32bits() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PackObjectSizeIndexWriter writer = PackObjectSizeIndexWriter
				.createWriter(out
		List<PackedObjectInfo> objs = new ArrayList<>();
		objs.add(blobObjInfo(7
		objs.add(blobObjInfo(5
		objs.add(blobObjInfo(9

		writer.write(objs);
		byte[] expected = new byte[] {
				-1
				0x00
				0x00
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
	public void write_64bitsSize() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PackObjectSizeIndexWriter writer = PackObjectSizeIndexWriter
				.createWriter(out
		List<PackedObjectInfo> objs = new ArrayList<>();
		objs.add(blobObjInfo(0
		objs.add(blobObjInfo(1
		objs.add(blobObjInfo(2
		objs.add(blobObjInfo(3

		writer.write(objs);
		byte[] expected = new byte[] { -1
				0x00
				0x00
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
	public void write_64bitsOffset() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PackObjectSizeIndexWriter writer = PackObjectSizeIndexWriter
				.createWriter(out
		List<PackedObjectInfo> objs = new ArrayList<>();
		objs.add(blobObjInfo(0
		objs.add(blobObjInfo(1L << 34
		objs.add(blobObjInfo(2

		writer.write(objs);
		byte[] expected = new byte[] { -1
				0x00
				0x00
				0x00
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
	public void write_64bitsOffsetsAndSizes() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PackObjectSizeIndexWriter writer = PackObjectSizeIndexWriter
				.createWriter(out
		List<PackedObjectInfo> objs = new ArrayList<>();
		objs.add(blobObjInfo(0
		objs.add(blobObjInfo(1L << 34
		objs.add(blobObjInfo(3
		objs.add(blobObjInfo(2

		writer.write(objs);
		byte[] expected = new byte[] { -1
				0x00
				0x00
				0x00
				0x00
				0x00
				0x00
				0x00
				0x00
				0x00
				0x00
				0x00
				(byte) 0xff
				0x00
				0x00
				0x00
				(byte) 0xc0
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
		objs.add(blobObjInfo(0
		objs.add(blobObjInfo(1L << 34
		objs.add(blobObjInfo(2

		writer.write(objs);
		byte[] expected = new byte[] { -1
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
	public void write_onlyBlobsIndexed() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PackObjectSizeIndexWriter writer = PackObjectSizeIndexWriter
				.createWriter(out
		List<PackedObjectInfo> objs = new ArrayList<>();
		objs.add(objInfo(Constants.OBJ_COMMIT
		objs.add(objInfo(Constants.OBJ_TAG
		objs.add(blobObjInfo(7
		objs.add(blobObjInfo(5
		objs.add(blobObjInfo(9

		writer.write(objs);
		byte[] expected = new byte[] { -1
				0x00
				0x00
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
	public void read_only32bits() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PackObjectSizeIndexWriter writer = PackObjectSizeIndexWriter
				.createWriter(out
		List<PackedObjectInfo> objs = new ArrayList<>();
		objs.add(blobObjInfo(0
		objs.add(blobObjInfo(1
		objs.add(blobObjInfo(2
		objs.add(blobObjInfo(3

		writer.write(objs);
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		PackObjectSizeIndex index = PackObjectSizeIndexLoader.load(in);
		assertEquals(100
		assertEquals(200
		assertEquals(400
		assertEquals(1500
	}

	@Test
	public void read_32and64bits_offsetsAndsizes() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PackObjectSizeIndexWriter writer = PackObjectSizeIndexWriter
				.createWriter(out
		List<PackedObjectInfo> objs = new ArrayList<>();
		objs.add(blobObjInfo(0
		objs.add(blobObjInfo(1L << 34
		objs.add(blobObjInfo(2
		objs.add(blobObjInfo(500
		objs.add(blobObjInfo(3

		writer.write(objs);
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		PackObjectSizeIndex index = PackObjectSizeIndexLoader.load(in);
		assertEquals(100
		assertEquals(200
		assertEquals(400
		assertEquals(3 * GB
		assertEquals(4 * GB
	}

	@Test
	public void read_withMinSize() throws IOException {
		int minSize = 1000;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PackObjectSizeIndexWriter writer = PackObjectSizeIndexWriter
				.createWriter(out
		List<PackedObjectInfo> objs = new ArrayList<>();
		objs.add(blobObjInfo(0
		objs.add(blobObjInfo(1
		objs.add(blobObjInfo(2
		objs.add(blobObjInfo(3
		objs.add(blobObjInfo(4

		writer.write(objs);
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		PackObjectSizeIndex index = PackObjectSizeIndexLoader.load(in);
		assertEquals(-1
		assertEquals(1000
		assertEquals(1500
		assertEquals(2000
		assertEquals(3 * GB
	}

	private static PackedObjectInfo blobObjInfo(long offset
		return objInfo(Constants.OBJ_BLOB
	}

	private static PackedObjectInfo objInfo(int type
		PackedObjectInfo objectInfo = new PackedObjectInfo(OBJ_ID);
		objectInfo.setType(type);
		objectInfo.setFullSize(size);
		objectInfo.setOffset(offset);
		return objectInfo;
	}
}
