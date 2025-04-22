		Git git = new Git(db);
		RevCommit commit = git.commit().setMessage("initial commit").call();
		Ref tagRef = git.tag().setName("tag").call();
		assertEquals(commit.getId(), db.peel(tagRef).getPeeledObjectId());
		RevWalk walk = new RevWalk(db);
		assertEquals("tag", walk.parseTag(tagRef.getObjectId()).getTagName());
