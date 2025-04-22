	private static String[][] getCustomDiffOrMergeTools(
			String sectionName4BaseTool, String[][] baseToolAttributes,
			String sectionName4AllTools, String[][] allToolAttributes,
			BaseToolManager manager) {
		List<String> toolsList = loadToolManager(sectionName4BaseTool,
				baseToolAttributes, sectionName4AllTools, allToolAttributes,
				manager);
		String[][] toolsArray = new String[toolsList.size()][2];
		for (int index = 0; index < toolsList.size(); index++) {
			toolsArray[index][0] = toolsList.get(index);
			toolsArray[index][1] = toolsList.get(index);
		}
		return toolsArray;
	}

	private static List<String> loadToolManager(String sectionName4BaseTool,
			String[][] baseToolAttributes, String sectionName4AllTools,
			String[][] allToolAttributes, BaseToolManager manager) {
