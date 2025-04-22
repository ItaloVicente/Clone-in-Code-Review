			RevCommit ancestorCommit = null;
			boolean unknownAncestor = false;
			switch (repo.getRepositoryState()) {
			case CHERRY_PICKING:
			case REBASING_INTERACTIVE:
			case REBASING_MERGE:
				if (rightCommit.getParentCount() == 1) {
					try {
						ancestorCommit = rw
								.parseCommit(rightCommit.getParent(0));
					} catch (IOException e) {
						unknownAncestor = true;
					}
				} else {
					unknownAncestor = true;
				}
				break;
			default:
				List<RevCommit> startPoints = new ArrayList<>();
				rw.setRevFilter(RevFilter.MERGE_BASE);
				startPoints.add(rightCommit);
				startPoints.add(headCommit);
				try {
					rw.markStart(startPoints);
					ancestorCommit = rw.next();
				} catch (Exception e) {
				}
				break;
