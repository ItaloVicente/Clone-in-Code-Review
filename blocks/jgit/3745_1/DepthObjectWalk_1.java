
package org.eclipse.jgit.revwalk;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;

class DepthGenerator extends Generator {
	final static int UNINTERESTING = RevWalk.UNINTERESTING;

	private final FIFORevQueue pending;
	private final int depth;
	private final RevWalk walk;
	final RevFlag SHALLOW;
	final RevFlag BOUNDARY;

			The maximum commit depth to generate
			The flag that denotes maximum depth
			The flag that denotes the edge of an uninteresting area
	DepthGenerator(RevWalk w
			final DateRevQueue s) throws MissingObjectException
			IncorrectObjectTypeException
		pending = new FIFORevQueue();
		walk = w;
		depth = d;
		SHALLOW = shallow;
		BOUNDARY = boundary;

		s.shareFreeList(pending);

		for (;;) {
			final DepthCommit c = (DepthCommit)s.next();
			if (c == null)
				break;
			c.depth = 0;
			pending.add(c);
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
			final DepthCommit c = (DepthCommit)pending.next();
			if (c == null)
				return null;

			if ((c.flags & RevWalk.PARSED) == 0)
				c.parseHeaders(walk);

			if (c.depth > depth) {
				c.flags |= UNINTERESTING;
				continue;
			}
			if (c.depth == depth)
				c.add(SHALLOW);

			int newDepth = c.depth + 1;

			for (final RevCommit p : c.parents) {
				DepthCommit dp = (DepthCommit)p;
				boolean add = false;

				if (dp.depth == -1) {
					dp.depth = newDepth;
					add = true;
				}

				if ((c.flags & UNINTERESTING) == 0
						&& ((p.flags & UNINTERESTING) != 0)
						&& (!p.has(BOUNDARY))) {
					p.add(BOUNDARY);
					add = true;
				}

				if (add)
					pending.add(p);
			}

			return c;
		}
	}
}
