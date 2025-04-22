		try (TreeWalk walk = new TreeWalk(db)) {
			walk.addTree(new EmptyTreeIterator());
			walk.addTree(c.getTree());
			walk.addTree(new EmptyTreeIterator());
			List<ThreeWayDiffEntry> result = ThreeWayDiffEntry.scan(walk);
			assertThat(result, notNullValue());
			assertThat(Integer.valueOf(result.size()), is(Integer.valueOf(1)));

			ThreeWayDiffEntry entry = result.get(0);
			assertThat(entry.getDirection(), is(Direction.CONFLICTING));
			assertThat(entry.getChangeType(), is(ChangeType.MODIFY));
		}
