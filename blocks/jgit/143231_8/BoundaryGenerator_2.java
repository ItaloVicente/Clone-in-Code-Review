				for (int i = 0; i < c.parents.length; i++) {
					if (firstParent && i > 0) {
						break;
					}
					RevCommit p = c.parents[i];
					if ((p.flags & UNINTERESTING) != 0) {
