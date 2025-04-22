
		List<String> recentWorkspacesList = Arrays.asList(launchData.getRecentWorkspaces()).stream()
				.filter(s -> s != null && !s.isEmpty()).collect(Collectors.toList());
		List<Entry<String, String>> sortedList = uniqueWorkspaceNames.entrySet().stream().sorted((e1, e2) -> Integer
				.compare(recentWorkspacesList.indexOf(e1.getValue()), recentWorkspacesList.indexOf(e2.getValue())))
				.collect(Collectors.toList());

		for (Entry<String, String> uniqueWorkspaceEntry : sortedList) {
