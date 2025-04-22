		return true;
	}

	boolean queryOverwriteFile(IFile targetFile, int policy) {
		if (policy != POLICY_FORCE_OVERWRITE) {
			if (this.overwriteState == OVERWRITE_NOT_SET
					&& !queryOverwrite(targetFile.getFullPath())) {
