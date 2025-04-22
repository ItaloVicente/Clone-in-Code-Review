		List<RefSpec> specs = calculateRefSpecs(fetchAll
		if (!fetchAll) {
			calculateTagRefSpecs(specs);
		}
		if (!specs.isEmpty()) {
			config.setFetchRefSpecs(specs);
		}
