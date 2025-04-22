		int posI = test.commit(i).lanePos(childPositions).parents(h)
				.getLanePos();
		test.commit(h).lanePos(posI).parents(f);
		test.commit(g).lanePos(childPositions).parents(a);
		test.commit(f).lanePos(posI).parents(e
		test.commit(e).lanePos(posI).parents(c);
		test.commit(d).lanePos(2).parents(b);
		test.commit(c).lanePos(posI).parents(b);
		test.commit(b).lanePos(posI).parents(a);
		test.commit(a).lanePos(0).parents();
