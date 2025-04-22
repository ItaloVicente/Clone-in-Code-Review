
package org.eclipse.jgit.revwalk;

import java.io.IOException;

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.RevWalkException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdOwnerMap;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;

public class DepthRevWalk extends RevWalk {
        private int depth;
	final RevFlag SHALLOW;

	public DepthRevWalk(final Repository repo
		super(repo);

		this.depth = depth;
		SHALLOW = shallow == null ? newFlag("SHALLOW") : shallow;
	}

	public DepthRevWalk(ObjectReader or
		super(or);

		this.depth = depth;
		SHALLOW = shallow == null ? newFlag("SHALLOW") : shallow;
	}

	@Override
	public void markUninteresting(final RevCommit c)
			throws MissingObjectException
			IOException {
	}

	@Override
	protected RevCommit createCommit(final AnyObjectId id) {
		return new DepthCommit(id);
	}

	public int getDepth() {
		return depth;
	}

	void setDepth(int depth) {
		this.depth = depth;
	}

	@Override
	public ObjectWalk toObjectWalkWithSameObjects() {
		DepthObjectWalk dow = new DepthObjectWalk(reader
		RevWalk rw = dow;
		rw.objects = objects;
		rw.freeFlags = freeFlags;
		return dow;
	}
}
