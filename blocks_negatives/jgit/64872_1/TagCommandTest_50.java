		Git git = new Git(db);
		git.commit().setMessage("initial commit").call();
		Ref tagRef = git.tag().setName("tag").call();
		assertEquals(1, db.getTags().size());

		List<String> deleted = git.tagDelete().setTags(tagRef.getName())
				.call();
		assertEquals(1, deleted.size());
		assertEquals(tagRef.getName(), deleted.get(0));
		assertEquals(0, db.getTags().size());

		Ref tagRef1 = git.tag().setName("tag1").call();
		Ref tagRef2 = git.tag().setName("tag2").call();
		assertEquals(2, db.getTags().size());
		deleted = git.tagDelete().setTags(tagRef1.getName(), tagRef2.getName())
				.call();
		assertEquals(2, deleted.size());
		assertEquals(0, db.getTags().size());
