		if (gitdir == null)
			gitdir = new File(localName, Constants.DOT_GIT).getAbsolutePath();

		dst = new FileRepositoryBuilder().setGitDir(new File(gitdir)).build();
		dst.create();
		final StoredConfig dstcfg = dst.getConfig();
		dstcfg.setBoolean("core", null, "bare", false); //$NON-NLS-1$ //$NON-NLS-2$
		dstcfg.save();
		db = dst;

		outw.print(MessageFormat.format(
				CLIText.get().initializedEmptyGitRepositoryIn, gitdir));
		outw.println();
		outw.flush();

		saveRemote(uri);
		final FetchResult r = runFetch();

		if (!noCheckout) {
			final Ref checkoutRef;
			if (branch == null)
				checkoutRef = guessHEAD(r);
			else {
				checkoutRef = r.getAdvertisedRef(Constants.R_HEADS + branch);
				if (checkoutRef == null)
					throw die(MessageFormat.format(
							CLIText.get().noSuchRemoteRef, branch));
			}
			doCheckout(checkoutRef);
		}
	}

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
