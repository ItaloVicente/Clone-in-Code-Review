
package org.eclipse.jgit.revwalk;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;

public class FIFORevQueue extends BlockRevQueue {
	private Block head;

	private Block tail;

	public FIFORevQueue() {
		super();
	}

	FIFORevQueue(Generator s) throws MissingObjectException
			IncorrectObjectTypeException
		super(s);
	}

	@Override
	public void add(RevCommit c) {
		Block b = tail;
		if (b == null) {
			b = free.newBlock();
			b.add(c);
			head = b;
			tail = b;
			return;
		} else if (b.isFull()) {
			b = free.newBlock();
			tail.next = b;
			tail = b;
		}
		b.add(c);
	}

	public void unpop(RevCommit c) {
		Block b = head;
		if (b == null) {
			b = free.newBlock();
			b.resetToMiddle();
			b.add(c);
			head = b;
			tail = b;
			return;
		} else if (b.canUnpop()) {
			b.unpop(c);
			return;
		}

		b = free.newBlock();
		b.resetToEnd();
		b.unpop(c);
		b.next = head;
		head = b;
	}

	@Override
	public RevCommit next() {
		final Block b = head;
		if (b == null)
			return null;

		final RevCommit c = b.pop();
		if (b.isEmpty()) {
			head = b.next;
			if (head == null)
				tail = null;
			free.freeBlock(b);
		}
		return c;
	}

	@Override
	public void clear() {
		head = null;
		tail = null;
		free.clear();
	}

	@Override
	boolean everbodyHasFlag(int f) {
		for (Block b = head; b != null; b = b.next) {
			for (int i = b.headIndex; i < b.tailIndex; i++)
				if ((b.commits[i].flags & f) == 0)
					return false;
		}
		return true;
	}

	@Override
	boolean anybodyHasFlag(int f) {
		for (Block b = head; b != null; b = b.next) {
			for (int i = b.headIndex; i < b.tailIndex; i++)
				if ((b.commits[i].flags & f) != 0)
					return true;
		}
		return false;
	}

	void removeFlag(int f) {
		final int not_f = ~f;
		for (Block b = head; b != null; b = b.next) {
			for (int i = b.headIndex; i < b.tailIndex; i++)
				b.commits[i].flags &= not_f;
		}
	}

	@Override
	public String toString() {
		final StringBuilder s = new StringBuilder();
		for (Block q = head; q != null; q = q.next) {
			for (int i = q.headIndex; i < q.tailIndex; i++)
				describe(s
		}
		return s.toString();
	}
}
