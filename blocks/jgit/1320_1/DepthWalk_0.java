
package org.eclipse.jgit.revwalk;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;

public class DepthGenerator extends Generator {
	private final FIFORevQueue pending;

	private final int outputType;
	private final int depth;
	private final DepthWalk.CompareMode compareMode;
	private final RevWalk walk;

	public DepthGenerator(RevWalk w
	IncorrectObjectTypeException
		pending = new FIFORevQueue();
		outputType = s.outputType();
		walk = w;
		this.depth = depth;
		this.compareMode = compareMode;

		s.shareFreeList(pending);

		for (;;) {
			final RevCommit c = s.next();
			if (c == null)
				break;
			pending.add(c);
		}
	}

	@Override
	int outputType() {
		return outputType;
	}

	@Override
	void shareFreeList(final BlockRevQueue q) {
		q.shareFreeList(pending);
	}

	@Override
	RevCommit next() throws MissingObjectException
			IncorrectObjectTypeException
		for (;;) {
			final RevCommit c = pending.next();
			if (c == null)
				return null;

			c.parseHeaders(walk);

			DepthWalk.Commit dc = (DepthWalk.Commit)c;
			int newDepth = dc.getDepth() + 1;

			for (final RevCommit p : c.parents) {
				DepthWalk.Commit dp = (DepthWalk.Commit)p;

				if (dp.getDepth() > newDepth) {
					dp.setDepth(newDepth);

					if (newDepth <= depth)
						pending.add(p);
				}
			}

			boolean produce = true;
			switch (compareMode) {
			case EQUAL:
				produce = (dc.getDepth() == depth);
				break;

			case LESS_THAN_EQUAL:
				produce = (dc.getDepth() <= depth);
				break;
			}

			if (produce)
				return c;
			else
				continue;
		}
	}
}
