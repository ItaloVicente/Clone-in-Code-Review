			c.flags |= RevWalk.TEMP_MARK;
			tempQueue.add(c);
		}
		pending = new FIFORevQueue(firstParent);
		tempQueue.shareFreeList(pending);
		for (;;) {
			RevCommit c = tempQueue.next();
			if (c == null) {
				break;
			}
			c.flags &= ~RevWalk.TEMP_MARK;
