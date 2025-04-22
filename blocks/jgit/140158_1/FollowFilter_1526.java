
package org.eclipse.jgit.revwalk;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;

final class FixUninterestingGenerator extends Generator {
	private final Generator pending;

	FixUninterestingGenerator(Generator g) {
		pending = g;
	}

	@Override
	int outputType() {
		return pending.outputType();
	}

	@Override
	RevCommit next() throws MissingObjectException
			IncorrectObjectTypeException
		for (;;) {
			final RevCommit c = pending.next();
			if (c == null)
				return null;
			if ((c.flags & RevWalk.UNINTERESTING) == 0)
				return c;
		}
	}
}
