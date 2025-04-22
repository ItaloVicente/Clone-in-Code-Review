	public void testDerefTag() throws IOException {
		assertEquals("17768080a2318cd89bba4c8b87834401e2095703",db.resolve("refs/tags/B").name());
		assertEquals("d86a2aada2f5e7ccf6f11880bfb9ab404e8a8864",db.resolve("refs/tags/B^{commit}").name());
		assertEquals("032c063ce34486359e3ee3d4f9e5c225b9e1a4c2",db.resolve("refs/tags/B10th").name());
		assertEquals("d86a2aada2f5e7ccf6f11880bfb9ab404e8a8864",db.resolve("refs/tags/B10th^{commit}").name());
		assertEquals("d86a2aada2f5e7ccf6f11880bfb9ab404e8a8864",db.resolve("refs/tags/B10th^{}").name());
		assertEquals("d86a2aada2f5e7ccf6f11880bfb9ab404e8a8864",db.resolve("refs/tags/B10th^0").name());
		assertEquals("d86a2aada2f5e7ccf6f11880bfb9ab404e8a8864",db.resolve("refs/tags/B10th~0").name());
		assertEquals("0966a434eb1a025db6b71485ab63a3bfbea520b6",db.resolve("refs/tags/B10th^").name());
		assertEquals("2c349335b7f797072cf729c4f3bb0914ecb6dec9",db.resolve("refs/tags/B10th^^").name());
		assertEquals("0966a434eb1a025db6b71485ab63a3bfbea520b6",db.resolve("refs/tags/B10th^1").name());
		assertEquals("0966a434eb1a025db6b71485ab63a3bfbea520b6",db.resolve("refs/tags/B10th~1").name());
		assertEquals("2c349335b7f797072cf729c4f3bb0914ecb6dec9",db.resolve("refs/tags/B10th~2").name());
		assertEquals("2c349335b7f797072cf729c4f3bb0914ecb6dec9",db.resolve("refs/tags/B10th^~1").name());
