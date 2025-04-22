
package org.eclipse.jgit.merge;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.EmptyTreeIterator;

public abstract class ThreeWayMerger extends Merger {
	private RevTree baseTree;

	private ObjectId baseCommitId;

	protected ThreeWayMerger(Repository local) {
		super(local);
	}

	protected ThreeWayMerger(Repository local
		this(local);
	}

	protected ThreeWayMerger(ObjectInserter inserter) {
		super(inserter);
	}

	public void setBase(AnyObjectId id) throws MissingObjectException
			IncorrectObjectTypeException
		if (id != null) {
			baseTree = walk.parseTree(id);
		} else {
			baseTree = null;
		}
	}

	@Override
	public boolean merge(AnyObjectId... tips) throws IOException {
		if (tips.length != 2)
			return false;
		return super.merge(tips);
	}

	@Override
	public ObjectId getBaseCommitId() {
		return baseCommitId;
	}

	protected AbstractTreeIterator mergeBase() throws IOException {
		if (baseTree != null)
			return openTree(baseTree);
		RevCommit baseCommit = (baseCommitId != null) ? walk
				.parseCommit(baseCommitId) : getBaseCommit(sourceCommits[0]
				sourceCommits[1]);
		if (baseCommit == null) {
			baseCommitId = null;
			return new EmptyTreeIterator();
		} else {
			baseCommitId = baseCommit.toObjectId();
			return openTree(baseCommit.getTree());
		}
	}
}
