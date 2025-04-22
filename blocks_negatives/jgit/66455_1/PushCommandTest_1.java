
		RefSpec spec = new RefSpec("refs/heads/master:refs/heads/x");
		git1.push().setRemote("test").setRefSpecs(spec)
				.call();

		assertEquals(commit.getId(),
				db2.resolve(commit.getId().getName() + "^{commit}"));
		assertEquals(tagRef.getObjectId(),
				db2.resolve(tagRef.getObjectId().getName()));
