		for (;;) {
			final RevCommit c = source.next();
			if (c == null)
				return null;

			boolean rewrote = false;
			final RevCommit[] pList = c.parents;
			final int nParents = pList.length;
			for (int i = 0; i < nParents; i++) {
				final RevCommit oldp = pList[i];
				final RevCommit newp = rewrite(oldp);
				if (oldp != newp) {
					pList[i] = newp;
					rewrote = true;
				}
