			final RevCommit newp = rewrite(oldp);
			if (firstParent) {
				if (newp == null) {
					c.parents = RevCommit.NO_PARENTS;
				} else {
					c.parents = new RevCommit[] { newp };
				}
