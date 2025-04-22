	public static String getSubmoduleRemoteUrl(final Repository parent
			final String url) throws IOException {
		if (!url.startsWith("./") && !url.startsWith("../"))
			return url;

		String remoteName = null;
		Ref ref = parent.getRef(Constants.HEAD);
		if (ref != null) {
			if (ref.isSymbolic())
				ref = ref.getLeaf();
			remoteName = parent.getConfig().getString(
					ConfigConstants.CONFIG_BRANCH_SECTION
					Repository.shortenRefName(ref.getName())
					ConfigConstants.CONFIG_KEY_REMOTE);
		}

		if (remoteName == null)
			remoteName = Constants.DEFAULT_REMOTE_NAME;

		String remoteUrl = parent.getConfig().getString(
				ConfigConstants.CONFIG_REMOTE_SECTION
				ConfigConstants.CONFIG_KEY_URL);

		if (remoteUrl == null) {
			remoteUrl = parent.getWorkTree().getAbsolutePath();
			if ('\\' == File.separatorChar)
				remoteUrl = remoteUrl.replace('\\'
		}

		if (remoteUrl.charAt(remoteUrl.length() - 1) == '/')
			remoteUrl = remoteUrl.substring(0

		char separator = '/';
		String submoduleUrl = url;
		while (submoduleUrl.length() > 0) {
			if (submoduleUrl.startsWith("./"))
				submoduleUrl = submoduleUrl.substring(2);
			else if (submoduleUrl.startsWith("../")) {
				int lastSeparator = remoteUrl.lastIndexOf('/');
				if (lastSeparator < 1) {
					lastSeparator = remoteUrl.lastIndexOf(':');
					separator = ':';
				}
				if (lastSeparator < 1)
					throw new IOException(MessageFormat.format(
							JGitText.get().submoduleParentRemoteUrlInvalid
							remoteUrl));
				remoteUrl = remoteUrl.substring(0
				submoduleUrl = submoduleUrl.substring(3);
			} else
				break;
		}
		return remoteUrl + separator + submoduleUrl;
	}

