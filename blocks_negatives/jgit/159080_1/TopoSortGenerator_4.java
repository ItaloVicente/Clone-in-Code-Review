			if ((c.flags & TOPO_QUEUED) == 0) {
				for (RevCommit p : c.parents) {
					p.inDegree++;

					if (firstParent) {
						break;
					}
