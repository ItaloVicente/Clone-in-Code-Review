		}

		List<RepoProject> filteredProjects;
		try {
			ManifestParser parser = new ManifestParser(includedReader
					manifestPath
			parser.read(inputStream);
			filteredProjects = parser.getFilteredProjects();
		} catch (IOException e) {
			throw new ManifestErrorException(e);
