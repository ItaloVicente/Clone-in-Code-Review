			List<RevCommit> startPoints = new ArrayList<>();
			rw.setRevFilter(RevFilter.MERGE_BASE);
			startPoints.add(rightCommit);
			startPoints.add(headCommit);
			RevCommit ancestorCommit;
			try {
				rw.markStart(startPoints);
				ancestorCommit = rw.next();
			} catch (Exception e) {
				ancestorCommit = null;
