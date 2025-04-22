package org.eclipse.jgit.internal.storage.pack;

import static org.eclipse.jgit.storage.pack.PackConfig.DEFAULT_BITMAP_CONTIGUOUS_COMMIT_COUNT;
import static org.eclipse.jgit.storage.pack.PackConfig.DEFAULT_BITMAP_DISTANT_COMMIT_SPAN;
import static org.eclipse.jgit.storage.pack.PackConfig.DEFAULT_BITMAP_RECENT_COMMIT_COUNT;
import static org.eclipse.jgit.storage.pack.PackConfig.DEFAULT_BITMAP_RECENT_COMMIT_SPAN;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.storage.file.PackBitmapIndexBuilder;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.storage.pack.PackConfig;
import org.junit.Test;

public class PackWriterBitmapPreparerTest {
	private static class StubObjectReader extends ObjectReader {
		@Override
		public ObjectReader newReader() {
			return null;
		}

		@Override
		public Collection<ObjectId> resolve(AbbreviatedObjectId id)
				throws IOException {
			return null;
		}

		@Override
		public ObjectLoader open(AnyObjectId objectId
				throws MissingObjectException
				IOException {
			return null;
		}

		@Override
		public Set<ObjectId> getShallowCommits() throws IOException {
			return null;
		}

		@Override
		public void close() {
		}
	}

	@Test
	public void testNextSelectionDistanceForActiveBranch() throws Exception {
		PackWriterBitmapPreparer preparer = newPeparer(
				DEFAULT_BITMAP_CONTIGUOUS_COMMIT_COUNT
				DEFAULT_BITMAP_RECENT_COMMIT_COUNT
				DEFAULT_BITMAP_RECENT_COMMIT_SPAN
				DEFAULT_BITMAP_DISTANT_COMMIT_SPAN);
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(1
		assertEquals(2
		assertEquals(10
		assertEquals(64
		assertEquals(99
		assertEquals(100
		assertEquals(100
		assertEquals(100
		assertEquals(100
		assertEquals(100
		assertEquals(100
		assertEquals(100
		assertEquals(100
		assertEquals(100
		assertEquals(100
		assertEquals(100
		assertEquals(101
		assertEquals(200
		assertEquals(1000
		assertEquals(4999
		assertEquals(5000
		assertEquals(5000
		assertEquals(5000
		assertEquals(5000
	}

	@Test
	public void testNextSelectionDistanceWithMoreContiguousCommits()
			throws Exception {
		PackWriterBitmapPreparer preparer = newPeparer(1000
				DEFAULT_BITMAP_RECENT_COMMIT_COUNT
				DEFAULT_BITMAP_RECENT_COMMIT_SPAN
				DEFAULT_BITMAP_DISTANT_COMMIT_SPAN);
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(1
		assertEquals(99
		assertEquals(100
		assertEquals(100
		assertEquals(100
		assertEquals(100
		assertEquals(100
		assertEquals(100
		assertEquals(100
		assertEquals(100
		assertEquals(101
		assertEquals(200
		assertEquals(1000
		assertEquals(4999
		assertEquals(5000
		assertEquals(5000
		assertEquals(5000
		assertEquals(5000
	}

	@Test
	public void testNextSelectionDistanceWithLessRecentCommits()
			throws Exception {
		PackWriterBitmapPreparer preparer = newPeparer(
				DEFAULT_BITMAP_CONTIGUOUS_COMMIT_COUNT
				DEFAULT_BITMAP_RECENT_COMMIT_SPAN
				DEFAULT_BITMAP_DISTANT_COMMIT_SPAN);
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(1
		assertEquals(2
		assertEquals(10
		assertEquals(64
		assertEquals(99
		assertEquals(100
		assertEquals(100
		assertEquals(100
		assertEquals(100
		assertEquals(101
		assertEquals(300
		assertEquals(4999
		assertEquals(5000
		assertEquals(5000
		assertEquals(5000
		assertEquals(5000
	}

	@Test
	public void testNextSelectionDistanceWithSmallerRecentSpan()
			throws Exception {
		PackWriterBitmapPreparer preparer = newPeparer(
				DEFAULT_BITMAP_CONTIGUOUS_COMMIT_COUNT
				DEFAULT_BITMAP_RECENT_COMMIT_COUNT
				DEFAULT_BITMAP_DISTANT_COMMIT_SPAN);
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(1
		assertEquals(2
		assertEquals(10
		assertEquals(10
		assertEquals(10
		assertEquals(10
		assertEquals(11
		assertEquals(200
		assertEquals(1000
		assertEquals(4999
		assertEquals(5000
		assertEquals(5000
		assertEquals(5000
		assertEquals(5000
	}

	@Test
	public void testNextSelectionDistanceWithShorterDistantSpan()
			throws Exception {
		PackWriterBitmapPreparer preparer = newPeparer(
				DEFAULT_BITMAP_CONTIGUOUS_COMMIT_COUNT
				DEFAULT_BITMAP_RECENT_COMMIT_COUNT
				DEFAULT_BITMAP_RECENT_COMMIT_SPAN
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(1
		assertEquals(2
		assertEquals(10
		assertEquals(64
		assertEquals(99
		assertEquals(100
		assertEquals(100
		assertEquals(100
		assertEquals(100
		assertEquals(100
		assertEquals(100
		assertEquals(100
		assertEquals(100
		assertEquals(100
		assertEquals(100
		assertEquals(100
		assertEquals(101
		assertEquals(200
		assertEquals(1000
		assertEquals(1000
		assertEquals(1000
		assertEquals(1000
		assertEquals(1000
	}

	@Test
	public void testNextSelectionDistanceWithCommitOffset() throws Exception {
		PackWriterBitmapPreparer preparer = newPeparer(
				DEFAULT_BITMAP_CONTIGUOUS_COMMIT_COUNT
				DEFAULT_BITMAP_RECENT_COMMIT_COUNT
				DEFAULT_BITMAP_RECENT_COMMIT_SPAN
				DEFAULT_BITMAP_DISTANT_COMMIT_SPAN);
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(1
		assertEquals(99
		assertEquals(100
		assertEquals(100
		assertEquals(100
		assertEquals(101
		assertEquals(4999
		assertEquals(5000
		assertEquals(5000
		assertEquals(5000
				preparer.nextSelectionDistance(10000
		assertEquals(5000
				preparer.nextSelectionDistance(10000
	}

	@Test
	public void testNextSelectionDistanceForInactiveBranch() throws Exception {
		PackWriterBitmapPreparer preparer = newPeparer(
				DEFAULT_BITMAP_CONTIGUOUS_COMMIT_COUNT
				DEFAULT_BITMAP_RECENT_COMMIT_COUNT
				DEFAULT_BITMAP_RECENT_COMMIT_SPAN
				DEFAULT_BITMAP_DISTANT_COMMIT_SPAN);
		assertEquals(0
		assertEquals(100
		assertEquals(100
		assertEquals(100
		assertEquals(5000
		assertEquals(5000
		assertEquals(5000
		assertEquals(5000
	}

	private PackWriterBitmapPreparer newPeparer(int consecutive
			int recentCount
					throws IOException {
		List<ObjectToPack> objects = Collections.emptyList();
		Set<ObjectId> wants = Collections.emptySet();
		PackConfig config = new PackConfig();
		config.setBitmapContiguousCommitCount(consecutive);
		config.setBitmapRecentCommitCount(recentCount);
		config.setBitmapRecentCommitSpan(recentSpan);
		config.setBitmapDistantCommitSpan(distantSpan);
		PackBitmapIndexBuilder indexBuilder = new PackBitmapIndexBuilder(
				objects);
		return new PackWriterBitmapPreparer(new StubObjectReader()
				indexBuilder
	}
}
