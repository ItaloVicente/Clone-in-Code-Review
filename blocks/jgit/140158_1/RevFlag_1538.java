
package org.eclipse.jgit.revwalk;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.revwalk.filter.RevFilter;

public class RevCommitList<E extends RevCommit> extends RevObjectList<E> {
	private RevWalk walker;

	@Override
	public void clear() {
		super.clear();
		walker = null;
	}

	public void applyFlag(RevFilter matching
			throws MissingObjectException
			IOException {
		applyFlag(matching
	}

	public void applyFlag(final RevFilter matching
			int rangeBegin
			IncorrectObjectTypeException
		final RevWalk w = flag.getRevWalk();
		rangeEnd = Math.min(rangeEnd
		while (rangeBegin < rangeEnd) {
			int index = rangeBegin;
			Block s = contents;
			while (s.shift > 0) {
				final int i = index >> s.shift;
				index -= i << s.shift;
				s = (Block) s.contents[i];
			}

			while (rangeBegin++ < rangeEnd && index < BLOCK_SIZE) {
				final RevCommit c = (RevCommit) s.contents[index++];
				if (matching.include(w
					c.add(flag);
				else
					c.remove(flag);
			}
		}
	}

	public void clearFlag(RevFlag flag) {
		clearFlag(flag
	}

	public void clearFlag(final RevFlag flag
			final int rangeEnd) {
		try {
			applyFlag(RevFilter.NONE
		} catch (IOException e) {
		}
	}

	public int indexOf(RevFlag flag
		while (begin < size()) {
			int index = begin;
			Block s = contents;
			while (s.shift > 0) {
				final int i = index >> s.shift;
				index -= i << s.shift;
				s = (Block) s.contents[i];
			}

			while (begin++ < size() && index < BLOCK_SIZE) {
				final RevCommit c = (RevCommit) s.contents[index++];
				if (c.has(flag))
					return begin;
			}
		}
		return -1;
	}

	public int lastIndexOf(RevFlag flag
		begin = Math.min(begin
		while (begin >= 0) {
			int index = begin;
			Block s = contents;
			while (s.shift > 0) {
				final int i = index >> s.shift;
				index -= i << s.shift;
				s = (Block) s.contents[i];
			}

			while (begin-- >= 0 && index >= 0) {
				final RevCommit c = (RevCommit) s.contents[index--];
				if (c.has(flag))
					return begin;
			}
		}
		return -1;
	}

	public void source(RevWalk w) {
		walker = w;
	}

	public boolean isPending() {
		return walker != null;
	}

	@SuppressWarnings("unchecked")
	public void fillTo(int highMark) throws MissingObjectException
			IncorrectObjectTypeException
		if (walker == null || size > highMark)
			return;

		RevCommit c = walker.next();
		if (c == null) {
			walker = null;
			return;
		}
		enter(size
		add((E) c);

		while (size <= highMark) {
			int index = size;
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

			final Object[] dst = s.contents;
			while (size <= highMark && index < BLOCK_SIZE) {
				c = walker.next();
				if (c == null) {
					walker = null;
					return;
				}
				enter(size++
				dst[index++] = c;
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void fillTo(RevCommit commitToLoad
			throws MissingObjectException
			IOException {
		if (walker == null || commitToLoad == null
				|| (highMark > 0 && size > highMark))
			return;

		RevCommit c = walker.next();
		if (c == null) {
			walker = null;
			return;
		}
		enter(size
		add((E) c);

		while ((highMark == 0 || size <= highMark) && !c.equals(commitToLoad)) {
			int index = size;
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

			final Object[] dst = s.contents;
			while ((highMark == 0 || size <= highMark) && index < BLOCK_SIZE
					&& !c.equals(commitToLoad)) {
				c = walker.next();
				if (c == null) {
					walker = null;
					return;
				}
				enter(size++
				dst[index++] = c;
			}
		}
	}

	protected void enter(int index
	}
}
