import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.Constants;
import org.junit.Test;

public class MergeAlgorithmTest {
	MergeFormatter fmt=new MergeFormatter();

	boolean newlineAtEnd = true;

	/**
	 * Check for a conflict where the second text was changed similar to the
	 * first one, but the second texts modification covers one more line.
	 *
	 * @throws IOException
	 */
	@Test
	public void testTwoConflictingModifications() throws IOException {
		assertEquals(t("a<b=Z>Zdefghij"),
				merge("abcdefghij", "abZdefghij", "aZZdefghij"));
	}

	/**
	 * Test a case where we have three consecutive chunks. The first text
	 * modifies all three chunks. The second text modifies the first and the
	 * last chunk. This should be reported as one conflicting region.
	 *
	 * @throws IOException
	 */
	@Test
	public void testOneAgainstTwoConflictingModifications() throws IOException {
		assertEquals(t("aZ<Z=c>Zefghij"),
				merge("abcdefghij", "aZZZefghij", "aZcZefghij"));
	}

	/**
	 * Test a merge where only the second text contains modifications. Expect as
	 * merge result the second text.
	 *
	 * @throws IOException
	 */
	@Test
	public void testNoAgainstOneModification() throws IOException {
		assertEquals(t("aZcZefghij"),
				merge("abcdefghij", "abcdefghij", "aZcZefghij"));
	}

	/**
	 * Both texts contain modifications but not on the same chunks. Expect a
	 * non-conflict merge result.
	 *
	 * @throws IOException
	 */
	@Test
	public void testTwoNonConflictingModifications() throws IOException {
		assertEquals(t("YbZdefghij"),
				merge("abcdefghij", "abZdefghij", "Ybcdefghij"));
	}

	/**
	 * Merge two complicated modifications. The merge algorithm has to extend
	 * and combine conflicting regions to get to the expected merge result.
	 *
	 * @throws IOException
	 */
	@Test
	public void testTwoComplicatedModifications() throws IOException {
		assertEquals(t("a<ZZZZfZhZj=bYdYYYYiY>"),
				merge("abcdefghij", "aZZZZfZhZj", "abYdYYYYiY"));
	}

	/**
	 * Test a conflicting region at the very start of the text.
	 *
	 * @throws IOException
	 */
	@Test
	public void testConflictAtStart() throws IOException {
		assertEquals(t("<Z=Y>bcdefghij"),
				merge("abcdefghij", "Zbcdefghij", "Ybcdefghij"));
	}

	/**
	 * Test a conflicting region at the very end of the text.
	 *
	 * @throws IOException
	 */
	@Test
	public void testConflictAtEnd() throws IOException {
		assertEquals(t("abcdefghi<Z=Y>"),
				merge("abcdefghij", "abcdefghiZ", "abcdefghiY"));
	}

	/**
	 * Check for a conflict where the second text was changed similar to the
	 * first one, but the second texts modification covers one more line.
	 *
	 * @throws IOException
	 */
	@Test
	public void testSameModification() throws IOException {
		assertEquals(t("abZdefghij"),
				merge("abcdefghij", "abZdefghij", "abZdefghij"));
	}

	/**
	 * Check that a deleted vs. a modified line shows up as conflict (see Bug
	 * 328551)
	 *
	 * @throws IOException
	 */
	@Test
	public void testDeleteVsModify() throws IOException {
		assertEquals(t("ab<=Z>defghij"),
				merge("abcdefghij", "abdefghij", "abZdefghij"));
	}

	@Test
	public void testInsertVsModify() throws IOException {
		assertEquals(t("a<bZ=XY>"), merge("ab", "abZ", "aXY"));
	}

	@Test
	public void testAdjacentModifications() throws IOException {
		assertEquals(t("a<Zc=bY>d"), merge("abcd", "aZcd", "abYd"));
	}

	@Test
	public void testSeparateModifications() throws IOException {
		assertEquals(t("aZcYe"), merge("abcde", "aZcde", "abcYe"));
	}

	/**
	 * Test merging two contents which do one similar modification and one
	 * insertion is only done by one side. Between modification and insertion is
	 * a block which is common between the two contents and the common base
	 *
	 * @throws IOException
	 */
	@Test
	public void testTwoSimilarModsAndOneInsert() throws IOException {
		assertEquals(t("IAAJ"), merge("iA", "IA", "IAAJ"));
		assertEquals(t("aBcDde"), merge("abcde", "aBcde", "aBcDde"));
		assertEquals(t("IAJ"), merge("iA", "IA", "IAJ"));
		assertEquals(t("IAAAJ"), merge("iA", "IA", "IAAAJ"));
		assertEquals(t("IAAAJCAB"), merge("iACAB", "IACAB", "IAAAJCAB"));
		assertEquals(t("HIAAAJCAB"), merge("HiACAB", "HIACAB", "HIAAAJCAB"));
		assertEquals(t("AGADEFHIAAAJCAB"),
				merge("AGADEFHiACAB", "AGADEFHIACAB", "AGADEFHIAAAJCAB"));
