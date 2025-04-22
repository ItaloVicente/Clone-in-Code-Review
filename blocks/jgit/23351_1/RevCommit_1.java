				return null;
			c.flags |= carry;
		}
	}

	private static FIFORevQueue defer(FIFORevQueue q
			RevCommit[] pList
		carryOneStep(q

		q.add(pList[i]);

		for (i++; i < pList.length; i++)
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
