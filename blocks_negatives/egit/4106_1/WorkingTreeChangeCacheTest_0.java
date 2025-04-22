	private void assertFileAddition(Map<String, Change> result, String path, String fileName) {
		commonFileAsserts(result, path, fileName);
		assertThat(result.get(path).getKind(), is(LEFT | ADDITION));
		assertThat(result.get(path).getObjectId(), not(ZERO_ID));
		assertNull(result.get(path).getRemoteObjectId());
	}

	private void assertFileDeletion(Map<String, Change> result, String path, String fileName) {
		commonFileAsserts(result, path, fileName);
		assertThat(result.get(path).getKind(), is(LEFT | DELETION));
		assertThat(result.get(path).getRemoteObjectId(), not(ZERO_ID));
		assertNull(result.get(path).getObjectId());
	}

	private void assertFileChange(Map<String, Change> result, String path, String fileName) {
		commonFileAsserts(result, path, fileName);
		assertThat(result.get(path).getKind(), is(LEFT | CHANGE));
		assertThat(result.get(path).getObjectId(), not(ZERO_ID));
		assertThat(result.get(path).getRemoteObjectId(), not(ZERO_ID));
	}

	private void commonFileAsserts(Map<String, Change> result, String path,
			String fileName) {
		assertTrue(result.containsKey(path));
		assertThat(result.get(path).getName(), is(fileName));
	}

