package org.eclipse.jgit.internal.storage.pack;

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
				DEFAULT_BITMAP_RECENT_COMMIT_COUNT
				DEFAULT_BITMAP_RECENT_COMMIT_SPAN
		int[][] distancesAndSpans = { { 0
				{ 20000
				{ 22200
				{ 50000

		for (int[] pair : distancesAndSpans) {
			assertEquals(pair[1]
		}
	}

	@Test
	public void testNextSelectionDistanceWithFewerRecentCommits()
			throws Exception {
		PackWriterBitmapPreparer preparer = newPeparer(1000
				DEFAULT_BITMAP_RECENT_COMMIT_SPAN
		int[][] distancesAndSpans = { { 0
				{ 1100
				{ 6000
				{ 1000000

		for (int[] pair : distancesAndSpans) {
			assertEquals(pair[1]
		}
	}

	@Test
	public void testNextSelectionDistanceWithSmallerRecentSpan()
			throws Exception {
		PackWriterBitmapPreparer preparer = newPeparer(
				DEFAULT_BITMAP_RECENT_COMMIT_COUNT
				10
		int[][] distancesAndSpans = { { 0
				{ 20000
				{ 20200
				{ 25000

		for (int[] pair : distancesAndSpans) {
			assertEquals(pair[1]
		}
	}

	@Test
	public void testNextSelectionDistanceWithSmallerDistantSpan()
			throws Exception {
		PackWriterBitmapPreparer preparer = newPeparer(
				DEFAULT_BITMAP_RECENT_COMMIT_COUNT
				DEFAULT_BITMAP_RECENT_COMMIT_SPAN
				1000);
		int[][] distancesAndSpans = { { 0
				{ 20000
				{ 20999
				{ 25000

		for (int[] pair : distancesAndSpans) {
			assertEquals(pair[1]
		}
	}

	private PackWriterBitmapPreparer newPeparer(int recentCount
			int distantSpan) throws IOException {
		List<ObjectToPack> objects = Collections.emptyList();
		Set<ObjectId> wants = Collections.emptySet();
		PackConfig config = new PackConfig();
		config.setBitmapRecentCommitCount(recentCount);
		config.setBitmapRecentCommitSpan(recentSpan);
		config.setBitmapDistantCommitSpan(distantSpan);
		PackBitmapIndexBuilder indexBuilder = new PackBitmapIndexBuilder(
				objects);
		return new PackWriterBitmapPreparer(new StubObjectReader()
				indexBuilder
	}
}
