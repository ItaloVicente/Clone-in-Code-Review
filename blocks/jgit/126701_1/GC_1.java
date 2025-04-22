	private boolean canBeSafelyDeleted(Path path
		try {
			return Files.getLastModifiedTime(path).toInstant().isBefore(threshold);
		}
		catch (IOException e) {
			LOG.warn(MessageFormat.format(
					JGitText.get().cannotAccessLastModifiedForSafeDeletion
					path)
			return false;
		}
	}

