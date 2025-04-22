
package org.eclipse.jgit.revwalk;

import java.io.IOException;
import java.util.Arrays;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.revwalk.AddToBitmapFilter;
import org.eclipse.jgit.internal.revwalk.AddUnseenToBitmapFilter;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.BitmapIndex;
import org.eclipse.jgit.lib.BitmapIndex.Bitmap;
import org.eclipse.jgit.lib.BitmapIndex.BitmapBuilder;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;

final class BitmapCalculator {

	private final RevWalk walk;

	private final BitmapIndex bitmapIndex;

	private final ProgressMonitor pm;

	private long countOfBitmapIndexMisses;

	private final BitmapWalkHook preWalkHook;

	private final BitmapWalkHook postWalkHook;

	interface BitmapWalkHook {
		void run(RevWalk walk
				ProgressMonitor pm) throws IOException;
	}

	private static final BitmapWalkHook NULL_BITMAP_HOOK = new BitmapWalkHook() {
		@Override
		public void run(RevWalk walk
				ProgressMonitor pm) {
		}
	};

	public BitmapCalculator(RevWalk walk
			ProgressMonitor pm) {
		this(walk
	}

	BitmapCalculator(RevWalk walk
			ProgressMonitor pm
			BitmapWalkHook postWalkHook) {
		this.walk = walk;
		this.bitmapIndex = bitmapIndex;
		this.pm = (pm == null) ? NullProgressMonitor.INSTANCE : pm;
		this.preWalkHook = preWalkHook;
		this.postWalkHook = postWalkHook;
	}

	public long getCountOfBitmapIndexMisses() {
		return countOfBitmapIndexMisses;
	}

	public BitmapBuilder getBitmapFor(Iterable<? extends ObjectId> start
			BitmapBuilder seen
			throws MissingObjectException
			IOException {
		if (!ignoreMissing) {
			return walkBuildingBitmap(start
		}

		try {
			return walkBuildingBitmap(start
		} catch (MissingObjectException ignore) {
		}

		final BitmapBuilder result = bitmapIndex.newBitmapBuilder();
		for (ObjectId obj : start) {
			Bitmap bitmap = bitmapIndex.getBitmap(obj);
			if (bitmap != null) {
				result.or(bitmap);
			}
		}

		for (ObjectId obj : start) {
			if (result.contains(obj)) {
				continue;
			}
			try {
				result.or(walkBuildingBitmap(Arrays.asList(obj)
			} catch (MissingObjectException ignore) {
			}
		}
		return result;
	}

	private BitmapBuilder walkBuildingBitmap(Iterable<? extends ObjectId> start
			BitmapBuilder seen
			throws MissingObjectException
			IOException {
		walk.reset();
		final BitmapBuilder bitmapResult = bitmapIndex.newBitmapBuilder();

		for (ObjectId obj : start) {
			Bitmap bitmap = bitmapIndex.getBitmap(obj);
			if (bitmap != null)
				bitmapResult.or(bitmap);
		}

		boolean marked = false;
		for (ObjectId obj : start) {
			try {
				if (!bitmapResult.contains(obj)) {
					walk.markStart(walk.parseCommit(obj));
					marked = true;
				}
			} catch (MissingObjectException e) {
				if (ignoreMissingStart)
					continue;
				throw e;
			}
		}

		if (marked) {
			if (seen == null) {
				walk.setRevFilter(new AddToBitmapFilter(bitmapResult));
			} else {
				walk.setRevFilter(
						new AddUnseenToBitmapFilter(seen
			}

			preWalkHook.run(walk

			while (walk.next() != null) {
				pm.update(1);
				countOfBitmapIndexMisses++;
			}

			postWalkHook.run(walk
		}

		return bitmapResult;
	}
}
