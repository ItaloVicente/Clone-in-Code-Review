		try (RevWalk rw = new RevWalk(db)) {
			RevCommit rc = rw.parseCommit(headId);
			RevCommit parent = rw.parseCommit(rc.getParent(0));
			assertEquals("change file1 in topic\n\nThis is conflicting"
					.getFullMessage());
		}
