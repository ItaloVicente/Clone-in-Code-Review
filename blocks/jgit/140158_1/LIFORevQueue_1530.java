
package org.eclipse.jgit.revwalk;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;

abstract class Generator {
	static final int SORT_COMMIT_TIME_DESC = 1 << 0;

	static final int HAS_REWRITE = 1 << 1;

	static final int NEEDS_REWRITE = 1 << 2;

	static final int SORT_TOPO = 1 << 3;

	static final int HAS_UNINTERESTING = 1 << 4;

	void shareFreeList(BlockRevQueue q) {
	}

	abstract int outputType();

	abstract RevCommit next() throws MissingObjectException
			IncorrectObjectTypeException
}
