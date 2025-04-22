
package org.eclipse.jgit.revwalk;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;

class TopoSortGenerator extends Generator {
	private static final int TOPO_DELAY = RevWalk.TOPO_DELAY;

	private final FIFORevQueue pending;

	private final int outputType;

	TopoSortGenerator(Generator s) throws MissingObjectException
			IncorrectObjectTypeException
		pending = new FIFORevQueue();
		outputType = s.outputType() | SORT_TOPO;
		s.shareFreeList(pending);
		for (;;) {
			final RevCommit c = s.next();
			if (c == null)
				break;
			for (RevCommit p : c.parents)
				p.inDegree++;
			pending.add(c);
		}
	}

	@Override
	int outputType() {
		return outputType;
	}

	@Override
	void shareFreeList(BlockRevQueue q) {
		q.shareFreeList(pending);
	}

	@Override
	RevCommit next() throws MissingObjectException
			IncorrectObjectTypeException
		for (;;) {
			final RevCommit c = pending.next();
			if (c == null)
				return null;

			if (c.inDegree > 0) {
				c.flags |= TOPO_DELAY;
				continue;
			}

			for (RevCommit p : c.parents) {
				if (--p.inDegree == 0 && (p.flags & TOPO_DELAY) != 0) {
					p.flags &= ~TOPO_DELAY;
					pending.unpop(p);
				}
			}
			return c;
		}
	}
}
