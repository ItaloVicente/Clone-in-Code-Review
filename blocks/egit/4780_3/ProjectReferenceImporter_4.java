	private boolean repositoryAlreadyExistsForUrl(File repositoryPath,
			URIish gitUrl) {
		if (repositoryPath.exists()) {
			try {
				FileRepository existingRepository = new FileRepository(
						repositoryPath);
				String remoteUrl = existingRepository.getConfig().getString(
						ConfigConstants.CONFIG_REMOTE_SECTION,
						Constants.DEFAULT_REMOTE_NAME,
						ConfigConstants.CONFIG_KEY_URL);
				existingRepository.close();
				URIish existingUrl = new URIish(remoteUrl);
				if (existingUrl.equals(gitUrl)) {
					return true;
				}
			} catch (IOException e) {
				return false;
			} catch (URISyntaxException e) {
				return false;
			}
		}
		return false;
	}

