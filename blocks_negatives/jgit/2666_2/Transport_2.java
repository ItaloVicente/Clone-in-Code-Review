	public static Transport open(final Repository local, final URIish remote)
			throws NotSupportedException {
		if (TransportGitSsh.canHandle(remote))
			return new TransportGitSsh(local, remote);

		else if (TransportHttp.canHandle(remote))
			return new TransportHttp(local, remote);

		else if (TransportSftp.canHandle(remote))
			return new TransportSftp(local, remote);

		else if (TransportGitAnon.canHandle(remote))
			return new TransportGitAnon(local, remote);

		else if (TransportAmazonS3.canHandle(remote))
			return new TransportAmazonS3(local, remote);

		else if (TransportBundleFile.canHandle(remote, local.getFS()))
			return new TransportBundleFile(local, remote);
