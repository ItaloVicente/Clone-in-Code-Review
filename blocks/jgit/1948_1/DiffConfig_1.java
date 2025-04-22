
		final String renameString = rc.getString("diff"
		if ("true".equals(renameString))
			renameDetectionType = RenameDetectionType.TRUE;
		else if ("copy".equals(renameString) || "copies".equals(renameString))
			renameDetectionType = RenameDetectionType.COPY;
		else
			renameDetectionType = RenameDetectionType.FALSE;

