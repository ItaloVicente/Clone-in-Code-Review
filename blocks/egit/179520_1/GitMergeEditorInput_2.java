			RevCommit ancestorCommit = null;
			boolean unknownCherryPickParent = false;
			if (RepositoryState.CHERRY_PICKING
					.equals(repo.getRepositoryState())) {
				if (rightCommit.getParentCount() == 1) {
					try {
						ancestorCommit = rw
								.parseCommit(rightCommit.getParent(0));
					} catch (IOException e) {
						unknownCherryPickParent = true;
					}
				} else {
					unknownCherryPickParent = true;
				}
			} else {
				List<RevCommit> startPoints = new ArrayList<>();
				rw.setRevFilter(RevFilter.MERGE_BASE);
				startPoints.add(rightCommit);
				startPoints.add(headCommit);
				try {
					rw.markStart(startPoints);
					ancestorCommit = rw.next();
				} catch (Exception e) {
				}
