	private void checkSubmodules()
			throws IOException {
		ObjectDatabase odb = db.getObjectDatabase();
		if (objectChecker == null) {
			return;
		}
		for (GitmoduleEntry entry : objectChecker.getGitsubmodules()) {
			AnyObjectId blobId = entry.getBlobId();
			ObjectLoader blob = odb.open(blobId

			SubmoduleValidator.assertValidGitModulesFile(
					new String(blob.getBytes()
		}
	}

