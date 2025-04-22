
	protected void assertFileAddition(Map<String, Change> result, String path, String fileName) {
		commonFileAsserts(result, path, fileName);
		assertThat(result.get(path).getKind(), is(RIGHT | ADDITION));
		assertThat(result.get(path).getObjectId(), not(ZERO_ID));
		assertNull(result.get(path).getRemoteObjectId());
	}

	protected void assertFileDeletion(Map<String, Change> result, String path, String fileName) {
		commonFileAsserts(result, path, fileName);
		assertThat(result.get(path).getKind(), is(RIGHT | DELETION));
		assertThat(result.get(path).getRemoteObjectId(), not(ZERO_ID));
		assertNull(result.get(path).getObjectId());
	}

	protected void assertFileChange(Map<String, Change> result, String path, String fileName) {
		commonFileAsserts(result, path, fileName);
		assertThat(result.get(path).getKind(), is(RIGHT | CHANGE));
		assertThat(result.get(path).getObjectId(), not(ZERO_ID));
		assertThat(result.get(path).getRemoteObjectId(), not(ZERO_ID));
	}

	private void commonFileAsserts(Map<String, Change> result, String path,
			String fileName) {
		assertTrue(result.containsKey(path));
		assertThat(result.get(path).getName(), is(fileName));
	}
