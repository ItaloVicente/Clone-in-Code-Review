
package org.eclipse.jgit.treewalk.filter;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.treewalk.TreeWalk;

public class TreeFilterMarker {

	private final TreeFilter[] filters;

	public TreeFilterMarker(TreeFilter[] markTreeFilters) {
		if (markTreeFilters.length > Integer.SIZE) {
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().treeFilterMarkerTooManyFilters
					Integer.valueOf(Integer.SIZE)
					Integer.valueOf(markTreeFilters.length)));
		}
		filters = new TreeFilter[markTreeFilters.length];
		System.arraycopy(markTreeFilters
	}

	public int getMarks(TreeWalk walk) throws MissingObjectException
			IncorrectObjectTypeException
		int marks = 0;
		for (int index = 0; index < filters.length; index++) {
			TreeFilter filter = filters[index];
			if (filter != null) {
				try {
					boolean marked = filter.include(walk);
					if (marked)
						marks |= (1L << index);
				} catch (StopWalkException e) {
					filters[index] = null;
				}
			}
		}
		return marks;
	}

}
