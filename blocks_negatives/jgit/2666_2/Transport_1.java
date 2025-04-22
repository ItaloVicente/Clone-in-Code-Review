	public static boolean canHandleProtocol(final URIish remote, final FS fs) {
		if (TransportGitSsh.canHandle(remote))
			return true;

		else if (TransportHttp.canHandle(remote))
			return true;

		else if (TransportSftp.canHandle(remote))
			return true;

		else if (TransportGitAnon.canHandle(remote))
			return true;

		else if (TransportAmazonS3.canHandle(remote))
			return true;

		else if (TransportBundleFile.canHandle(remote, fs))
			return true;

		else if (TransportLocal.canHandle(remote, fs))
			return true;

		return false;
