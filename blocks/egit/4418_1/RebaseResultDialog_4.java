	private static String getProblemDescription(CheckResult checkResult) {
		StringBuffer result = new StringBuffer();
		if (checkResult.containsNonWorkspaceFiles())
			result.append(UIText.RebaseResultDialog_notInWorkspaceMessage);
		if (checkResult.containsNotSharedResources()) {
			if (result.length() > 0)
				result.append('\n');
			result.append(UIText.RebaseResultDialog_notSharedMessage);
		}
		return result.toString();
	}

