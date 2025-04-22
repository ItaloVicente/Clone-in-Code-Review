			RevCommit newp;
			if (firstParent && i > 0) {
				newp = null;
			} else {
				newp = rewrite(oldp);
			}
