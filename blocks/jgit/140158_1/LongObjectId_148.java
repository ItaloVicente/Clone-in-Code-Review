
package org.eclipse.jgit.lfs.lib;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lfs.LfsPointer;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectStream;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

public class LfsPointerFilter extends TreeFilter {

	private LfsPointer pointer;

	public LfsPointer getPointer() {
		return pointer;
	}

	@Override
	public boolean include(TreeWalk walk) throws MissingObjectException
			IncorrectObjectTypeException
		pointer = null;
		if (walk.isSubtree()) {
			return walk.isRecursive();
		}
		ObjectId objectId = walk.getObjectId(0);
		ObjectLoader object = walk.getObjectReader().open(objectId);
		if (object.getSize() > 1024) {
			return false;
		}

		try (ObjectStream stream = object.openStream()) {
			pointer = LfsPointer.parseLfsPointer(stream);
			return pointer != null;
		}
	}

	@Override
	public boolean shouldBeRecursive() {
		return false;
	}

	@Override
	public TreeFilter clone() {
		return new LfsPointerFilter();
	}
}
