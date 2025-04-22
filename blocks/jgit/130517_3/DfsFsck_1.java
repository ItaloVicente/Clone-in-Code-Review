	private void checkGitModules(ProgressMonitor pm
			throws IOException {
		pm.beginTask(JGitText.get().validatingGitModules
				objChecker.getGitsubmodules().size());
		for (GitmoduleEntry entry : objChecker.getGitsubmodules()) {
			AnyObjectId blobId = entry.getBlobId();
			ObjectLoader blob = objdb.open(blobId

			try {
				SubmoduleValidator.assertValidGitModulesFile(
						new String(blob.getBytes()
			} catch (SubmoduleValidationException e) {
				CorruptObject co = new FsckError.CorruptObject(
						blobId.toObjectId()
						exceptionToFsckError(e.getSubdmoduleError()));
				errors.getCorruptObjects().add(co);
			}
			pm.update(1);
		}
		pm.endTask();
	}

	private ErrorType exceptionToFsckError(SubmoduleError submoduleError) {
		switch (submoduleError) {
		case GITMODULES_NAME:
			return ObjectChecker.ErrorType.GITMODULES_NAME;
		case GITMODULES_LARGE:
			return ObjectChecker.ErrorType.GITMODULES_LARGE;
		case GITMODULES_PARSE:
			return ObjectChecker.ErrorType.GITMODULES_PARSE;
		case GITMODULES_PATH:
			return ObjectChecker.ErrorType.GITMODULES_PATH;
		case GITMODULES_URL:
			return ObjectChecker.ErrorType.GITMODULES_URL;
		}

		return ErrorType.GITMODULES_BLOB;
	}

