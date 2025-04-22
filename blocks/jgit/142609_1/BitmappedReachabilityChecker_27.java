package org.eclipse.jgit.revwalk;

import static java.util.Objects.requireNonNull;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.revwalk.AddToBitmapFilter;
import org.eclipse.jgit.lib.BitmapIndex;
import org.eclipse.jgit.lib.BitmapIndex.Bitmap;
import org.eclipse.jgit.lib.BitmapIndex.BitmapBuilder;
import org.eclipse.jgit.lib.ProgressMonitor;

class BitmapCalculator {

	private final RevWalk walk;
	private final BitmapIndex bitmapIndex;

	BitmapCalculator(RevWalk walk) throws IOException {
		this.walk = walk;
		this.bitmapIndex = requireNonNull(
				walk.getObjectReader().getBitmapIndex());
	}

	BitmapBuilder getBitmap(RevCommit start
			throws MissingObjectException
			IncorrectObjectTypeException
		Bitmap precalculatedBitmap = bitmapIndex.getBitmap(start);
		if (precalculatedBitmap != null) {
			return asBitmapBuilder(precalculatedBitmap);
		}

		walk.reset();
		walk.sort(RevSort.TOPO);
		walk.markStart(start);

		BitmapBuilder bitmapResult = bitmapIndex.newBitmapBuilder();
		walk.setRevFilter(new AddToBitmapFilter(bitmapResult));
		while (walk.next() != null) {
			pm.update(1);
		}

		return bitmapResult;
	}

	private BitmapBuilder asBitmapBuilder(Bitmap bitmap) {
		return bitmapIndex.newBitmapBuilder().or(bitmap);
	}
}
