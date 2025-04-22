
package org.eclipse.jgit.revwalk;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;

class DepthGenerator extends Generator {
	private final FIFORevQueue pending;

	private final int depth;
	private final RevWalk walk;

	private final RevFlag UNSHALLOW;

	private final RevFlag REINTERESTING;

	DepthGenerator(DepthWalk w
			IncorrectObjectTypeException
		pending = new FIFORevQueue();
		walk = (RevWalk)w;

		this.depth = w.getDepth();
		this.UNSHALLOW = w.getUnshallowFlag();
		this.REINTERESTING = walk.newFlag("REINTERESTING");

		s.shareFreeList(pending);

		for (;;) {
			final RevCommit c = s.next();
			if (c == null)
				break;
			if ((c.flags & RevWalk.UNINTERESTING) == 0 && !c.has(UNSHALLOW)) {
				((DepthWalk.Commit)c).depth = 0;
				pending.add(c);
			}
		}
	}

	@Override
	int outputType() {
		return pending.outputType() | HAS_UNINTERESTING;
	}

	@Override
	void shareFreeList(final BlockRevQueue q) {
		pending.shareFreeList(q);
	}

	@Override
	RevCommit next() throws MissingObjectException
			IncorrectObjectTypeException
		for (;;) {
			final DepthWalk.Commit c = (DepthWalk.Commit)pending.next();
			if (c == null)
				return null;

			if ((c.flags & RevWalk.PARSED) == 0)
				c.parseHeaders(walk);

			int newDepth = c.depth + 1;

			for (final RevCommit p : c.parents) {
				DepthWalk.Commit dp = (DepthWalk.Commit)p;

				if (dp.depth == -1) {
					dp.depth = newDepth;

					if (newDepth <= depth)
						pending.add(p);
				}

				if(c.has(UNSHALLOW) || c.has(REINTERESTING))
					p.add(REINTERESTING);
			}

			boolean produce = c.depth <= depth;

			if (c.has(REINTERESTING))
				c.flags &= ~RevWalk.UNINTERESTING;

			if ((c.flags & RevWalk.UNINTERESTING) != 0 && !c.has(UNSHALLOW))
				produce = false;

			if (produce)
				return c;
		}
	}
}
