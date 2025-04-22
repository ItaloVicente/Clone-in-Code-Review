	private static boolean isGroupNameInvalid(String groupName) {
		return StringUtils.isEmptyOrNull(groupName)
				|| !groupName.equals(groupName.trim())
				|| Utils.isMultiLine(groupName);
	}

	private static void checkGroupName(String groupName) {
		if (isGroupNameInvalid(groupName)) {
