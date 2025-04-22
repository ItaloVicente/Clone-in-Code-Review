
package org.eclipse.jgit.internal.storage.file;

import static org.junit.Assert.assertEquals;

import org.eclipse.jgit.internal.storage.file.BasePackBitmapIndex.StoredBitmap;
import org.eclipse.jgit.lib.ObjectId;
import org.junit.Test;

import com.googlecode.javaewah.EWAHCompressedBitmap;

public class StoredBitmapTest {

	@Test
	public void testGetBitmapWithoutXor() {
		EWAHCompressedBitmap b = bitmapOf(100);
		StoredBitmap sb = newStoredBitmap(bitmapOf(100));
		assertEquals(b
	}

	@Test
	public void testGetBitmapWithOneXor() {
		StoredBitmap sb = newStoredBitmap(bitmapOf(100)
		assertEquals(bitmapOf(101)
	}

	@Test
	public void testGetBitmapWithThreeXor() {
		StoredBitmap sb = newStoredBitmap(
				bitmapOf(100)
				bitmapOf(90
				bitmapOf(100
				bitmapOf(50));
		assertEquals(bitmapOf(50
		assertEquals(bitmapOf(50
	}

	private static final StoredBitmap newStoredBitmap(
			EWAHCompressedBitmap... bitmaps) {
		StoredBitmap sb = null;
		for (EWAHCompressedBitmap bitmap : bitmaps)
			sb = new StoredBitmap(ObjectId.zeroId()
		return sb;
	}

	private static final EWAHCompressedBitmap bitmapOf(int... bits) {
		EWAHCompressedBitmap b = new EWAHCompressedBitmap();
		for (int bit : bits)
			b.set(bit);
		return b;
	}
}
