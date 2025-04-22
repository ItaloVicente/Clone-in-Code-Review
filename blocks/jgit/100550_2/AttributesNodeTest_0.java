	@Test
	public void testDoubleAsteriskAtEnd() throws IOException {

		is = new ByteArrayInputStream(attributeFileContent.getBytes());
		AttributesNode node = new AttributesNode();
		node.parse(is);
		assertAttribute("dir"
				asSet(new Attribute[]{}));
		assertAttribute("dir/file.type1"
				asSet(A_SET_ATTR
	}

