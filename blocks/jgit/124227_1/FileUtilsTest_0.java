
	@Test
	public void testRefreshAttributeCache() throws IOException {
		File f = new File(trash
		FileUtils.createNewFile(f);
		long lastmod = f.lastModified();

		assertTrue(FileUtils.refreshAttributeCache(trash.toPath()));

		assertEquals(lastmod
	}
