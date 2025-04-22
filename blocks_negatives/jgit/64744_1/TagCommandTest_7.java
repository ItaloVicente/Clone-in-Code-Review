		Git git = new Git(db);
		git.commit().setMessage("initial commit").call();
		Ref tagRef = git.tag().setName("tag").call();
		assertEquals(1, db.getTags().size());

		List<String> deleted = git.tagDelete()
				.setTags(Repository.shortenRefName(tagRef.getName())).call();
		assertEquals(1, deleted.size());
		assertEquals(tagRef.getName(), deleted.get(0));
		assertEquals(0, db.getTags().size());
