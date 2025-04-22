			try {
				SubmoduleValidator.assertValidGitModulesFile(
						new String(blob.getBytes()
			} catch (LargeObjectException | SubmoduleValidationException e) {
				throw new IOException(e.getMessage()
			}
