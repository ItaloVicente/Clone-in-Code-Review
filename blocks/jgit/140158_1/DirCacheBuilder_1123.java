
package org.eclipse.jgit.dircache;

import java.io.IOException;

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;

public class DirCacheBuildIterator extends DirCacheIterator {
	private final DirCacheBuilder builder;

	public DirCacheBuildIterator(DirCacheBuilder dcb) {
		super(dcb.getDirCache());
		builder = dcb;
	}

	DirCacheBuildIterator(final DirCacheBuildIterator p
			final DirCacheTree dct) {
		super(p
		builder = p.builder;
	}

	@Override
	public AbstractTreeIterator createSubtreeIterator(ObjectReader reader)
			throws IncorrectObjectTypeException
		if (currentSubtree == null)
			throw new IncorrectObjectTypeException(getEntryObjectId()
					Constants.TYPE_TREE);
		return new DirCacheBuildIterator(this
	}

	@Override
	public void skip() throws CorruptObjectException {
		if (currentSubtree != null)
			builder.keep(ptr
		else
			builder.keep(ptr
		next(1);
	}

	@Override
	public void stopWalk() {
		final int cur = ptr;
		final int cnt = cache.getEntryCount();
		if (cur < cnt)
			builder.keep(cur
	}

	@Override
	protected boolean needsStopWalk() {
		return ptr < cache.getEntryCount();
	}
}
