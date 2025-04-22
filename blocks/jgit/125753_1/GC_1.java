	private boolean canBeSafelyDeleted(Path path
		try {
			return Files.getLastModifiedTime(path).toInstant().isBefore(threshold);
		}
		catch (IOException e) {
			LOG.warn("Unable to access lastModifiedTime of {} and thus cannot be 100% sure that it can be deleted"
			return false;
		}
	}

