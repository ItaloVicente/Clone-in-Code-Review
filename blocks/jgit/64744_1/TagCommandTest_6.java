		try (Git git = new Git(db)) {
			git.commit().setMessage("initial commit").call();
			Ref tagRef = git.tag().setName("tag").call();
			assertEquals(1

			List<String> deleted = git.tagDelete().setTags(tagRef.getName())
					.call();
			assertEquals(1
			assertEquals(tagRef.getName()
			assertEquals(0

			Ref tagRef1 = git.tag().setName("tag1").call();
			Ref tagRef2 = git.tag().setName("tag2").call();
			assertEquals(2
			deleted = git.tagDelete().setTags(tagRef1.getName()
					.call();
			assertEquals(2
			assertEquals(0
		}
