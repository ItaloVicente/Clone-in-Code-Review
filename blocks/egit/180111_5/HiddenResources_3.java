	public Repository getRepository(URI uri) {
		if (!EgitFileSystem.SCHEME.equals(uri.getScheme())) {
			return null;
		}
		try {
			return UriComponents.parse(uri).getRepository();
		} catch (URISyntaxException e) {
			return null;
		}
	}

	public String getGitPath(URI uri) {
		if (!EgitFileSystem.SCHEME.equals(uri.getScheme())) {
			return null;
		}
		try {
			return UriComponents.parse(uri).getGitPath();
		} catch (URISyntaxException e) {
			return null;
		}
	}

	public String getGitPath(URI uri, Repository repository) {
		if (!EgitFileSystem.SCHEME.equals(uri.getScheme())) {
			return null;
		}
		try {
			UriComponents parsed = UriComponents.parse(uri);
			if (parsed.getRepoDir().equals(repository.getDirectory())) {
				return parsed.getGitPath();
			}
		} catch (URISyntaxException e) {
		}
		return null;
	}

