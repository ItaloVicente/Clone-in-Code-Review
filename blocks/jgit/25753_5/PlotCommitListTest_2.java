		int posG = test.commit(g).lanePos(childPositions).parents(f)
				.getLanePos();
		test.commit(f).lanePos(posG).parents(a);

		test.commit(e).lanePos(childPositions).parents(a);
		test.commit(d).lanePos(childPositions).parents(a);
		test.commit(c).lanePos(childPositions).parents(a);
		test.commit(b).lanePos(childPositions).parents(a);
		test.commit(a).lanePos(0).parents();
