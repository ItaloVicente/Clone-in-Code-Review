	@Test()
	public void test002_CreateBadTree() throws IOException {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("Can't append a tree entry with zero-length name");
		final TreeFormatter formatter = new TreeFormatter();
		formatter.append(""
				ObjectId.fromString("4b825dc642cb6eb9a060e54bf8d69288fbee4904"));
	}

