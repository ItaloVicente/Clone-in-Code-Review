		if (!gitModulesAttributes.isEmpty()) {
			String gitModulesLine = gitModulesAttributes
				.entrySet()
				.stream()
				.map(e -> e.getValue() == null ? e.getKey()
				.collect(Collectors.joining(" "
			attributes.append(gitModulesLine);
		}

		String gitAttrContents = attributes.toString();
		if (!gitAttrContents.isEmpty()) {
