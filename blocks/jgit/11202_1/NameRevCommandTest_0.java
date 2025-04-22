		RevCommit c = c0;
		int mergeCost = 5;
		for (int i = 0; i < mergeCost; i++) {
			c = tr.commit().parent(c).create();
		}
		RevCommit c2 = tr.commit().parent(c).parent(c1).create();
		tr.update("master"
		assertOneResult("master^2~1"
