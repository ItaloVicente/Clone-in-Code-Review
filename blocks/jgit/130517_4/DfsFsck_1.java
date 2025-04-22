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
						e.getSubmoduleError());
				errors.getCorruptObjects().add(co);
			}
			pm.update(1);
		}
		pm.endTask();
	}

