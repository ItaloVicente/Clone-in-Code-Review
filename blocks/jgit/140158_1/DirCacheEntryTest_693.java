
package org.eclipse.jgit.dircache;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.IO;
import org.junit.Test;

public class DirCacheCGitCompatabilityTest extends LocalDiskRepositoryTestCase {
	private final File index = pathOf("gitgit.index");

	@Test
	public void testReadIndex_LsFiles() throws Exception {
		final Map<String
		final DirCache dc = new DirCache(index
		assertEquals(0
		dc.read();
		assertEquals(ls.size()
		{
			final Iterator<CGitIndexRecord> rItr = ls.values().iterator();
			for (int i = 0; rItr.hasNext(); i++)
				assertEqual(rItr.next()
		}
	}

	@Test
	public void testTreeWalk_LsFiles() throws Exception {
		final Repository db = createBareRepository();
		final Map<String
		final DirCache dc = new DirCache(index
		assertEquals(0
		dc.read();
		assertEquals(ls.size()
		{
			final Iterator<CGitIndexRecord> rItr = ls.values().iterator();
			try (TreeWalk tw = new TreeWalk(db)) {
				tw.setRecursive(true);
				tw.addTree(new DirCacheIterator(dc));
				while (rItr.hasNext()) {
					final DirCacheIterator dcItr;

					assertTrue(tw.next());
					dcItr = tw.getTree(0
					assertNotNull(dcItr);

					assertEqual(rItr.next()
				}
			}
		}
	}

	@Test
	public void testUnsupportedOptionalExtension() throws Exception {
		final DirCache dc = new DirCache(pathOf("gitgit.index.ZZZZ")
				FS.DETECTED);
		dc.read();
		assertEquals(1
		assertEquals("A"
	}

	@Test
	public void testUnsupportedRequiredExtension() throws Exception {
		final DirCache dc = new DirCache(pathOf("gitgit.index.aaaa")
				FS.DETECTED);
		try {
			dc.read();
			fail("Cache loaded an unsupported extension");
		} catch (CorruptObjectException err) {
			assertEquals("DIRC extension 'aaaa'"
					+ " not supported by this version."
		}
	}

	@Test
	public void testCorruptChecksumAtFooter() throws Exception {
		final DirCache dc = new DirCache(pathOf("gitgit.index.badchecksum")
				FS.DETECTED);
		try {
			dc.read();
			fail("Cache loaded despite corrupt checksum");
		} catch (CorruptObjectException err) {
			assertEquals("DIRC checksum mismatch"
		}
	}

	private static void assertEqual(final CGitIndexRecord c
			final DirCacheEntry j) {
		assertNotNull(c);
		assertNotNull(j);

		assertEquals(c.path
		assertEquals(c.id
		assertEquals(c.mode
		assertEquals(c.stage
	}

	@Test
	public void testReadIndex_DirCacheTree() throws Exception {
		final Map<String
		final Map<String
		final DirCache dc = new DirCache(index
		assertEquals(0
		dc.read();
		assertEquals(cList.size()

		final DirCacheTree jTree = dc.getCacheTree(false);
		assertNotNull(jTree);
		assertEquals(""
		assertEquals(""
		assertTrue(jTree.isValid());
		assertEquals(ObjectId
				.fromString("698dd0b8d0c299f080559a1cffc7fe029479a408")
				.getObjectId());
		assertEquals(cList.size()

		final ArrayList<CGitLsTreeRecord> subtrees = new ArrayList<>();
		for (CGitLsTreeRecord r : cTree.values()) {
			if (FileMode.TREE.equals(r.mode))
				subtrees.add(r);
		}
		assertEquals(subtrees.size()

		for (int i = 0; i < jTree.getChildCount(); i++) {
			final DirCacheTree sj = jTree.getChild(i);
			final CGitLsTreeRecord sc = subtrees.get(i);
			assertEquals(sc.path
			assertEquals(sc.path + "/"
			assertTrue(sj.isValid());
			assertEquals(sc.id
		}
	}

	@Test
	public void testReadWriteV3() throws Exception {
		final File file = pathOf("gitgit.index.v3");
		final DirCache dc = new DirCache(file
		dc.read();

		assertEquals(10
		assertV3TreeEntry(0
		assertV3TreeEntry(1
		assertV3TreeEntry(2
		assertV3TreeEntry(3
		assertV3TreeEntry(4
		assertV3TreeEntry(5
		assertV3TreeEntry(6
		assertV3TreeEntry(7
		assertV3TreeEntry(8
		assertV3TreeEntry(9

		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		dc.writeTo(null
		final byte[] indexBytes = bos.toByteArray();
		final byte[] expectedBytes = IO.readFully(file);
		assertArrayEquals(expectedBytes
	}

	private static void assertV3TreeEntry(int indexPosition
			boolean skipWorkTree
		final DirCacheEntry entry = dc.getEntry(indexPosition);
		assertEquals(path
		assertEquals(skipWorkTree
		assertEquals(intentToAdd
	}

	private static File pathOf(String name) {
		return JGitTestUtil.getTestResourceFile(name);
	}

	private static Map<String
		final LinkedHashMap<String
		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(pathOf("gitgit.lsfiles"))
			String line;
			while ((line = br.readLine()) != null) {
				final CGitIndexRecord cr = new CGitIndexRecord(line);
				r.put(cr.path
			}
		}
		return r;
	}

	private static Map<String
		final LinkedHashMap<String
		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(pathOf("gitgit.lstree"))
			String line;
			while ((line = br.readLine()) != null) {
				final CGitLsTreeRecord cr = new CGitLsTreeRecord(line);
				r.put(cr.path
			}
		}
		return r;
	}

	private static class CGitIndexRecord {
		final int mode;

		final ObjectId id;

		final int stage;

		final String path;

		CGitIndexRecord(String line) {
			final int tab = line.indexOf('\t');
			final int sp1 = line.indexOf(' ');
			final int sp2 = line.indexOf(' '
			mode = Integer.parseInt(line.substring(0
			id = ObjectId.fromString(line.substring(sp1 + 1
			stage = Integer.parseInt(line.substring(sp2 + 1
			path = line.substring(tab + 1);
		}
	}

	private static class CGitLsTreeRecord {
		final int mode;

		final ObjectId id;

		final String path;

		CGitLsTreeRecord(String line) {
			final int tab = line.indexOf('\t');
			final int sp1 = line.indexOf(' ');
			final int sp2 = line.indexOf(' '
			mode = Integer.parseInt(line.substring(0
			id = ObjectId.fromString(line.substring(sp2 + 1
			path = line.substring(tab + 1);
		}
	}
}
