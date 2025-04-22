
package org.eclipse.jgit.revwalk;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.filter.RevFilter;

class PendingGenerator extends Generator {
	private static final int PARSED = RevWalk.PARSED;

	private static final int SEEN = RevWalk.SEEN;

	private static final int UNINTERESTING = RevWalk.UNINTERESTING;

	static final int OVER_SCAN = 5 + 1;

	private static final RevCommit INIT_LAST;

	static {
		INIT_LAST = new RevCommit(ObjectId.zeroId());
		INIT_LAST.commitTime = Integer.MAX_VALUE;
	}

	private final RevWalk walker;

	private final DateRevQueue pending;

	private final RevFilter filter;

	private final int output;

	private RevCommit last = INIT_LAST;

	private int overScan = OVER_SCAN;

	boolean canDispose;

	PendingGenerator(final RevWalk w
			final RevFilter f
		walker = w;
		pending = p;
		filter = f;
		output = out;
		canDispose = true;
	}

	@Override
	int outputType() {
		return output | SORT_COMMIT_TIME_DESC;
	}

	@Override
	RevCommit next() throws MissingObjectException
			IncorrectObjectTypeException
		try {
			for (;;) {
				final RevCommit c = pending.next();
				if (c == null) {
					return null;
				}

				final boolean produce;
				if ((c.flags & UNINTERESTING) != 0)
					produce = false;
				else {
					if (filter.requiresCommitBody())
						c.parseBody(walker);
					produce = filter.include(walker
				}

				for (RevCommit p : c.parents) {
					if ((p.flags & SEEN) != 0)
						continue;
					if ((p.flags & PARSED) == 0)
						p.parseHeaders(walker);
					p.flags |= SEEN;
					pending.add(p);
				}
				walker.carryFlagsImpl(c);

				if ((c.flags & UNINTERESTING) != 0) {
					if (pending.everbodyHasFlag(UNINTERESTING)) {
						final RevCommit n = pending.peek();
						if (n != null && n.commitTime >= last.commitTime) {
							overScan = OVER_SCAN;
						} else if (--overScan == 0)
							throw StopWalkException.INSTANCE;
					} else {
						overScan = OVER_SCAN;
					}
					if (canDispose)
						c.disposeBody();
					continue;
				}

				if (produce)
					return last = c;
				else if (canDispose)
					c.disposeBody();
			}
		} catch (StopWalkException swe) {
			pending.clear();
			return null;
		}
	}
}
