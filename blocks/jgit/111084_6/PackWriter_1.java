
package org.eclipse.jgit.internal.revwalk;

import org.eclipse.jgit.lib.BitmapIndex.Bitmap;
import org.eclipse.jgit.lib.BitmapIndex.BitmapBuilder;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevFlag;

public class AddUnseenToBitmapFilter extends RevFilter {
	private final BitmapBuilder seen;
	private final BitmapBuilder bitmap;

	public AddUnseenToBitmapFilter(BitmapBuilder seen
		this.seen = seen;
		this.bitmap = bitmapResult;
	}

	@Override
	public final boolean include(RevWalk walker
		Bitmap visitedBitmap;

		if (seen.contains(cmit) || bitmap.contains(cmit)) {
		} else if ((visitedBitmap = bitmap.getBitmapIndex()
				.getBitmap(cmit)) != null) {
			bitmap.or(visitedBitmap);
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
