	public static boolean canHandleProtocol(final URIish remote) {
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

		else if (TransportBundleFile.canHandle(remote))
			return true;

		else if (TransportLocal.canHandle(remote))
			return true;

		return false;
	}

