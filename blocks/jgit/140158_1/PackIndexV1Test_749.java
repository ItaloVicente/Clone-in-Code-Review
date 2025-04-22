
package org.eclipse.jgit.internal.storage.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.storage.file.PackIndex.MutableEntry;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.junit.Test;

public abstract class PackIndexTestCase extends RepositoryTestCase {

	PackIndex smallIdx;

	PackIndex denseIdx;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		smallIdx = PackIndex.open(getFileForPack34be9032());
		denseIdx = PackIndex.open(getFileForPackdf2982f28());
	}

	public abstract File getFileForPack34be9032();

	public abstract File getFileForPackdf2982f28();

	public abstract void testCRC32() throws MissingObjectException
			UnsupportedOperationException;

	@Test
	public void testIteratorMethodsContract() {
		Iterator<PackIndex.MutableEntry> iter = smallIdx.iterator();
		while (iter.hasNext()) {
			iter.next();
		}

		try {
			iter.next();
			fail("next() unexpectedly returned element");
		} catch (NoSuchElementException x) {
		}

		try {
			iter.remove();
			fail("remove() shouldn't be implemented");
		} catch (UnsupportedOperationException x) {
		}
	}

	@Test
	public void testIteratorReturnedValues1() {
		Iterator<PackIndex.MutableEntry> iter = smallIdx.iterator();
		assertEquals("4b825dc642cb6eb9a060e54bf8d69288fbee4904"
				.name());
		assertEquals("540a36d136cf413e4b064c2b0e0a4db60f77feab"
				.name());
		assertEquals("5b6e7c66c276e7610d4a73c70ec1a1f7c1003259"
				.name());
		assertEquals("6ff87c4664981e4397625791c8ea3bbb5f2279a3"
				.name());
		assertEquals("82c6b885ff600be425b4ea96dee75dca255b69e7"
				.name());
		assertEquals("902d5476fa249b7abc9d84c611577a81381f0327"
				.name());
		assertEquals("aabf2ffaec9b497f0950352b3e582d73035c2035"
				.name());
		assertEquals("c59759f143fb1fe21c197981df75a7ee00290799"
				.name());
		assertFalse(iter.hasNext());
	}

	@Test
	public void testCompareEntriesOffsetsWithFindOffsets() {
		for (MutableEntry me : smallIdx) {
			assertEquals(smallIdx.findOffset(me.toObjectId())
		}
		for (MutableEntry me : denseIdx) {
			assertEquals(denseIdx.findOffset(me.toObjectId())
		}
	}

	@Test
	public void testCompareEntriesOffsetsWithGetOffsets() {
		int i = 0;
		for (MutableEntry me : smallIdx) {
			assertEquals(smallIdx.getOffset(i++)
		}
		int j = 0;
		for (MutableEntry me : denseIdx) {
			assertEquals(denseIdx.getOffset(j++)
		}
	}

	@Test
	public void testIteratorReturnedValues2() {
		Iterator<PackIndex.MutableEntry> iter = denseIdx.iterator();
		while (!iter.next().name().equals(
				"0a3d7772488b6b106fb62813c4d6d627918d9181")) {
		}
		assertEquals("1004d0d7ac26fbf63050a234c9b88a46075719d3"
		assertEquals("10da5895682013006950e7da534b705252b03be6"
		assertEquals("1203b03dc816ccbb67773f28b3c19318654b0bc8"
				.name());
	}

}
