			throw new IOException(
					MessageFormat.format(
							JGitText.get().invalidGitModules,
							e));
		} catch (SubmoduleValidationException e) {
			throw new IOException(e.getMessage(), e);
