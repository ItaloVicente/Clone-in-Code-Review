
package org.eclipse.jgit.revwalk;

import java.io.IOException;
import java.util.Collection;

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

import static org.eclipse.jgit.revwalk.RevWalk.UNINTERESTING;
public class DepthObjectWalk extends ObjectWalk {
	private int depth;

	final RevFlag BOUNDARY;

	final RevFlag SHALLOW;

	private final Collection<? extends ObjectId> clientShallows;

	public DepthObjectWalk(final Repository repo
			final Collection<? extends ObjectId> clientShallows) {
		super(repo);

		this.depth = depth;
		this.clientShallows = clientShallows;
		this.SHALLOW = newFlag("SHALLOW");
		BOUNDARY = newFlag("BOUNDARY");
	}

	public DepthObjectWalk(ObjectReader or
			final Collection<? extends ObjectId> clientShallows) {
		super(or);

		this.depth = depth;
		this.clientShallows = clientShallows;
		SHALLOW = newFlag("SHALLOW");
		BOUNDARY = newFlag("BOUNDARY");
	}

	DepthObjectWalk(ObjectReader or
			final Collection<? extends ObjectId> clientShallows) {
		super(or);

		this.depth = depth;
		SHALLOW = shallow == null ? newFlag("SHALLOW") : shallow;
		this.clientShallows = clientShallows;
		BOUNDARY = newFlag("BOUNDARY");
	}

	@Override
	protected RevCommit createCommit(final AnyObjectId id) {
		return new DepthCommit(id);
	}

	public int getDepth() {
		return depth;
	}

	@Override
	public void markUninteresting(RevObject o) throws MissingObjectException
			IncorrectObjectTypeException
		while (o instanceof RevTag) {
			o.flags |= UNINTERESTING;
			if (hasRevSort(RevSort.BOUNDARY))
				addObject(o);
			o = ((RevTag) o).getObject();
			parseHeaders(o);
		}

		if (o instanceof RevCommit)
			markUninteresting((RevCommit) o);
		else if (o instanceof RevTree)
			markTreeUninteresting((RevTree) o);
		else
			o.flags |= UNINTERESTING;

		if (o.getType() != Constants.OBJ_COMMIT
				&& hasRevSort(RevSort.BOUNDARY)) {
			addObject(o);
		}
	}

	@Override
	public void markUninteresting(final RevCommit c)
			throws MissingObjectException
			IOException {
		c.flags |= UNINTERESTING;
		if (clientShallows == null || !clientShallows.contains(c)) {
			if ((c.flags & PARSED) == 0)
				c.parseHeaders(this);
			for (RevCommit p : c.parents)
				markUninteresting(p);
		}
	}

	@Override
	public RevCommit next() throws MissingObjectException
			IncorrectObjectTypeException
		for (;;) {
			final RevCommit r = super.next();
			if ((r != null)
					&& (r.flags & UNINTERESTING) != 0
					&& !r.has(BOUNDARY))
				continue;
			return r;
		}
	}

	void setDepth(int depth) {
		this.depth = depth;
	}

	@Override
	protected boolean shouldSkipObject(final RevObject o) {
		if (o.has(BOUNDARY))
			return !hasRevSort(RevSort.BOUNDARY);
		return (o.flags & UNINTERESTING) != 0;
	}
}
