			if ((c.flags & UNINTERESTING) == 0) {
				for (RevCommit p : c.parents) {
					p.inDegree++;

					if (firstParent) {
						break;
					}
