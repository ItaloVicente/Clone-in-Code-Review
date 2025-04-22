	private void closeLocal() {
		if (local != null) {
			local.close();
			local = null;
		}
	}

	private void doInit(final IProgressMonitor monitor)
			throws URISyntaxException, IOException {
		monitor.setTaskName(CoreText.CloneOperation_initializingRepository);

		local = new FileRepository(gitdir);
		local.create();

		if (refName != null && refName.startsWith(Constants.R_HEADS)) {
			final RefUpdate head = local.updateRef(Constants.HEAD);
			head.disableRefLog();
			head.link(refName);
		}

		FileBasedConfig config = local.getConfig();
		remoteConfig = new RemoteConfig(config, remoteName);
		remoteConfig.addURI(uri);

		final String dst = Constants.R_REMOTES + remoteConfig.getName();
		RefSpec wcrs = new RefSpec();
		wcrs = wcrs.setForceUpdate(true);
		wcrs = wcrs.setSourceDestination(Constants.R_HEADS
				+ "*", dst + "/*"); //$NON-NLS-1$ //$NON-NLS-2$

		if (allSelected) {
			remoteConfig.addFetchRefSpec(wcrs);
		} else {
			for (final Ref selectedRef : selectedBranches)
				if (wcrs.matchSource(selectedRef))
					remoteConfig.addFetchRefSpec(wcrs.expandFromSource(selectedRef));
		}

		config.setBoolean(
				ConfigConstants.CONFIG_CORE_SECTION, null, ConfigConstants.CONFIG_KEY_BARE, false);

		remoteConfig.update(config);

		if (refName != null && refName.startsWith(Constants.R_HEADS)) {
			String branchName = Repository.shortenRefName(refName);

			config.setString(ConfigConstants.CONFIG_BRANCH_SECTION, branchName, ConfigConstants.CONFIG_KEY_REMOTE, remoteName);
			config.setString(ConfigConstants.CONFIG_BRANCH_SECTION, branchName, ConfigConstants.CONFIG_KEY_MERGE, refName);
		}
		config.save();
	}

	private void doFetch(final IProgressMonitor monitor)
			throws NotSupportedException, TransportException {
		final Transport tn = Transport.open(local, remoteConfig);
		if (credentialsProvider != null)
			tn.setCredentialsProvider(credentialsProvider);
		tn.setTimeout(this.timeout);
		try {
			final EclipseGitProgressTransformer pm;
			pm = new EclipseGitProgressTransformer(monitor);
			fetchResult = tn.fetch(pm, null);
		} finally {
			tn.close();
		}
	}

	private void doCheckout(final IProgressMonitor monitor) throws IOException {
		if (refName == null)
			return;
		final Ref head = fetchResult.getAdvertisedRef(refName);
		if (head == null || head.getObjectId() == null)
			return;

		final RevWalk rw = new RevWalk(local);
		final RevCommit mapCommit;
		try {
			mapCommit = rw.parseCommit(head.getObjectId());
		} finally {
			rw.release();
		}

		final RefUpdate u;

		boolean detached = !head.getName().startsWith(Constants.R_HEADS);
		u = local.updateRef(Constants.HEAD, detached);
		u.setNewObjectId(mapCommit.getId());
		u.forceUpdate();

		monitor.setTaskName(CoreText.CloneOperation_checkingOutFiles);
		DirCacheCheckout dirCacheCheckout = new DirCacheCheckout(
				local, null, local.lockDirCache(), mapCommit.getTree());
		dirCacheCheckout.setFailOnConflict(true);
		boolean result = dirCacheCheckout.checkout();
		if (!result)
		monitor.setTaskName(CoreText.CloneOperation_writingIndex);
	}
