		test.commit(i).lanePos(1).parents(h);
		test.commit(h).lanePos(1).parents(f);
		test.commit(g).lanePos(0).parents(a);
		test.commit(f).lanePos(1).parents(e, d);
		test.commit(e).lanePos(0).parents(c);
		test.commit(d).lanePos(1).parents(b);
		test.commit(c).lanePos(0).parents(b);
		test.commit(b).lanePos(1).parents(a);
		test.commit(a).lanePos(2).parents();
