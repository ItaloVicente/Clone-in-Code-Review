
package org.eclipse.jgit.revwalk.filter;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.revwalk.ObjectWalk;

public abstract class ObjectFilter {
	public static final ObjectFilter ALL = new AllFilter();

	private static final class AllFilter extends ObjectFilter {
		@Override
		public boolean include(ObjectWalk walker
			return true;
		}
	}

	public abstract boolean include(ObjectWalk walker
			throws MissingObjectException
			       IOException;
}
