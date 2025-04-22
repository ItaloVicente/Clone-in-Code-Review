		TreeWalk walk = new TreeWalk(db);
		walk.addTree(c.getTree());
		walk.addTree(c.getTree());
		walk.addTree(c1.getTree());
		List<ThreeWayDiffEntry> result = ThreeWayDiffEntry.scan(walk);

		assertThat(result, notNullValue());
		assertThat(Integer.valueOf(result.size()), is(Integer.valueOf(1)));

		ThreeWayDiffEntry entry = result.get(0);
		assertThat(entry.getDirection(), is(Direction.INCOMING));
		assertThat(entry.getChangeType(), is(ChangeType.MODIFY));
		assertThat(entry.getPath(), is("a.txt"));
