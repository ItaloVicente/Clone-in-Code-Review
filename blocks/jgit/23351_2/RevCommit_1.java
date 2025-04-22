				return null;
			c.flags |= carry;
		}
	}

	private static FIFORevQueue defer(RevCommit c) {
		FIFORevQueue q = new FIFORevQueue();
		q.add(c);
		return q;
	}

	private static FIFORevQueue defer(FIFORevQueue q
			RevCommit[] pList
		carryOneStep(q

		for (; i < pList.length; i++)
			carryOneStep(q
		return q;
	}

	private static void slowCarryFlags(FIFORevQueue q
		for (RevCommit c; (c = q.next()) != null;) {
			for (RevCommit p : c.parents)
				carryOneStep(q
		}
	}

	private static void carryOneStep(FIFORevQueue q
		if ((c.flags & carry) != carry) {
