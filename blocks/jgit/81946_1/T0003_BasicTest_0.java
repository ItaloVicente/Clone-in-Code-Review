	@Test(expected=IllegalArgumentException.class)
	public void test002_CreateBadTree() throws IOException {
		final TreeFormatter formatter = new TreeFormatter();
		formatter.append(""
				ObjectId.fromString("4b825dc642cb6eb9a060e54bf8d69288fbee4904"));
	}

