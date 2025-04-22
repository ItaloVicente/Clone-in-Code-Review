TODO
package org.eclipse.jgit.internal.storage.file;

import static org.eclipse.jgit.lib.Constants.OBJECT_ID_STRING_LENGTH;
import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;
import static org.eclipse.jgit.lib.Constants.OBJ_COMMIT;
import static org.eclipse.jgit.lib.Constants.OBJ_TREE;
import static org.junit.Assert.assertArrayEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.transport.PackedObjectInfo;
import org.junit.Test;

public class PackReverseIndexWriterV1Test {

	private static byte[] PACK_CHECKSUM = new byte[] { 'P'
			'4'
	};

	@Test
	public void write_noObjects() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PackReverseIndexWriter writer = PackReverseIndexWriter.createWriter(out
		List<PackedObjectInfo> objectsSortedByName = new ArrayList<>();

		writer.write(objectsSortedByName

		byte[] expected = new byte[] { 'R'
				0x00
				0x00
				0
				(byte) 0xd1
				(byte) 0xf2
		};
		System.arraycopy(PACK_CHECKSUM
		assertArrayEquals(expected
	}

	@Test
	public void write_oneObject() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PackReverseIndexWriter writer = PackReverseIndexWriter.createWriter(out
		PackedObjectInfo a = objectInfo("a"
		List<PackedObjectInfo> objectsSortedByName = List.of(a);

		writer.write(objectsSortedByName

		byte[] expected = new byte[] { 'R'
				0x00
				0x00
				0x00
				0
				0x48
				(byte) 0xc2
		};
		System.arraycopy(PACK_CHECKSUM
		assertArrayEquals(expected
	}

	@Test
	public void write_multipleObjects() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PackReverseIndexWriter writer = PackReverseIndexWriter.createWriter(out
		PackedObjectInfo a = objectInfo("a"
		PackedObjectInfo b = objectInfo("b"
		PackedObjectInfo c = objectInfo("c"
		PackedObjectInfo d = objectInfo("d"
		PackedObjectInfo e = objectInfo("e"
		List<PackedObjectInfo> objectsSortedByName = List.of(a

		writer.write(objectsSortedByName

		byte[] expected = new byte[] { 'R'
				0x00
				0x00
				0x00
				0x00
				0x00
				0x00
				0x00
				0
				(byte) 0xe3
				0x3c
		};
		System.arraycopy(PACK_CHECKSUM
		assertArrayEquals(expected
	}

	private static PackedObjectInfo objectInfo(String objectId
		assert (objectId.length() == 1);
		PackedObjectInfo objectInfo = new PackedObjectInfo(ObjectId.fromString(objectId.repeat(OBJECT_ID_STRING_LENGTH)));
		objectInfo.setType(type);
		objectInfo.setOffset(offset);
		return objectInfo;
	}
}
