		try (Git git = new Git(db)) {
			git.commit().setMessage("initial commit").call();
			Ref tagRef = git.tag().setName("tag").call();
			assertEquals(1

			List<String> deleted = git.tagDelete()
					.setTags(Repository.shortenRefName(tagRef.getName())).call();
			assertEquals(1
			assertEquals(tagRef.getName()
			assertEquals(0
		}
