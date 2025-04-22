
package org.eclipse.jgit.revwalk;

import java.text.MessageFormat;
import java.util.AbstractList;

import org.eclipse.jgit.internal.JGitText;

public class RevObjectList<E extends RevObject> extends AbstractList<E> {
	static final int BLOCK_SHIFT = 8;

	static final int BLOCK_SIZE = 1 << BLOCK_SHIFT;

	protected Block contents = new Block(0);

	protected int size = 0;

	public RevObjectList() {
	}

	@Override
	public void add(int index
		if (index != size)
			throw new UnsupportedOperationException(MessageFormat.format(
					JGitText.get().unsupportedOperationNotAddAtEnd
					Integer.valueOf(index)));
		set(index
		size++;
	}

	@Override
	@SuppressWarnings("unchecked")
	public E set(int index
		Block s = contents;
		while (index >> s.shift >= BLOCK_SIZE) {
			s = new Block(s.shift + BLOCK_SHIFT);
			s.contents[0] = contents;
			contents = s;
		}
		while (s.shift > 0) {
			final int i = index >> s.shift;
			index -= i << s.shift;
			if (s.contents[i] == null)
				s.contents[i] = new Block(s.shift - BLOCK_SHIFT);
			s = (Block) s.contents[i];
		}
		final Object old = s.contents[index];
		s.contents[index] = element;
		return (E) old;
	}

	@Override
	@SuppressWarnings("unchecked")
	public E get(int index) {
		Block s = contents;
		if (index >> s.shift >= 1024)
			return null;
		while (s != null && s.shift > 0) {
			final int i = index >> s.shift;
			index -= i << s.shift;
			s = (Block) s.contents[i];
		}
		return s != null ? (E) s.contents[index] : null;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void clear() {
		contents = new Block(0);
		size = 0;
	}

	protected static class Block {
		final Object[] contents = new Object[BLOCK_SIZE];

		final int shift;

		Block(int s) {
			shift = s;
		}
	}
}
