		} else {
			int commitCnt = 0;
			boolean putTagTargets = false;
			for (RevCommit cmit : commits) {
				if (!cmit.has(added)) {
					cmit.add(added);
					addObject(cmit
