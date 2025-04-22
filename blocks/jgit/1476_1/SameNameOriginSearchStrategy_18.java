package org.eclipse.jgit.blame;

import java.io.IOException;

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

public class RenameModifiedSearchStrategy extends CopyModifiedSearchStrategy
		implements IOriginSearchStrategy {

	@Override
	protected TreeWalk createTreeWalk(Origin source
			throws MissingObjectException
			CorruptObjectException
		TreeWalk treeWalk = super.createTreeWalk(source
		treeWalk.addTree(source.getCommit().getTree());
		treeWalk.setFilter(TreeFilter.ANY_DIFF);
		return treeWalk;
	}

}
