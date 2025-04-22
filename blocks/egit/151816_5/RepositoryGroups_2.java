	private static boolean isGroupNameValid(String groupName) {
		return !(StringUtils.isEmptyOrNull(groupName)
				|| !groupName.equals(groupName.trim())
				|| Utils.isMultiLine(groupName));
	}

	private static void checkGroupName(String groupName) {
		if (!isGroupNameValid(groupName)) {
