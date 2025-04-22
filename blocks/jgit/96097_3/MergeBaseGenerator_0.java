			carryOntoHistoryInnerLoop(c
			if (stack == null) {
				break;
			}
			c = stack.c;
			carry = stack.carry;
			stack = stack.prev;
		}
	}

	private void carryOntoHistoryInnerLoop(RevCommit c
		for (;;) {
			RevCommit[] parents = c.parents;
			if (parents == null || parents.length == 0) {
				break;
