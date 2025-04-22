	@Test
	public void shouldReturnChildren() throws Exception {
		FileRepository repo = lookupRepository(leftRepoFile);
		writeTrashFile(repo, "dir/a.txt", "trash");
		writeTrashFile(repo, "dir/b.txt", "trash");
		writeTrashFile(repo, "dir/c.txt", "trash");
		writeTrashFile(repo, "dir/d.txt", "trash");
		new Git(repo).add().addFilepattern("dir").call();

		Map<String, Change> changes = StagedChangeCache.build(repo);
		assertEquals(4, changes.size());

		GitModelCache cache = new GitModelCache(createModelRepository(), repo,
				changes);

		GitModelObject[] cacheChildren = cache.getChildren();
		assertEquals(1, cacheChildren.length);
		GitModelObject dir = cacheChildren[0];
		assertEquals("dir", dir.getName());

		GitModelObject[] dirChildren = dir.getChildren();
		assertEquals(4, dirChildren.length);
	}

