
package org.eclipse.jgit.merge;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.Constants;
import org.junit.Assume;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class MergeAlgorithmTest {
	MergeFormatter fmt=new MergeFormatter();

	private final boolean newlineAtEnd;

	@DataPoints
	public static boolean[] newlineAtEndDataPoints = { false

	public MergeAlgorithmTest(boolean newlineAtEnd) {
		this.newlineAtEnd = newlineAtEnd;
	}

	@Test
	public void testTwoConflictingModifications() throws IOException {
		assertEquals(t("a<b=Z>Zdefghij")
				merge("abcdefghij"
	}

	@Test
	public void testOneAgainstTwoConflictingModifications() throws IOException {
		assertEquals(t("aZ<Z=c>Zefghij")
				merge("abcdefghij"
	}

	@Test
	public void testNoAgainstOneModification() throws IOException {
		assertEquals(t("aZcZefghij")
				merge("abcdefghij"
	}

	@Test
	public void testTwoNonConflictingModifications() throws IOException {
		assertEquals(t("YbZdefghij")
				merge("abcdefghij"
	}

	@Test
	public void testTwoComplicatedModifications() throws IOException {
		assertEquals(t("a<ZZZZfZhZj=bYdYYYYiY>")
				merge("abcdefghij"
	}

	@Test
	public void testTwoModificationsWithSharedDelete() throws IOException {
		assertEquals(t("Cb}n}")
				merge("ab}n}n}"
	}

	@Test
	public void testModificationsWithMiddleInsert() throws IOException {
		assertEquals(t("aBcd123123uvwxPq")
				merge("abcd123uvwxpq"
	}

	@Test
	public void testModificationsWithMiddleDelete() throws IOException {
		assertEquals(t("Abz}z123Q")
				merge("abz}z}z123q"
	}

	@Test
	public void testConflictAtStart() throws IOException {
		assertEquals(t("<Z=Y>bcdefghij")
				merge("abcdefghij"
	}

	@Test
	public void testConflictAtEnd() throws IOException {
		assertEquals(t("abcdefghi<Z=Y>")
				merge("abcdefghij"
	}

	@Test
	public void testSameModification() throws IOException {
		assertEquals(t("abZdefghij")
				merge("abcdefghij"
	}

	@Test
	public void testDeleteVsModify() throws IOException {
		assertEquals(t("ab<=Z>defghij")
				merge("abcdefghij"
	}

	@Test
	public void testInsertVsModify() throws IOException {
		assertEquals(t("a<bZ=XY>")
	}

	@Test
	public void testAdjacentModifications() throws IOException {
		assertEquals(t("a<Zc=bY>d")
	}

	@Test
	public void testSeparateModifications() throws IOException {
		assertEquals(t("aZcYe")
	}

	@Test
	public void testBlankLines() throws IOException {
		assertEquals(t("aZc\nYe")
	}

	@Test
	public void testTwoSimilarModsAndOneInsert() throws IOException {
		assertEquals(t("aBcDde")
		assertEquals(t("IAAAJCAB")
		assertEquals(t("HIAAAJCAB")
		assertEquals(t("AGADEFHIAAAJCAB")
				merge("AGADEFHiACAB"
	}

	@Test
	public void testTwoSimilarModsAndOneInsertAtEnd() throws IOException {
		Assume.assumeTrue(newlineAtEnd);
		assertEquals(t("IAAJ")
		assertEquals(t("IAJ")
		assertEquals(t("IAAAJ")
	}

	@Test
	public void testTwoSimilarModsAndOneInsertAtEndNoNewlineAtEnd()
			throws IOException {
		Assume.assumeFalse(newlineAtEnd);
		assertEquals(t("I<A=AAJ>")
		assertEquals(t("I<A=AJ>")
		assertEquals(t("I<A=AAAJ>")
	}

	@Test
	public void testEmptyTexts() throws IOException {
		assertEquals(t("<AB=>")
		assertEquals(t("<=AB>")

		assertEquals(t("")
		assertEquals(t("")

		assertEquals(t("")
	}

	private String merge(String commonBase
		MergeResult r = new MergeAlgorithm().merge(RawTextComparator.DEFAULT
				T(commonBase)
		ByteArrayOutputStream bo=new ByteArrayOutputStream(50);
		fmt.formatMerge(bo
		return new String(bo.toByteArray()
	}

	public String t(String text) {
		StringBuilder r = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			switch (c) {
			case '<':
				r.append("<<<<<<< O\n");
				break;
			case '=':
				r.append("=======\n");
				break;
			case '>':
				r.append(">>>>>>> T\n");
				break;
			default:
				r.append(c);
				if (newlineAtEnd || i < text.length() - 1)
					r.append('\n');
			}
		}
		return r.toString();
	}

	public RawText T(String text) {
		return new RawText(Constants.encode(t(text)));
	}
}
