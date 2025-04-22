
package org.eclipse.jgit.treewalk;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.lib.FileMode.REGULAR_FILE;
import static org.eclipse.jgit.lib.FileMode.SYMLINK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.TreeFormatter;
import org.eclipse.jgit.util.RawParseUtils;
import org.junit.Before;
import org.junit.Test;

public class CanonicalTreeParserTest {
	private final CanonicalTreeParser ctp = new CanonicalTreeParser();

	private final FileMode m644 = FileMode.REGULAR_FILE;

	private final FileMode mt = FileMode.TREE;

	private final ObjectId hash_a = ObjectId
			.fromString("6b9c715d21d5486e59083fb6071566aa6ecd4d42");

	private final ObjectId hash_foo = ObjectId
			.fromString("a213e8e25bb2442326e86cbfb9ef56319f482869");

	private final ObjectId hash_sometree = ObjectId
			.fromString("daf4bdb0d7bb24319810fe0e73aa317663448c93");

	private byte[] tree1;

	private byte[] tree2;

	private byte[] tree3;

	@Before
	public void setUp() throws Exception {
		tree1 = mktree(entry(m644
		tree2 = mktree(entry(m644
		tree3 = mktree(entry(m644
				hash_sometree)
	}

	private static byte[] mktree(byte[]... data) throws Exception {
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		for (byte[] e : data)
			out.write(e);
		return out.toByteArray();
	}

	private static byte[] entry(final FileMode mode
			final ObjectId id) throws Exception {
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		mode.copyTo(out);
		out.write(' ');
		out.write(Constants.encode(name));
		out.write(0);
		id.copyRawTo(out);
		return out.toByteArray();
	}

	private String path() {
		return RawParseUtils.decode(UTF_8
				ctp.pathOffset
	}

	@Test
	public void testEmptyTree_AtEOF() throws Exception {
		ctp.reset(new byte[0]);
		assertTrue(ctp.eof());
	}

	@Test
	public void testOneEntry_Forward() throws Exception {
		ctp.reset(tree1);

		assertTrue(ctp.first());
		assertFalse(ctp.eof());
		assertEquals(m644.getBits()
		assertEquals("a"
		assertEquals(hash_a

		ctp.next(1);
		assertFalse(ctp.first());
		assertTrue(ctp.eof());
	}

	@Test
	public void testTwoEntries_ForwardOneAtATime() throws Exception {
		ctp.reset(tree2);

		assertTrue(ctp.first());
		assertFalse(ctp.eof());
		assertEquals(m644.getBits()
		assertEquals("a"
		assertEquals(hash_a

		ctp.next(1);
		assertFalse(ctp.eof());
		assertEquals(m644.getBits()
		assertEquals("foo"
		assertEquals(hash_foo

		ctp.next(1);
		assertFalse(ctp.first());
		assertTrue(ctp.eof());
	}

	@Test
	public void testOneEntry_Seek1IsEOF() throws Exception {
		ctp.reset(tree1);
		ctp.next(1);
		assertTrue(ctp.eof());
	}

	@Test
	public void testTwoEntries_Seek2IsEOF() throws Exception {
		ctp.reset(tree2);
		ctp.next(2);
		assertTrue(ctp.eof());
	}

	@Test
	public void testThreeEntries_Seek3IsEOF() throws Exception {
		ctp.reset(tree3);
		ctp.next(3);
		assertTrue(ctp.eof());
	}

	@Test
	public void testThreeEntries_Seek2() throws Exception {
		ctp.reset(tree3);

		ctp.next(2);
		assertFalse(ctp.eof());
		assertFalse(ctp.eof());
		assertEquals(m644.getBits()
		assertEquals("foo"
		assertEquals(hash_foo

		ctp.next(1);
		assertTrue(ctp.eof());
	}

	@Test
	public void testOneEntry_Backwards() throws Exception {
		ctp.reset(tree1);
		ctp.next(1);
		assertFalse(ctp.first());
		assertTrue(ctp.eof());

		ctp.back(1);
		assertTrue(ctp.first());
		assertFalse(ctp.eof());
		assertEquals(m644.getBits()
		assertEquals("a"
		assertEquals(hash_a
	}

	@Test
	public void testTwoEntries_BackwardsOneAtATime() throws Exception {
		ctp.reset(tree2);
		ctp.next(2);
		assertTrue(ctp.eof());

		ctp.back(1);
		assertFalse(ctp.eof());
		assertEquals(m644.getBits()
		assertEquals("foo"
		assertEquals(hash_foo

		ctp.back(1);
		assertFalse(ctp.eof());
		assertEquals(m644.getBits()
		assertEquals("a"
		assertEquals(hash_a
	}

	@Test
	public void testTwoEntries_BackwardsTwo() throws Exception {
		ctp.reset(tree2);
		ctp.next(2);
		assertTrue(ctp.eof());

		ctp.back(2);
		assertFalse(ctp.eof());
		assertEquals(m644.getBits()
		assertEquals("a"
		assertEquals(hash_a

		ctp.next(1);
		assertFalse(ctp.eof());
		assertEquals(m644.getBits()
		assertEquals("foo"
		assertEquals(hash_foo

		ctp.next(1);
		assertTrue(ctp.eof());
	}

	@Test
	public void testThreeEntries_BackwardsTwo() throws Exception {
		ctp.reset(tree3);
		ctp.next(3);
		assertTrue(ctp.eof());

		ctp.back(2);
		assertFalse(ctp.eof());
		assertEquals(mt.getBits()
		assertEquals("b_sometree"
		assertEquals(hash_sometree

		ctp.next(1);
		assertFalse(ctp.eof());
		assertEquals(m644.getBits()
		assertEquals("foo"
		assertEquals(hash_foo

		ctp.next(1);
		assertTrue(ctp.eof());
	}

	@Test
	public void testBackwards_ConfusingPathName() throws Exception {
		final String aVeryConfusingName = "confusing 644 entry 755 and others";
		ctp.reset(mktree(entry(m644
				hash_sometree)
		ctp.next(3);
		assertTrue(ctp.eof());

		ctp.back(2);
		assertFalse(ctp.eof());
		assertEquals(mt.getBits()
		assertEquals(aVeryConfusingName
		assertEquals(hash_sometree

		ctp.back(1);
		assertFalse(ctp.eof());
		assertEquals(m644.getBits()
		assertEquals("a"
		assertEquals(hash_a
	}

	@Test
	public void testBackwords_Prebuilts1() throws Exception {
		final ObjectId common = ObjectId
				.fromString("af7bf97cb9bce3f60f1d651a0ef862e9447dd8bc");
		final ObjectId darwinx86 = ObjectId
				.fromString("e927f7398240f78face99e1a738dac54ef738e37");
		final ObjectId linuxx86 = ObjectId
				.fromString("ac08dd97120c7cb7d06e98cd5b152011183baf21");
		final ObjectId windows = ObjectId
				.fromString("6c4c64c221a022bb973165192cca4812033479df");

		ctp.reset(mktree(entry(mt
				darwinx86)
				"windows"
		ctp.next(3);
		assertEquals("windows"
		assertSame(mt
		assertEquals(windows

		ctp.back(1);
		assertEquals("linux-x86"
		assertSame(mt
		assertEquals(linuxx86

		ctp.next(1);
		assertEquals("windows"
		assertSame(mt
		assertEquals(windows
	}

	@Test
	public void testBackwords_Prebuilts2() throws Exception {
		final ObjectId common = ObjectId
				.fromString("af7bf97cb9bce3f60f1d651a0ef862e9447dd8bc");
		final ObjectId darwinx86 = ObjectId
				.fromString("0000000000000000000000000000000000000037");
		final ObjectId linuxx86 = ObjectId
				.fromString("ac08dd97120c7cb7d06e98cd5b152011183baf21");
		final ObjectId windows = ObjectId
				.fromString("6c4c64c221a022bb973165192cca4812033479df");

		ctp.reset(mktree(entry(mt
				darwinx86)
				"windows"
		ctp.next(3);
		assertEquals("windows"
		assertSame(mt
		assertEquals(windows

		ctp.back(1);
		assertEquals("linux-x86"
		assertSame(mt
		assertEquals(linuxx86

		ctp.next(1);
		assertEquals("windows"
		assertSame(mt
		assertEquals(windows
	}

	@Test
	public void testFreakingHugePathName() throws Exception {
		final int n = AbstractTreeIterator.DEFAULT_PATH_SIZE * 4;
		final StringBuilder b = new StringBuilder(n);
		for (int i = 0; i < n; i++)
			b.append('q');
		final String name = b.toString();
		ctp.reset(entry(m644
		assertFalse(ctp.eof());
		assertEquals(name
				ctp.pathOffset
	}

	@Test
	public void testFindAttributesWhenFirst() throws CorruptObjectException {
		TreeFormatter tree = new TreeFormatter();
		tree.append(".gitattributes"
		ctp.reset(tree.toByteArray());

		assertTrue(ctp.findFile(".gitattributes"));
		assertEquals(REGULAR_FILE.getBits()
		assertEquals(".gitattributes"
		assertEquals(hash_a
	}

	@Test
	public void testFindAttributesWhenSecond() throws CorruptObjectException {
		TreeFormatter tree = new TreeFormatter();
		tree.append(".config"
		tree.append(".gitattributes"
		ctp.reset(tree.toByteArray());

		assertTrue(ctp.findFile(".gitattributes"));
		assertEquals(REGULAR_FILE.getBits()
		assertEquals(".gitattributes"
		assertEquals(hash_foo
	}

	@Test
	public void testFindAttributesWhenMissing() throws CorruptObjectException {
		TreeFormatter tree = new TreeFormatter();
		tree.append("src"
		tree.append("zoo"
		ctp.reset(tree.toByteArray());

		assertFalse(ctp.findFile(".gitattributes"));
		assertEquals(11
		assertEquals("src"
	}
}
