
package org.eclipse.jgit.internal.revwalk;

import org.eclipse.jgit.internal.storage.pack.BitmapCommit;
import org.eclipse.jgit.lib.BitmapIndex.Bitmap;
import org.eclipse.jgit.lib.BitmapIndex.BitmapBuilder;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevFlag;

public class AddToBitmapWithCacheFilter extends RevFilter {
	private final BitmapCommit prevCommit;

	private final BitmapBuilder bitmap;

	public AddToBitmapWithCacheFilter(BitmapCommit prevCommit
			BitmapBuilder bitmap) {
		this.prevCommit = prevCommit;
		this.bitmap = bitmap;
	}

	@Override
	public final boolean include(RevWalk walker
		Bitmap visitedBitmap;

		if (bitmap.contains(cmit)) {
		} else if ((visitedBitmap = bitmap.getBitmapIndex()
				.getBitmap(cmit)) != null) {
			bitmap.or(visitedBitmap);
		} else if (prevCommit.equals(cmit)) {
			bitmap.or(prevCommit.getBitmap());
		} else {
			bitmap.addObject(cmit
			return true;
		}

		for (RevCommit p : cmit.getParents()) {
			p.add(RevFlag.SEEN);
		}
		return false;
	}

	@Override
	public final RevFilter clone() {
		throw new UnsupportedOperationException();
	}

	@Override
	public final boolean requiresCommitBody() {
		return false;
	}
}
