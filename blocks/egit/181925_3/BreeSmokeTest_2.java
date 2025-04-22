package org.eclipse.egit.internal.mylyn;

import static org.junit.Assert.assertNotNull;

import java.nio.Buffer;
import java.nio.ByteBuffer;

import org.junit.Test;

public class BreeSmokeTest {

	@Test
	public void testByteBuffer() {
		ByteBuffer buffer = ByteBuffer.allocate(10);
		Buffer flipped = buffer.flip();
		assertNotNull(flipped);
	}
}
