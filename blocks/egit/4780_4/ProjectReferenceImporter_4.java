	private static boolean repositoryAlreadyExistsForUrl(File repositoryPath,
			URIish gitUrl) {
		if (repositoryPath.exists()) {
			try {
				FileRepository existingRepository = new FileRepository(
						repositoryPath);
				boolean exists = containsRemoteForUrl(
						existingRepository.getConfig(), gitUrl);
				existingRepository.close();
				return exists;
			} catch (IOException e) {
				return false;
			} catch (URISyntaxException e) {
				return false;
			}
		}
		return false;
	}

	private static boolean containsRemoteForUrl(Config config, URIish url) throws URISyntaxException {
		Set<String> remotes = config.getSubsections(ConfigConstants.CONFIG_REMOTE_SECTION);
		for (String remote : remotes) {
			String remoteUrl = config.getString(
					ConfigConstants.CONFIG_REMOTE_SECTION,
					remote,
					ConfigConstants.CONFIG_KEY_URL);
			URIish existingUrl = new URIish(remoteUrl);
			if (existingUrl.equals(url)) {
				return true;
			}
		}
		return false;
	}

