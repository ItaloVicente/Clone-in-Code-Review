
package org.eclipse.jgit.treewalk;

import java.io.IOException;

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;

public class EmptyTreeIterator extends AbstractTreeIterator {
	public EmptyTreeIterator() {
	}

	EmptyTreeIterator(AbstractTreeIterator p) {
		super(p);
		pathLen = pathOffset;
	}

	public EmptyTreeIterator(final AbstractTreeIterator p
			final byte[] childPath
		super(p
		pathLen = childPathOffset - 1;
	}

	@Override
	public AbstractTreeIterator createSubtreeIterator(ObjectReader reader)
			throws IncorrectObjectTypeException
		return new EmptyTreeIterator(this);
	}

	@Override
	public boolean hasId() {
		return false;
	}

	@Override
	public ObjectId getEntryObjectId() {
		return ObjectId.zeroId();
	}

	@Override
	public byte[] idBuffer() {
		return zeroid;
	}

	@Override
	public int idOffset() {
		return 0;
	}

	@Override
	public void reset() {
	}

	@Override
	public boolean first() {
		return true;
	}

	@Override
	public boolean eof() {
		return true;
	}

	@Override
	public void next(int delta) throws CorruptObjectException {
	}

	@Override
	public void back(int delta) throws CorruptObjectException {
	}

	@Override
	public void stopWalk() {
		if (parent != null)
			parent.stopWalk();
	}

	@Override
	protected boolean needsStopWalk() {
		return parent != null && parent.needsStopWalk();
	}
}
