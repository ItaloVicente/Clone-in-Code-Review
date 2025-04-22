		RevCommit old = new RevWalk(db).parseCommit(ppid);
		RefUpdate updateRef2 = db.updateRef("refs/heads/master");
		updateRef2.setExpectedOldObjectId(old);
		updateRef2.setNewObjectId(pid);
		Result update2 = updateRef2.update();
		assertEquals(Result.FAST_FORWARD, update2);
		assertEquals(pid, db.resolve("refs/heads/master"));
