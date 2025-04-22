	@Test
	public void testSingleAsteriskAtEnd() throws IOException {

		is = new ByteArrayInputStream(attributeFileContent.getBytes());
		AttributesNode node = new AttributesNode();
		node.parse(is);
		assertAttribute("dir"
				asSet(new Attribute[]{}));
		assertAttribute("dir/"
				asSet(new Attribute[]{}));
		assertAttribute("dir/file.type1"
				asSet(A_SET_ATTR
		assertAttribute("dir/sub/"
				asSet(A_SET_ATTR
		assertAttribute("dir/sub/file.type1"
				asSet(new Attribute[]{}));
	}

	@Test
	public void testSingleAndDoubleAsteriskAtEnd() throws IOException {

		is = new ByteArrayInputStream(attributeFileContent.getBytes());
		AttributesNode node = new AttributesNode();
		node.parse(is);
		assertAttribute("dir"
				asSet(new Attribute[]{}));
		assertAttribute("dir/"
				asSet(new Attribute[]{}));
		assertAttribute("dir/file.type1"
				asSet(A_SET_ATTR
		assertAttribute("dir/sub/"
				asSet(A_SET_ATTR
		assertAttribute("dir/sub/file.type1"
				asSet(A_SET_ATTR
	}

