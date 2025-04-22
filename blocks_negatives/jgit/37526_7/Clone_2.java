	private void saveRemote(final URIish uri) throws URISyntaxException,
			IOException {
		final StoredConfig dstcfg = dst.getConfig();
		final RemoteConfig rc = new RemoteConfig(dstcfg, remoteName);
		rc.addURI(uri);
		rc.addFetchRefSpec(new RefSpec().setForceUpdate(true)
				.setSourceDestination(Constants.R_HEADS + "*", //$NON-NLS-1$
		rc.update(dstcfg);
		dstcfg.save();
	}

	private FetchResult runFetch() throws URISyntaxException, IOException {
		final Transport tn = Transport.open(db, remoteName);
		final FetchResult r;
		try {
			tn.setTagOpt(TagOpt.FETCH_TAGS);
			r = tn.fetch(new TextProgressMonitor(), null);
		} finally {
			tn.close();
		}
		showFetchResult(r);
		return r;
	}

	private static Ref guessHEAD(final FetchResult result) {
		final Ref idHEAD = result.getAdvertisedRef(Constants.HEAD);
		Ref head = null;
		for (final Ref r : result.getAdvertisedRefs()) {
			final String n = r.getName();
			if (!n.startsWith(Constants.R_HEADS))
				continue;
			if (idHEAD == null || head != null)
				continue;
			if (r.getObjectId().equals(idHEAD.getObjectId()))
				head = r;
		}
		if (idHEAD != null && head == null)
			head = idHEAD;
		return head;
	}

	private void doCheckout(final Ref branch) throws IOException {
