		Set<Integer> childPositions = asSet(0, 1);
		CommitListAssert test = new CommitListAssert(pcl);
		test.commit(c).lanePos(childPositions).parents(a);
		test.commit(b).lanePos(childPositions).parents(a);
		test.commit(a).lanePos(0).parents();
		test.noMoreCommits();
