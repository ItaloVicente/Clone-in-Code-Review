	private void checkGitModules(ProgressMonitor pm
			throws IOException {
		for (GitmoduleEntry entry : objChecker.getGitsubmodules()) {
			AnyObjectId blobId = entry.getBlobId();
			ObjectLoader blob = objdb.open(blobId

			try {
				SubmoduleValidator.assertValidGitModulesFile(
						new String(blob.getBytes()
			} catch (IOException e) {
				CorruptObject co = new FsckError.CorruptObject(
						blobId.toObjectId()
						ObjectChecker.ErrorType.INVALID_GIT_MODULES);
				errors.getCorruptObjects().add(co);
			}
		}
	}

