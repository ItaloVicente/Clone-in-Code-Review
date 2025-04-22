
		final String renameString = rc.getString("diff"
		if ("copy".equals(renameString) || "copies".equals(renameString))
			renameDetectionType = RenameDetectionType.COPY;
		else {
			final Boolean renameBoolean = StringUtils
					.toBooleanOrNull(renameString);
			renameDetectionType = Boolean.TRUE.equals(renameBoolean) ? RenameDetectionType.TRUE
					: RenameDetectionType.FALSE;
		}

