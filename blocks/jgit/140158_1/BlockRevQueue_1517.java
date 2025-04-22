
package org.eclipse.jgit.revwalk;

class BlockObjQueue {
	private BlockFreeList free;

	private Block head;

	private Block tail;

	BlockObjQueue() {
		free = new BlockFreeList();
	}

	void add(RevObject c) {
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

	RevObject next() {
		final Block b = head;
		if (b == null)
			return null;

		final RevObject c = b.pop();
		if (b.isEmpty()) {
			head = b.next;
			if (head == null)
				tail = null;
			free.freeBlock(b);
		}
		return c;
	}

	static final class BlockFreeList {
		private Block next;

		Block newBlock() {
			Block b = next;
			if (b == null)
				return new Block();
			next = b.next;
			b.clear();
			return b;
		}

		void freeBlock(Block b) {
			b.next = next;
			next = b;
		}
	}

	static final class Block {
		private static final int BLOCK_SIZE = 256;

		Block next;

		final RevObject[] objects = new RevObject[BLOCK_SIZE];

		int headIndex;

		int tailIndex;

		boolean isFull() {
			return tailIndex == BLOCK_SIZE;
		}

		boolean isEmpty() {
			return headIndex == tailIndex;
		}

		void add(RevObject c) {
			objects[tailIndex++] = c;
		}

		RevObject pop() {
			return objects[headIndex++];
		}

		void clear() {
			next = null;
			headIndex = 0;
			tailIndex = 0;
		}
	}
}
