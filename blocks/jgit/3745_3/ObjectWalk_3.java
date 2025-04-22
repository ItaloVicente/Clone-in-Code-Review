
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
		this.SHALLOW = newFlag("SHALLOW");
	}

	public DepthRevWalk(ObjectReader or
		super(or);

		this.depth = depth;
		this.SHALLOW = newFlag("SHALLOW");
	}

	@Override
	public void markUninteresting(final RevCommit c)
			throws MissingObjectException
			IncorrectObjectTypeException
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
				SHALLOW
		return super.toObjectWalkWithSameObjects(dow);
	}
}
