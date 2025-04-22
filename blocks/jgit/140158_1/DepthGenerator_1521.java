
package org.eclipse.jgit.revwalk;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;

final class DelayRevQueue extends Generator {
	private static final int OVER_SCAN = PendingGenerator.OVER_SCAN;

	private final Generator pending;

	private final FIFORevQueue delay;

	private int size;

	DelayRevQueue(Generator g) {
		pending = g;
		delay = new FIFORevQueue();
	}

	@Override
	int outputType() {
		return pending.outputType();
	}

	@Override
	RevCommit next() throws MissingObjectException
			IncorrectObjectTypeException
		while (size < OVER_SCAN) {
			final RevCommit c = pending.next();
			if (c == null)
				break;
			delay.add(c);
			size++;
		}

		final RevCommit c = delay.next();
		if (c == null)
			return null;
		size--;
		return c;
	}
}
