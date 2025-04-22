
	private static RenameDetectionType parseRenameDetectionType(
			final String renameString) {
		if (renameString == null)
			return RenameDetectionType.FALSE;
		else if (StringUtils.equalsIgnoreCase("copy"
				|| StringUtils.equalsIgnoreCase("copies"
			return RenameDetectionType.COPY;
		else {
			final Boolean renameBoolean = StringUtils
					.toBooleanOrNull(renameString);
			if (renameBoolean == null)
				throw new IllegalArgumentException(MessageFormat.format(
						JGitText.get().enumValueNotSupported2
						"renames"
			else if (renameBoolean.booleanValue())
				return RenameDetectionType.TRUE;
			else
				return RenameDetectionType.FALSE;
		}
	}
