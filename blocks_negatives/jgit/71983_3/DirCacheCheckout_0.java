		OutputStream channel = EolStreamTypeUtil.wrapOutputStream(
				new FileOutputStream(tmpFile), nonNullEolStreamType);
		if (checkoutMetadata.smudgeFilterCommand != null) {
			ProcessBuilder filterProcessBuilder = fs.runInShell(
					checkoutMetadata.smudgeFilterCommand, new String[0]);
			filterProcessBuilder.directory(repo.getWorkTree());
			filterProcessBuilder.environment().put(Constants.GIT_DIR_KEY,
					repo.getDirectory().getAbsolutePath());
			ExecutionResult result;
			int rc;
			try {
				result = fs.execute(filterProcessBuilder, ol.openStream());
				rc = result.getRc();
				if (rc == 0) {
					result.getStdout().writeTo(channel,
							NullProgressMonitor.INSTANCE);
