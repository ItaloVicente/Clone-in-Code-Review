	private void checkSubmodules(PackParser packParser)
			throws IOException {
		ObjectDatabase odb = db.getObjectDatabase();
		for (GitmoduleEntry entry : packParser.getGitmodulesEntries()) {
			AnyObjectId blobId = entry.getBlobId();
			ObjectLoader blob = odb.open(blobId

			SubmoduleValidator.assertValidGitModulesFile(
					new String(blob.getBytes()
		}
	}

