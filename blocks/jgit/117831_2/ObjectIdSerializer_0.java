
package org.eclipse.jgit.lib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.junit.Test;

public class ObjectIdSerializerTest {
	@Test
	public void serialize() throws Exception {
		ObjectId original = new ObjectId(1
		ObjectId deserialized = writeAndReadBackFromTempFile(original);
		assertEquals(original
	}

	@Test
	public void serializeZeroId() throws Exception {
		ObjectId original = ObjectId.zeroId();
		ObjectId deserialized = writeAndReadBackFromTempFile(original);
		assertEquals(original
	}

	@Test
	public void serializeNull() throws Exception {
		ObjectId deserialized = writeAndReadBackFromTempFile(null);
		assertNull(deserialized);
	}

	private ObjectId writeAndReadBackFromTempFile(ObjectId objectId)
			throws Exception {
		File file = File.createTempFile("ObjectIdSerializerTest_"
		ObjectIdSerializer.write(new FileOutputStream(file)
		return ObjectIdSerializer.read(new FileInputStream(file));
	}
}
