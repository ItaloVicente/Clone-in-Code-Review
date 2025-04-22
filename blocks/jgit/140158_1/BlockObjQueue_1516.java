
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
import org.eclipse.jgit.revwalk.filter.ObjectFilter;

public final class BitmapWalker {

	private final ObjectWalk walker;

	private final BitmapIndex bitmapIndex;

	private final ProgressMonitor pm;

	private long countOfBitmapIndexMisses;

	public BitmapWalker(
			ObjectWalk walker
		this.walker = walker;
		this.bitmapIndex = bitmapIndex;
		this.pm = (pm == null) ? NullProgressMonitor.INSTANCE : pm;
	}

	public long getCountOfBitmapIndexMisses() {
		return countOfBitmapIndexMisses;
	}

	public BitmapBuilder findObjects(Iterable<? extends ObjectId> start
			boolean ignoreMissing)
			throws MissingObjectException
				   IOException {
		if (!ignoreMissing) {
			return findObjectsWalk(start
		}

		try {
			return findObjectsWalk(start
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
				result.or(findObjectsWalk(Arrays.asList(obj)
			} catch (MissingObjectException ignore) {
			}
		}
		return result;
	}

	private BitmapBuilder findObjectsWalk(Iterable<? extends ObjectId> start
			boolean ignoreMissingStart)
			throws MissingObjectException
			IOException {
		walker.reset();
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
					walker.markStart(walker.parseAny(obj));
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
				walker.setRevFilter(new AddToBitmapFilter(bitmapResult));
			} else {
				walker.setRevFilter(
						new AddUnseenToBitmapFilter(seen
			}
			walker.setObjectFilter(new BitmapObjectFilter(bitmapResult));

			while (walker.next() != null) {
				pm.update(1);
				countOfBitmapIndexMisses++;
			}

			RevObject ro;
			while ((ro = walker.nextObject()) != null) {
				bitmapResult.addObject(ro
				pm.update(1);
			}
		}

		return bitmapResult;
	}

	static class BitmapObjectFilter extends ObjectFilter {
		private final BitmapBuilder bitmap;

		BitmapObjectFilter(BitmapBuilder bitmap) {
			this.bitmap = bitmap;
		}

		@Override
		public final boolean include(ObjectWalk walker
			throws MissingObjectException
			       IOException {
			return !bitmap.contains(objid);
		}
	}
}
