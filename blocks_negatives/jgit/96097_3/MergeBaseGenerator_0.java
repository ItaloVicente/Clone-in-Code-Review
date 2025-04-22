			final RevCommit[] pList = c.parents;
			if (pList == null)
				return;
			final int n = pList.length;
			if (n == 0)
				return;

			for (int i = 1; i < n; i++) {
				final RevCommit p = pList[i];
				if (!carryOntoOne(p, carry))
					carryOntoHistory(p, carry);
