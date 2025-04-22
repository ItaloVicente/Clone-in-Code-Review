		test.commit(g).lanePos(0).parents(f);
		test.commit(f).lanePos(0).parents(a);
		test.commit(e).lanePos(0).parents(a);
		test.commit(d).lanePos(0).parents(a);
		test.commit(c).lanePos(0).parents(a);
		test.commit(b).lanePos(0).parents(a);
		test.commit(a).lanePos(1).parents();
