	@Test
	public void annotatedTagsNoResult() throws Exception {
		RevCommit c = tr.commit().create();
		tr.update("refs/heads/master"
		tr.update("refs/tags/tag1"
		tr.update("refs/tags/tag2"
		Map<ObjectId
				.add(c)
				.addAnnotatedTags()
				.call();
		assertTrue(result.toString()
	}

