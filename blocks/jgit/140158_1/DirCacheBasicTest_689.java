
package org.eclipse.jgit.diff;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.eclipse.jgit.diff.SimilarityIndex.TableFullException;
import org.eclipse.jgit.lib.Constants;
import org.junit.Test;

public class SimilarityIndexTest {
	@Test
	public void testIndexingSmallObject() throws TableFullException {
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

	@Test
	public void testIndexingLargeObject() throws IOException
			TableFullException {
				+ "B\n").getBytes(UTF_8);
		SimilarityIndex si = new SimilarityIndex();
		si.hash(new ByteArrayInputStream(in)
		assertEquals(2
	}

	@Test
	public void testCommonScore_SameFiles() throws TableFullException {
				+ "B\n";
		SimilarityIndex src = hash(text);
		SimilarityIndex dst = hash(text);
		assertEquals(8
		assertEquals(8

		assertEquals(100
		assertEquals(100
	}

	@Test
	public void testCommonScore_SameFiles_CR_canonicalization()
			throws TableFullException {
				+ "B\r\n";
		SimilarityIndex src = hash(text);
		SimilarityIndex dst = hash(text.replace("\r"
		assertEquals(8
		assertEquals(8

		assertEquals(100
		assertEquals(100
	}

	@Test
	public void testCommonScoreLargeObject_SameFiles_CR_canonicalization()
			throws TableFullException
				+ "B\r\n";
		SimilarityIndex src = new SimilarityIndex();
		byte[] bytes1 = text.getBytes(UTF_8);
		src.hash(new ByteArrayInputStream(bytes1)
		src.sort();

		SimilarityIndex dst = new SimilarityIndex();
		byte[] bytes2 = text.replace("\r"
		dst.hash(new ByteArrayInputStream(bytes2)
		dst.sort();

		assertEquals(8
		assertEquals(8

		assertEquals(100
		assertEquals(100
	}

	@Test
	public void testCommonScore_EmptyFiles() throws TableFullException {
		SimilarityIndex src = hash("");
		SimilarityIndex dst = hash("");
		assertEquals(0
		assertEquals(0
	}

	@Test
	public void testCommonScore_TotallyDifferentFiles()
			throws TableFullException {
		SimilarityIndex src = hash("A\n");
		SimilarityIndex dst = hash("D\n");
		assertEquals(0
		assertEquals(0
	}

	@Test
	public void testCommonScore_SimiliarBy75() throws TableFullException {
		SimilarityIndex src = hash("A\nB\nC\nD\n");
		SimilarityIndex dst = hash("A\nB\nC\nQ\n");
		assertEquals(6
		assertEquals(6

		assertEquals(75
		assertEquals(75
	}

	private static SimilarityIndex hash(String text) throws TableFullException {
		SimilarityIndex src = new SimilarityIndex();
		byte[] raw = Constants.encode(text);
		src.hash(raw
		src.sort();
		return src;
	}

	private static int keyFor(String line) throws TableFullException {
		SimilarityIndex si = hash(line);
		assertEquals("single line scored"
		return si.key(0);
	}
}
