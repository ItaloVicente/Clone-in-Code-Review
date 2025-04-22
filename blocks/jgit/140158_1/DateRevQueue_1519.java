
package org.eclipse.jgit.revwalk;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;

class BoundaryGenerator extends Generator {
	static final int UNINTERESTING = RevWalk.UNINTERESTING;

	Generator g;

	BoundaryGenerator(RevWalk w
		g = new InitialGenerator(w
	}

	@Override
	int outputType() {
		return g.outputType() | HAS_UNINTERESTING;
	}

	@Override
	void shareFreeList(BlockRevQueue q) {
		g.shareFreeList(q);
	}

	@Override
	RevCommit next() throws MissingObjectException
			IncorrectObjectTypeException
		return g.next();
	}

	private class InitialGenerator extends Generator {
		private static final int PARSED = RevWalk.PARSED;

		private static final int DUPLICATE = RevWalk.TEMP_MARK;

		private final RevWalk walk;

		private final FIFORevQueue held;

		private final Generator source;

		InitialGenerator(RevWalk w
			walk = w;
			held = new FIFORevQueue();
			source = s;
			source.shareFreeList(held);
		}

		@Override
		int outputType() {
			return source.outputType();
		}

		@Override
		void shareFreeList(BlockRevQueue q) {
			q.shareFreeList(held);
		}

		@Override
		RevCommit next() throws MissingObjectException
				IncorrectObjectTypeException
			RevCommit c = source.next();
			if (c != null) {
				for (RevCommit p : c.parents)
					if ((p.flags & UNINTERESTING) != 0)
						held.add(p);
				return c;
			}

			final FIFORevQueue boundary = new FIFORevQueue();
			boundary.shareFreeList(held);
			for (;;) {
				c = held.next();
				if (c == null)
					break;
				if ((c.flags & DUPLICATE) != 0)
					continue;
				if ((c.flags & PARSED) == 0)
					c.parseHeaders(walk);
				c.flags |= DUPLICATE;
				boundary.add(c);
			}
			boundary.removeFlag(DUPLICATE);
			g = boundary;
			return boundary.next();
		}
	}
}
