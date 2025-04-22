
package org.eclipse.jgit.diff;

import junit.framework.TestCase;

import org.eclipse.jgit.lib.Constants;

public class SimilarityIndexTest extends TestCase {
	public void testIndexing() {
		);

		int key_A = keyFor("A\n");
		int key_B = keyFor("B\n");
		int key_D = keyFor("D\n");
		assertTrue(key_A != key_B && key_A != key_D && key_B != key_D);

		assertEquals(3
		assertEquals(2
		assertEquals(4
		assertEquals(2
	}

	public void testCommonScore_SameFiles() {
				+ "B\n";
		SimilarityIndex src = hash(text);
		SimilarityIndex dst = hash(text);
		assertEquals(8
		assertEquals(8

		assertEquals(100
		assertEquals(100
	}

	public void testCommonScore_EmptyFiles() {
		SimilarityIndex src = hash("");
		SimilarityIndex dst = hash("");
		assertEquals(0
		assertEquals(0
	}

	public void testCommonScore_TotallyDifferentFiles() {
		SimilarityIndex src = hash("A\n");
		SimilarityIndex dst = hash("D\n");
		assertEquals(0
		assertEquals(0
	}

	public void testCommonScore_SimiliarBy75() {
		SimilarityIndex src = hash("A\nB\nC\nD\n");
		SimilarityIndex dst = hash("A\nB\nC\nQ\n");
		assertEquals(6
		assertEquals(6

		assertEquals(75
		assertEquals(75
	}

	private static SimilarityIndex hash(String text) {
		SimilarityIndex src = new SimilarityIndex() {
			@Override
			void hash(byte[] raw
				while (ptr < end) {
					int hash = raw[ptr] & 0xff;
					int start = ptr;
					do {
						int c = raw[ptr++] & 0xff;
						if (c == '\n')
							break;
					} while (ptr < end && ptr - start < 64);
					add(hash
				}
			}
		};
		byte[] raw = Constants.encode(text);
		src.setFileSize(raw.length);
		src.hash(raw
		src.sort();
		return src;
	}

	private static int keyFor(String line) {
		SimilarityIndex si = hash(line);
		assertEquals("single line scored"
		return si.key(0);
	}
}
