	static void carryFlags(RevCommit c
		FIFORevQueue q = carryFlags1(c
		if (q != null)
			slowCarryFlags(q
	}

	private static FIFORevQueue carryFlags1(RevCommit c
		for(;;) {
			RevCommit[] pList = c.parents;
			if (pList == null || pList.length == 0)
				return null;
			if (pList.length != 1) {
				if (depth == STACK_DEPTH)
					return new FIFORevQueue();
				for (int i = 1; i < pList.length; i++) {
					RevCommit p = pList[i];
					if ((p.flags & carry) == carry)
						continue;
					p.flags |= carry;
					FIFORevQueue q = carryFlags1(c
					if (q != null)
						return defer(q
				}
