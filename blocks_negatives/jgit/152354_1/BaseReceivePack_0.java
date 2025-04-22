			try {
				SubmoduleValidator.assertValidGitModulesFile(
						new String(blob.getBytes(), UTF_8));
			} catch (LargeObjectException | SubmoduleValidationException e) {
				throw new IOException(e);
			}
